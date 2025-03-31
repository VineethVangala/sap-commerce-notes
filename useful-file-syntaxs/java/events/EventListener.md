# Event Listener

Refer: [SAP Commerce 123 | Event Listener](https://help.sap.com/docs/SAP_COMMERCE_CLOUD_PUBLIC_CLOUD/d97b2ab46fde43a78640036ebf68e106/ab945b81e3474a14bba99169ccfd3d7b.html)

### Summary

|                 |                                      |
| --------------- | ------------------------------------ |
| **extends**     | AbstractEventListener<EVENT_TYPE>    |
| implements      |                                      |
| override method | **onEvent** (final EVENT_TYPE event) |

### Example

1. Create a listener class that listens for new Band events under the src folder of the concerttours extension.

```java
package concerttours.events;
import de.hybris.platform.servicelayer.event.events.AfterItemCreationEvent;
import de.hybris.platform.servicelayer.event.impl.AbstractEventListener;
import de.hybris.platform.servicelayer.model.ModelService;
import java.util.Date;
import concerttours.model.BandModel;
import concerttours.model.NewsModel;

public class NewBandEventListener extends AbstractEventListener<AfterItemCreationEvent>
{
    private static final String NEW_BAND_HEADLINE = "New band, %s";
    private static final String NEW_BAND_CONTENT = "There is a new band in town called, %s. Tour news to be announced soon.";
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
    protected void onEvent(final AfterItemCreationEvent event)
    {
        if (event != null && event.getSource() != null)
        {
            final Object object = modelService.get(event.getSource());
            if (object instanceof BandModel)
            {
                final BandModel band = (BandModel) object;
                final String headline = String.format(NEW_BAND_HEADLINE, band.getName());
                final String content = String.format(NEW_BAND_CONTENT, band.getName());
                final NewsModel news = modelService.create(NewsModel.class);
                news.setDate(new Date());
                news.setHeadline(headline);
                news.setContent(content);
                modelService.save(news);
            }
        }
    }
}
```

2. Register the new listener in Spring by adding the concerttourEventListener Spring bean definition to the concerttours-spring.xml file in the resources folder of the concerttours extension.

```xml
<bean id="concerttourEventListener" class="concerttours.events.NewBandEventListener" parent="abstractEventListener">
  <property name="modelService" ref="modelService" />
</bean>
```
