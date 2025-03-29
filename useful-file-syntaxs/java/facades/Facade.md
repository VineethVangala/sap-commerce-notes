# Facade layer

Refer https://help.sap.com/docs/SAP_COMMERCE_CLOUD_PUBLIC_CLOUD/d97b2ab46fde43a78640036ebf68e106/3984c622893b425eb323600d6128526f.html

#### Example

1. Declare the data transfer objects in concerttours-beans.xml.

```xml
<bean class = "concerttours.data.TourSummaryData">
        <description >Data object for a tour summary which has no equivalent on the type system</description>
       <property name = "id" type = "String" />
        <property name = "tourName" type = "String" />
        <property name = "numberOfConcerts" type = "String" />
 </bean>
 <bean class = "concerttours.data.BandData" >
        <description>Data object representing a Band</description>
        <property name = "id" type = "String" />
        <property name = "name" type = "String" />
        <property name = "description" type = "String" />
        <property name = "albumsSold" type = "Long" />
        <property name = "genres" type="java.util.List&lt;String&gt;"/>
        <property name = "tours" type="java.util.List&lt;concerttours.data.TourSummaryData&gt;"/>
 </bean>
<bean class = "concerttours.data.ConcertSummaryData">
        <description >Data object for a concert summary</description>
        <property name = "id" type = "String" />
        <property name = "date" type = "java.util.Date" />
        <property name = "venue" type = "String" />
        <property name = "type" type = "String" />
 </bean>
 <bean class = "concerttours.data.TourData" >
        <description>Data object representing a tour</description>
        <property name = "id" type = "String" />
        <property name = "tourName" type = "String" />
        <property name = "description" type = "String" />
        <property name = "concerts" type="java.util.List&lt;concerttours.data.ConcertSummaryData&gt;"/>
   </bean>
```

2. Create the new DTOs from this XML definition by calling ant clean all from the <HYBRIS_HOME_DIR>/hybris/bin/platform directory
3. Create the Band and Tour facade interfaces in <HYBRIS_HOME_DIR>/hybris/bin/custom/concerttours/src/concerttours/facades/

```java
package concerttours.facades;
import java.util.List;
import concerttours.data.BandData;

public interface BandFacade
{
    BandData getBand(String name);
    List<BandData> getBands();
}
```

```java

package concerttours.facades;
import concerttours.data.TourData;

public interface TourFacade
{
    TourData getTourDetails(final String tourId);
}
```

4. Create the Band and Tour facade implementations in <HYBRIS_HOME_DIR>/hybris/bin/custom/concerttours/src/concerttours/facades/impl/.

```java
package concerttours.facades.impl;
import de.hybris.platform.core.model.product.ProductModel;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Required;
import concerttours.data.BandData;
import concerttours.data.TourSummaryData;
import concerttours.enums.MusicType;
import concerttours.facades.BandFacade;
import concerttours.model.BandModel;
import concerttours.service.BandService;
import java.util.Locale;

public class DefaultBandFacade implements BandFacade
{
    private BandService bandService;
    @Override
    public List<BandData> getBands()
    {
        final List<BandModel> bandModels = bandService.getBands();
        final List<BandData> bandFacadeData = new ArrayList<>();
        for (final BandModel sm : bandModels)
        {
            final BandData sfd = new BandData();
            sfd.setId(sm.getCode());
            sfd.setName(sm.getName());
            sfd.setDescription(sm.getHistory());
            sfd.setAlbumsSold(sm.getAlbumSales());
            bandFacadeData.add(sfd);
        }
        return bandFacadeData;
    }
    @Override
    public BandData getBand(final String name)
    {
        if (name == null)
        {
            throw new IllegalArgumentException("Band name cannot be null");
        }
        final BandModel band = bandService.getBandForCode(name);
        if (band == null)
        {
            return null;
        }

        // Create a list of genres
        final List<String> genres = new ArrayList<>();
        if (band.getTypes() != null)
        {
            for (final MusicType musicType : band.getTypes())
            {
                genres.add(musicType.getCode());
            }
        }
        // Create a list of TourSummaryData from the matches
        final List<TourSummaryData> tourHistory = new ArrayList<>();
        if (band.getTours() != null)
        {
            for (final ProductModel tour : band.getTours())
            {
                final TourSummaryData summary = new TourSummaryData();
                summary.setId(tour.getCode());
                summary.setTourName(tour.getName(Locale.ENGLISH));
                // making the big assumption that all variants are concerts and ignore product catalogs
                summary.setNumberOfConcerts(Integer.toString(tour.getVariants().size()));
                tourHistory.add(summary);
            }
        }
        // Now we can create the BandData transfer object
        final BandData bandData = new BandData();
        bandData.setId(band.getCode());
        bandData.setName(band.getName());
        bandData.setAlbumsSold(band.getAlbumSales());
        bandData.setDescription(band.getHistory());
        bandData.setGenres(genres);
        bandData.setTours(tourHistory);
        return bandData;
    }
    @Required
    public void setBandService(final BandService bandService)
    {
        this.bandService = bandService;
    }
}
```

```java
package concerttours.facades.impl;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.variants.model.VariantProductModel;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Required;
import concerttours.data.ConcertSummaryData;
import concerttours.data.TourData;
import concerttours.enums.ConcertType;
import concerttours.facades.TourFacade;
import concerttours.model.ConcertModel;

public class DefaultTourFacade implements TourFacade
{
    private ProductService productService;
    @Override
    public TourData getTourDetails(final String tourId)
    {
        if (tourId == null)
        {
            throw new IllegalArgumentException("Tour id cannot be null");
        }
        final ProductModel product = productService.getProductForCode(tourId);
        if (product == null)
        {
            return null;
        }
        // Create a list of ConcertSummaryData from the matches
        final List<ConcertSummaryData> concerts = new ArrayList<>();
        if (product.getVariants() != null)
        {
            for (final VariantProductModel variant : product.getVariants())
            {
                if (variant instanceof ConcertModel)
                {
                    final ConcertModel concert = (ConcertModel) variant;
                    final ConcertSummaryData summary = new ConcertSummaryData();
                    summary.setId(concert.getCode());
                    summary.setDate(concert.getDate());
                    summary.setVenue(concert.getVenue());
                    summary.setType(concert.getConcertType() == ConcertType.OPENAIR ? "Outdoors" : "Indoors");
                    concerts.add(summary);
                }
            }
        }
        // Now we can create the TourData transfer object
        final TourData tourData = new TourData();
        tourData.setId(product.getCode());
        tourData.setTourName(product.getName());
        tourData.setDescription(product.getDescription());
        tourData.setConcerts(concerts);
        return tourData;
    }
    @Required
    public void setProductService(final ProductService productService)
    {
        this.productService = productService;
    }
}
```

5. Declare the facades in concerttours-spring.xml so that they are wired in to your extension.

```xml
<alias name = "defaultBandFacade" alias = "bandFacade" />
<bean id = "defaultBandFacade" class ="concerttours.facades.impl.DefaultBandFacade" >
    <property name = "bandService" ref = "bandService" />
</bean>

<alias name = "defaultTourFacade" alias = "tourFacade" />
<bean id = "defaultTourFacade" class ="concerttours.facades.impl.DefaultTourFacade" >
    <property name = "productService" ref = "productService" />
</bean>
```
