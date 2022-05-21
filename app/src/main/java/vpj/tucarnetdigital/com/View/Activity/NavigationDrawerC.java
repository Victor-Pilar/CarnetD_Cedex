package vpj.tucarnetdigital.com.View.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import vpj.tucarnetdigital.com.Data.Api.ApiRoutes;
import vpj.tucarnetdigital.com.Data.Model.Cita;
import vpj.tucarnetdigital.com.Data.preferences.SessionPreferences;
import vpj.tucarnetdigital.com.R;
import vpj.tucarnetdigital.com.View.Activity.Fragments.CarnetFragment;
import vpj.tucarnetdigital.com.View.Activity.Fragments.MainFragment;
import vpj.tucarnetdigital.com.View.Activity.Fragments.MapFragment;
import vpj.tucarnetdigital.com.View.Adapter.CarnetAdapter;

public class NavigationDrawerC extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    NavigationView navigationView;
    private ProgressDialog pdDialogo;
    RecyclerView recyclerView;
    Adapter adapter;
    ArrayList<Cita> citas;
    ApiRoutes apiRoutes;
    //Variable para Cargar Nombre de usuario en el nav header
    private SessionPreferences prefs;
    /////////////////////////////////////////////////////////

    //Variables para cargar fragment Principal
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer_c);
        prefs = new SessionPreferences(getApplicationContext());
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigationView);
        //establecer onclick
        navigationView.setNavigationItemSelectedListener(this);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close );
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();
        //Carga Fragment Principal
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container, new MainFragment());
        fragmentTransaction.commit();


        ///////////----Cargar Nombre y email de usuario----////////////
        View header = navigationView.getHeaderView(0);
        TextView lbName = header.findViewById(R.id.lbName);
        TextView lbEmail = header.findViewById(R.id.lbEmail);
        lbName.setText(prefs.getUsuario().getName());
        lbEmail.setText(prefs.getUsuario().getEmail());
        //////////////////////////////////////////////////////////////
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        if(menuItem.getItemId() == R.id.nav_citas){
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, new MainFragment());
            fragmentTransaction.commit();

        }
        if(menuItem.getItemId() == R.id.nav_carnet){
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, new CarnetFragment());
            fragmentTransaction.commit();
        }
        if(menuItem.getItemId() == R.id.nav_clinicaw){
            Intent intent = new Intent(NavigationDrawerC.this, MapsActivity.class);
            startActivity(intent);

//            fragmentManager = getSupportFragmentManager();
//            fragmentTransaction = fragmentManager.beginTransaction();
//            fragmentTransaction.replace(R.id.container, new MapFragment());
//            fragmentTransaction.commit();

        }

        if(menuItem.getItemId() == R.id.nav_user){
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, new PerfilFragment());
            fragmentTransaction.commit();
        }
        if(menuItem.getItemId() == R.id.nav_about){
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, new FragmentAbout());
            fragmentTransaction.commit();

        }
        if(menuItem.getItemId() == R.id.nav_logout){
            pdDialogo = ProgressDialog.show(NavigationDrawerC.this,"Cerrando sesión","Borrando datos...",true,false);
            prefs.cerrarSesion();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    pdDialogo.dismiss();
                    Intent intent = new Intent(NavigationDrawerC.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);


                }
            },3000);

        }
        return false;
    }

}
