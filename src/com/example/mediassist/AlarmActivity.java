package com.example.mediassist;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

public class AlarmActivity extends Activity {
	static ArrayList alarmList = new ArrayList<>();
    AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private TimePicker alarmTimePicker;
    private static AlarmActivity inst;

    public static AlarmActivity instance() {
        return inst;
    }

    @Override
    public void onStart() {
     super.onStart();
        inst = this;
    
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	//long time = savedInstanceState.getLong("time");
        super.onCreate(savedInstanceState);
setContentView(R.layout.activity_home);
		final Button button = (Button) findViewById(R.id.btnCancel);
	     button.setOnClickListener(new View.OnClickListener() {
	         public void onClick(View v) {
	        	 Intent intent = new Intent(AlarmActivity.this, MainActivity.class);
	     		
	     		//intent.putExtra("time","FirstKeyValue");
	     	    startActivity(intent);
	         }
	     });
	     
	     final Button buttonView = (Button) findViewById(R.id.btnView);
         buttonView.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
                 Intent intent = new Intent(AlarmActivity.this, LVActivity.class);
                 intent.putParcelableArrayListExtra("al", alarmList);
                 //intent.putParcelableArrayListExtra(name, value)
                 //intent.putParcelableArrayListExtra(name, value)leArrayListExtra(name, value)leArrayListExtra("al", alarmList);
                 startActivity(intent);
             }
         });
	     
	     final Button btnSv = (Button) findViewById(R.id.btnSave);
	     btnSv.setOnClickListener(new View.OnClickListener() {
	         public void onClick(View v) {
	        	 EditText stDt = (EditText)findViewById(R.id.eTStartDate);
	        	 String stDtStr = stDt.getText().toString();
	        	 EditText endDt = (EditText)findViewById(R.id.eTEndDate);
	        	 String endDtStr = endDt.getText().toString();
	        	 EditText frWk = (EditText)findViewById(R.id.eTFrequencyWeek);
	        	 String frWkStr = frWk.getText().toString();
	        	 EditText frDay = (EditText)findViewById(R.id.eTFrequencyDay);
	        	 String frDayStr = frDay.getText().toString();
	        	 EditText frHr = (EditText)findViewById(R.id.eTFrequencyHour);
	        	 String frHrStr = frHr.getText().toString();
	        	 EditText med = (EditText)findViewById(R.id.eTMedicine);
	        	 String medStr = med.getText().toString();
	        	 EditText desc = (EditText)findViewById(R.id.eTDescription);
	        	 String descStr = desc.getText().toString();
	        	 Alarm a = new Alarm(stDtStr,endDtStr,frWkStr,frDayStr,frHrStr,medStr,descStr);
	        	 alarmList.add(a);
	        	 createAlarm(frHrStr, frDayStr, medStr);
	             //setContentView(R.layout.activity_alarmlist);
	        	 Intent intent = new Intent(AlarmActivity.this, LVActivity.class);
	        	 intent.putParcelableArrayListExtra("al", alarmList);
	        	 //intent.putParcelableArrayListExtra(name, value)
	     		//intent.putParcelableArrayListExtra(name, value)leArrayListExtra(name, value)leArrayListExtra("al", alarmList);
	     	    startActivity(intent);
	         }
	     });
        
    }

 

    public void setAlarmText(String alarmText) {
    	AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
		alertbox.setMessage("Time To Take your medicine");
		alertbox.create();
		alertbox.show();
        
    }
    
    public void createAlarm(String hr, String min, String medicine){
    	int hour = Integer.parseInt(min);
    	int minute = Integer.parseInt(hr);
    	Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
		alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent myIntent = new Intent(AlarmActivity.this, AlarmReceiver.class);
        myIntent.putExtra("medicine", medicine);
        pendingIntent = PendingIntent.getBroadcast(AlarmActivity.this, 0, myIntent, 0);
        alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);
    	
    }
}