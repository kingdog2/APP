package com.example.ra08d_recycleview_readfromassets_json01_09163104;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    ArrayList<String> personNames;
    ArrayList<String> emailIds;
    ArrayList<String> mobileNumbers;
    Context context;
    public CustomAdapter(Context context,
                         ArrayList<String> personNames,
                        ArrayList<String> emailIds,
                        ArrayList<String> mobileNumbers){
        this.context = context;
        this.personNames = personNames;
        this.emailIds = emailIds;
        this.mobileNumbers = mobileNumbers;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowlayout, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position0) {
        final int position =position0;
        holder.name.setText(personNames.get(position));
        holder.email.setText(emailIds.get(position));
        holder.mobileNo.setText(mobileNumbers.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(context, personNames.get(position), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return personNames.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name, email, mobileNo;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            email = (TextView) itemView.findViewById(R.id.email);
            mobileNo = (TextView) itemView.findViewById(R.id.moblieNo);
        }
    }
}

