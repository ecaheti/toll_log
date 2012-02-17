package com.mpe.toll_logger;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class EventOpenHelper extends SQLiteOpenHelper {

	// Version de la base de données
	private static final int DATABASE_VERSION = 1;

	// Nom de la base
	private static final String EVENT_BASE_NAME = "toll_log_event.db";

	// Nom de la table
	public static final String EVENT_TABLE_NAME = "Toll-Logger-Event";

	// Description des colonnes
	public static final String COLUMN_ID = "ID";
	public static final int NUM_COLUMN_ID = 0;
	public static final String COLUMN_TOLL = "TOLL";
	public static final int NUM_COLUMN_TOLL = 1;
	public static final String COLUMN_DATE = "DATE";
	public static final int NUM_COLUMN_DATE = 2;
	public static final String COLUMN_TIMEOFDAY = "TIMEOFDAY";
	public static final int NUM_COLUMN_TIMEOFDAY = 3;

	// Requête SQL pour la création da la base
	private static final String REQUETE_CREATION_BDD = "CREATE TABLE "
			+ EVENT_TABLE_NAME + " (" + COLUMN_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_TOLL
			+ " TEXT NOT NULL, " + COLUMN_DATE + " INTEGER NOT NULL, "
			+ COLUMN_TIMEOFDAY + " INTEGER NOT NULL);";

	public EventOpenHelper(Context context, CursorFactory factory) {
		super(context, EVENT_BASE_NAME, factory, DATABASE_VERSION);
	}

	/*
	 * Création de la base
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(REQUETE_CREATION_BDD);
	}

	/*
	 * Mise à jour de la base
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Lorsque l'on change le numéro de version de la base on supprime la
		// table puis on la recrée
		if (newVersion > DATABASE_VERSION) {
			db.execSQL("DROP TABLE " + EVENT_TABLE_NAME + ";");
			onCreate(db);
		}
	}


}
