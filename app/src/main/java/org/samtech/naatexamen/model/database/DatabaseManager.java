package org.samtech.naatexamen.model.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseManager extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "proximate_test_db";


    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(Sales.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Sales.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public long insertSale(Sales sales) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues salesRecord = new ContentValues();
        salesRecord.put(Sales.COLUMN_BC_NAME, sales.getBcname());
        salesRecord.put(Sales.COLUMN_CELL_NUMBER, sales.getCellnumber());
        salesRecord.put(Sales.COLUMN_AMOUNT, sales.getAmount());
        salesRecord.put(Sales.COLUMN_CONCEPT, sales.getConcept());

        long id = db.insert(Sales.TABLE_NAME, null, salesRecord);
        db.close();
        return id;
    }

    public void deletaSale(long sale_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Sales.TABLE_NAME, Sales.COLUMN_ID + " = ?",
                new String[]{String.valueOf(sale_id)});
    }

    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + Sales.TABLE_NAME);
    }

    public Sales getSale(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Sales.TABLE_NAME,
                new String[]{Sales.COLUMN_ID, Sales.COLUMN_BC_NAME, Sales.COLUMN_CELL_NUMBER,
                        Sales.COLUMN_AMOUNT, Sales.COLUMN_CONCEPT},
                Sales.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        assert cursor != null;
        Sales sale = new Sales(
                cursor.getInt(cursor.getColumnIndex(Sales.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(Sales.COLUMN_BC_NAME)),
                cursor.getString(cursor.getColumnIndex(Sales.COLUMN_CELL_NUMBER)),
                cursor.getString(cursor.getColumnIndex(Sales.COLUMN_AMOUNT)),
                cursor.getString(cursor.getColumnIndex(Sales.COLUMN_CONCEPT)));

        cursor.close();
        return sale;
    }

    public List<Sales> getAllSales() {
        List<Sales> salesList = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + Sales.TABLE_NAME + " ORDER BY " +
                Sales.COLUMN_ID + " ASC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Sales sales = new Sales();
                sales.setId(cursor.getInt(cursor.getColumnIndex(Sales.COLUMN_ID)));
                sales.setBcname(cursor.getString(cursor.getColumnIndex(Sales.COLUMN_BC_NAME)));
                sales.setCellnumber(cursor.getString(cursor.getColumnIndex(Sales.COLUMN_CELL_NUMBER)));
                sales.setAmount(cursor.getString(cursor.getColumnIndex(Sales.COLUMN_AMOUNT)));
                sales.setConcept(cursor.getString(cursor.getColumnIndex(Sales.COLUMN_CONCEPT)));

                salesList.add(sales);
            } while (cursor.moveToNext());
        }

        db.close();
        return salesList;
    }

}
