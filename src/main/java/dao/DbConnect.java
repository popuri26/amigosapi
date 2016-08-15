package dao;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.net.ssl.HttpsURLConnection;

import beans.User;


public class DbConnect {

	public static Connection connectDB(){
		Connection connection=null;
		try{
			String host="ec2-54-225-246-33.compute-1.amazonaws.com";
			String dbName="df6qteidc6i5cg";
			String userName="qcsqjvxvpxbxfz";
			String password="x8a1YCcHO-2kGbB6BJE1gRbxJC";
			String url="jdbc:postgresql://"+host+":5432/"+dbName;
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(url,userName, password);
		}
		catch(Exception exception){
			exception.printStackTrace();
		}
		return connection;
	}

	public static String registerUser(User user){
		Connection connection=connectDB();
		String sql="insert into smart_cards (name,email,auth_id,ph_no,credit,org,details)values (?,?,?,?,?,?,?)";
		try{
			if(connectPA()){
				if(connection==null)
					System.out.println("No Connection");
				else{
					PreparedStatement query=connection.prepareStatement(sql);
					query.setString(1,user.getName());
					query.setString(2, user.getEmail());
					query.setString(3,user.getAuthId());
					query.setString(4, user.getPhoneNumber());
					query.setString(5,"0");
					query.setString(6, user.getOrganization());
					query.setString(7,user.getDetails());
					if(query.executeUpdate()!=0)
						return "success";
					else 
						return "failed";
				}
			}else
				return "Auth Failed";
		}catch(Exception exception){
			exception.printStackTrace();
			return "failed";
		}
		return "failed";
	}
	public static boolean connectPA(){
		try{

			String link="https://primeauth.com/api/v1/smart_card/edit_auth.json?"
					+ "token=63f28afd95d178df830f74dc805a7770&"
					+ "secret=1f44a62157590c492b088176257272cea8795320f33ffce2da2def932ab0d337&auth_id=123";
			URL url=new URL(link);
			HttpsURLConnection httpsURLConnection=(HttpsURLConnection) url.openConnection();
			httpsURLConnection.setRequestMethod("POST");
			httpsURLConnection.setDoInput(true);
			httpsURLConnection.setDoOutput(true);
			httpsURLConnection.addRequestProperty("User-Agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
			return httpsURLConnection.getResponseMessage()=="true";
		}catch(Exception exception){

		}
		return false;
	}
}


