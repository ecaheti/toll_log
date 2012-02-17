package com.mpe.toll_logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.text.format.Time;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;




public class Toll_logger extends Activity {
    
	Button btn_fill_date;
	Button btn_log;
	DatePicker input_choix_date;
	RadioGroup rdgrp_1;
	EditText toll;
    Time time = new Time();

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


	public void fill_date(){
	    time.setToNow();
		input_choix_date.updateDate(time.year,time.month,time.monthDay);
    }

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
		
		total = text.toString() + " " 
				+ input_choix_date.getYear() + " "
				+ input_choix_date.getMonth() + " "
				+ input_choix_date.getDayOfMonth() + " "
				+ tod + "\r\n"; 
		
		int duration = Toast.LENGTH_SHORT;

		Toast toast = Toast.makeText(context, total, duration);
		toast.show();
		write_log(total);

	}	
	public void write_log(String log){
		String NOTES ="toll_log.txt";
		File file = new File(Environment.getExternalStorageDirectory(), NOTES);
		try {
			file.createNewFile();
			FileWriter filewriter = new FileWriter(file,true);
			filewriter.write(log);
			filewriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

    }
	

}