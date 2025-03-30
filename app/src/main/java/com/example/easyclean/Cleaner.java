package com.example.easyclean;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Cleaner {
    private int CleanerID;
    private String firstName;
    private String lastName;
    private String age;
    private String email;
    private String mobileNO;
    private String workExperience;
    private String password;

    public Cleaner() {
    }

    public Cleaner(int cleanerID, String firstName, String lastName, String age, String email, String mobileNO, String workExperience, String password) {
        CleanerID = cleanerID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.mobileNO = mobileNO;
        this.workExperience = workExperience;
        this.password = password;
    }

    public int getCleanerID() {
        return CleanerID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public String getMobileNO() {
        return mobileNO;
    }

    public String getWorkExperience() {
        return workExperience;
    }

    public String getPassword() {
        return password;
    }

    public void setCleanerID(int cleanerID) {
        CleanerID = cleanerID;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMobileNO(String mobileNO) {
        this.mobileNO = mobileNO;
    }

    public void setWorkExperience(String workExperience) {
        this.workExperience = workExperience;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean AddCleaner(SQLiteDatabase db){
        try{
            ContentValues values = new ContentValues();
            values.put("firstName",firstName);
            values.put("lastName",lastName);
            values.put("age",age);
            values.put("email",email);
            values.put("mobileNo",mobileNO);
            values.put("workExperience",workExperience);
            values.put("password",password);

            long result = db.insert("Cleaner",null,values);
            if (result > 0)
                return true;
            else
                return false;
        }catch (Exception ex){
            throw ex;
        }
    }
    public String CheckCleanerLogin (SQLiteDatabase db){
        try {
            String name= null;
            String query="select email from cleaner where email='"+ email
                    +"' and password='"+password+ "'";
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst())
            {
                do {
                    name=cursor.getString(0);
                }
                while (cursor.moveToNext());
            }
            return name;
        }catch (Exception ex){
            throw ex;
        }
    }
}
