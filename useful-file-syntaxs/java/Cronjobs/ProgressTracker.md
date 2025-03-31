# Cronjob Progress Tracker

### Syntax

```java
final CronJobProgressTracker tracker = new CronJobProgressTracker(modelService.getSource(cronJob));
tracker.setProgress(Double.valueOf(i));
tracker.close();
```

### Example

```java
package de.hybris.extendedcronjob;

import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.cronjob.jalo.CronJobProgressTracker;
import de.hybris.platform.cronjob.model.CronJobModel;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;

public class SampleProgressingJobPerformable extends AbstractJobPerformable<CronJobModel>
{
    @Override
    public PerformResult perform(final CronJobModel cronJob)
    {
        final CronJobProgressTracker tracker = new CronJobProgressTracker(modelService.getSource(cronJob)); // <- new tracker instance is created
        for (int i = 1; i < 100; i++)
        {
            try
            {
                tracker.setProgress(Double.valueOf(i)); // <- set progress
                Thread.sleep(Double.valueOf(100 + (1000 * Math.random())).intValue());
            }
            catch (final InterruptedException e)
            {
                return new PerformResult(CronJobResult.FAILURE, CronJobStatus.ABORTED);
            }
        }
        tracker.close(); // <- save last progress to the database
        return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
    }
}
```

Register the cronjob in spring.

```xml
<bean id="sampleProgressingJobPerformable" class="de.hybris.extendedcronjob.SampleProgressingJobPerformable" parent="abstractJobPerformable"/>
```
