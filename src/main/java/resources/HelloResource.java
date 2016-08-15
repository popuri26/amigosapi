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
    @Produces(MediaType.TEXT_PLAIN)
    public String handleGreeting() {
    	return "Success";
    }
    @POST
	@Path("/addnewuser")
//	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public static String signUp(User user){
	user.setAuthId("2423");
	user.setEmail("user1@api.com");
	user.setName("user1");
	user.setDetails("Test1");
	user.setPhoneNumber("1234567890");
	user.setOrganization("org1");
	return UserData.registerUser(user);
	}
}
