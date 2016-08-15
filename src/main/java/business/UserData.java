package business;

import beans.User;
import dao.DbConnect;

public class UserData {
	public static boolean validateUser(User user){
		if( user.getEmail()=="none" && user.getPhoneNumber()=="none")
			return false;
		else 
			return true;
	}
	public static String registerUser(User user,String token,String secret, String pin){
		if(validateUser(user))
		return DbConnect.registerUser(user,token,secret,pin);
		else 
			return "Invalid Inputs";
	}
}
