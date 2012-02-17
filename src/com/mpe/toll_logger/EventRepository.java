package com.mpe.toll_logger;

import java.util.List;

import android.content.Context;
import android.database.Cursor;

public class EventRepository extends Repository{

	public EventRepository(Context context) {
		sqLiteOpenHelper = new EventOpenHelper(context, null);
	}

	/**
	 * Suppression d'un produit
	 *
	 * @param id
	 */
	public void DeleteProduit(int id) {
		myDB.delete(EventOpenHelper.EVENT_TABLE_NAME,
				EventOpenHelper.COLUMN_ID + "=?",
				new String[] { String.valueOf(id) });
	}

	/**
	 * Récupération de la liste de tous les produits
	 */
	@Override
	public List GetAll() {
		// Récupération de la liste des courses
		Cursor cursor = myDB.query(EventOpenHelper.EVENT_TABLE_NAME,
				new String[] { EventOpenHelper.COLUMN_ID,
				EventOpenHelper.COLUMN_TOLL,
				EventOpenHelper.COLUMN_DATE,
				EventOpenHelper.COLUMN_TIMEOFDAY }, null, null, null,
				null, null);

		return ConvertCursorToListObject(cursor);
	}

		    /**
		     * Retourne un seul produit
		     */
		    @Override
		    public Event GetById(int id) {
		        Cursor cursor = myDB.query(EventOpenHelper.EVENT_TABLE_NAME,
		                new String[] { EventOpenHelper.COLUMN_ID,
						EventOpenHelper.COLUMN_TOLL,
						EventOpenHelper.COLUMN_DATE,
						EventOpenHelper.COLUMN_TIMEOFDAY },
						EventOpenHelper.COLUMN_ID + "=?",
		                new String[] { String.valueOf(id) }, null, null, null);
		 
		        return ConvertCursorToObject(cursor);
	/*48*/	    }
		    //à finir http://www.ace-art.fr/wordpress/2011/10/08/tutoriel-android-partie-16-base-de-donnees/

}
