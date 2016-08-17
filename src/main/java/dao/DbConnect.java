package dao;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

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

	public static String registerUser(User user,String token,String secret, String pin){
		Connection connection=connectDB();
		String sql="insert into smart_cards (name,email,auth_id,ph_no,credit,org,details)values (?,?,?,?,?,?,?)";
		try{
			String status=connectPA(token,secret,pin,user.getAuthId());
			if(status.equals("false")){
				return "Auth Failed";}
			else	
				if(connection==null)
					return "error";
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
		}catch(Exception exception){
			exception.printStackTrace();
			return "failed";
		}
	}
	public static String connectPA(String token,String secret, String pin,String authId){
		try{
			String link="https://primeauth.com/api/v1/smart_card/edit_auth.json?"
					+ "token="+token.replace("\\", "\\\\")+"&secret="+secret.replace("\\", "\\\\")+"&auth_id="+authId.replace("\\", "\\\\");
			URL url=new URL(link);
			HttpsURLConnection httpsURLConnection=(HttpsURLConnection) url.openConnection();
			httpsURLConnection.setRequestMethod("POST");
			httpsURLConnection.setDoInput(true);
			httpsURLConnection.setDoOutput(true);
			httpsURLConnection.addRequestProperty("User-Agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
			return httpsURLConnection.getInputStream().toString();
		}catch(Exception exception){

		}
		return "false";
	}

	public static User getUser(String authId){
		User user=new User();
		Connection connection=connectDB();
		String sql="select name,email,ph_no,credit,org,details from smart_cards where auth_id like ?";
		try{	
			if(connection!=null){
				PreparedStatement query=connection.prepareStatement(sql);
				query.setMaxRows(1);
				query.setString(1,authId);
				ResultSet result=query.executeQuery();
				while(result.next()){
					user.setAuthId(authId);
					user.setName(result.getString(1));
					user.setEmail(result.getString(2));
					user.setPhoneNumber(result.getString(3));
					user.setCredit(result.getString(4));
					user.setOrganization(result.getString(5));
					user.setDetails(result.getString(6));
				}
				return user;
			}
		}catch(Exception exception){
			exception.printStackTrace();

		}
		return null;
	}
	public static String editUser(User user){
		Connection connection=connectDB();
		String sql="update smart_cards set ";
		try{
			if(connection==null)
				return "error";
			else{
				if(!user.getName().equals("none"))
					sql=sql+"name = '"+user.getName()+"' , ";
				if(!user.getEmail().equals("none"))
					sql=sql+"email = '"+user.getEmail()+"' , ";
				if(!user.getPhoneNumber().equals("none"))
					sql=sql+"ph_no = '"+user.getPhoneNumber()+"' , ";
				if(!user.getCredit().equals("none"))
					sql=sql+"credit = '"+user.getCredit()+"' , ";
				if(!user.getOrganization().equals("none"))
					sql=sql+"org = '"+user.getOrganization()+"' , ";
				if(!user.getDetails().equals("none"))
					sql=sql+"details = '"+user.getDetails()+"' ";
				sql=sql+"where auth_id = '"+user.getAuthId()+"';";
				Statement statement=connection.createStatement();
				return statement.execute("update smart_cards set credit='3' where auth_id = '261993';")+"";
			}
		}catch(Exception exception){
			exception.printStackTrace();
			return "failed";

		}
	}
}


