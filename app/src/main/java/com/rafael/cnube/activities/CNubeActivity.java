package com.rafael.cnube.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.rafael.cnube.R;
import com.rafael.cnube.fragment.FragmentHome;
import com.rafael.cnube.fragment.FragmentNew;


public class CNubeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        /*Elimina el titulo de la toolbar*/
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        /*Establece el Fragment de la pantalla principal*/
        home();


        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.nv_bottom);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.nv_home:
                        fragmentManager.beginTransaction().replace(R.id.container, new FragmentHome()).commit();
                        break;

                    case R.id.nv_new:
                        fragmentManager.beginTransaction().replace(R.id.container, new FragmentNew()).commit();
                        break;

                    default:
                        return false;

                }
                return true;
            }
        });



        ImageButton imageButtonHelp = (ImageButton)findViewById(R.id.imageButtonHelp);
        imageButtonHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(CNubeActivity.this, "Help", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void home(){
        /*Establecer el Fragment que ocupara la pantalla principal*/
        fragmentManager.beginTransaction().replace(R.id.container, new FragmentHome()).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id){
            case R.id.nav_get_out:
                /*Llama a la activity principal para cerrarla*/
                Intent getOut = new Intent(Intent.ACTION_MAIN);
                /*La cierra*/
                finish();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
