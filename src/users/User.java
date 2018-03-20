package users;

import java.util.ArrayList;
import java.util.Date;

//This class handles all the actions the user can use the application to do.
/*
 * input information and change information
 * rate other users
 * request a list of relevant users from the server
 * check personal information
 */

public class User extends NewAccount {
	
	private static final long serialVersionUID = 1L;
	
	public User(String username, String first, String last, Date birthday) {
		this.setUsername(username);
		this.setFirst(first);
		this.setLast(last);
		this.setBirthday(birthday);
	}

	@Override
	public ArrayList<Object> toSQLRow() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NewAccount fromSQLRow(ArrayList<Object> fields) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
