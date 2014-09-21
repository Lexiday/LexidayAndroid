package com.android.lexiday;

import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.ParseException;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class LoginActivity extends ActionBarActivity implements OnClickListener{

	EditText uEmail, uPassword;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		Parse.initialize(this, "EbTGZ0TfK4NhcGyX2X4nXS7rDbhPyZGGF4ZAha0M", "M6CfIKGVcYVHU1PrhQanrvuorIlyFXWJ3T92eku6");
		setTitle("");
		uEmail = (EditText)findViewById(R.id.user_email);
		uPassword = (EditText)findViewById(R.id.user_password);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		if(!isNetworkOn(getBaseContext())) 
	    {
	        Toast.makeText(getBaseContext(), "No network connection", Toast.LENGTH_SHORT).show();
	    }
		else
		{
			switch(v.getId()){
				case R.id.signIn:
					//do sign in
					onLoginClicked();
					break;
				case R.id.signUp:
					onSignupClicked();
					break;
				default:
					break;
			}
		}
	}
	
	
	/*
	 * verifies user is already a user in parse.com
	 * and signs them in
	 */
	private void onLoginClicked() throws ParseException {
    	if(isDataValid())
    	{    		
    		ParseUser.logInInBackground(getEmail(), getPassword(), new LogInCallback() {
				@Override
				public void done(ParseUser user, com.parse.ParseException e) {
					if (user != null) 
					{
    			    	startMainActivity();
    			    } 
					else {
    			      // Signup failed. Look at the ParseException to see what happened.
    	        		Toast.makeText(getBaseContext(), "Wrong username or password", Toast.LENGTH_SHORT).show();
    			    	System.out.println(e.toString());
    			    }
				}
    		});
    	}
    	

	}
	
	/*
	 * sign user up, and add them to parse.com database
	 */
	public void onSignupClicked(){
    	if(isDataValid())
    	{
    		//sign up
    		ParseUser user = new ParseUser();
    		user.setUsername(getEmail());
    		user.setPassword(getPassword());
    		user.setEmail(getEmail());
    		user.signUpInBackground(new SignUpCallback() {
				@Override
				public void done(com.parse.ParseException e) {
					if (e == null) 
				    {
				    	startMainActivity();
					}
				    else 
					{
					  // Sign up didn't succeed. Look at the ParseException
				    	System.out.println(e.toString());
    	        		Toast.makeText(getBaseContext(), "Sign up failed", Toast.LENGTH_SHORT).show();

				    }
				}
    		});
    	}
	}

	/*
	 * checks network access of device
	 */
	private boolean isNetworkOn(Context context) { 
		ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE); 
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
	    return (networkInfo != null && networkInfo.isConnected());
	}
	
	/*
	 * verifies the input fields are filled with usable strings
	 */
	private boolean isDataValid() {
	    boolean isEmailValid = Patterns.EMAIL_ADDRESS.matcher(getEmail()).matches();
	    boolean isPasswordValid = !getPassword().isEmpty();
	    return isEmailValid && isPasswordValid;
	    //return true;
	}
	
	/*
	 * starts main activity after logging in is successful
	 */
	private void startMainActivity()
	{
		Intent i = new Intent(this, MainActivity.class);
		startActivity(i);
	}

	/*
	 * returns user password
	 */
	private String getPassword() {
	    return uPassword.getText().toString().trim(); // user password - EditText
	}

	/*
	 * returns user email
	 */
	private String getEmail() {
	    return uEmail.getText().toString().trim(); // user email - EditText
	}
	
}
