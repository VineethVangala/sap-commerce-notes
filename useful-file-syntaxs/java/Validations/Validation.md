# Validation

## Ways to validate

Validation logic may be triggered in the following ways:

1. Implicitly with the ValidationInterceptor that hooks into calls to the save method in a model
2. Explicitly by manually calling the validate method of the ValidationService, and passing in a SAP Commerce Cloud model or POJO to be validated

### Example- Custom validation constraints

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

### Example - Defining Validation Constraints in the Items.xml File

```xml
<attribute qualifier="name" type="java.lang.String">
        <description>name of band</description>
        <persistence type="property" />
        <modifiers optional="false" unique="true" />
</attribute>
```

### Example - Defining OOTB Validation Constraints in the Backoffice

1. Go to **System > Validation > Constraints**.
2. Expand the **Attribute constraint** menu item by clicking on the arrow to the left of the menu item text.
3. Click on the Min Constraint item to display the Create New Min Constraint wizard
4. Give the new constraint the ID newConstraint.
5. Set the Minimal value field to 0 to forbid negative values.
6. Since you want to validate the enclosing type, start typing Band in the Composed type to validate field and choose Band from the list of suggestions when it appears.
7. Do the same in the Attribute descriptor to validate field to pick the albumSales attribute.
8. Click Next.
9. Enter the error message Album sales must be > 0. Do pay attention. in the Error message field.
10. Optional: Click on the globe symbol to access other language options so that you can enter the error message in different languages.
11. Click on Done.
12. Click on the right most toolbar button to **reload the validation engine** so that it picks up the new constraint.

### Example - Defining OOTB Validation Constraints in the Backoffice

```
INSERT_UPDATE MinConstraint;id[unique=true];severity(code,itemtype(code));active;annotation;descriptor(enclosingType(code),qualifier);message[lang=de];message[lang=en];value
;AlbumSalesMustNotBeNegative;ERROR:Severity;true;javax.validation.constraints.Min;Band:albumSales;Albumverkäufe dürfen nicht negativ sein;Album sales must not be negative;0
```
