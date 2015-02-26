package com.remindus.projetcommun.remindus.view;

import android.app.Activity;
import android.app.ListActivity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.remindus.projetcommun.remindus.R;

import java.util.ArrayList;

/**
 * Created by bahia on 24/02/2015.
 */
public class AfficherContact extends ListFragment{

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.vue_afficher_contact, container,
                false);

        Uri queryUri = ContactsContract.Contacts.CONTENT_URI;
        String[] projection = new String[] {
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.Contacts.HAS_PHONE_NUMBER};

        String selection = ContactsContract.Contacts.DISPLAY_NAME + " IS NOT NULL";

        CursorLoader cursorLoader = new CursorLoader(getActivity(),queryUri, projection,selection,null,null);

        Cursor cursor = cursorLoader.loadInBackground();

        String[] from = {ContactsContract.Contacts.DISPLAY_NAME};
        //String[] from = {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone._ID};
        //int[] to = {android.R.id.text1};
        int[] to = {android.R.id.text1, android.R.id.text2};

        ListAdapter adapter = new SimpleCursorAdapter(
                getActivity(),
                android.R.layout.simple_list_item_1,
                cursor,
                from,
                to,
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        setListAdapter(adapter);
        /*ListView lv;
        Cursor cursor1;

        // create a cursor to query the Contacts on the device to start populating a listview
        cursor1 = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        getActivity().startManagingCursor(cursor1);

        String[] from = {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone._ID}; // get the list items for the listadapter could be TITLE or URI


        int[] to = {android.R.id.text1, android.R.id.text2}; // sets the items from above string to listview

        // new listadapter, created to use android checked template
        SimpleCursorAdapter listadapter = new SimpleCursorAdapter(getActivity(), android.R.layout.simple_list_item_2, cursor1, from, to);


        setListAdapter(listadapter);

        // adds listview so I can get data from it
        lv = getActivity().getListView();
        lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);*/

        return rootView;
    }


    /*private String RecupererContact(String num) {
        String contact = null;
        String numfr = num.replace("+33", "0");
        ContentResolver cr = getActivity().getContentResolver();

        Cursor curNumero = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                ContactsContract.CommonDataKinds.Phone.NUMBER +" = ? OR " + ContactsContract.CommonDataKinds.Phone.NUMBER +" = ?",
                new String[]{num, numfr}, null);

        if(curNumero.moveToFirst()){ //si on [color=red]as[/color] trouvé un numéro
            String id = curNumero.getString(curNumero.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));;
            Cursor curContact = cr.query(ContactsContract.Contacts.CONTENT_URI, null,
                    ContactsContract.Contacts._ID +" = ?", new String[]{id}, null);

            if(curContact.moveToFirst()){
                contact = curContact.getString(curContact.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                curContact.close();
            }
        }

        curNumero.close();
        return contact;
    }*/
}