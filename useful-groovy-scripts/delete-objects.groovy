/* This groovy script will 
1. use flexibleSearchService to search products to be deleted. 
2. use modelService to delete the products in flexibleSearchService result. */


flexibleSearchService = spring.getBean("flexibleSearchService")
modelService = spring.getBean("modelService")
flexibleSearchService.search("select {pk} from {product} where ....").result.each {
modelService.remove(it)
}