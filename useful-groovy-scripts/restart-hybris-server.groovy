//This groovy script is useful to restart hybris sever
de.hybris.platform.jmx.JmxClient.restartWrapper(new File('../../../../data/hybristomcat.java.pid').text as Integer);
