# Populator

### Summary

|                     |                                                                       |
| ------------------- | --------------------------------------------------------------------- |
| **extends**         | Populator<SOURCE_MODEL, TARGET_DTO>                                   |
| implements          |                                                                       |
| **override method** | void **populate**(final SOURCE_MODEL source, final TARGET_DTO target) |

### Example

```java
public class ProductUrlPopulator implements Populator<ProductModel, ProductData>
{
	private UrlResolver<ProductModel> productModelUrlResolver;

	protected UrlResolver<ProductModel> getProductModelUrlResolver()
	{
		return productModelUrlResolver;
	}

	@Required
	public void setProductModelUrlResolver(final UrlResolver<ProductModel> productModelUrlResolver)
	{
		this.productModelUrlResolver = productModelUrlResolver;
	}

	@Override
	public void populate(final ProductModel source, final ProductData target)
	{
		Assert.notNull(source, "Parameter source cannot be null.");
		Assert.notNull(target, "Parameter target cannot be null.");

		target.setCode(source.getCode());
		target.setName(source.getName());
		target.setUrl(getProductModelUrlResolver().resolve(source));
	}
}
```
