package com.example.easyclean;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class Job {
    private int jobID;
    private String jobtitle;
    private String jobdes;
    private String noofrooms;
    private String noofbathrooms;
    private String floortype;
    private String nooffloors;
    private String ownername;
    private String ownercontactno;
    private String homeaddress;
    private Double price;
    private byte[] homephoto;

    public int getJobID() {
        return jobID;
    }

    public String getJobtitle() {
        return jobtitle;
    }

    public String getJobdes() {
        return jobdes;
    }

    public String getNoofrooms() {
        return noofrooms;
    }

    public String getNoofbathrooms() {
        return noofbathrooms;
    }

    public String getFloortype() {
        return floortype;
    }

    public String getNooffloors() {
        return nooffloors;
    }

    public String getOwnername() {
        return ownername;
    }

    public String getOwnercontactno() {
        return ownercontactno;
    }

    public String getHomeaddress() {
        return homeaddress;
    }

    public Double getPrice() {
        return price;
    }

    public byte[] getHomephoto() {
        return homephoto;
    }

    //setters

    public void setJobID(int jobID) {
        this.jobID = jobID;
    }

    public void setJobtitle(String jobtitle) {
        this.jobtitle = jobtitle;
    }

    public void setJobdes(String jobdes) {
        this.jobdes = jobdes;
    }

    public void setNoofrooms(String noofrooms) {
        this.noofrooms = noofrooms;
    }

    public void setNoofbathrooms(String noofbathrooms) {
        this.noofbathrooms = noofbathrooms;
    }

    public void setFloortype(String floortype) {
        this.floortype = floortype;
    }

    public void setNooffloors(String nooffloors) {
        this.nooffloors = nooffloors;
    }

    public void setOwnername(String ownername) {
        this.ownername = ownername;
    }

    public void setOwnercontactno(String ownercontactno) {
        this.ownercontactno = ownercontactno;
    }

    public void setHomeaddress(String homeaddress) {
        this.homeaddress = homeaddress;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setHomephoto(byte[] homephoto) {
        this.homephoto = homephoto;
    }

    // add job method
    boolean AddJob(SQLiteDatabase db) {
        try {
            ContentValues values = new ContentValues();
            values.put("jobtitle", jobtitle);
            values.put("jobdes", jobdes);
            values.put("noofrooms", noofrooms);
            values.put("noofbathrooms", noofbathrooms);
            values.put("floortype", floortype);
            values.put("nooffloors", nooffloors);
            values.put("ownername", ownername);
            values.put("ownercontactno", ownercontactno);
            values.put("homeaddress", homeaddress);
            values.put("price", price);
            values.put("homephoto", homephoto);

            long result = db.insert("JobsTable", null, values);


            return result != -1; // Return true if successful
        } catch (Exception e) {

            return false;
        }
    }

    //get jobs method for veiw jobs
    public List<Job> Getjobs(SQLiteDatabase db){
        List<Job> jobs = new ArrayList<>();
        String query = "Select jobID,jobtitle,jobdes,noofrooms,noofbathrooms,floortype,nooffloors,ownername,ownercontactno,homeaddress,price,homephoto from JobsTable";
        Cursor cursor= db.rawQuery(query,null);
        if (cursor.moveToFirst()){
            do {
                Job job= new Job();
                job.setJobID(cursor.getInt(0));
                job.setJobtitle(cursor.getString(1));
                job.setJobtitle(cursor.getString(2));
                job.setNoofrooms(cursor.getString(3));
                job.setNoofbathrooms(cursor.getString(4));
                job.setFloortype(cursor.getString(5));
                job.setNooffloors(cursor.getString(6));
                job.setOwnername(cursor.getString(7));
                job.setOwnercontactno(cursor.getString(8));
                job.setHomeaddress(cursor.getString(9));
                job.setPrice(cursor.getDouble(10));
                job.setHomephoto(cursor.getBlob(11));

                jobs.add(job);
            }
            while (cursor.moveToNext());

        }
        return jobs;
    }

    public boolean UpdateJob(SQLiteDatabase db){
        try {
            ContentValues values = new ContentValues();
            values.put("jobtitle", jobtitle);
            values.put("jobdes", jobdes);
            values.put("noofrooms", noofrooms);
            values.put("noofbathrooms", noofbathrooms);
            values.put("floortype", floortype);
            values.put("nooffloors", nooffloors);
            values.put("ownername", ownername);
            values.put("ownercontactno", ownercontactno);
            values.put("homeaddress", homeaddress);
            values.put("price", price);
            values.put("homephoto", homephoto);

            long result = db.update("JobsTable",values,"jobID="+jobID,null);


            return result != -1; // Return true if successful

        }catch (Exception ex){
            throw ex;
        }
    }
    public boolean DeleteJob(SQLiteDatabase db){
        try {
            long r = db.delete("JobsTable", "jobID = ?", new String[] {String.valueOf(jobID)});
            if (r > 0)
                return true;
            else
                return false;
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

}

