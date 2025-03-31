### Example

1. Create new itemtype

```xml
<items 	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
			xsi:noNamespaceSchemaLocation="items.xsd">

	<itemtypes>
		<itemtype generate="true"
		   code="HelloWorldCronJob"
		   jaloclass="de.hybris.cronjobtutorial.jalo.HelloWorldCronJob"
		   extends="CronJob"
		   autocreate="true">
		</itemtype>
	</itemtypes>
</items>
```

2. Creatae new ServicelayerJob

```java
public class MyJobPerformable extends AbstractJobPerformable<HelloWorldCronJobModel>
{
	private static final Logger LOG = Logger.getLogger(MyJobPerformable.class.getName());

	@Override
	public PerformResult perform(final HelloWorldCronJobModel cronJobModel)
	{
		LOG.info("**********************************");
		LOG.info("Greeting from MyJobPerformable!!!");
		LOG.info("**********************************");

		return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
	}
}
```

3. Register the cronjob in spring

```xml
<bean id="myJobPerformable" class="de.hybris.cronjobtutorial.MyJobPerformable"
	parent="abstractJobPerformable"/>

```
