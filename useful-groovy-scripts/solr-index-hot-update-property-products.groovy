//This groovy script is useful to index a list of properties for a list of products.

import de.hybris.platform.solrfacetsearch.indexer.IndexerService;
import de.hybris.platform.solrfacetsearch.config.FacetSearchConfig;
import de.hybris.platform.solrfacetsearch.config.IndexedType;
import de.hybris.platform.solrfacetsearch.config.IndexedProperty;
import de.hybris.platform.core.PK;
import java.util.stream.*;

faetSearchConfigService = spring.getBean("facetSearchConfigService");
FacetSearchConfig fsc = facetSearchConfigService.getConfiguration("custom_config_name");
IndexedType indexedType = fsc.getIndexConfig().getIndexedTypes().get("indexed_name_prefix")

// Specify the list of properties to be updated
Collection<IndexedProperty> indexedProperties = Collections.singletonList(indexedType.getIndexedProperties().get("property_name"));

//Alternatively, you can use flexible search to fetch PKs list.
List<Long> pks = Arrays.asList(pk1,pk2,pk3);
List<PK> pkList = new ArrayList<>();
for(Long pk : pks){
    pkList.add(new PK(pk));
}

indexerService = spring.getBean("indexerService");
try{
    indexerService.updatePartialTypeIndex(fsc,indexedType,indexedProperties,pkList);
}
catch(Exception e){
    println e.getMessage();
}