package com.svastek.sqliteexample;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vinil on 30/6/18.
 */

class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int    DATABASE_VERSION        = 1;

    // Database Name
    private static final String DATABASE_NAME           = "SQLiteExample";

    // table name
    private static final String TABLE_PRODUCTS          = "productsTable";

    // Table Columns names
    private static final String KEY_ID                  = "id";
    private static final String KEY_NAME                = "itemName";
    private static final String KEY_PRICE               = "price";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String createProductsTable =  "CREATE TABLE "
                + TABLE_PRODUCTS    + "("
                + KEY_ID            + " INTEGER PRIMARY KEY,"
                + KEY_NAME          + " TEXT,"
                + KEY_PRICE          + " INTEGER )";
        db.execSQL(createProductsTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
    }

    public void addProduct(Product product){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID,        product.getId());
        values.put(KEY_NAME,        product.getName());
        values.put(KEY_PRICE,        product.getPrice());

        db.insert(TABLE_PRODUCTS, null, values);
        db.close();
    }

    public List<Product> getAllProducts(){
        List<Product> products = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_PRODUCTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Product product=new Product();
                product.setId(      Integer.parseInt(   cursor.getString(0)));
                product.setName(                        cursor.getString(1));
                product.setPrice(    Integer.parseInt(   cursor.getString(2)));

                // Adding product to list
                products.add(product);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return products;
    }

    public void deleteProduct(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PRODUCTS, KEY_ID + " = ?",
                new String[] { String.valueOf(id) });
        db.close();
    }

    public int getLastProductId() {
        List<Product> products = getAllProducts();

        if (products.size()==0)
            return 0;

        return products.get(products.size()-1).getId();
    }

}
