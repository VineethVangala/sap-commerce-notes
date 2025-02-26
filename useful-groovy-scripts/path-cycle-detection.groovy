/* This groovy script is useful in checking path cycles in category hierarchy.
1. uses flexibleSearchService to fetch all categories.
2. uses defaultCategoryService to print the hierarchy level of category. 
defaultCategoryService prints a warning log if any path cycle found for a category. 
Log's search keyword = "path cycle found for category" */

flexibleSearchService = spring.getBean("flexibleSearchService");
defaultCategoryService = spring.getBean("defaultCategoryService");

query = "Select {pk} from {Category}"
result = flexibleSearchService.search(query);

for(category in result.getResult()){
    print category.getCode() + " : ";
    for(path in defaultCategoryService.getPathsForCategory(category)){
        print path.size() + ",";
    }
    println "";
}