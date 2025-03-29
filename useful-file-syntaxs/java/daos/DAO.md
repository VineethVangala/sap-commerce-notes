# DAO layer

https://help.sap.com/docs/SAP_COMMERCE_CLOUD_PUBLIC_CLOUD/d97b2ab46fde43a78640036ebf68e106/ec3d78f39a1444a0b7d9db6ca503b8df.html

![MVC Code Flow](image-code-flow.png)

### Example

1. Create a new BandDAO DAO interface under the concerttours extension's src/concerttours/daos folder.

```java
package concerttours.daos;
import java.util.List;
import concerttours.model.BandModel;

public interface BandDAO
{
    List<BandModel> findBands();
    List<BandModel> findBandsByCode(String code);
}
```

2. Create the DefaultBandDAO DAO implementation under the src/concerttours/daos/impl folder of the concerttours extension

```java
package concerttours.daos.impl;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import concerttours.daos.BandDAO;
import concerttours.model.BandModel;

@Component(value = "bandDAO")
public class DefaultBandDAO implements BandDAO
{
    @Autowired
    private FlexibleSearchService flexibleSearchService;

    @Override
    public List<BandModel> findBands()
    {
        final String queryString = "SELECT {p:" + BandModel.PK + "} FROM {" + BandModel._TYPECODE + " AS p} ";
        final FlexibleSearchQuery query = new FlexibleSearchQuery(queryString);
        //query.setStart(start);
        //query.setCount(count);
        return flexibleSearchService.<BandModel> search(query).getResult();
    }


    @Override
    public List<BandModel> findBandsByCode(final String code)
    {
        final String queryString = "SELECT {p:" + BandModel.PK + "} FROM {" + BandModel._TYPECODE + " AS p} WHERE {p:" + BandModel.CODE + "}=?code ";
        final FlexibleSearchQuery query = new FlexibleSearchQuery(queryString);
        query.addQueryParameter("code", code);
        return flexibleSearchService.<BandModel> search(query).getResult();
    }
}
```

3. Configure Spring to register the DAO as Spring beans in <HYBRIS_HOME_DIR>/hybris/bin/custom/concerttours/resources/concerttours-spring.xml

```xml
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:context="http://www.springframework.org/schema/context"
    xsi:schemaLocation="http://www.springframework.org/schema/beans
           http://www.springframework.org/schema/beans/spring-beans-3.1.xsd
           http://www.springframework.org/schema/context
           http://www.springframework.org/schema/context/spring-context-3.1.xsd">

    <context:component-scan base-package="concerttours"/>

</beans>
```
