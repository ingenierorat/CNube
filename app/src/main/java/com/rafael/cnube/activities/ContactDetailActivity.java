package com.rafael.cnube.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import com.rafael.cnube.R;
import com.rafael.cnube.models.Contact;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import io.realm.Realm;


public class ContactDetailActivity extends AppCompatActivity {


    private Realm realm;

    private int contactId;
    private TextView textViewId;
    private TextView textViewFirtName;
    private TextView textViewLastName;
    private TextView textViewPhone;
    private TextView textViewType;
    private TextView textViewDate;
    private Contact contact;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Detalles Contacto");

        /*Instancia de realm*/
        realm = Realm .getDefaultInstance();
        /*Referenciar todos los elementos a utilizar*/
        toAssignUI();
        /*Recoje los datos enviados desde la actvidad principal*/
        getExtras();



    }

    private void toAssignUI(){
        textViewId = (TextView) findViewById(R.id.textViewId);
        textViewFirtName = (TextView) findViewById(R.id.textViewFirstName);
        textViewLastName = (TextView) findViewById(R.id.textViewLastName);
        textViewPhone = (TextView) findViewById(R.id.textViewPhone);
        textViewType = (TextView) findViewById(R.id.textViewType);
        textViewDate = (TextView) findViewById(R.id.textViewDate);
    }

    private void getExtras(){
        if (getIntent().getExtras() != null){
            contactId = getIntent().getExtras().getInt("id");
            Contact contact = realm.where(Contact.class).equalTo("id", contactId).findFirst();
            textViewId.setText("Id " + contact.getId());
            textViewFirtName.setText("Nombre " + contact.getNombre());
            textViewLastName.setText("Apellidos " + contact.getApellidos());
            textViewPhone.setText("Telèfono " + contact.getTelefono());
            textViewType.setText("Tipo " + contact.getTipo());
            /*Formatear la fecha y presentarla de tipo corta*/
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String createAt = dateFormat.format(contact.getCreateAt());
            textViewDate.setText("Fecha " + createAt);
        }
    }



}
