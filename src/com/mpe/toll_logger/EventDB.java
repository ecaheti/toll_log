package com.mpe.toll_logger;

import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class EventDB {
	private static final int DB_VERSION = 1;
	private static final String DB_NAME = "event_toll_logger.db";
 
	private static final String EVENT_TABLE = "Event Table";
	private static final String COL_ID = "ID";
	private static final int NUM_COL_ID = 0;
	private static final String COL_TOLL = "Toll";
	private static final int NUM_COL_TOLL = 1;
	private static final String COL_DATE = "Date";
	private static final int NUM_COL_DATE = 2;
	private static final String COL_TOD = "Time of Day";
	private static final int NUM_COL_TOD = 3;
	
	private SQLiteDatabase mydb;
	 
	private toll_loggerDBSQLite toll_loggerDBSQLite;
 
	public EventDB(Context context){
		//On créer la BDD et sa table
		toll_loggerDBSQLite = new toll_loggerDBSQLite(context, DB_NAME, null, DB_VERSION);
	}
 
	public void open(){
		//on ouvre la BDD en écriture
		mydb = toll_loggerDBSQLite.getWritableDatabase();
	}
 
	public void close(){
		//on ferme l'accès à la BDD
		mydb.close();
	}

	public SQLiteDatabase getBDD(){
		return mydb;
	}

	public List GetAll() {
		return null;

	}

	public long insertEvent(Event evnt){
		//Création d'un ContentValues (fonctionne comme une HashMap)
		ContentValues values = new ContentValues();
		//on lui ajoute une valeur associé à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
		values.put(COL_TOLL, evnt.getToll());
		values.put(COL_DATE, evnt.getDate());
		values.put(COL_TOD, evnt.getToD());
		//on insère l'objet dans la BDD via le ContentValues
		return mydb.insert(EVENT_TABLE, null, values);
	}
	
	public int updateEvent(int id, Event evnt){
		//La mise à jour d'un livre dans la BDD fonctionne plus ou moins comme une insertion
		//il faut simple préciser quelle livre on doit mettre à jour grâce à l'ID
		ContentValues values = new ContentValues();
		values.put(COL_TOLL, evnt.getToll());
		values.put(COL_DATE, evnt.getDate());
		values.put(COL_TOD, evnt.getToD());
		return mydb.update(EVENT_TABLE, values, COL_ID + " = " +id, null);
	}
	
	public int removeEventWithID(int id){
		//Suppression d'un livre de la BDD grâce à l'ID
		return mydb.delete(EVENT_TABLE, COL_ID + " = " +id, null);
	}
 
	public Event getEventWithToll(String toll){
		//Récupère dans un Cursor les valeur correspondant à un toll contenu dans la BDD (ici on sélectionne le toll grâce à son nom)
		Cursor c = mydb.query(EVENT_TABLE, new String[] {COL_ID, COL_TOLL, COL_DATE,COL_TOD }, COL_TOLL + " LIKE \"" + toll +"\"", null, null, null, null);
		return cursorToEvent(c);
	}
	
	//Cette méthode permet de convertir un cursor en un livre
		private Event cursorToEvent(Cursor c){
			//si aucun élément n'a été retourné dans la requête, on renvoie null
			if (c.getCount() == 0)
				return null;
	 
			//Sinon on se place sur le premier élément
			c.moveToFirst();
			//On créé un livre
			Event evnt = new Event();
			//on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
			evnt.setId(c.getInt(NUM_COL_ID));
			evnt.setToll(c.getString(NUM_COL_TOLL));
			evnt.setDate(c.getInt(NUM_COL_DATE));
			evnt.setToD(c.getInt(NUM_COL_TOD)==1);   //equivalent of getBoolean that does not exist.
			//On ferme le cursor
			c.close();
	 
			//On retourne le livre
			return evnt;
		}
	
}
