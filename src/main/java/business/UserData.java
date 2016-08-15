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
	public static String registerUser(User user){
		if(validateUser(user))
		return DbConnect.registerUser(user);
		else 
			return "Invalid Inputs";
	}
}
