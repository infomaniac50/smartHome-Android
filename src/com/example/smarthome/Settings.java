package com.example.smarthome;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

public class Settings extends Activity {

	Button save;
	EditText editText;
	String link;
	Context context;
	String textFromPreferences;
	
	
	private static final String PREFERENCES_NAME = "myPreferences";
	private static final String PREFERENCES_LINK = "link";
    private static final String PREFERENCES_SERVICE = "service";
    private SharedPreferences preferences;
	
    
    private void saveData(String key, String values) {
        SharedPreferences.Editor preferencesEditor = preferences.edit();
        preferencesEditor.putString(key, values);
        preferencesEditor.commit();
    }
    
    private void restoreData(String key) {
        textFromPreferences  = preferences.getString(key, "");
   }
    

    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings_layout);

		
		preferences = getSharedPreferences(PREFERENCES_NAME, Activity.MODE_PRIVATE);
		
		editText=(EditText) findViewById (R.id.editText1);
		save=(Button) findViewById (R.id.button1);
		
		
		ToggleButton  onOff = (ToggleButton)findViewById(R.id.togglebutton);
		
		restoreData(PREFERENCES_LINK);
		if(textFromPreferences!= null){
		editText.setHint("Now: " + textFromPreferences);
		}
		
		restoreData(PREFERENCES_SERVICE);
		if(textFromPreferences.equals("true")){
		onOff.setChecked(true);
		}
		if(textFromPreferences.equals("false")){
			onOff.setChecked(false);
		}
		
	OnClickListener save1 = new OnClickListener() {

		@Override
		public void onClick(View v) {
			
	 String link = editText.getText().toString();
	 saveData(PREFERENCES_LINK, link);
	 
	    Context context = getApplicationContext();
		CharSequence text = "Saved";
		int duration = Toast.LENGTH_SHORT;
		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
		
		

			
		}
	};
	save.setOnClickListener(save1);
	
	}
	
	 public void onToggleClicked(View view) {
		  boolean on = ((ToggleButton) view).isChecked();
		  if (on) {
			  startService(new Intent(Settings.this, MyService.class));
		  } else {
			  
			  stopService(new Intent(Settings.this, MyService.class));
		  }
		 }
	
	
	}
	
	
	
	

