# Interceptor

Refer: [SAP Commerce 123 | Interceptor](https://help.sap.com/docs/SAP_COMMERCE_CLOUD_PUBLIC_CLOUD/d97b2ab46fde43a78640036ebf68e106/bec6c3a13b1b4ec59cbd4150ebd4df17.html)

### Summary

|                 |                                                                                                                                                                                                                                                                       |
| --------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| extends         |                                                                                                                                                                                                                                                                       |
| **implements**  | LoadInterceptor <br> InitDefaultsInterceptor <br> PrepareInterceptor <br> ValidateInterceptor <br> RemoveInterceptor                                                                                                                                                  |
| override method | onLoad(Object model, InterceptorContext ctx) <br> onInitDefaults(Object model, InterceptorContext ctx) <br> onPrepare(Object model, InterceptorContext ctx) <br> onValidate(Object model, InterceptorContext ctx) <br> onRemove(Object model, InterceptorContext ctx) |

### Example

1. Create an interceptor for the custom event.

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
        //ctx.getDirtyAttributes()
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

2. Register the interceptor and the event listener.

```xml
<bean id="bandAlbumSalesInterceptor" class="concerttours.interceptors.BandAlbumSalesInterceptor" />
<bean id="BandInterceptorMapping" class="de.hybris.platform.servicelayer.interceptor.impl.InterceptorMapping">
  <property name="interceptor" ref="bandAlbumSalesInterceptor" />
  <property name="typeCode" value="Band" />
</bean>
```
