package resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import beans.User;
import business.UserData;

@Path("/users")
public class HelloResource {

    @GET
    @Produces("text/plain")
    public String handleGreeting() {
    	return "Success";
    }
    @POST
	@Path("/addnewuser")
//	@Consumes("application/json")
	@Produces("text/plain")
	public static String signUp(){
	User user=new User();
	user.setAuthId("2423");
	user.setEmail("user1@api.com");
	user.setName("user1");
	user.setDetails("Test1");
	user.setPhoneNumber("1234567890");
	user.setOrganization("org1");
	String status= UserData.registerUser(user);
	System.out.println("The status is "+status);
	return status;
	}
}
