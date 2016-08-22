package resources;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

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
	@Produces("text/plain")
	public static String signUp(@QueryParam("name") String username,@QueryParam("auth_id")String authId,@QueryParam("phNo")String nubmer,
			@QueryParam("email") String email,@QueryParam("details") String details,@QueryParam("org") String organization,
			@QueryParam("token") String token,@QueryParam("secret")String secret,@QueryParam("pin")String pin){
		User user=new User();
		user.setAuthId(authId);
		user.setName(username);
		user.setPhoneNumber(nubmer);
		user.setEmail(email);
		user.setOrganization(organization);
		user.setDetails(details);
		//	user.setAuthId("2423");
		//	user.setEmail("user1@api.com");
		//	user.setName("user1");
		//	user.setDetails("Test1");
		//	user.setPhoneNumber("1234567890");
		//	user.setOrganization("org1");
		String status= UserData.registerUser(user,token,secret,pin);
		return status;
	}

	@POST
	@Path("/edituser")
	@Produces("text/plain")
	public static String editUser(@QueryParam("name") String username,@QueryParam("authId")String authId,@QueryParam("phNo")String number,
			@QueryParam("email") String email,@QueryParam("details") String details,@QueryParam("org") String organization,@QueryParam("credit")String credit){
		User user=new User();
		user.setAuthId(authId);
		user.setName(username);
		user.setPhoneNumber(number);
		user.setEmail(email);
		user.setOrganization(organization);
		user.setDetails(details);
		user.setCredit(credit);
		//	user.setAuthId("2423");
		//	user.setEmail("user1@api.com");
		//	user.setName("user1");
		//	user.setDetails("Test1");
		//	user.setPhoneNumber("1234567890");
		//	user.setOrganization("org1");
		String status= UserData.editUser(user);
		return status;
	}

	@POST
	@Path("/getuser")
	@Produces("application/json")
	public static User editUser(@QueryParam("authId") String authId){
		User user= UserData.getUser(authId);
		if(user==null){
			user=new User();
			user.setName("none");
			user.setEmail("none");
			user.setPhoneNumber("none");
			user.setDetails("none");
			user.setCredit("none");
			user.setOrganization("none");
			user.setAuthId(authId);
		}
		return user;
	}

}
