package com.remindus.projetcommun.remindus.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.remindus.projetcommun.remindus.R;

public class ControllerMail extends ControllerHeader {

    Button btnOK;
    EditText txtTo;
    EditText txtSubject;
    EditText txtMessage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_afficher_mail);

        btnOK = (Button) findViewById(R.id.mail_bouton);
        txtTo = (EditText) findViewById(R.id.mail_destinataire_edit);
        txtSubject = (EditText) findViewById(R.id.mail_sujet_edit);
        txtMessage = (EditText) findViewById(R.id.mail_message_edit);

        btnOK.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String to = txtTo.getText().toString();
                String subject = txtSubject.getText().toString();
                String message = txtMessage.getText().toString();
                Intent mail = new Intent(Intent.ACTION_SEND);
                mail.putExtra(Intent.EXTRA_EMAIL, new String[]{to});
                mail.putExtra(Intent.EXTRA_SUBJECT, subject);
                mail.putExtra(Intent.EXTRA_TEXT, message);
                mail.setType("message/rfc822");
                startActivity(Intent.createChooser(mail, "Send email via:"));
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.global, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        switch (item.getItemId()) {
            case R.id.bouton_parametre:
                Intent intent = new Intent(ControllerMail.this, ControllerParametre.class);
                startActivity(intent);
                break;

        }

        return false;
    }


}