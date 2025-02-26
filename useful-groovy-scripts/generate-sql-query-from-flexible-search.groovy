//This groovy script is useful to convert flexible search query to its equivalent SQL query

query="SELECT count(a.pk) AS itemcount, a.code AS itemtype FROM "+
"({{SELECT {i:pk} AS pk, {c:code} AS code FROM {Item AS i},"+
"{ComposedType AS c} WHERE {i:itemtype}={c:pk}}}) a GROUP BY a.code ORDER BY code";

import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.persistence.property.JDBCValueMappings;
import de.hybris.platform.core.Registry;
import com.mysql.jdbc.JDBC4PreparedStatement;
import de.hybris.platform.jdbcwrapper.PreparedStatementImpl;

flexibleSearchService = spring.getBean("flexibleSearchService");
flexibleSearchQuery = new FlexibleSearchQuery(query);
params = flexibleSearchService.translate(flexibleSearchQuery).getSQLQueryParameters();
sql = flexibleSearchService.translate(flexibleSearchQuery).getSQLQuery();
con = Registry.getMasterTenant().getDataSource().getConnection();
preparedStatement = con.prepareStatement(sql);
JDBCValueMappings.getInstance().fillStatement(preparedStatement, params);
stringRepresentationOfPreparedStatement=preparedStatement.getPrepStmtPassthruString();
sql = (stringRepresentationOfPreparedStatement =~ /^(.*?)\: (.*?)$/)[0][2]
println sql;