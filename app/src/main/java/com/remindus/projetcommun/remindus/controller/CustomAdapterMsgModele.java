package com.remindus.projetcommun.remindus.controller;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.remindus.projetcommun.remindus.R;
import com.remindus.projetcommun.remindus.model.ModelModelMsg;

import java.util.HashMap;
import java.util.List;

/**
 * Created by samairi on 23/03/15.
 */
public class CustomAdapterMsgModele extends ArrayAdapter<ModelModelMsg> {

    List<ModelModelMsg> modelItems = null;
    HashMap<RadioButton, TextView> listbox = null;
    Context context;

    public CustomAdapterMsgModele(Context context, int textViewResourceId, List<ModelModelMsg> resource) {
        super(context, textViewResourceId, resource);
        this.context = context;
        this.modelItems = resource;
        listbox = new HashMap<RadioButton, TextView>();

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        convertView = inflater.inflate(R.layout.row, parent, false);
        TextView name = (TextView) convertView.findViewById(R.id.radioMsg);
        RadioButton cb = (RadioButton) convertView.findViewById(R.id.radioMsg);
        //name.setText(modelItems.get(position).getContact()+"\n"+modelItems.get(position).getTelephone());
        name.setText(modelItems.get(position).getTitre() + " - " +modelItems.get(position).getContenu());
        cb.setTag(position);

        listbox.put(cb, name);

        return convertView;
    }

    public HashMap<RadioButton, TextView> getListChecked() {
        return listbox;
    }

}
