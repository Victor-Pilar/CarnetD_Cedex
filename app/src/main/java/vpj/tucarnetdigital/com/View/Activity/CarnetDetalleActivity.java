package vpj.tucarnetdigital.com.View.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vpj.tucarnetdigital.com.Data.Api.Api;
import vpj.tucarnetdigital.com.Data.Model.Cita;
import vpj.tucarnetdigital.com.R;
import vpj.tucarnetdigital.com.View.Activity.Fragments.FragmentDetalleCarnet;
import vpj.tucarnetdigital.com.View.Activity.Fragments.FragmentDetalleCarnet2;
import vpj.tucarnetdigital.com.View.Activity.Fragments.FragmentDetalleCarnet3;
import vpj.tucarnetdigital.com.View.Activity.Fragments.FragmentDetalleCarnet4;
import vpj.tucarnetdigital.com.View.Adapter.CarnetVPAdapter;

public class CarnetDetalleActivity extends AppCompatActivity {

    private TabLayout tabCarnet;
    private ViewPager pagerCarnet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carnet_detalle);
        Intent i = getIntent();
        String id = i.getStringExtra("id");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setElevation(0);

        pagerCarnet = findViewById(R.id.pagerCarnet);
        setupViewPager(pagerCarnet);
        tabCarnet = findViewById(R.id.tabCarnet);
        tabCarnet.setupWithViewPager(pagerCarnet);


    }

    private void setupViewPager(ViewPager vp){
        CarnetVPAdapter adapter = new CarnetVPAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentDetalleCarnet(), "Datos Generales");
        adapter.addFragment(new FragmentDetalleCarnet2(), "Consulta General");
        adapter.addFragment(new FragmentDetalleCarnet4(), "Nutricion");
        adapter.addFragment(new FragmentDetalleCarnet3(), "Vacunas");

        vp.setAdapter(adapter);
    }
}
