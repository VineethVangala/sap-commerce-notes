//This groovy script is useful to view contents of a file

new File('/etc/passwd').eachLine {
    println it
}