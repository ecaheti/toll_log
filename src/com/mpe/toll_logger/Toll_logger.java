package com.mpe.toll_logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.os.Environment;
import android.text.format.Time;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;




public class Toll_logger extends Activity {

	private static final int LOG_ACTIVITY_CODE = 1;
	
	Button btn_fill_date;
	Button btn_log;
	DatePicker input_choix_date;
	RadioGroup rdgrp_1;
	EditText toll;
	

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		btn_fill_date 		= (Button)findViewById(R.id.bt_fill_date);
		btn_log 		= (Button)findViewById(R.id.bt_log);
		input_choix_date 	= (DatePicker)findViewById(R.id.datePicker1);
		rdgrp_1 = (RadioGroup)findViewById(R.id.radio_bt_grp_tod);
		toll = (EditText)findViewById(R.id.editText1);

		toll.setText("default");

		//*On attribut un écouteur d'évènement sur le bouton date
		btn_fill_date.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				fill_date();
			}
		});

		//*On attribut un écouteur d'évènement sur le bouton log
		btn_log.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				log_entry();
			}
		});

		

		
		
	}

	// Set DatePicker date to RTC date
	public void fill_date(){
		Time time = new Time();
		time.setToNow();
		input_choix_date.updateDate(time.year,time.month,time.monthDay);
	}

	// Gather all information from Toll_logger activity and log it
	public void log_entry(){
		Context context = getApplicationContext();
		CharSequence text = toll.getText();
		String total;
		String tod;
		int checkedRadioButton = rdgrp_1.getCheckedRadioButtonId();

		switch (checkedRadioButton) {
		case R.id.rad_morning :
			tod = "AM";
			break;
		case R.id.rad_evening:
			tod = "PM";
			break;
		default: 
			tod = "None";
		}

		total = text.toString() + " " + input_choix_date.getYear() + " " + input_choix_date.getMonth() + " " + input_choix_date.getDayOfMonth() + " " + tod ; 

		int duration = Toast.LENGTH_SHORT;

		Toast toast = Toast.makeText(context, total, duration);
		toast.show();
		
		//log event in text file
		//write_log_txtfile(total);
		
		
		// Create Event
		Event evnt = new Event(text.toString(), Date_TO_DB(input_choix_date), (checkedRadioButton ==R.id.rad_morning));

		// log event in database
		write_log_DB(evnt);

		

	}

	// Append new string to text file for simple storage 
	public void write_log_txtfile(String log){
		String NOTES ="toll_log.txt";
		File file = new File(Environment.getExternalStorageDirectory(), NOTES);
		try {
			file.createNewFile();
			FileWriter filewriter = new FileWriter(file,true);
			filewriter.write("\r\n" +log);
			filewriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	// Append new string to text file for simple storage 
	public void write_log_DB(Event evnt){
		//Création d'une instance de ma classe EventDB
		EventDBAdapter db = new EventDBAdapter(this);
        
        //---add 2 titles---
        db.open();
        long id;
        id = db.insertEvent(evnt);
        db.close();

		Toast.makeText(this, "Event wrote in DB event.db", Toast.LENGTH_SHORT).show();
	}
	
	// Convert DatePicker date to formatted date (only day, month, year) for storage database
	public int Date_TO_DB(DatePicker date){
		int dbDate;
		dbDate = date.getDayOfMonth() + 100 * date.getMonth() + 100*100 *date.getYear();
		return dbDate;
	}
	// Convert formatted date (only day, month, year) for storage database to DatePicker date
	public DatePicker DB_TO_Date(int dbDate){
		DatePicker date = null;
		date.updateDate(dbDate % 100, 
				dbDate % 10000 - (dbDate % 100),
				dbDate - dbDate % 10000 - (dbDate % 100));
		return date;
	}

    //Méthode qui se déclenchera lorsque vous appuierez sur le bouton menu du téléphone
    public boolean onCreateOptionsMenu(Menu menu) {
 
        //Création d'un MenuInflater qui va permettre d'instancier un Menu XML en un objet Menu
        MenuInflater inflater = getMenuInflater();
        //Instanciation du menu XML spécifier en un objet Menu
        inflater.inflate(R.menu.menu, menu);
 
        
        return true;
     }
    
    //Méthode qui se déclenchera au clic sur un item
    public boolean onOptionsItemSelected(MenuItem item) {
       //On regarde quel item a été cliqué grâce à son id et on déclenche une action
       switch (item.getItemId()) {
          case R.id.viewDB:
             //Toast.makeText(this, "Not Available", Toast.LENGTH_SHORT).show();
             
             //Creating the Intent that will allow switching to other activity
             Intent intent = new Intent(Toll_logger.this, DBViewerActivity.class);
             
             //Start other Activity
             startActivityForResult(intent, LOG_ACTIVITY_CODE);

             
             return true;
         case R.id.quit:
             //Pour fermer l'application il suffit de faire finish()
             finish();
             return true;
       }
       return false;}
}