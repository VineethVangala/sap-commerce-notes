# Service layer

Refer https://help.sap.com/docs/SAP_COMMERCE_CLOUD_PUBLIC_CLOUD/d97b2ab46fde43a78640036ebf68e106/ec3d78f39a1444a0b7d9db6ca503b8df.html

### Example

1. Create the BandService band service interface under the src/concerttours/service folder of the concerttours extension.

```java
package concerttours.service;
import java.util.List;
import concerttours.model.BandModel;

public interface BandService
{

   List<BandModel> getBands();

   BandModel getBandForCode(String code);
}
```

2. Create the DefaultBandService band service implementation under the src/concerttours/service/impl folder of the concerttours extension.

```java
package concerttours.service.impl;
import de.hybris.platform.servicelayer.exceptions.AmbiguousIdentifierException;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import java.util.List;
import org.springframework.beans.factory.annotation.Required;
import concerttours.daos.BandDAO;
import concerttours.model.BandModel;
import concerttours.service.BandService;

public class DefaultBandService implements BandService
{
    private BandDAO bandDAO;

    @Override
    public List<BandModel> getBands()
    {
        return bandDAO.findBands();
    }

    @Override
    public BandModel getBandForCode(final String code) throws AmbiguousIdentifierException, UnknownIdentifierException
    {
        final List<BandModel> result = bandDAO.findBandsByCode(code);
        if (result.isEmpty())
        {
            throw new UnknownIdentifierException("Band with code '" + code + "' not found!");
        }
        else if (result.size() > 1)
        {
            throw new AmbiguousIdentifierException("Band code '" + code + "' is not unique, " + result.size() + " bands found!");
        }
        return result.get(0);
    }
    @Required
    public void setBandDAO(final BandDAO bandDAO)
    {
        this.bandDAO = bandDAO;
    }
}
```

3. Configure Spring to register the DAO and DefaultBandService as Spring beans in <HYBRIS_HOME_DIR>/hybris/bin/custom/concerttours/resources/concerttours-spring.xml

```xml
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:context="http://www.springframework.org/schema/context"
    xsi:schemaLocation="http://www.springframework.org/schema/beans
           http://www.springframework.org/schema/beans/spring-beans-3.1.xsd
           http://www.springframework.org/schema/context
           http://www.springframework.org/schema/context/spring-context-3.1.xsd">

    <context:component-scan base-package="concerttours"/>

    <alias name = "defaultBandService" alias = "bandService" />
    <bean id = "defaultBandService" class = "concerttours.service.impl.DefaultBandService" >
    <property name = "bandDAO" ref = "bandDAO" />
    </bean>
</beans>
```
