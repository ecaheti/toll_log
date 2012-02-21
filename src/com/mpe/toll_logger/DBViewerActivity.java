package com.mpe.toll_logger;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DBViewerActivity extends Activity{
	
	
	private static final int VIEWER_ACTIVITY_CODE = 1;
	
	
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dbviewer);
		
		
		Button btn_refr = (Button)findViewById(R.id.btn_refr);
		
		
		//*On attribut un écouteur d'évènement sur le bouton date
		btn_refr.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				logview();
				
			}
		});
		
	}
	
	

	
    //Méthode qui se déclenchera lorsque vous appuierez sur le bouton menu du téléphone
    public boolean onCreateOptionsMenu(Menu menu) {
 
        //Création d'un MenuInflater qui va permettre d'instancier un Menu XML en un objet Menu
        MenuInflater inflater = getMenuInflater();
        //Instanciation du menu XML spécifier en un objet Menu
        inflater.inflate(R.menu.viewermenu, menu);
 
        
        return true;
     }
    
    //Méthode qui se déclenchera au clic sur un item
    public boolean onOptionsItemSelected(MenuItem item) {
       //On regarde quel item a été cliqué grâce à son id et on déclenche une action
       switch (item.getItemId()) {
          case R.id.viewlogger:
             //Toast.makeText(this, "Not Available", Toast.LENGTH_SHORT).show();
             
             //Creating the Intent that will allow switching to other activity
             Intent intent = new Intent(DBViewerActivity.this, Toll_logger.class);
             
             //Start other Activity
             startActivityForResult(intent, VIEWER_ACTIVITY_CODE);
             
             return true;
         case R.id.quit:
             //Pour fermer l'application il suffit de faire finish()
             finish();
             return true;
       }
       return false;}

    public void logview()
    {
    	EventDBAdapter db = new EventDBAdapter(this);
		EditText evnt_id_input = (EditText)findViewById(R.id.evnt_id_input);
		Cursor c;
		//---get a title---
        db.open();
        c = db.getEvent(Integer.parseInt(evnt_id_input.getText().toString()));
        if (c.moveToFirst())
        {
        	DisplayEvent(c);
        }
        else
        {
        	Toast.makeText(this, "No title found",Toast.LENGTH_LONG).show();
        }
        db.close();
    }

    public void DisplayEvent(Cursor c)
    {
    	Toast.makeText(this,
    			"id: " + c.getString(0) + "\n" +
    					"Toll: " + c.getString(1) + "\n" +
    					"Date: " + c.getString(2) + "\n" +
    					"ToD: " + c.getString(3),
    					Toast.LENGTH_LONG).show();
    }


}
