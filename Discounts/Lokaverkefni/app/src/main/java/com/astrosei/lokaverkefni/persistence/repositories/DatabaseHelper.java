package com.astrosei.lokaverkefni.persistence.repositories;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.astrosei.lokaverkefni.R;
import com.astrosei.lokaverkefni.persistence.entities.Discount;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by astrosei on 04/04/2017.
 * This is the database connector.
 */

public class DatabaseHelper extends SQLiteOpenHelper{

    // Database name.
    public static final String DATABASE_NAME = "Discount.db";

    // Database version.
    public static final int DATABASE_VERSION = 2;

    // Table name.
    public static final String TABLE_NAME = "discount_table";

    // Names of the columns.
    public static final String KEY_ID = "ID";
    public static final String KEY_TITLE = "title";
    public static final String KEY_DISCOUNT_INFO = "discount_info";
    public static final String KEY_COMPANY_NAME = "company_name";
    public static final String KEY_DISCOUNT_PROVIDER = "discount_provider";
    public static final String KEY_CATEGORY = "category";
    public static final String KEY_IS_CONDITION = "is_condition";
    public static final String KEY_CONDITION = "condition";

    public static Context context;

    // Create our database.
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        SQLiteDatabase db = getWritableDatabase();

        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Create our table.
        String CREATE_DISCOUNT_TABLE = "CREATE TABLE "+TABLE_NAME+" ("+
                KEY_ID+" INTEGER PRIMARY KEY,"+
                KEY_TITLE+" TEXT,"+
                KEY_DISCOUNT_INFO+" TEXT,"+
                KEY_COMPANY_NAME+" TEXT,"+
                KEY_DISCOUNT_PROVIDER+" TEXT,"+
                KEY_CATEGORY+" TEXT,"+
                KEY_IS_CONDITION+" BOOLEAN,"+
                KEY_CONDITION+" TEXT)";

        db.execSQL(CREATE_DISCOUNT_TABLE);
        db.close();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table and create again.
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    // Get all discounts
    public List<Discount> getAllDiscounts(){
        List<Discount> discounts = new ArrayList<>();

        String selectQuery = "SELECT * FROM "+ TABLE_NAME+" ORDER BY "+KEY_TITLE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        while (cursor.moveToNext()){
            boolean condition = cursor.getInt(6) > 0;
            Discount discount = new Discount(cursor.getString(1),cursor.getString(2),cursor.getString(3),
                    cursor.getString(4),cursor.getString(5),condition,cursor.getString(7));

            discounts.add(discount);
        }
        cursor.close();
        db.close();
        return discounts;

    }

    // Get discounts.
    public List<Discount> getDiscounts(String category){

        String startingCategory = context.getResources().getString(R.string.starting_category);
        List<Discount> discounts = new ArrayList<>();

        // Get all discounts if "everything" is chosen.
        if(category.equals(startingCategory)){
            discounts = getAllDiscounts();
            return discounts;
        }

        // Get discounts from the chosen category.
        String selectQuery = "SELECT * FROM "+TABLE_NAME+" WHERE "+KEY_CATEGORY+"='"+category+"' " +
                "ORDER BY "+ KEY_TITLE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        while (cursor.moveToNext()){
            Discount discount = new Discount(cursor.getString(1),cursor.getString(2),cursor.getString(3),
                    cursor.getString(4),cursor.getString(5),true,cursor.getString(7));

            discounts.add(discount);
        }
        cursor.close();
        db.close();
        return discounts;

    }

    // Used to manually add discounts into database.
    public void addDiscount(){


        SQLiteDatabase db = this.getWritableDatabase();

        //db.execSQL("DELETE FROM "+ TABLE_NAME+" WHERE company_name='fedex'");
        ContentValues values = new ContentValues();
        /*values.put(KEY_TITLE,"Dalía Blómabúð 10%");
        values.put(KEY_DISCOUNT_INFO,"Býður 10% afslátt af öllu.");
        values.put(KEY_COMPANY_NAME,"dalia");
        values.put(KEY_DISCOUNT_PROVIDER,"hi");
        values.put(KEY_CATEGORY,"Annað");
        values.put(KEY_IS_CONDITION,false);
        values.put(KEY_CONDITION,"");
        db.insert(TABLE_NAME,null,values);*/
        db.close();
    }
}
