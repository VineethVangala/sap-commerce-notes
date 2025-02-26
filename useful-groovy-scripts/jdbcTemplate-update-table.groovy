//This groovy script is useful to run DML commands in database 

import org.springframework.jdbc.core.JdbcTemplate
JdbcTemplate jdbcTemplate = spring.getBean("jdbcTemplate")

try{
    sqlQuery = "TRUNCATE table table_name";
    sqlQuery2= "DROP INDEX index_name ON table_name";
    sqlQuery3 = "ALTER TABLE table_name DROP COLUMN column_name";
    sqlQuery4 = "ALTER TABLE table_name DROP COLUMN column_name1,column_name2";
    sqlQuery5 = "ALTER TABLE table_name ALTER COLUMN column_name1 nvarchar(MAX)";
    

    jdbcTemplate.update(sqlQuery);
}
catch(Exception e){
    println e.getMessage();
}