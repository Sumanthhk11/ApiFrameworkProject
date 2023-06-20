package api.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import api.endpoints.UserEndPoints;
import api.payload.User;
import api.utilities.DataProviders;
import io.restassured.response.Response;

public class DDTests 
{
	@Test(priority = 1,dataProvider = "Data",dataProviderClass = DataProviders.class)  //diffrent package so we have to write dataprovider class name
    public void testPostUser(String userID,String userName,String fname,String lname,String useremail,String pwd,String ph)
    {
		User userpayload = new User();
		userpayload.setId(Integer.parseInt(userID));
		userpayload.setUsername(userName);
		userpayload.setFirstName(fname);
		userpayload.setLastName(lname);
		userpayload.setEmail(useremail);
		userpayload.setPassword(pwd);
		userpayload.setPhone(ph);
		Response response = UserEndPoints.createUser(userpayload);
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200);
    }
	@Test(priority = 2,dataProvider = "UserNames",dataProviderClass = DataProviders.class)  //diffrent package so we have to write dataprovider class name
    public void testDeleteUserName(String userName)
    {
		Response response =UserEndPoints.deleteUser(userName);
		Assert.assertEquals(response.getStatusCode(), 200);
    }
}
