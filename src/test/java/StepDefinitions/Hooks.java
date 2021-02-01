package StepDefinitions;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;

import Utilites.Tools;
import io.cucumber.java.After;
import io.cucumber.java.Before;

public class Hooks extends Tools {

		
	


		@Before ("@auto")
		public void beforeScenario() throws Throwable
		{
			PropertyConfigurator.configure(log4jPath);
			Properties p= new Properties();;
	   	   	FileInputStream f= new FileInputStream(System.getProperty("user.dir")+"\\global.properties");
	   		p.load(f);
	   	    baseUri=p.getProperty("UriBase");
	     	keyValueAPI=p.getProperty("keyValue");
	     	log.info("*****************************************************************");
			log.info("Launching the Weather forecast API Testing");
		}
		
		
		@After ("@auto")
		public void afterScenario()
		{
			log.info("Closing the Weather forecast API Testing");
			log.info("*****************************************************************");
		}
}
