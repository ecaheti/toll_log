package com.mpe.toll_logger;

import java.util.List;

import android.database.Cursor;

public interface IRepository{
	public List GetAll();
	public T GetById(int id);

	public void Save(T entite);
	public void Update(T entite);
	public void Delete(int id);

	public List ConvertCursorToListObject(Cursor c);
	public T ConvertCursorToObject(Cursor c);
	public T ConvertCursorToOneObject(Cursor c);
}
