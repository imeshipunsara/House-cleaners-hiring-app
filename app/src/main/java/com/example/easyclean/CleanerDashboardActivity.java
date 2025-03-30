package com.example.easyclean;

import android.content.Intent;
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

public class CleanerDashboardActivity extends AppCompatActivity {
    SQLiteDatabase db;
    Button btnView_JobList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cleaner_dashboard);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btnView_JobList=findViewById(R.id.btnView_JobList);

        btnView_JobList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CleanerDashboardActivity.this,ViewJobPostActivity.class);
                startActivity(intent);
            }
        });




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