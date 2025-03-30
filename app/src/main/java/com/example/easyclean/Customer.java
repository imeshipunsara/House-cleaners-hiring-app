package com.example.easyclean;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

public class Customer {
    private int customerID;
    private String firstName;
    private String lastName;
    private String email;
    private String mobileNumber;
    private String homeAddress;
    private String password;

    public Customer() {
    }

    public Customer(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public int getCustomerID() {
        return customerID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public void setPassword(String password) {
        this.password = password;
    }

//    database oparations
    public boolean AddCustomer(SQLiteDatabase db){
        try {
            ContentValues values = new ContentValues();
            values.put("firstName",firstName);
            values.put("lastName",lastName);
            values.put("email",email);
            values.put("mobileNumber",mobileNumber);
            values.put("homeAddress",homeAddress);
            values.put("password",password);

            long result = db.insert("Customer",null,values);
            if (result > 0)
                return true;
            else
                return false;
        }catch (Exception ex){
            throw ex;
        }

    }



    public boolean isCustomerExists(SQLiteDatabase db, String email){
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT * FROM Customer WHERE email =?", new String[]{email});
            return cursor.getCount() > 0;
        }finally {
            if (cursor != null){
                cursor.close();
            }
        }
    }

    public boolean validateLogin(SQLiteDatabase db,String email,String password){
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT * FROM Customer WHERE email = ? AND password =?",
                    new String[]{email,password});
            return cursor.getCount() > 0;
        }finally {
            if (cursor != null){
                cursor.close();
            }
        }
    }

    public String CheckLogin(SQLiteDatabase db) {
        try {
            String  name=null;
            String query= "select email from customer where email='"+ email
                    +"' and password='"+password+"'";
            Cursor cursor= db.rawQuery(query,null);
            if(cursor.moveToFirst())
            {
                do
                {
                    name=cursor.getString(0);
                }
                while (cursor.moveToNext());
            }
            return name;
        } catch (Exception ex) {
            throw ex;
        }
    }
}
