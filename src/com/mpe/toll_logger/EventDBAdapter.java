package com.mpe.toll_logger;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class EventDBAdapter {
	public static final String KEY_ROWID = "_id";
	public static final String KEY_TOLL = "toll";
	public static final String KEY_DATE = "date";
	public static final String KEY_TIMEOFDAY = "timeofday";
	private static final String TAG = "EventDBAdapter";
	private static final String DATABASE_NAME = "event.db";
	private static final String DATABASE_TABLE = "eventlog";
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_CREATE =
			"create table eventlog (_id integer primary key autoincrement, "
					+ "toll text not null, date integer not null, "
					+ "timeofday integer not null);";
	private final Context context;
	private DatabaseHelper DBHelper;
	private SQLiteDatabase db;
	
	public EventDBAdapter(Context ctx)
	{
		this.context = ctx;
		DBHelper = new DatabaseHelper(context);
	}

	private static class DatabaseHelper extends SQLiteOpenHelper
	{
		DatabaseHelper(Context context)
		{
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}
		@Override
		public void onCreate(SQLiteDatabase db)
		{
			db.execSQL(DATABASE_CREATE);
		}
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion,
				int newVersion)
		{
			Log.w(TAG, "Upgrading database from version " + oldVersion
					+ " to "
					+ newVersion + ", which will destroy all old data");
			db.execSQL("DROP TABLE IF EXISTS titles");
			onCreate(db);
		}
	}

	//---opens the database---
	public EventDBAdapter open() throws SQLException
	{
		db = DBHelper.getWritableDatabase();
		return this;
	}

	//---closes the database---
	public void close()
	{
		DBHelper.close();
	}
	
	//---insert a Event into the database---
	public long insertEvent(Event evnt)
	{
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_TOLL, evnt.getToll());
		initialValues.put(KEY_DATE, evnt.getDate());
		initialValues.put(KEY_TIMEOFDAY, evnt.getToD());
		return db.insert(DATABASE_TABLE, null, initialValues);
	}

	//---deletes a particular Event---
	public boolean deleteEvent(long rowId)
	{
		return db.delete(DATABASE_TABLE, KEY_ROWID +
				"=" + rowId, null) > 0;
	}
	
	
	//---retrieves all the titles---
	public Cursor getAllEvents()
	{
		return db.query(DATABASE_TABLE, new String[] {
				KEY_ROWID,
				KEY_TOLL,
				KEY_DATE,
				KEY_TIMEOFDAY},
				null,
				null,
				null,
				null,
				null);
	}
	
	//---retrieves a particular Event---
	public Cursor getEvent(long rowId) throws SQLException
	{
		Cursor mCursor =
				db.query(true, DATABASE_TABLE, new String[] {
						KEY_ROWID,
						KEY_TOLL,
						KEY_DATE,
						KEY_TIMEOFDAY
				},
				KEY_ROWID + "=" + rowId,
				null,
				null,
				null,
				null,
				null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}
	

	
	
	//---updates a Event---
	public boolean updateEvent(long rowId,Event evnt)
	{
		ContentValues args = new ContentValues();
		args.put(KEY_TOLL, evnt.getToll());
		args.put(KEY_DATE, evnt.getDate());
		args.put(KEY_TIMEOFDAY, evnt.getToD());
		return db.update(DATABASE_TABLE, args,
				KEY_ROWID + "=" + rowId, null) > 0;
	}
}
