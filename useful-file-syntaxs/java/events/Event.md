# Event

Refer: [SAP Commerce 123 | Event](https://help.sap.com/docs/SAP_COMMERCE_CLOUD_PUBLIC_CLOUD/d97b2ab46fde43a78640036ebf68e106/bec6c3a13b1b4ec59cbd4150ebd4df17.html)

### Summary

|                 |                                          |
| --------------- | ---------------------------------------- |
| **extends**     | AbstractEvent                            |
| implements      | ClusterAwareEvent/ TransactionAwareEvent |
| override method | **onEvent** (final EVENT_TYPE event)     |

### Example

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
