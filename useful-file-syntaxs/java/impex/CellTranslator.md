## Impex Cell Translator Syntax

### Summary

|                  |                                                                                            |
| ---------------- | ------------------------------------------------------------------------------------------ |
| extends          | AbstractValueTranslator                                                                    |
| implements       | AbstractValueTranslator                                                                    |
| override methods | importValue(final String valueExpr, final Item toItem) <br>exportValue(final Object value) |

### Syntax

```java
public class customTranslator extends AbstractValueTranslator
{

	@Override
	public Object importValue(final String valueExpr, final Item toItem) throws JaloInvalidParameterException
	{
		clearStatus();
		Object result = null;
		//Write custom logic here
		return result;
	}

	@Override
	public String exportValue(final Object value) throws JaloInvalidParameterException
	{
		return value == null ? "" : value.toString();
	}
}
```

### Example

```
INSERT_UPDATE Media			 ; $catalogversion[unique=true]							   ; code[unique=true]						  ; realfilename			 ; @media[translator=de.hybris.platform.impex.jalo.media.MediaDataTranslator]			 ; mime[default='text/plain']
							 ;														   ; siteMapMedia							  ; sitemap.vm				 ; jar:de.hybris.platform.acceleratorservices.constants.AcceleratorServicesConstants&/acceleratorservices/test/sitemap.vm;
```
