//This groovy script is useful to print last 500 lines of console.log
println "tail -n 500 ../../../../log/tomcat/console.log".execute().text