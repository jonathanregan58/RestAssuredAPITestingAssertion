package testCases;


import org.testng.Assert;
import org.testng.annotations.*;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TC_postmanschemavalidation3freebeeceptorcom {

	@Test(priority=1)
	public void test_getAllRestaurants()
	{
	 //test whether it can be accessed
	 given()

	 .when()
	   .get("https://postmanschemavalidation3.free.beeceptor.com/")

	  .then()
	   .statusCode(200);

	}
	
	@Test(priority=2)
	public void test_length() {
		//test whether there are 29 restaurants
		Response res = given()
			      .when()
			      .get("https://postmanschemavalidation3.free.beeceptor.com/");

			      //convert JSON to string
			      JsonPath j = new JsonPath(res.asString());

			      //length of JSON array
			      int s = j.getInt("data.size()");
			      System.out.println(s);
			      
			    //  int s2 = j.getInt("Location.size()");
			     // System.out.println("Array size of Location: " +s2);
			      
			      assertTrue(s == 29);
			      
			     


	}
	
	@Test(priority=3)
	public void get_restaurant_from_id_0_to_10() {
		Response res = given()
			      .when()
			      .get("https://postmanschemavalidation3.free.beeceptor.com/");

		JsonPath jsonPathObj = res.jsonPath();

		//get all restaurants's id between 0 and 10
		List<Map> restaurantsid = jsonPathObj.get("id.findAll { id -> id >= 0 && id <= 10 }");
		System.out.println(restaurantsid);

	}
	

	
	@Test(priority=4)
	public void get_name_of_restaurant_from_restaurant_whose_index_is_7() {
		Response res = given()
			      .when()
			      .get("https://postmanschemavalidation3.free.beeceptor.com/");

		//first solution
		JsonPath jsonPathObj = res.jsonPath();
		String getnameoftherestaurant = jsonPathObj.get("name[7]");
        
		System.out.println("The restaurant name whose index is 7 is " + getnameoftherestaurant);
		
		//second solution
		String getnameoftherestaurant2 = com.jayway.jsonpath.JsonPath.read(res.asString(), "$.[7][\"name\"]");
		
		System.out.println("The restaurant name whose index is 7 is " + getnameoftherestaurant2);


	}
	
	@Test(priority=5)
	public void get_ingredients_and_validate_the_amount_of_ingredients_from_restaurant_whose_index_is_7() {
		//there are 5 ingredients
		
		Response res = given()
			      .when()
			      .get("https://postmanschemavalidation3.free.beeceptor.com/");

		
		List<Map> restaurantsingredients = com.jayway.jsonpath.JsonPath.read(res.asString(), "$.[7][\"ingredients\"]");
		
		int length = restaurantsingredients.size();
		
		System.out.println("The ingredients restaurant whose index is 7 is " + restaurantsingredients);
        System.out.println(length);
        assertTrue(length == 5);
	}
	
	@Test(priority=6)
	public void get_the_details_of_adresses_as_well_as_make_sure_there_are_2_addresses_from_restaurant_whose_index_is_7() {
		//the restaurant has 2 addresses
		
		Response res = given()
			      .when()
			      .get("https://postmanschemavalidation3.free.beeceptor.com/");

		
		List<Map> restaurantsaddresses = com.jayway.jsonpath.JsonPath.read(res.asString(), "$.[7][\"addresses\"]");
		
		int length = restaurantsaddresses.size();
		
		System.out.println("The details of addresses restaurant whose index is 7 is " + restaurantsaddresses);
        System.out.println(length);
        assertTrue(length == 2);
	}
	
	@Test(priority=7)
	public void make_sure_the_postcode_of_first_address_from_restaurant_whose_index_is_7_is_Potsdamer_Platz() {
		
		
		Response res = given()
			      .when()
			      .get("https://postmanschemavalidation3.free.beeceptor.com/");

		String postcode = "Potsdamer Platz";
		String getpostcode = com.jayway.jsonpath.JsonPath.read(res.asString(), "$.[7][\"addresses\"][0][\"line1\"]");
		
        System.out.println("The postcode of first address from restaurant whose index is 7 is "+ getpostcode);
        
        
        Assert.assertEquals(getpostcode, postcode, "succesful");

	}
	
 
	
	
	
}
