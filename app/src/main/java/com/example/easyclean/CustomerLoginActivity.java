package com.example.easyclean;

import android.content.Intent;
import android.content.SharedPreferences;
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

import java.util.Set;

public class CustomerLoginActivity extends AppCompatActivity {

    EditText edtCS_Email,edtCS_Password;

    Button btnCS_Login;

    TextView txvCS_SignupRedirect;

    SQLiteDatabase db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_customer_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        SetDB();
        edtCS_Email=findViewById(R.id.edtCS_Email);
        edtCS_Password=findViewById(R.id.edtCS_Password);
        btnCS_Login=findViewById(R.id.btnCS_Login);
        txvCS_SignupRedirect=findViewById(R.id.txvCS_SignupRedirect);

        txvCS_SignupRedirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(CustomerLoginActivity.this,CustomerSignUpActivity.class);
                startActivity(intent);
            }
        });
        btnCS_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInputs()) {
                    Customer customer = new Customer();
                    customer.setEmail(edtCS_Email.getText().toString());
                    customer.setPassword(edtCS_Password.getText().toString());
                    String name = customer.CheckLogin(db);
                    if (name != null) {
                        // Login successful
                        Toast.makeText(getApplicationContext(), "Login successful!", Toast.LENGTH_SHORT).show();
                        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("userType", "customer"); // "Customer" or "Cleaner"
                        editor.apply();

                        // Navigate to next screen
                        Intent intent = new Intent(CustomerLoginActivity.this, CustomerDashboard.class);
                        startActivity(intent);
                    } else {
                        // Login failed
                        Toast.makeText(getApplicationContext(), "Invalid email or password", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


    }
    private boolean validateInputs(){
        if (edtCS_Email.getText().toString().trim().isEmpty()){
            showError("Email is required");
            return false;
        }
        if (edtCS_Password.getText().toString().trim().isEmpty()){
            showError("password is required");
            return false;
        }
        return true;
    }

    private void showError(String message){
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
    }



    private void SetDB()
    {
        try {
            db = openOrCreateDatabase("CleanEasyDB",MODE_PRIVATE,null);
            db.execSQL("Create Table if not exists "+
                    "Customer(customerID integer primary key autoincrement,"+
                    "firstName text,lastName text, email text, mobileNumber text, homeAddress text, password text)");


        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"Error database"+e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

}