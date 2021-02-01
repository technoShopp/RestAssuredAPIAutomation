package StepDefinitions;

import POJO.WeatherSixteenDays;
import Utilites.Tools;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.specification.ResponseSpecification;	

public class WeatherForecastStepDefinitions extends Tools {
	Tools t= new Tools();
    private ResponseSpecification res;
	private String baseUri;
	
	@Given("I like to surf beach {string} in Sydney with the {string} and {string}")
	public void I_like_to_surf_beach_in_Sydney_with_the_and(String beach, String postalCode , String countryCode) throws Throwable
	{
		log.info("Hitting the EndPoint URL for "+beach+" beach");
		 baseUri=Tools.getBaseURI();
		 res= Tools.getResponseSpec(postalCode , countryCode);
	}
	
	@When("I hit the GET request")
	public void I_hit_the_GET_request()
	{
		wfrSixteen=res.when().get(baseUri).then().assertThat().statusCode(200).extract().response().as(WeatherSixteenDays.class);
		
	}
	
	@Then("I like to surf on {string} and those days should be {string}")
	public void I_like_to_surf_on_and_those_days_should_be(String noOfDays, String days) throws Throwable
	{
		
		t.applyFilterForDays(noOfDays, days);
	}
				
     
       @Then("I check for {string} to be {string} for {string}")
       public void I_check_for_to_be(String factor,String condition, String beach) throws Throwable
       {
    	   t.applyFilter( factor,condition, beach);
       }
       
	}
	
	
	

