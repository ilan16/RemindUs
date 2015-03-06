package com.remindus.projetcommun.remindus.controller;

/**
 * Created by bahia on 26/02/2015.
 */

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.remindus.projetcommun.remindus.R;

public class ControllerContact extends ListActivity {


    @Override
    public long getSelectedItemId() {
        // TODO Auto-generated method stub
        return super.getSelectedItemId();
    }

    @Override
    public int getSelectedItemPosition() {
        // TODO Auto-generated method stub
        return super.getSelectedItemPosition();
    }

    ListView lv;
    Cursor cursor1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_afficher_contact);

        // create a cursor to query the Contacts on the device to start populating a listview
        cursor1 = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
        startManagingCursor(cursor1);

        String[] from = {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone._ID}; // get the list items for the listadapter could be TITLE or URI


        int[] to = {android.R.id.text1, android.R.id.text2}; // sets the items from above string to listview

        // new listadapter, created to use android checked template
        SimpleCursorAdapter listadapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, cursor1, from, to);


        setListAdapter(listadapter);

        // adds listview so I can get data from it
        lv = getListView();
        lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.global, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
