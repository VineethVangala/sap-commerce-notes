//This groovy script is useful to trigger a catalog sync cronjob to sync a lost of products.

import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.cmsfacades.data.SyncRequestData;

//Below query only syncs approved products. Update the query as per requirement
query = "SELECT DISTINCT({p:pk}) from {Product as p join Catalogversion as cv on {cv:pk}={p:catalogversion} left join catalog as c on {c:pk}={cv:catalog} left join ArticleApprovalStatus as s on {s:pk}={p:approvalstatus}} where {s:code}='Approved' and {c:id}='customProductCatalog' and {cv:version}='Staged'"
FlexibleSearchQuery fsq = new FlexibleSearchQuery(query);
flexibleSearchService = spring.getBean("flexibleSearchService");
result = flexibleSearchService.search(fsq);
itemsList = result.getResult();

SyncRequestData syncRequestData = new SyncRequestData();
syncRequestData.setCatalogId("customProductCatalog");
syncRequestData.setSourceVersionId("Staged");
syncRequestData.setTargetVersionId("Online");

modelService = spring.getBean("modelService");
defaultItemSynchtonizationService = spring.getBean("defaultItemSynchtonizationService");
syncConfig = spring.getBean("syncConfig");

println "Stared Sync"
defaultItemSynchtonizationService.performItemSynchronization(syncRequestData,itemsList,syncConfig);
println "Ended Sync"