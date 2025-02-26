/* This groovy script 
1. uses flexibleSearchService to fetch an item, 
2. modifies item and 
3. uses modelService to save the change. */

import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
flexibleSearchService = spring.getBean("flexibleSearchService")
mimeService = spring.getBean("mimeService")
modelService = spring.getBean("modelService")
def findMediasWithoutMime() {
    query = "SELECT {PK} FROM {Media} WHERE {mime} IS NULL")
    flexibleSearchService.search(query).result;
    }
findMediasWithoutMime().each {
    it.mime = mimeService.getMimeFromFileExtension(it.realfilename)
    modelService.save(it)
}