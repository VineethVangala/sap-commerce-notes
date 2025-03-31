# Abortable Cron Jobs

### Summary

|                  |                                                                                      |
| ---------------- | ------------------------------------------------------------------------------------ |
| **extends**      | AbstractJobPerformable<CronJobModel>                                                 |
| implements       |                                                                                      |
| override methods | PerformResult **perform**(final CronJobModel cronJob) <br> boolean **isAbortable**() |

### Example

1. Create the job class.

```java
public class MyJobPerformable extends AbstractJobPerformable<HelloWorldCronJobModel>
{
	private L10NService l10nService;

	@Required
	public void setL10nService(final L10NService l10nService)
	{
		this.l10nService = l10nService;
	}

	@Override
	public PerformResult perform(final HelloWorldCronJobModel cronJob)
	{

        for (int i = 0; i <= 1000; i++)
	{
		try
		{
			System.out.println("Greeting '" + l10nService.getLocalizedString(cronJob.getMessage())
					+ "' from MyJobPerformable  for " + i + " times.");
			Thread.sleep(5000);

			if (clearAbortRequestedIfNeeded(cronJob))
			{
				System.out.println("The job is aborted.");
				return new PerformResult(CronJobResult.ERROR, CronJobStatus.ABORTED);
			}
		}
		catch (final InterruptedException e)
		{
                        Thread.currentThread().interrupt();
		}
	 }

	return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
	}

	@Override
	public boolean isAbortable()
	{
		return true;
	}

}
```

2. Register the job in spring.

```xml
<bean id="myJobPerformable" class="de.hybris.cronjobtutorial.MyJobPerformable"
parent="abstractJobPerformable" >
  <property name="l10nService" ref="l10nService" />
  <property name="abortable" value="true"/>
</bean>
```

3. To abort the job, you can call this method.

```
cronJobService.requestAbortCronJob(cronJob);
```
