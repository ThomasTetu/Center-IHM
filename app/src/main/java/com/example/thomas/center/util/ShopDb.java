package com.example.thomas.center.util;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.thomas.center.model.Product;
import com.example.thomas.center.model.Shop;
import com.example.thomas.center.model.ShopType;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ShopDb extends SQLiteOpenHelper {

    private static String DB_NAME = "myDatabase.db";

    private SQLiteDatabase myDataBase;
    private final Context myContext;

    public ShopDb(Context context) {
        super(context, DB_NAME, null, 1);
        this.myContext = context;
    }

    public void openDataBase() throws SQLException, IOException {
        //Open the database
        String myPath = myContext.getDatabasePath(DB_NAME).getAbsolutePath();
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase();
        if(!dbExist){
            //By calling this method and empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
            this.getWritableDatabase();
            try {
                // Copy the database in assets to the application database.
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database", e);
            }
        }
    }

    private boolean checkDataBase(){
        SQLiteDatabase checkDB = null;
        try{
            String myPath = myContext.getDatabasePath(DB_NAME).getAbsolutePath();
            File file = new File(myPath);
            if (file.exists() && !file.isDirectory()){
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);}
        } catch(SQLiteException e){
            //database doesn't exist yet.
        }
        if(checkDB != null){
            checkDB.close();
        }
        return checkDB != null;
    }

    private void copyDataBase() throws IOException{
        InputStream myInput = myContext.getAssets().open(DB_NAME);
        String outFileName = myContext.getDatabasePath(DB_NAME).getAbsolutePath();
        OutputStream myOutput = new FileOutputStream(outFileName);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    @Override
    public synchronized void close() {
        if(myDataBase != null)
            myDataBase.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public List<Shop> getAllShops() throws MalformedURLException {
        List<Shop> shops = new ArrayList<>();
        Cursor cursor = myDataBase.rawQuery("SELECT * FROM Shop ORDER BY Type DESC", null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            String name = cursor.getString(0);
            String img = cursor.getString(1);
            String prevImg = cursor.getString(2);
            ShopType type = null;
            if(cursor.getInt(3)==1){
                type = ShopType.MODE;
            }else if (cursor.getInt(3)==2){
                type = ShopType.DECO;
            }else if (cursor.getInt(3)==3){
                type = ShopType.SPORT;
            }
            boolean event = !cursor.getString(4).equals("0");
            String description = cursor.getString(6);
            Shop shop = new Shop(name,description,img,prevImg,type,event);
            if(event){
                shop.setDate(cursor.getString(4));
            }
            boolean registered = cursor.getString(5).equals("1");
            if(registered){
                shop.setRegistered();
            }
            shops.add(shop);
            cursor.moveToNext();
        }
        cursor.close();
        return shops;
    }

    private List<Product> getProducts(String brand) throws MalformedURLException{
        List<Product> products = new ArrayList<>();
        Cursor cursor = myDataBase.rawQuery("SELECT * FROM Product WHERE Marque = '"+ brand+"'" , null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            String name = cursor.getString(0);
            String imgpath = cursor.getString(1);
            String type = cursor.getString(2);
            products.add(new Product(name,imgpath,type));
            cursor.moveToNext();
        }
        cursor.close();
        return products;
    }

    public void setBooked(String name){
        myDataBase.execSQL("UPDATE Shop SET Booked=1 WHERE name=\'"+name+"\'");
    }

    public void unSetBooked(String name){
        myDataBase.execSQL("UPDATE Shop SET Booked=0 WHERE name=\'"+name+"\'");
    }

    public List<Shop> getFavorites(){
        List<Shop> shops = new ArrayList<>();
        Cursor cursor = myDataBase.rawQuery("SELECT * FROM Shop WHERE Event=0 AND Booked=1 ORDER BY Type DESC", null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            String name = cursor.getString(0);
            String img = cursor.getString(1);
            String prevImg = cursor.getString(2);
            ShopType type = null;
            if(cursor.getInt(3)==1){
                type = ShopType.MODE;
            }else if (cursor.getInt(3)==2){
                type = ShopType.DECO;
            }else if (cursor.getInt(3)==3){
                type = ShopType.SPORT;
            }
            boolean event = !cursor.getString(4).equals("0");
            String description = cursor.getString(6);
            Shop shop = new Shop(name,description,img,prevImg,type,event);
            if(event){
                shop.setDate(cursor.getString(4));
            }
            boolean registered = cursor.getString(5).equals("1");
            if(registered){
                shop.setRegistered();
            }
            shops.add(shop);
            cursor.moveToNext();
        }
        cursor.close();
        return shops;
    }

    public List<Shop> getBookedEvent(){
        List<Shop> shops = new ArrayList<>();
        Cursor cursor = myDataBase.rawQuery("SELECT * FROM Shop WHERE Event<>0 AND Booked=1 ORDER BY Type DESC", null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            String name = cursor.getString(0);
            String img = cursor.getString(1);
            String prevImg = cursor.getString(2);
            ShopType type = null;
            if(cursor.getInt(3)==1){
                type = ShopType.MODE;
            }else if (cursor.getInt(3)==2){
                type = ShopType.DECO;
            }else if (cursor.getInt(3)==3){
                type = ShopType.SPORT;
            }
            boolean event = !cursor.getString(4).equals("0");
            String description = cursor.getString(6);
            Shop shop = new Shop(name,description,img,prevImg,type,event);
            if(event){
                shop.setDate(cursor.getString(4));
            }
            boolean registered = cursor.getString(5).equals("1");
            if(registered){
                shop.setRegistered();
            }
            shops.add(shop);
            cursor.moveToNext();
        }
        cursor.close();
        return shops;
    }

    public void setProducts(List<Shop> shopList) throws MalformedURLException {
        for (Shop s : shopList){
            s.setProductList(this.getProducts(s.getName()));
        }
    }
}