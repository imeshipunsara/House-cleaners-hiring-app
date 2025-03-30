package com.example.easyclean;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CleanerLoginActivity extends AppCompatActivity {

    EditText edtCLog_Email,edtCLog_password;
    Button btnCLog_Login;
    TextView txvCL_SignupRedirect;

    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cleaner_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        SetCleanerDB();
        edtCLog_Email=findViewById(R.id.edtCLog_Email);
        edtCLog_password=findViewById(R.id.edtCLog_password);
        btnCLog_Login=findViewById(R.id.btnCLog_Login);
        txvCL_SignupRedirect=findViewById(R.id.txvCL_SignupRedirect);

        txvCL_SignupRedirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CleanerLoginActivity.this,CleanerSignUpActivity.class);
                startActivity(intent);
            }
        });
        btnCLog_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateCleanerInputes()){
                    Cleaner cleaner = new Cleaner();
                    cleaner.setEmail(edtCLog_Email.getText().toString());
                    cleaner.setPassword(edtCLog_password.getText().toString());
                    String name = cleaner.CheckCleanerLogin(db);
                    if (name != null){
                        //Login Successfull
                        Toast.makeText(getApplicationContext(),"Login is successfull",Toast.LENGTH_LONG).show();
                        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("userType", "cleaner"); // "Customer" or "Cleaner"
                        editor.apply();


                        //navigate to next screen
                        Intent intent = new Intent(CleanerLoginActivity.this,CleanerDashboardActivity.class);
                        startActivity(intent);
                    }else {
                        //login failed
                        Toast.makeText(getApplicationContext(), "Invalied your Email or password",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

    }
    private boolean validateCleanerInputes(){
        if (edtCLog_Email.getText().toString().trim().isEmpty()){
            errorMassage("Email is required");
            return false;
        }
        if (edtCLog_password.getText().toString().trim().isEmpty()){
            errorMassage("password is requied");
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