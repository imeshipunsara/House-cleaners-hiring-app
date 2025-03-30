package com.example.easyclean;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class JobAdapter extends RecyclerView.Adapter<JobAdapter.ViewHolder>
{
    SQLiteDatabase db;
    List<Job> jobs;

    String userType;

    public JobAdapter(List<Job>jobList,SQLiteDatabase database, String userType)
    {
        db=database;
        jobs=jobList;
        this.userType=userType;
    }

    @NonNull
    @Override
    public JobAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        View jobitems = inflater.inflate(R.layout.job_layout,parent,false);
        ViewHolder holder= new ViewHolder(jobitems);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull JobAdapter.ViewHolder holder, int position) {

        Job job= jobs.get(position);


        holder.txvVownerName.setText(job.getOwnername());
        holder.txvVContactno.setText(job.getOwnercontactno());
        holder.txvViewHomeAddress.setText(job.getHomeaddress());
        holder.txvViewPrice.setText(job.getPrice().toString());
        holder.txvViewDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (job != null && job.getJobdes() != null) {
                    holder.txvViewDescription.setText(job.getJobdes());
                } else {
                    holder.txvViewDescription.setText("No description available");
                }
            }
        });
        Bitmap bitmap= BitmapFactory.decodeByteArray(job.getHomephoto(),0,job.getHomephoto().length);
        holder.imvVhomephoto.setImageBitmap(bitmap);

        if (this.userType.compareTo("cleaner")==0){
            holder.ibtnVedit.setVisibility(View.GONE);
        }
        else {
            holder.btnCleanerAccept.setVisibility(View.GONE);
        }
        holder.ibtnVedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();

                intent.putExtra("jobID",job.getJobID());
                intent.putExtra("jobtitle",job.getJobtitle());
                intent.putExtra("jobdes",job.getJobdes());
                intent.putExtra("noofrooms", job.getNoofrooms());
                intent.putExtra("noofbathrooms",job.getNoofbathrooms());
                intent.putExtra("floortype",job.getFloortype());
                intent.putExtra("nooffloors",job.getNooffloors());
                intent.putExtra("ownername",job.getOwnername());
                intent.putExtra("ownercontactno",job.getOwnercontactno());
                intent.putExtra("homeaddress",job.getHomeaddress());
                intent.putExtra("price",String.valueOf(job.getPrice()));
                intent.putExtra("homephoto",job.getHomephoto());
                ((Activity)v.getContext()).setResult(Activity.RESULT_OK,intent);
                ((Activity)v.getContext()).finish();
            }
        });


    }

    @Override
    public int getItemCount() {return jobs.size();}

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imvVhomephoto;
        TextView txvVownerName, txvVContactno, txvViewHomeAddress, txvViewPrice, txvViewDescription;
        ImageButton ibtnVedit;
        Button btnCleanerAccept;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imvVhomephoto=itemView.findViewById(R.id.imvVhomephoto);
            txvVownerName=itemView.findViewById(R.id.txvVownerName);
            txvVContactno=itemView.findViewById(R.id.txvVContactno);
            ibtnVedit=itemView.findViewById(R.id.ibtnVedit);
            txvViewHomeAddress=itemView.findViewById(R.id.txvViewHomeAddress);
            txvViewPrice=itemView.findViewById(R.id.txvViewPrice);
            txvViewDescription=itemView.findViewById(R.id.txvViewDescription);
            btnCleanerAccept=itemView.findViewById(R.id.btnCleanerAccept);


        }
    }
}
