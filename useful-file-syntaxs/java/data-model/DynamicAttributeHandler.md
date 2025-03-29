# Dynamic Attributes

Refer: [SAP Commerce 123 | Dynamic Attributes](https://help.sap.com/docs/SAP_COMMERCE_CLOUD_PUBLIC_CLOUD/d97b2ab46fde43a78640036ebf68e106/d9b3c27bdede40839799918d432990d8.html)

### Summary

|                   |                                                          |
| ----------------- | -------------------------------------------------------- |
| **extends**       | AbstractDynamicAttributeHandler<RETURN_TYPE, MODEL_TYPE> |
| implements        |                                                          |
| override method 1 | **get** (final MODEL_TYPE model)                         |
| override method 2 | **set** (final MODEL_TYPE model,final Object value)      |

### Example

1. Define a new dynamic attribute handler within the Concert itemtype in the <HYBRIS_HOME_DIR>/hybris/bin/custom/concerttours/resources/concerttours-items.xml file.

```xml
    <attribute qualifier="daysUntil" type="java.lang.Long">
        <persistence type="dynamic" attributeHandler="concertDaysUntilAttributeHandler" />
        <modifiers read="true" write="false" />
    </attribute>
```

2.Define an attribute handler in <HYBRIS_HOME_DIR>/hybris/bin/custom/concerttours/src/concerttours/attributehandlers/ConcertDaysUntilAttributeHandler.java

```java
package concerttours.attributehandlers;
import de.hybris.platform.servicelayer.model.attribute.AbstractDynamicAttributeHandler;
import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import org.springframework.stereotype.Component;
import concerttours.model.ConcertModel;

@Component
public class ConcertDaysUntilAttributeHandler extends AbstractDynamicAttributeHandler<Long, ConcertModel>
{
    @Override
    public Long get(final ConcertModel model)
    {
        if (model.getDate() == null)
        {
            return null;
        }
        final ZonedDateTime concertDate = model.getDate().toInstant().atZone(ZoneId.systemDefault());
        final ZonedDateTime now = ZonedDateTime.now();
        if (concertDate.isBefore(now))
        {
            return Long.valueOf(0L);
        }
        final Duration duration = Duration.between(now, concertDate);
        return Long.valueOf(duration.toDays());
    }

    @Override
	 public void set(final ConcertModel model, final Long value)
	 {
	  // Ignore
	 }
}
```

3. Register the attribute handler in Spring in <HYBRIS_HOME_DIR>/hybris/bin/custom/concerttours/resources/concerttours-spring.xml

```xml
<bean id="concertDaysUntilAttributeHandler" class="concerttours.attributehandlers.ConcertDaysUntilAttributeHandler"/>
```
