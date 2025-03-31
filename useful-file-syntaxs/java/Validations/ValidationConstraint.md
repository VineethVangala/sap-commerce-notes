# Custom validation constraints

### Summary

|                     |                                                                                                                                        |
| ------------------- | -------------------------------------------------------------------------------------------------------------------------------------- |
| extends             |                                                                                                                                        |
| **implements**      | **ConstraintValidator<NotLoremIpsum, String>**                                                                                         |
| **override method** | **initialize**(final NotLoremIpsum constraintAnnotation) <br>**isValid**(final String value, final ConstraintValidatorContext context) |

1. Create the Constraint Item Type.

```xml
<itemtype code="NotLoremIpsumConstraint" extends="AttributeConstraint"
    autocreate="true" generate="true">
    <description>Custom constraint which checks for Lorem Ipsum text.</description>
    <attributes>
        <attribute qualifier="annotation" type="java.lang.Class"
            redeclare="true">
            <modifiers write="false" initial="true" optional="false" />
            <defaultvalue>
                concerttours.constraints.NotLoremIpsum.class
            </defaultvalue>
        </attribute>
    </attributes>
</itemtype>
```

2. Create the annotation interface called NotLoremIpsum in a package called concerttours.constraints under the src folder of the concerttours extension.

```java
package concerttours.constraints;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target(
{ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = NotLoremIpsumValidator.class)
@Documented
public @interface NotLoremIpsum
{
    String message() default "{concerttours.constraints.NotLoremIpsum.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
```

3. Create the validator class by creating a new class called NotLoremIpsumValidator in the same concerttours.constraints package under the src folder of the concerttours extension

```java
package concerttours.constraints;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotLoremIpsumValidator implements ConstraintValidator<NotLoremIpsum, String>
{
    @Override
    public void initialize(final NotLoremIpsum constraintAnnotation)
    {
        // empty
    }
    @Override
    public boolean isValid(final String value, final ConstraintValidatorContext context)
    {
        return value != null && !value.isEmpty() && !value.toLowerCase().startsWith("lorem ipsum");
    }
}
```

4. Add the following property to the local.properties file in the <HYBRIS_CONFIG_DIR> directory to tell the platform that your constraint is for checking character strings (and not for numbers and dates).

```
validation.constraints.attribute.mapping.concerttours.constraints.NotLoremIpsum=strings
```

6. Open Backoffice. Create a new NotLoremIpsum constraint for the Band history attribute in the same way you created the new MinConstraint previously and give it the ID NotIpsum.
7. Click on the right most toolbar button to **reload the validation engine** so that it picks up the new constraint.
8. Try modifying the history attributes of one of the bands so that it starts with lorem ipsum, and confirm that the correct error message appears when you click Save.
