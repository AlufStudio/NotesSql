package com.mohak.notessql;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mohak.notessql.R;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.Cityholder> {

    ArrayList<String> data;
    ArrayList<String> data2;
    LayoutInflater inflater;
    Context context;
    sql s;
    SQLiteDatabase sqLiteDatabase;
    //List<singleSecondRow> refers to list of objects of singleSecondRow

    public Adapter(Context context, ArrayList<String> data , sql s) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
        this.s=s;
    }


    @Override
    public Cityholder onCreateViewHolder(ViewGroup viewGroup, int i) {
        //Whenever new row is to be displayed this fn is called
        View view = inflater.inflate(R.layout.singlefaq, viewGroup, false);
        Cityholder viewHolder = new Cityholder(view);
        return viewHolder;

    }


    @Override
    public void onBindViewHolder(final Cityholder viewHolder, final int position) {
        //used to set data to be displayed int the recycler view and update it as per the position (i)
        viewHolder.textView.setText(data.get(position));
       viewHolder.relative.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               Intent intent = new Intent(context,Addnote3.class);
               intent.putExtra("val", viewHolder.textView.getText().toString());
               context.startActivity(intent);

           }
       });
    }


    public int getItemCount() {
        return data.size();
    }


    class Cityholder extends RecyclerView.ViewHolder
            //This class needs to be a sub class of the Adapter class
    {
        RelativeLayout relative;
        TextView textView;

        public Cityholder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.Mytext);
            relative = (RelativeLayout) itemView.findViewById(R.id.view);

        }

    }
}