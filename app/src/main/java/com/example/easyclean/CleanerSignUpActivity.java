package com.example.easyclean;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CleanerSignUpActivity extends AppCompatActivity {

    EditText edtCL_firstname, edtCL_lastName, edtCL_Email, edtCL_Birthday, edtCL_PhoneNumber, edtCL_Experience, edtCL_Password,edtCL_ConfirmPW;
    Button btnCL_SIgnUP;

    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cleaner_sign_up);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        SetCleanerDB();
        edtCL_firstname=findViewById(R.id.edtCL_firstname);
        edtCL_lastName=findViewById(R.id.edtCL_lastName);
        edtCL_Email=findViewById(R.id.edtCL_Email);
        edtCL_Birthday=findViewById(R.id.edtClAge);
        edtCL_PhoneNumber=findViewById(R.id.edtCL_PhoneNumber);
        edtCL_Experience=findViewById(R.id.edtCL_Experience);
        edtCL_Password=findViewById(R.id.edtCL_Password);
        edtCL_ConfirmPW=findViewById(R.id.edtCL_ConfirmPW);
        btnCL_SIgnUP=findViewById(R.id.btnCL_SIgnUP);

        btnCL_SIgnUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateCleanerInputes()){
                    try {
                        Cleaner cleaner = new Cleaner();
                        cleaner.setFirstName(edtCL_firstname.getText().toString());
                        cleaner.setLastName(edtCL_lastName.getText().toString());
                        cleaner.setEmail(edtCL_Email.getText().toString());
                        cleaner.setAge(edtCL_Birthday.getText().toString());
                        cleaner.setMobileNO(edtCL_PhoneNumber.getText().toString());
                        cleaner.setWorkExperience(edtCL_Experience.getText().toString());
                        cleaner.setPassword(edtCL_Password.getText().toString());

                        if (cleaner.AddCleaner(db)){
                            Toast.makeText(getApplicationContext(),"Registration is successfully",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(),CleanerLoginActivity.class);
                            startActivity(intent);
                        }else
                            Toast.makeText(getApplicationContext(), "Registration is failed", Toast.LENGTH_LONG).show();
                    }catch (Exception ex){
                        Toast.makeText(getApplicationContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

    }
    private boolean validateCleanerInputes(){
        if (edtCL_firstname.getText().toString().trim().isEmpty()){
            errorMassage("First name is required");
            return false;
        }
        if (edtCL_lastName.getText().toString().trim().isEmpty()){
            errorMassage("Last name is required");
            return false;
        }
        if (edtCL_Email.getText().toString().trim().isEmpty()){
            errorMassage("Email is required");
            return false;
        }
        if (edtCL_Birthday.getText().toString().trim().isEmpty()){
            errorMassage("Age is required");
            return false;
        }
        if (edtCL_PhoneNumber.getText().toString().trim().isEmpty()){
            errorMassage("Mobile NUmber is required");
            return false;
        }
        if (edtCL_Experience.getText().toString().trim().isEmpty()){
            errorMassage("work experience is required");
            return false;
        }
        if (edtCL_Password.getText().toString().trim().isEmpty()){
            errorMassage("Password is required");
            return false;
        }
        if (!edtCL_ConfirmPW.getText().toString().equals(edtCL_Password.getText().toString())){
            errorMassage("passwerd does not match");
            return false;
        }
        return true;
    }

    private void errorMassage(String massege){
        Toast.makeText(getApplicationContext(),massege,Toast.LENGTH_LONG).show();
    }



    //cleaner DB
    private void SetCleanerDB()
    {
        try {
            db = openOrCreateDatabase("CleanEasyDB",MODE_PRIVATE,null);
            db.execSQL("Create Table if not Exists " +
                    "Cleaner(CleanerID integer primary key autoincrement,"+
                    "firstname text, lastname text, age text, email text, mobileNo text, workExperience text, password text)");

        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "database error"+e.getMessage(),Toast.LENGTH_LONG).show();
        }

    }
}