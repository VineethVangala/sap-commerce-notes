# Custom Setup class for Essential and Project Data import by Code

Refer : [SAP Commerce 123 | Essential and Project Data by Code](https://help.sap.com/docs/SAP_COMMERCE_CLOUD_PUBLIC_CLOUD/d97b2ab46fde43a78640036ebf68e106/02b4f9d4ba0740eabec09508a293e25e.html)

### Summary

|                                        |                                                 |
| -------------------------------------- | ----------------------------------------------- |
| extends                                |                                                 |
| implements                             |                                                 |
| Setup class annotation                 | @SystemSetup(extension = "concerttours")        |
| Essential Data setup method annotation | @SystemSetup(type = SystemSetup.Type.ESSENTIAL) |
| Project Data setup method annotation   | @SystemSetup(type = SystemSetup.Type.PROJECT)   |

### Example

1. Create a setup class called ConcerttoursCustomSetup.java in your <HYBRIS_HOME_DIR>/hybris/bin/custom/concerttours/src/concerttours/setup folder.

```java
package concerttours.setup;
import de.hybris.platform.core.initialization.SystemSetup;
import de.hybris.platform.servicelayer.impex.ImportConfig;
import de.hybris.platform.servicelayer.impex.ImportResult;
import de.hybris.platform.servicelayer.impex.ImportService;
import de.hybris.platform.servicelayer.impex.impl.StreamBasedImpExResource;
import java.io.InputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SystemSetup(extension = "concerttours")
public class ConcerttoursCustomSetup
{
    private static final Logger LOG = LoggerFactory.getLogger(ConcerttoursCustomSetup.class);
    private ImportService importService;
    public ImportService getImportService()
    {
        return importService;
    }
    public void setImportService(final ImportService importService)
    {
        this.importService = importService;
    }
    @SystemSetup(type = SystemSetup.Type.ESSENTIAL)
    public boolean putInMyEssentialData()
    {
        LOG.info("Starting custom essential data loading for the Concerttours extension");
        LOG.info("Custom essential data loading for the Concerttours extension completed.");
        return true;
    }
    @SystemSetup(type = SystemSetup.Type.PROJECT)
    public boolean addMyProjectData()
    {
        LOG.info("Starting custom project data loading for the Concerttours extension");
        impexImport("/impex/concerttours-bands.impex");
        impexImport("/impex/concerttours-yBandTour.impex");
        LOG.info("Custom project data loading for the Concerttours extension completed.");
        return true;
    }
    protected boolean impexImport(final String filename)
    {
        final String message = "Concerttours impexing [" + filename + "]...";
        try (final InputStream resourceAsStream = getClass().getResourceAsStream(filename))
        {
            LOG.info(message);
            final ImportConfig importConfig = new ImportConfig();
            importConfig.setScript(new StreamBasedImpExResource(resourceAsStream, "UTF-8"));
            importConfig.setLegacyMode(Boolean.FALSE);
            final ImportResult importResult = getImportService().importData(importConfig);
            if (importResult.isError())
            {
                LOG.error(message + " FAILED");
                return false;
            }
        }
        catch (final Exception e)
        {
            LOG.error(message + " FAILED", e);
            return false;
        }
        return true;
    }
}
```

2. Register this class as a Spring bean in <HYBRIS_HOME_DIR>/hybris/bin/custom/concerttours/resources/concerttours-spring.xml

```xml
<bean id="ConcerttoursCustomSetup" class="concerttours.setup.ConcerttoursCustomSetup" >
  <property name="importService" ref="importService"/>
</bean>
```
