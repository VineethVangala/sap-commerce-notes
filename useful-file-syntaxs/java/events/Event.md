# Event

Refer: [SAP Commerce 123 | Event](https://help.sap.com/docs/SAP_COMMERCE_CLOUD_PUBLIC_CLOUD/d97b2ab46fde43a78640036ebf68e106/bec6c3a13b1b4ec59cbd4150ebd4df17.html)

### Summary

|                 |                                          |
| --------------- | ---------------------------------------- |
| **extends**     | AbstractEvent                            |
| implements      | ClusterAwareEvent/ TransactionAwareEvent |
| override method |                                          |

### Example

1. Create a new event class

```java
package concerttours.events;
import de.hybris.platform.servicelayer.event.events.AbstractEvent;

public class BandAlbumSalesEvent extends AbstractEvent
{
    private final String code;
    private final String name;
    private final Long sales;
    public BandAlbumSalesEvent(final String code, final String name, final Long sales)
    {
        super();
        this.code = code;
        this.name = name;
        this.sales = sales;
    }
    public String getCode()
    {
        return code;
    }
    public String getName()
    {
        return name;
    }
    public Long getSales()
    {
        return sales;
    }
    @Override
    public String toString()
    {
        return this.name;
    }
}
```

2. Create an interceptor for the custom event.

```java
package concerttours.interceptors;
import static de.hybris.platform.servicelayer.model.ModelContextUtils.getItemModelContext;
import de.hybris.platform.servicelayer.event.EventService;
import de.hybris.platform.servicelayer.interceptor.InterceptorContext;
import de.hybris.platform.servicelayer.interceptor.InterceptorException;
import de.hybris.platform.servicelayer.interceptor.PrepareInterceptor;
import de.hybris.platform.servicelayer.interceptor.ValidateInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import concerttours.events.BandAlbumSalesEvent;
import concerttours.model.BandModel;

public class BandAlbumSalesInterceptor implements ValidateInterceptor, PrepareInterceptor
{
    private static final long BIG_SALES = 50000L;
    private static final long NEGATIVE_SALES = 0L;
    @Autowired
    private EventService eventService;
    @Override
    public void onValidate(final Object model, final InterceptorContext ctx) throws InterceptorException
    {
        if (model instanceof BandModel)
        {
            final BandModel band = (BandModel) model;
            final Long sales = band.getAlbumSales();
            if (sales != null && sales.longValue() < NEGATIVE_SALES)
            {
                throw new InterceptorException("Album sales must be positive");
            }
        }
    }
    @Override
    public void onPrepare(final Object model, final InterceptorContext ctx) throws InterceptorException
    {
        if (model instanceof BandModel)
        {
            final BandModel band = (BandModel) model;
            if (hasBecomeBig(band, ctx))
            {
                eventService.publishEvent(new BandAlbumSalesEvent(band.getCode(), band.getName(), band.getAlbumSales()));
            }
        }
    }
    private boolean hasBecomeBig(final BandModel band, final InterceptorContext ctx)
    {
        final Long sales = band.getAlbumSales();
        if (sales != null && sales.longValue() >= BIG_SALES)
        {
            if (ctx.isNew(band))
            {
                return true;
            }
            else
            {
                final Long oldValue = getItemModelContext(band).getOriginalValue(BandModel.ALBUMSALES);
                if (oldValue == null || oldValue.intValue() < BIG_SALES)
                {
                    return true;
                }
            }
        }
        return false;
    }
}
```

3. Create an event listener.

```java
package concerttours.events;
import de.hybris.platform.servicelayer.event.impl.AbstractEventListener;
import de.hybris.platform.servicelayer.model.ModelService;
import java.util.Date;
import concerttours.model.NewsModel;

public class BandAlbumSalesEventListener extends AbstractEventListener<BandAlbumSalesEvent>
{
    private static final String BAND_SALES_HEADLINE = "%s album sales exceed 50000";
    private static final String BAND_SALES_CONTENT = "%s album sales reported as %d";
    private ModelService modelService;
    public ModelService getModelService()
    {
        return modelService;
    }
    public void setModelService(final ModelService modelService)
    {
        this.modelService = modelService;
    }
    @Override
    protected void onEvent(final BandAlbumSalesEvent event)
    {
        if (event != null)
        {
            final String headline = String.format(BAND_SALES_HEADLINE, event.getName());
            final String content = String.format(BAND_SALES_CONTENT, event.getName(), event.getSales());
            final NewsModel news = modelService.create(NewsModel.class);
            news.setDate(new Date());
            news.setHeadline(headline);
            news.setContent(content);
            modelService.save(news);
        }
    }
}
```

4. Register the interceptor and the event listener

```xml
<bean id="bandAlbumSalesInterceptor" class="concerttours.interceptors.BandAlbumSalesInterceptor" />

<bean id="BandInterceptorMapping" class="de.hybris.platform.servicelayer.interceptor.impl.InterceptorMapping">
  <property name="interceptor" ref="bandAlbumSalesInterceptor" />
  <property name="typeCode" value="Band" />
</bean>

<bean id="bandAlbumSalesEventListener" class="concerttours.events.BandAlbumSalesEventListener" parent="abstractEventListener" >
  <property name="modelService" ref="modelService" />
</bean>
```
