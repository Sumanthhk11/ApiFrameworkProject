package api.test;

import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndPoints;
import api.payload.User;
import groovyjarjarantlr4.v4.runtime.misc.LogManager;
import io.restassured.response.Response;

public class UserTests 
{
	Faker faker;
	User userpayload;
	public Logger logger;
	@BeforeClass
	public void setupData()
	{
		faker=new Faker();
		userpayload=new User();

		userpayload.setId(faker.idNumber().hashCode());
		userpayload.setUsername(faker.name().username());
		userpayload.setFirstName(faker.name().firstName());
		userpayload.setLastName(faker.name().lastName());
		userpayload.setEmail(faker.internet().safeEmailAddress());
		userpayload.setPassword(faker.internet().password(5, 10));
		userpayload.setPhone(faker.phoneNumber().cellPhone());
		
		//logs
		logger	=org.apache.logging.log4j.LogManager.getLogger(this.getClass());
		logger.debug("debugging*****************");
	}
	@Test(priority = 1)
     public void testPostUser()
     {
		logger.info("************ Creating User **********");
		Response response = UserEndPoints.createUser(userpayload);
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200);
		logger.info("************ User is Created **********");
     }
	@Test(priority = 2)
    public void testGetUserbyName()
    {
		logger.info("************ Reading User Info **********");
		Response response = UserEndPoints.readUser(this.userpayload.getUsername());
		response.then().log().all();
		response.statusCode();
		Assert.assertEquals(response.getStatusCode(),200);
		logger.info("************ User Info is Displayed**********");
    }
	@Test(priority = 3)
    public void testUpdateUserbyName()
    {
		//update data using payload
		logger.info("************ Updating User **********");
		userpayload.setFirstName(faker.name().firstName());
		userpayload.setLastName(faker.name().lastName());
		userpayload.setEmail(faker.internet().safeEmailAddress());
		
		Response response = UserEndPoints.updateUser(this.userpayload.getUsername(),userpayload);
		response.then().log().all();
		response.then().log().body().statusCode(200); //rest assured validation
		
		Assert.assertEquals(response.getStatusCode(), 200); //testng validation
		
	   //checking data after updation
		Response responseafterupdate = UserEndPoints.readUser(this.userpayload.getUsername());
		Assert.assertEquals(responseafterupdate.getStatusCode(),200);
		logger.info("************ User is Updated **********");
    }
	@Test(priority = 4)
    public void testDeleteUserbyName()
    {
		logger.info("************ Deleting User **********");
		Response response = UserEndPoints.deleteUser(this.userpayload.getUsername());
		Assert.assertEquals(response.getStatusCode(),200);
		logger.info("************ User is Deleted **********");
    }
}
