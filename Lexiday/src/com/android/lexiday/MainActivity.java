package com.android.lexiday;

import java.util.List;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.ParseInstallation;
import com.parse.PushService;



public class MainActivity extends ActionBarActivity {
	
	ParseUser currentUser;
	TextView word, pronunciation, pos, definition;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);   
        setContentView(R.layout.activity_main);
        setTitle("");
        
        /* initializes word info textviews */
        word = (TextView) findViewById(R.id.word);
        pronunciation = (TextView) findViewById(R.id.pronunciation);
        pos = (TextView) findViewById(R.id.part_of_speech);
        definition = (TextView) findViewById(R.id.definition);
        
        Parse.initialize(this, "EbTGZ0TfK4NhcGyX2X4nXS7rDbhPyZGGF4ZAha0M", "M6CfIKGVcYVHU1PrhQanrvuorIlyFXWJ3T92eku6");
        PushService.setDefaultPushCallback(this, MainActivity.class);
        currentUser = ParseUser.getCurrentUser();
        
        if (currentUser == null) 
        {
        	returnToLoginActivity();
        }
        else
        {
        	updateWordOfDay();
        }
    }

    private void loginActivity()
    {
    	Intent i = new Intent(this, LoginActivity.class);
    	i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
    	i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);	//stops the login activity from being pushed onto the stack history
    	startActivityForResult(i, 1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        menu.add("Logout");
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
        else
        {
        	ParseUser.logOut();
        	returnToLoginActivity();
        }
        return super.onOptionsItemSelected(item);
    }
    
    /* 
     * if current user has expired, closes activity and starts login activity 
     */
    private void returnToLoginActivity()
    {
        finish();
        loginActivity();
        return;
    }
    
    /* 
     * query parse DB for word, pronunciation, part of speech and definition 
     * ************ NOTE: still need to determine how we are going to query
     * 					  and determine correct word of the day, currently hard coded
     */
    private void updateWordOfDay()
    {
    	ParseQuery<ParseObject> query = ParseQuery.getQuery("Words");
    	query.whereEqualTo("word", "definition");
    	query.findInBackground(new FindCallback<ParseObject>() {
    	    public void done(List<ParseObject> wordInfo, ParseException e) {
    	        if (e == null) {
    	            Log.d("word", "Retrieved " + wordInfo.size() + " words");
    	        	ParseObject po = wordInfo.get(0);
    	        	word.setText(po.getString("word"));
    	        	pronunciation.setText(po.getString("pron"));
    	        	pos.setText(po.getString("typeOfWord"));
    	        	definition.setText(po.getString("definition"));        	        	
    	        } else {
    	            Log.d("word", "Error: " + e.getMessage());
    	        }
    	    }
    	});
    }
 
}
