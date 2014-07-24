package com.android.lexiday;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;



public class MainActivity extends ActionBarActivity {
	
	String token = null;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    
        if (!isUserSignedIn()) {
            finish();
            loginActivity();
            return;
        }
        else   
        {
        	//setContentView(R.layout.activity_main);
        	 WebView webview = new WebView(this);
        	 setContentView(webview);
        	 webview.loadUrl("http://www.lexiday.com/");
        }
    }

    private boolean isUserSignedIn(){
    	Intent i = getIntent();
    	token = i.getStringExtra("Token");
    	if(token != null)	// change to check token validity / expiration
    		return true;
    	else
    		return false;
    }
    
    private void loginActivity()
    {
    	Intent i = new Intent(this, LoginActivity.class);
    	i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
    	startActivityForResult(i, 1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
 
}
