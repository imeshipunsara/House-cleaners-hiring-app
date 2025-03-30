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

import java.util.Set;

public class CustomerSignUpActivity extends AppCompatActivity {
    EditText edtCustomer_firstName, edtCustomer_lastName, edtCustomer_email, edtCustomer_phoneNumber, edtCustomer_homeAddress, edtCustomer_Password, edtCustomer_confirmPassword;
    Button btnCustomer_SignUp;
    SQLiteDatabase db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_customer_sign_up);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        SetDB();
        edtCustomer_firstName=findViewById(R.id.edtCustomer_firstName);
        edtCustomer_lastName=findViewById(R.id.edtCustomer_lastName);
        edtCustomer_email=findViewById(R.id.edtCustomer_email);
        edtCustomer_phoneNumber=findViewById(R.id.edtCustomer_phoneNumber);
        edtCustomer_homeAddress=findViewById(R.id.edtCustomer_homeAddress);
        edtCustomer_Password=findViewById(R.id.edtCustomer_Password);
        edtCustomer_confirmPassword=findViewById(R.id.edtCustomer_confirmPassword);
        btnCustomer_SignUp=findViewById(R.id.btnCustomer_SignUp);

        btnCustomer_SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInputes()) {
                    try {
                        Customer customer = new Customer();
                        customer.setFirstName(edtCustomer_firstName.getText().toString());
                        customer.setLastName(edtCustomer_lastName.getText().toString());
                        customer.setEmail(edtCustomer_email.getText().toString());
                        customer.setMobileNumber(edtCustomer_phoneNumber.getText().toString());
                        customer.setHomeAddress(edtCustomer_homeAddress.getText().toString());
                        customer.setPassword(edtCustomer_Password.getText().toString());

                        if (customer.AddCustomer(db)) {
                            Toast.makeText(getApplicationContext(), "Registration successful", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(),CustomerLoginActivity.class);
                            startActivity(intent);
                        } else
                            Toast.makeText(getApplicationContext(), "Registration failed", Toast.LENGTH_LONG).show();
                    } catch (Exception ex) {
                        Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

    }
    private boolean validateInputes(){
        if (edtCustomer_firstName.getText().toString().trim().isEmpty()){
            showError("First name is required");
            return false;
        }
        if (edtCustomer_lastName.getText().toString().trim().isEmpty()){
            showError("Last name is required");
            return false;
        }
        if (edtCustomer_email.getText().toString().trim().isEmpty()){
            showError("Email is required");
            return false;
        }
        if (edtCustomer_phoneNumber.getText().toString().trim().isEmpty()){
            showError("Mobile Number is required");
            return false;
        }
        if (edtCustomer_homeAddress.getText().toString().trim().isEmpty()){
            showError("Home Address is required");
            return false;
        }
        if (edtCustomer_Password.getText().toString().trim().isEmpty()){
            showError("Password is required");
            return false;
        }
        if (edtCustomer_Password.getText().toString().trim().isEmpty()){
            showError("Confirm password is required");
            return false;
        }
        if (!edtCustomer_Password.getText().toString().equals(edtCustomer_confirmPassword.getText().toString())){
            showError("Password do not match");
            return false;
        }
        return true;
    }

    private void showError(String message){
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
    }

    //Customer table create
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