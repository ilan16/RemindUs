package com.remindus.projetcommun.remindus.controller;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.remindus.projetcommun.remindus.R;
import com.remindus.projetcommun.remindus.model.ModelContact;

import java.util.List;

/**
 * Created by ilanmalka on 15/03/15.
 */
public class CustomAdapterContact extends ArrayAdapter<ModelContact> {

    List<ModelContact> modelItems = null;
    Context context;

    public CustomAdapterContact(Context context,int textViewResourceId ,List<ModelContact> resource) {
        super(context, textViewResourceId, resource);
        this.context = context;
        this.modelItems = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        convertView = inflater.inflate(R.layout.row, parent, false);
        TextView name = (TextView) convertView.findViewById(R.id.textView1);
        CheckBox cb = (CheckBox) convertView.findViewById(R.id.checkBox1);
        name.setText(modelItems.get(position).getContact()+"\n"+modelItems.get(position).getTelephone());
        cb.setTag(position);
        return convertView;
    }

}
