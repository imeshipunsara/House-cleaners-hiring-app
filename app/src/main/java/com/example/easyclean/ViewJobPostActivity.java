package com.example.easyclean;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Set;

public class ViewJobPostActivity extends AppCompatActivity {



    SQLiteDatabase db;
    String userType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_job_post);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });




        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        userType = sharedPreferences.getString("userType", "cleaner"); // Default to "Guest" if not set



        RecyclerView rcvJobpost=findViewById(R.id.rcvJobpost);
        rcvJobpost.setLayoutManager(new LinearLayoutManager(this));
        createDBIfNotExist();
        Job job = new Job();
        List<Job> jobs=job.Getjobs(db);
        JobAdapter adapter = new JobAdapter(jobs,db,userType);
        rcvJobpost.setAdapter(adapter);
    }
    private void createDBIfNotExist() {
        try {
            db = openOrCreateDatabase("CleanEasyDB", MODE_PRIVATE, null);
            db.execSQL("CREATE TABLE IF NOT EXISTS JobsTable ("
                    + "jobID INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "jobtitle TEXT, "
                    + "jobdes TEXT, "
                    + "noofrooms TEXT, "
                    + "noofbathrooms TEXT, "
                    + "floortype TEXT, "
                    + "nooffloors TEXT, "
                    + "ownername TEXT, "
                    + "ownercontactno TEXT, "
                    + "homeaddress TEXT, "
                    + "price REAL, "
                    + "homephoto BLOB)");

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error database" + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

}