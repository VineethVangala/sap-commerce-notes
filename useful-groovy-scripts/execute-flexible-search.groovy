// This groovy script uses flexibleSearchService to search products

flexibleSearchService = spring.getBean("flexibleSearchService");
query = "select {pk} from {Category}";
result = flexibleSearchService.search(query);
itempk = "";
for (item in result.getResult()) {
println item.getName();
}