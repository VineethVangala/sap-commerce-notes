cd \hybris\bin\platform
ant executeScript -Dresource=file://../scripts/some-script.groovy -Dparams="key=value"
pause
ant executeScript -Dresource=myLocalScript.groovy
pause
ant executeScript -Dresource=file:///Users/zeus/myLocalScript.groovy
pause
ant executeScript -Dresource=C:/Users/zeus/myLocalScript.groovy
pause
ant executeScript -Dresource=http://some.server.com/myScript.groovy
pause
ant executeScript -Dresource=ftp://some.server.com/myScript.groovy
pause