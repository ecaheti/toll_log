package com.mpe.toll_logger;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;


public class toll_loggerDBSQLite extends SQLiteOpenHelper {

	private static final String EVENT_TABLE = "Event Toll-Logger";
	private static final String COL_ID = "ID";
	private static final String COL_TOLL = "Toll";
	private static final String COL_DATE = "Date";
	private static final String COL_TOD = "Time of Day";

	private static final String CREATE_BDD = "CREATE TABLE " + EVENT_TABLE + " ("
			+ COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " 
			+ COL_TOLL + " TEXT NOT NULL, " 
			+ COL_DATE + " INTEGER NOT NULL, "
			+ COL_TOD + " INTEGER NOT NULL);";

	public toll_loggerDBSQLite(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//on créé la table à partir de la requête écrite dans la variable CREATE_BDD
		db.execSQL(CREATE_BDD);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		//On peut fait ce qu'on veut ici moi j'ai décidé de supprimer la table et de la recréer
		//comme ça lorsque je change la version les id repartent de 0
		db.execSQL("DROP TABLE " + EVENT_TABLE + ";");
		onCreate(db);
	}




}
