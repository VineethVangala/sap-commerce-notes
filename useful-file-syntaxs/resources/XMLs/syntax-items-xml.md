# items.xml Syntax

## Available Types

| Type            | Description / Comment                                                                                                                                                                                                                                                                                                                              | unique identifier | Database storage                                                                                                 | Examples                                                                       |
| --------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----------------- | ---------------------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------ |
| AtomicType      | The most basic types available in SAP Commerce.<br> Any custom type should be serializable and have a java class objecct to it.                                                                                                                                                                                                                    | class             | strings (VARCHAR) or numbers (NUMBER)                                                                            | java.lang.Integer, java.lang.String, java.util.Date localized:java.lang.String |
| CollectionType  | It contains a typed number of instances of types.<br> The values are written in a CSV format and not in a normalized way.<br> We can use it for limited 1:many or many:1 relations.<br> As the maximum length of the database field of a CollectionType is limited, **a CollectionType with many values may end up getting its values truncated.** | code              | AtomicTypes - binary fields;<br> items - string with comma separated PKs                                         | StringCollection, OrderEntryCollection, OrderCollection                        |
| EnumerationType | It maps a predefined verbatim value to another, internal kind of value.<br>This kind of type is useful for attributes whose values only have a limited number of choices.<br>**The values are also their instances.**                                                                                                                              | code              | All EnumTypes end up in one single database table if deployment table not specified                              | OrderStatus, ArticleApprovalStatus                                             |
| MapType         | It is a typed collection of **key/value pairs**.<br> For each key (referred to as argument), there is a corresponding value (referred to as return type). <br>The direction of mapping is always argument - return value.                                                                                                                          | code              | Localized values are stored in a separate database table,having the **suffix lp** (short for localized property) | localized:java.lang.String , ParameterMap, localized:Media                     |
| RelationTypes   | They represent many:many relations.<br> Internally, the elements on both sides of the relation are linked together via instances of a helper type called **LinkItem**.<br> LinkItems hold two attributes, SourceItem and TargetItem, that hold references to the respective item.                                                                  | code              | LinkItem instance stores the PKs of the related source and target items.                                         | PrincipalGroupRelation, AbstractOrder2AbstractOrderEntry                       |
| ItemTypes       | The foundation of the SAP Commerce type system.<br> All types are ultimately derived of a ComposedType.<br> Every type may have any number of attributes.<br> These attributes may either be defined by the type's AttributeDescriptors or inherited from its supertypes.<br>                                                                      | code              | GenericItem table or Separate table                                                                              | Category, Product, Order, OrderEntry                                           |

### AtomicType Modifiers

| Modifier           | Description / Comment                                                                                                                                                                               | Default value |
| ------------------ | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------- |
| <em>**class**</em> | Corresponding Java class in the hybris Suite                                                                                                                                                        |               |
| extends            | The superclass of this ItemType                                                                                                                                                                     |               |
| jaloclass          | The fully qualified classpath of this ItemType                                                                                                                                                      |               |
| **autocreate**     | If set to true, **a new ItemType gets created when the Platform creates the type system during initialization**. Set this to false if you are adding to an existing type that is deifned elsewhere. |               |
| **generate**       | If set to true, the Platform **creates jalo classes**.                                                                                                                                              | true          |

### Collectiontype Modifiers

| Modifier          | Description / Comment                                                                                                                                                                               | Default value |
| ----------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------- |
| <em>**code**</em> | The identifier of this ItemType                                                                                                                                                                     |               |
| extends           | The superclass of this ItemType                                                                                                                                                                     |               |
| jaloclass         | The fully qualified classpath of this ItemType                                                                                                                                                      |               |
| **autocreate**    | If set to true, **a new ItemType gets created when the Platform creates the type system during initialization**. Set this to false if you are adding to an existing type that is deifned elsewhere. |               |
| **generate**      | If set to true, the Platform **creates jalo classes**.                                                                                                                                              | true          |
| type=             | value comes from list: {'set','list','collection'}"                                                                                                                                                 | collection    |

### EnumtypeType Modifiers

| Modifier             | Description / Comment                                                                                                                                                                                                                                                                    | Default value    |
| -------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ---------------- |
| <em>**code**</em>    | The unique code of this Enumeration.                                                                                                                                                                                                                                                     |                  |
| autocreate           | If 'true', the item will be created during initialization.                                                                                                                                                                                                                               |                  |
| generate             | If 'false' no constants will be generated at constant class of extension as well as at corresponding servicelayer enum class.                                                                                                                                                            | true             |
| jaloclass            | Specifies the name of the associated jalo class. The specified class must extend de.hybris.platform.jalo.enumeration.EnumerationValue and will not be generated. By specifying a jalo class you can change the implementation to use for the values of this enumeration.                 | EnumerationValue |
| <em>**dynamic**</em> | Whether it is possible to add new values by runtime. Also results in different types of enums: 'true' results in 'classic' hybris enums, 'false' results in Java enums. Both kinds of enums are API compatible, and switching between enum types is possible by running a system update. | false            |

### Maptype modifiers

| Modifier          | Description / Comment                                      | Default value |
| ----------------- | ---------------------------------------------------------- | ------------- |
| <em>**code**</em> | The unique code of the map.                                |               |
| argumenttype      | The type of the key attributes.                            |               |
| returntype        | The type of the value attributes.                          |               |
| autocreate        | If 'true', the item will be created during initialization. | true          |
| generate          | Deprecated. Has no effect for map types.                   | true          |
| redeclare         | Deprecated. Has no effect for map types.                   | false         |

### RelationType Modifiers

| Modifier   | Description / Comment                                                                                                                                                                                                                                       | Default value |
| ---------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------- |
| code       | The type code.                                                                                                                                                                                                                                              |               |
| localized  | A localized n-m relation can have a link between two items for each language.                                                                                                                                                                               | false         |
| deployment | Deprecated, please use separate deployment sub tag. All instances of this type will be stored in a separated database table. The value of this attribute represents a reference to the specified deployment in the corresponding 'advanced-deployment.xml'. | empty         |
| autocreate | If 'true', the item will be created during initialization.                                                                                                                                                                                                  |               |
| generate   | Deprecated. Will have no effect for relations.                                                                                                                                                                                                              |               |

#### RelationElemetType Modifiers

| Modifier       | Description / Comment                                                                                                                                                                                                   | Default value      |
| -------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------------ |
| type           | Type of attribute which will be generated at type configured for opposite relation end.                                                                                                                                 |                    |
| qualifier      | Qualifier of attribute which will be generated at type configured for opposite relation end. If navigable is not set to false the qualifier is mandatory.                                                               | empty              |
| metatype       | The (meta)type which describes the attributes type.<br> Must be type extending RelationDescriptor.                                                                                                                      | RelationDescriptor |
| cardinality    | value comes from list: {'one', 'many'} <br> The cardinality of this relation end. Choose 'one' for 'one' part of a 1:n relation or 'many' when part of a n:m relation. A 1:1 relation is not supported.                 | many               |
| navigable      | Is the relation navigable from this side. Can only be disabled for one side of many to many relation. If disabled, no qualifier as well as modifiers can be defined.                                                    | true               |
| collectiontype | value comes from list: {'set','list','collection'}" <br> Configures the type of this collection if element has cardinality 'many'. Related attribute getter / setter will use corresponding java collection interfaces. | Collection         |
| ordered        | If 'true' an additional ordering attribute will be generated for maintaining ordering.                                                                                                                                  | false              |

### ItemType Modifiers

| Modifier          | Description / Comment                                                                                                                                                                               | Default value |
| ----------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------- |
| <em>**code**</em> | The identifier of this ItemType                                                                                                                                                                     |               |
| extends           | The superclass of this ItemType                                                                                                                                                                     |               |
| jaloclass         | The fully qualified classpath of this ItemType                                                                                                                                                      |               |
| **autocreate**    | If set to true, **a new ItemType gets created when the Platform creates the type system during initialization**. Set this to false if you are adding to an existing type that is deifned elsewhere. | true          |
| **generate**      | If set to true, the Platform **creates jalo classes**.                                                                                                                                              | true          |
| singleton         | If 'true', type gets marked as singleton which will be evaluated by some modules, or impex, with that allowing only one instance per system                                                         | false         |
| metatype          | The (meta)type which describes the assigned type. Must be a type extending ComposedType.                                                                                                            | ComposedType  |
| abstract          | Marks type and jalo class as abstract. If 'true', the type cannot be instantiated.                                                                                                                  | false         |

### Attributetype Modifiers

| Modifier               | Description / Comment                                                                                  | Default values      |
| ---------------------- | ------------------------------------------------------------------------------------------------------ | ------------------- |
| <em>**qualifier**</em> | The identifier of this Attribute                                                                       |                     |
| autocreate             | If 'true', the attribute descriptor will be created during initialization.                             | true                |
| generate               | If 'true', getter and setter methods for this attribute will be generated during a hybris Suite build. | true                |
| type                   | The type of the attribute, such as 'Product', 'int' or 'java.lang.String'.                             |                     |
| redeclare              | If set to true, we can re-define the attribute definition from an inherited type                       | false               |
| isSelectionOf          | References an attribute of the same type.                                                              |                     |
| metatype               | Specifies the metatype for the attributes definition                                                   | AttributeDescriptor |

#### Deployment modifiers

| Modifier              | Description / Comment                                             | Default values |
| --------------------- | ----------------------------------------------------------------- | -------------- |
| <em>**table**</em>    | The mapped database table. Must be globally unique.               |                |
| <em>**typecode**</em> | The mapped item type code. Must be globally unique.               |                |
| propertytable         | The mapped dump property database table to be used for this item. | props          |

#### Attribute Modifiers

| Modifier     | Description / Comment                                                                                                                                                                                                                                                                                                                     | Default values |
| ------------ | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | -------------- |
| read         | Defines if this attribute is readable (that is, if a getter method will be generated).                                                                                                                                                                                                                                                    | true           |
| write        | Defines if this attribute is writable (that is, if a setter method will be generated).                                                                                                                                                                                                                                                    | true           |
| search       | Defines if this attribute is searchable by a FlexibleSearch. Attributes with persistence type set to 'jalo' cannot be searchable.                                                                                                                                                                                                         | true           |
| optional     | Defines if this attribute is mandatory or optional.Set to 'false' for mandatory, 'true' for optional.                                                                                                                                                                                                                                     | true           |
| private      | Defines the Java visibility of the generated getter and setter methods for this attribute. If 'true', the visibility modifier of generated methods is set to 'protected'; if 'false', the visibility modifier is 'public'. Also, you will have no generated methods in the ServiceLayer if 'true'.'false' for 'public' generated methods. | false          |
| initial      | If 'true', the attribute will only be writable during the item creation. Setting this to 'true' is only useful in combination with write='false'.                                                                                                                                                                                         | false          |
| removable    | Defines if this attribute is removable.                                                                                                                                                                                                                                                                                                   | true           |
| partof       | Defines if the assigned attribute value only belongs to the current instance of this type.                                                                                                                                                                                                                                                | false          |
| unique       | If 'true', the value of this attribute has to be unique within all instances of this type. If there are multiple attributes marked as unique, then their combined values must be unique. Will not be evaluated at jalo layer, if you want to manage the attribute directly using jalo layer you have to ensure uniqueness manually.       | false          |
| dontOptimize | If 'true' the attribute value will be stored in the 'global' property table, otherwise a separate column (inside the table of the associated type)will be created for storing its values.                                                                                                                                                 | false          |
| encrypted    | If 'true', the attribute value will be stored in an encrypted way.                                                                                                                                                                                                                                                                        | false          |

#### Example xml configuration

```
<items xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:noNamespaceSchemaLocation="items.xsd">
    <atomictypes>
        <atomictype class="java.lang.Integer" extends="java.lang.Number" autocreate="true" generate="false"/>
        <atomictype class="de.hybris.platform.core.PK" extends="java.lang.Object" autocreate="true" generate="false"/>
        <atomictype class="de.hybris.platform.util.TaxValue" extends="java.lang.Object" autocreate="true" generate="false"/>
    </atomictypes>

    <collectiontypes>
            <collectiontype code="ExampleCollection" elementtype="Item" autocreate="true" generate="false"/>
            <collectiontype code="ItemCollection" elementtype="Item" autocreate="true" generate="false"/>
            <collectiontype code="StringCollection" elementtype="java.lang.String" autocreate="true" generate="false"/>
            <collectiontype code="ObjectCollection" elementtype="java.lang.Object" autocreate="true" generate="false"/>
            <collectiontype code="MediaCollection" elementtype="Media" autocreate="true" generate="true"/>
            <collectiontype code="ViewAttributeList" elementtype="ViewAttributeDescriptor" autocreate="true" generate="false" type="list"/>
            <collectiontype code="ViewAttributeSet" elementtype="ViewAttributeDescriptor" autocreate="true" generate="false" type="set"/>

    </collectiontypes>

    <enumtypes>
        <enumtype code="CreditCardType" autocreate="true" generate="true">
            <value code="amex"/>
            <value code="visa"/>
            <value code="master"/>
            <value code="diners"/>
        </enumtype>
        <enumtype code="OrderStatus" autocreate="true" generate="true" dynamic="true">
                <value code="CREATED"/>
                <value code="ON_VALIDATION"/>
                <value code="COMPLETED"/>
                <value code="CANCELLED"/>
        </enumtype>
    </enumtypes>

    <maptypes>
        <maptype code="ExampleMap" argumenttype="Language" returntype="java.math.BigInteger" autocreate="true" generate="false"/>
        <maptype code="localized:java.lang.String" argumenttype="Language" returntype="java.lang.String" autocreate="true" generate="false"/>
    </maptypes>

    <relations>
        <relation code="PrincipalGroupRelation" autocreate="true" generate="false" localized="false"
                  deployment="de.hybris.platform.persistence.link.PrincipalGroupRelation">
            <sourceElement qualifier="members" type="Principal" collectiontype="set" cardinality="many" ordered="false">
                <modifiers read="true" write="true" search="true" optional="true"/>
            </sourceElement>
            <targetElement qualifier="groups" type="PrincipalGroup" collectiontype="set" cardinality="many"
                           ordered="false">
                <modifiers read="true" write="true" search="true" optional="true"/>
            </targetElement>
        </relation>

        <relation code="CategoryProductRelation" autocreate="true" generate="true" localized="false">
			<deployment table="Cat2ProdRel" typecode="143"/>
    		<sourceElement qualifier="supercategories" type="Category" cardinality="many" ordered="false">
    		    <description>Super Categories</description>
    			<modifiers read="true" write="true" search="true" optional="true"/>
    		</sourceElement>
    		<targetElement qualifier="products" type="Product" cardinality="many" collectiontype="list" ordered="true">
    		    <description>Products</description>
    			<modifiers read="true" write="true" search="true" optional="true"/>
    		</targetElement>
    	</relation>
    </relations>

     <!--
         modifier defaults

            inital      = false
            read        = true
            write       = true
            optional    = true
            private     = false
            search      = true

   -->
    <itemtypes>
        <itemtype code="Category"  generate="true"  jaloclass="de.hybris.platform.category.jalo.Category" extends="GenericItem" autocreate="true">
			<deployment table="Categories" typecode="142"/>
			<custom-properties>
				<property name="catalogItemType"><value>java.lang.Boolean.TRUE</value></property>
				<property name="catalogVersionAttributeQualifier"><value>"catalogVersion"</value></property>
				<property name="uniqueKeyAttributeQualifier"><value>"code"</value></property>
				<property name="catalog.sync.default.root.type"><value>Boolean.TRUE</value></property>
				<property name="catalog.sync.default.root.type.order"><value>Integer.valueOf(1)</value></property>
			</custom-properties>
			<attributes>
				<attribute qualifier="description" type="localized:java.lang.String">
					<description>Catalog Category Description</description>
					<modifiers read="true" write="true" search="true" optional="true"/>
					<persistence type="property">
						<columntype>
							<value>HYBRIS.LONG_STRING</value>
						</columntype>
					</persistence>
					<custom-properties>
						<property name="hmcIndexField"><value>"thefield"</value></property>
					</custom-properties>
				</attribute>
				<attribute qualifier="order" type="java.lang.Integer">
					<description>Order</description>
					<modifiers read="true" write="true" search="true" optional="true"/>
					<persistence type="property"/>
				</attribute>
				<attribute qualifier="catalog" type="Catalog">
					<description>Catalog</description>
					<modifiers read="true" write="true" initial="true" search="true" optional="false" private="true"/>
					<persistence type="property"/>
				</attribute>
				<attribute qualifier="catalogVersion" type="CatalogVersion">
					<description>CatalogVersion</description>
					<modifiers read="true" write="true" search="true" optional="false" unique="true"/>
					<persistence type="property"/>
				</attribute>
				<attribute qualifier="thumbnails" type="MediaCollection">
					<modifiers read="true" write="true" search="true" optional="true"/>
					<persistence type="property"/>
				</attribute>
				<attribute qualifier="thumbnail" type="Media">
					<description>Small Image</description>
					<modifiers read="true" write="true" search="true" optional="true"/>
					<persistence type="property"/>
				</attribute>
				<attribute qualifier="picture" type="Media">
					<description>Image</description>
					<modifiers read="true" write="true" search="true" optional="true"/>
					<persistence type="property"/>
				</attribute>
				<attribute qualifier="name" type="localized:java.lang.String">
					<description>Name</description>
					<modifiers read="true" write="true" search="true" optional="true"/>
					<persistence type="property"/>
					<custom-properties>
						<property name="hmcIndexField"><value>"thefield"</value></property>
					</custom-properties>
				</attribute>
				<attribute qualifier="code" type="java.lang.String">
					<description>Code</description>
					<modifiers read="true" write="true" search="true" optional="false" unique="true"/>
					<persistence type="property"/>
					<custom-properties>
						<property name="hmcIndexField"><value>"thefield"</value></property>
					</custom-properties>
				</attribute>
				<attribute qualifier="allSubcategories" type="CategoryCollection">
					<modifiers read="true" write="false" search="false" optional="true"/>
					<persistence type="dynamic" attributeHandler="categoryAllSubcategories"/>
				</attribute>
				<attribute qualifier="allSupercategories" type="CategoryCollection">
					<modifiers read="true" write="false" search="false" optional="true"/>
					<persistence type="dynamic" attributeHandler="categoryAllSupercategories" />
				</attribute>
			</attributes>
         <indexes>
         	<index name="codeIDX" unique="false">
         		<key attribute="code"/>
         	</index>
	       	<index name="versionIDX" unique="false">
         		<key attribute="catalogVersion"/>
         	</index>
	       	<index name="codeVersionIDX" unique="true">
         		<key attribute="code"/>
         		<key attribute="catalogVersion"/>
         	</index>
         </indexes>
		</itemtype>
    <itemtypes>
</items>
```
