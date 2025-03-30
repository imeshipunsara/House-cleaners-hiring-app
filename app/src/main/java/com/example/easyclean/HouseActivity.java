package com.example.easyclean;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Set;

public class HouseActivity extends AppCompatActivity {
    EditText edtJ_title, edtJ_des, edtJno_rooms, edtJno_bath, edtJ_floor, edtJno_floors, edtJ_contact, edtJ_address, edtJ_price, edtJ_owner;
    ImageButton ibtn_House;
    ImageView imvHouse;
    TextView txvID;

    Button btnH_add, btnH_Update, btnH_Delete, btnH_view;

    ActivityResultLauncher gallery, cam;
    Bitmap pic;
    SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_house);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        SetDB();
        edtJ_title = findViewById(R.id.edtJ_title);
        edtJ_des = findViewById(R.id.edtJ_des);
        edtJno_rooms = findViewById(R.id.edtJno_rooms);
        edtJno_bath = findViewById(R.id.edtJno_bath);
        edtJ_floor = findViewById(R.id.edtJ_floor);
        edtJno_floors = findViewById(R.id.edtJno_floors);
        edtJ_contact = findViewById(R.id.edtJ_contact);
        edtJ_address = findViewById(R.id.edtJ_address);
        edtJ_owner = findViewById(R.id.edtJ_owner);
        edtJ_price = findViewById(R.id.edtJ_price);
        ibtn_House = findViewById(R.id.ibtn_House);
        imvHouse = findViewById(R.id.imvHouse);
        btnH_add = findViewById(R.id.btnH_add);
        btnH_Update = findViewById(R.id.btnH_Update);
        btnH_Delete = findViewById(R.id.btnH_Delete);
        btnH_view = findViewById(R.id.btnH_view);
        txvID = findViewById(R.id.txvID);

        cam = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult o) {
                        Intent intent = o.getData();
                        pic = (Bitmap) intent.getExtras().get("data");
                    }
                });
        gallery = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult o) {
                        Intent intent = o.getData();
                        Uri uri = intent.getData();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                            ImageDecoder.Source source = ImageDecoder.createSource(getContentResolver(), uri);
                            try {
                                pic = ImageDecoder.decodeBitmap(source);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                });
        ibtn_House.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ibtn_House.getContext());
                builder.setMessage("Please select the option you want use").setTitle("Select Image").
                        setPositiveButton("Use Camera",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                        cam.launch(intent);
                                    }
                                }).setNegativeButton("Use Gallery", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                                intent.setType("image/*");
                                gallery.launch(intent);
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
                imvHouse.setImageBitmap(pic);
            }
        });

        //AddJob logic
        btnH_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Job job = new Job();
                    job.setJobtitle(edtJ_title.getText().toString());
                    job.setJobdes(edtJ_des.getText().toString());
                    job.setNoofrooms(edtJno_rooms.getText().toString());
                    job.setNoofbathrooms(edtJno_bath.getText().toString());
                    job.setFloortype(edtJ_floor.getText().toString());
                    job.setNooffloors(edtJno_floors.getText().toString());
                    job.setOwnername(edtJ_owner.getText().toString());
                    job.setOwnercontactno(edtJ_contact.getText().toString());
                    job.setHomeaddress(edtJ_address.getText().toString());
                    job.setPrice(Double.valueOf(edtJ_price.getText().toString()));
                    if (imvHouse.getDrawable() != null) {
                        BitmapDrawable drawable = (BitmapDrawable) imvHouse.getDrawable();
                        Bitmap bitmap = drawable.getBitmap();
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
                        byte[] homephoto = stream.toByteArray();
                        job.setHomephoto(homephoto);
                    }
                    if (job.AddJob(db))
                        Toast.makeText(getApplicationContext(), "Job Added Successfully", Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(getApplicationContext(), "job not added", Toast.LENGTH_LONG).show();
                } catch (Exception ex) {
                    Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

        ActivityResultLauncher launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult o) {
                        Intent intent = o.getData();
                        int a = intent.getIntExtra("jobID", -1);
                        txvID.setText(String.valueOf(a));
                        edtJ_title.setText(intent.getStringExtra("jobtitle"));
                        edtJ_des.setText(intent.getStringExtra("jobdes"));
                        edtJno_floors.setText(intent.getStringExtra("noofrooms"));
                        edtJno_bath.setText(intent.getStringExtra("noofbathrooms"));
                        edtJ_floor.setText(intent.getStringExtra("floortype"));
                        edtJno_floors.setText(intent.getStringExtra("nooffloors"));
                        edtJ_owner.setText(intent.getStringExtra("ownername"));
                        edtJ_contact.setText(intent.getStringExtra("ownercontactno"));
                        edtJ_address.setText(intent.getStringExtra("homeaddress"));
                        edtJ_price.setText(intent.getStringExtra("price"));
                        byte[] photohome = intent.getByteArrayExtra("homephoto");
                        Bitmap homephoto = BitmapFactory.decodeByteArray(photohome, 0, photohome.length);
                        imvHouse.setImageBitmap(homephoto);
                    }
                });

        //viewjob post method
       btnH_view.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(getApplicationContext(),ViewJobPostActivity.class);
               launcher.launch(intent);
           }
       });
       btnH_Update.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               try {
                   Job job= new Job();
                   job.setJobID(Integer.valueOf(txvID.getText().toString()));
                   job.setJobtitle(edtJ_title.getText().toString());
                   job.setJobdes(edtJ_des.getText().toString());
                   job.setNoofrooms(edtJno_rooms.getText().toString());
                   job.setNoofbathrooms(edtJno_bath.getText().toString());
                   job.setFloortype(edtJ_floor.getText().toString());
                   job.setNooffloors(edtJno_floors.getText().toString());
                   job.setOwnername(edtJ_owner.getText().toString());
                   job.setOwnercontactno(edtJ_contact.getText().toString());
                   job.setHomeaddress(edtJ_address.getText().toString());
                   job.setPrice(Double.valueOf(edtJ_price.getText().toString()));
                   if (imvHouse.getDrawable() !=null)
                   {
                       BitmapDrawable drawable= (BitmapDrawable) imvHouse.getDrawable();
                       Bitmap bitmap= drawable.getBitmap();
                       ByteArrayOutputStream stream = new ByteArrayOutputStream();
                       bitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
                       byte[] homephoto = stream.toByteArray();
                       job.setHomephoto(homephoto);
                   }
                   if (job.UpdateJob(db))
                       Toast.makeText(getApplicationContext(), "Job Updated", Toast.LENGTH_LONG).show();
                   else
                       Toast.makeText(getApplicationContext(), "job not Updated", Toast.LENGTH_LONG).show();
               }
               catch (Exception ex) {
                   Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
               }

           }

       });
       btnH_Delete.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               AlertDialog.Builder builder= new AlertDialog.Builder(v.getContext());
               builder.setMessage("Are you sure, you want to delete?").setTitle("Confirm Delete");
               builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       Job job = new Job();
                       job.setJobID(Integer.valueOf(txvID.getText().toString()));
                       if (job.DeleteJob(db))
                           Toast.makeText(getApplicationContext(),"Job Deleted", Toast.LENGTH_LONG).show();
                       else
                           Toast.makeText(getApplicationContext(),"Failed to delete job", Toast.LENGTH_LONG).show();
                   }
               }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {

                   }
               });
               AlertDialog dialog=builder.create();
               dialog.show();

           }
       });



    }




    private void LoadCam(ImageView imageView) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cam.launch(intent);
    }

    private void Loadgallery(ImageView imageView) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        gallery.launch(intent);
    }

    private void GetImage(ImageView imageView) {
        AlertDialog.Builder builder = new AlertDialog.Builder(imageView.getContext());
        builder.setMessage("Please select the option you want use").setTitle("Select image").
                setPositiveButton("Use camera",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                LoadCam(imageView);
                            }
                        }).setNegativeButton("Use Gallery", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Loadgallery(imageView);
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    private void SetDB() {
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




































