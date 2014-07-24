package com.android.lexiday;

import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends ActionBarActivity implements OnClickListener{

	EditText uEmail, uPassword;
	String token;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
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
		switch(v.getId()){
		case R.id.button1:
			//do sign in
			onLoginClicked();
			break;
		case R.id.button2:
			//do sign up
			break;
		default:
			break;
		}
	}
	
	private void onLoginClicked() {
	    if(!isNetworkOn(getBaseContext())) 
	    {
	        Toast.makeText(getBaseContext(), "No network connection", Toast.LENGTH_SHORT).show();
	    } 
	    else 
	    {
	    	if(isDataValid())
	    	{
	    		//sign in
	    		token = "this is the token";
	    		Intent i = new Intent(this, MainActivity.class).putExtra("Token", token);
	    		startActivity(i);
	    	}
	    	else
	    	{
	    		//failed login - retry
	    	}
	    }
	}

	/*
	 * checks network access of device
	 */
	public boolean isNetworkOn(Context context) { 
		ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE); 
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
	    return (networkInfo != null && networkInfo.isConnected());
	}
	
	/*
	 * need to add authentication with database & data encryption
	 */
	public boolean isDataValid() {
	    boolean isEmailValid = Patterns.EMAIL_ADDRESS.matcher(getEmail()).matches();
	    boolean isPasswordValid = !getPassword().isEmpty();
	    //return isEmailValid && isPasswordValid;
	    return true;
	}

	public String getPassword() {
	    return uPassword.getText().toString().trim(); // mEditPassword - EditText
	}

	public String getEmail() {
	    return uEmail.getText().toString().trim(); // mEditEmail - EditText
	}
	
}
