## Impex Cell Decorator Syntax

### Summary

|                  |                                                                             |
| ---------------- | --------------------------------------------------------------------------- |
| **extends**      | AbstractImpExCSVCellDecorator implements CSVCellDecorator                   |
| implements       | CSVCellDecorator                                                            |
| override methods | String **decorate**(final int position, final Map<Integer, String> srcLine) |

### Syntax

```java
public class CustomDecorator extends AbstractImpExCSVCellDecorator
{

	@Override
	public String decorate(final int position, final Map<Integer, String> srcLine)
	{
		String result = srcLine.get(Integer.valueOf(position));
		// wite custom logic
		return name;
	}

}
```

### Example

```
UPDATE ContentSlot;$contentCV[unique=true];uid[unique=true];cmsComponents(uid,$contentCV)[cellDecorator=de.hybris.platform.chineselogisticaddon.decorator.ChineseLogisticCellDecorator]
;;BodyContent-orderdetail;AccountOrderDetailsOverviewComponent,AccountOrderDetailsDeliveryTimeSlotComponent

```
