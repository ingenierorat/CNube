package com.rafael.cnube.models;
import com.rafael.cnube.app.MyApp;
import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;


public class Contact extends RealmObject {
    @PrimaryKey
    private int id;
    @Required
    private String nombre;
    @Required
    private String apellidos;
    @Required
    private String telefono;
    @Required
    private String tipo;
    @Required
    private Date createAt;


    public Contact(){

    }

    public Contact(String nombre, String apellidos, String telefono, String tipo){
        this.id = MyApp.ContactoID.incrementAndGet();
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.tipo = tipo;
        this.createAt = new Date();

    }


    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Date getCreateAt() {
        return createAt;
    }

}
