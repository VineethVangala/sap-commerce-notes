//This groovy script is useful to retrieve an object by its PK.
import de.hybris.platform.core.PK;
modelService = spring.getBean("modelService");
Long examplePK = 1234567890123;
object = modelService.get(new PK(examplePK));
print object.getPk();
print ", ";
println object.getName();