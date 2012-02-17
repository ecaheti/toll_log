package com.mpe.toll_logger;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public abstract class Repository implements IRepository {
	// Base de donn�es
	protected SQLiteDatabase myDB;

	protected SQLiteOpenHelper sqLiteOpenHelper;

	/**
	 * Constructeur par d�faut
	 */
	public Repository() {

	}

	/**
	 * Ouverture de la connection
	 */
	public void Open() {
		myDB = sqLiteOpenHelper.getWritableDatabase();
	}

	/**
	 * Fermeture de la connection
	 */
	public void Close() {
		myDB.close();
	}
}
