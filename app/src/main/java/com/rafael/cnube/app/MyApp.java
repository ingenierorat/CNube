package com.rafael.cnube.app;

import android.app.Application;

import com.rafael.cnube.models.Contact;
import com.rafael.cnube.models.Contact;

import java.util.concurrent.atomic.AtomicInteger;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class MyApp extends Application {

    public static  AtomicInteger ContactoID = new AtomicInteger();

    @Override
    public void onCreate() {
        super.onCreate();

        /*Llama el mètodo de configuración de Realm*/
        setUpRealmConfig();

        Realm realm = Realm.getDefaultInstance();
        /*Llamar el mètodo y pasarle la Base de Datos y la Tabla a consultar*/
        ContactoID = getIdByTable(realm, Contact.class);
        /*Cerrar la DB*/
        realm.close();

    }

    private void setUpRealmConfig(){
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration
                .Builder().name("CNubeSource.realm")
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);

    }

    private <T extends RealmObject> AtomicInteger getIdByTable(Realm realm, Class<T> anyClass){
        RealmResults<T> results = realm.where(anyClass).findAll();
        return (results.size() > 0) ? new AtomicInteger(results.max("id").intValue()): new AtomicInteger() ;

    }


}
