package Utilites;

import static io.restassured.RestAssured.given;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;

import POJO.WeatherForecastResponse;
import POJO.WeatherSixteenDays;
import io.restassured.parsing.Parser;
import io.restassured.specification.ResponseSpecification;

public class Tools {
	protected static String log4jPath = System.getProperty("user.dir")+"\\log4j.properties";
	protected static Logger log = Logger.getLogger("WeatherLogger");
	protected static WeatherSixteenDays wfrSixteen;
   	protected static ResponseSpecification  responseSpec;
   	protected static String baseUri,keyValueAPI;
   	protected static int expectedNoOfDays=2;
   	protected static WeatherForecastResponse wfr;
   	
   	protected  HashMap<Integer,HashMap<String,String>> filteredWeatherList= new HashMap<Integer,HashMap<String,String>>(); 
   	protected  HashMap<Integer,HashMap<String,String>> filteredTempList=new HashMap<Integer,HashMap<String,String>>();
   	protected  HashMap<Integer,HashMap<String,String>> filteredUVList= new HashMap<Integer,HashMap<String,String>>(); 
  	protected  HashMap<Integer,HashMap<String,String>> flist= new HashMap<Integer,HashMap<String,String>>(); 
   
   
   	public static String getBaseURI() throws Throwable
   	{
   	 return baseUri;
   	}
   	
   	
  	public static DayOfWeek getDay(String date) throws Throwable
   	{
  		DayOfWeek day=LocalDate.parse(                             
  				date ,
	             DateTimeFormatter.ofPattern( "uuuu-MM-dd" ) 
	         )                                    
	         .getDayOfWeek();                     
	     return day;
   	}
   	
  
  	public static ResponseSpecification getResponseSpec(String postalCode, String countryCode)
  	{
  		responseSpec=given().
		queryParam("key", keyValueAPI).queryParam("postal_code", postalCode).queryParam("country", countryCode).expect().defaultParser(Parser.JSON);
	
  		return responseSpec;
  	}
  
    public  void applyFilterForDays(String noOfDays, String days) throws Throwable
    {
    	int count=0;
    	  HashMap<String,String> m;
    	int actualNoOfDays=Integer.parseInt(noOfDays);
		String day1=days.split(" ")[0];
		String day2=days.split(" ")[1];
		
    	List<WeatherForecastResponse> wf = wfrSixteen.getData();
    	log.info(wf.size()+"DAYS OF WEATHER CONDITION");
    	if(actualNoOfDays==expectedNoOfDays)
		{
    		if(day1.equalsIgnoreCase("thursday")&& day2.equalsIgnoreCase("friday")) {
		for(int i=0;i<wf.size();i++)
		{
			wfr=wf.get(i);
			String date=wfr.getDatetime();
			DayOfWeek day=getDay(date);
			String actualDay=day.toString();
			
			if(actualDay.equalsIgnoreCase(day1) || actualDay.equalsIgnoreCase(day2) )
			{
				count=count+1;
				m= new HashMap<String,String>();
				m.put("day", actualDay);
				m.put("date", wfr.getDatetime()) ;
				m.put("temp", wfr.getTemp());
				m.put("uv", wfr.getUv()) ;
				filteredWeatherList.put(count, m);
				log.info("FILTER BASED ON SPECIFIC DAYS IS GETTING READY for "+count+ "with values"+m);
			}
			else {
				
				log.info(wfr.getDatetime()+"NOT A THURSDAY OR A FRIDAY");
				}
		}
		
		log.info("LIST FOR TEMP = = "+filteredWeatherList.size());
		if(filteredWeatherList.size()==0)
	  	   {log.info("NO THURSDAYS OR FRIDAYS IN THIS PERIOD");}
		} else {log.info("THE DAYS PROVIDED IN THE FEATURE FILE IS NOT A THURSDAY OR A FRIDAY");}
		}
			else
			{log.info("NUMBER OF DAYS NOT EQUAL TO 2");}
    	
    
		}
    
    public void applyFilterForTemperature(String tempRange, String beach)
    {
    	 int countTemp=0;
       	  HashMap<String,String> n;
      
       	String minTemp=tempRange.split("and")[0];
	    String maxTemp=tempRange.split("and")[1];
	    double minimumTemp=Double.parseDouble(minTemp);
	    double maximumTemp=Double.parseDouble(maxTemp);
	    if(filteredWeatherList.size()!=0) {
	    
    	for(int z=0;z<filteredWeatherList.size();z++)
    	{
    		
    		String tempActual=filteredWeatherList.get(z+1).get("temp");
    		double actualTemp=NumberUtils.toDouble(tempActual);
    		 log.info("ACTUAL TEMP BEFORE FILTER ="+actualTemp);
    		 if(actualTemp>=minimumTemp && actualTemp<=maximumTemp)
    		{
    			 log.info("ACTUAL TEMP AFTER FILTER ="+actualTemp);
    			countTemp=countTemp+1;
 				n= new HashMap<String,String>();
 				filteredTempList=putValuesInList(filteredWeatherList,n,z,countTemp,filteredTempList);
 				log.info("CONDUCIVE DAYS FOR "+beach+" BASED ON MAXIMUM AND MINIMUM TEMPERATURE IS GETTING READY");
 				
    		}
    		
    	}
    	
    	log.info("LIST FOR UV = = "+filteredTempList.size());
    	if(filteredTempList.size()==0)
    	{log.info("NOT A SINGLE DAY THAT HAS TEMPERATURE BETWEEN 20 and 30");}
    	
	    }
	   
     }

    public void applyFilterForUV(String uvThreshold, String beach)
    {
    	int countUV=0;
    	HashMap<String,String> o;
        double expectedUVThreshold=Double.parseDouble(uvThreshold);
       	if(filteredTempList.size()!=0) {
    	   for(int y=0;y<filteredTempList.size();y++)
   		{
    		String uvActual=filteredTempList.get(y+1).get("uv");
   			double actualUV=NumberUtils.toDouble(uvActual);
   			if(actualUV<=expectedUVThreshold)
   		{
   				
   				o= new HashMap<String,String>();
   				countUV=countUV+1;
   				filteredUVList=putValuesInList(filteredTempList,o,y,countUV,filteredUVList);
				log.info("CONDUCIVE DAYS FOR "+beach+" BASED ON UV THRESHOLS");	
   		}
   		}
    	   log.info("FInal filtered UV LIST "+filteredUVList.size());
    	   if(filteredUVList.size()==0)
    	   {log.info("NOT A SINGLE DAY THAT HAS UV THRESHOLD LESS THAN or EQUAL TO 3");}
    	 }
      }
    
    public void applyFilter(String factor, String condition, String beach) throws Throwable
    {
    factorCheck(factor,condition, beach);
    }
    
    public void factorCheck(String factor, String condition, String beach) throws Throwable
    {
    if(factor.equalsIgnoreCase("uv"))
    {applyFilterForUV(condition,beach);}
    else if(factor.equalsIgnoreCase("temperature"))
    {applyFilterForTemperature(condition, beach);}
    else
    {System.out.println("NO MATCHING CRITERIA");}
    }
    
 public HashMap<Integer,HashMap<String,String>> putValuesInList(HashMap<Integer,HashMap<String,String>> fromList, HashMap<String,String> fmap, int c, int countFactor, HashMap<Integer,HashMap<String,String>> toList )
{	
	 log.info("FILTERED TEMP LIST SIZE INSIDE THE LOOP"+flist.size());
	  fmap.put("day", fromList.get(c+1).get("day"));
	  fmap.put("date", fromList.get(c+1).get("date")) ;
	  fmap.put("temp", fromList.get(c+1).get("temp"));
	  fmap.put("uv", fromList.get(c+1).get("uv")) ;
	  toList.put(countFactor, fmap);
	  log.info(countFactor+ "WITH VALUES "+fmap);
	  return toList;
}
}
    
    
    
    

