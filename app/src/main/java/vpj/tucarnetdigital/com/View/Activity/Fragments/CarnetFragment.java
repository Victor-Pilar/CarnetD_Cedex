package vpj.tucarnetdigital.com.View.Activity.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import vpj.tucarnetdigital.com.Data.Api.Api;
import vpj.tucarnetdigital.com.Data.Api.ApiRoutes;
import vpj.tucarnetdigital.com.Data.Model.Carnet;
import vpj.tucarnetdigital.com.Data.Model.Cita;
import vpj.tucarnetdigital.com.Data.preferences.SessionPreferences;
import vpj.tucarnetdigital.com.R;
import vpj.tucarnetdigital.com.View.Activity.CarnetDetalleActivity;
import vpj.tucarnetdigital.com.View.Activity.NavigationDrawerC;
import vpj.tucarnetdigital.com.View.Adapter.CarnetAdapter;
import vpj.tucarnetdigital.com.View.Adapter.CitaAdapter;

public class CarnetFragment extends Fragment implements CarnetAdapter.CarnetAdapterListener {
    private ProgressDialog pdDialogo;
    private List<Cita> cita;
    private ProgressBar pbCarga;
    private RecyclerView citaRecycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private SessionPreferences prefs;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.carnet_fragment, container, false);
        Toast.makeText(getContext(), "Haga Click sobre la cita que quiera vizualiar", Toast.LENGTH_LONG);
        pbCarga = view.findViewById(R.id.pbCargaC);
        prefs = new SessionPreferences(getContext());
//        if(prefs.getUsuario().getId() == cita.get(0).getUser_id()){
//            Log.v("id_pruebaa", "el ide es el mismo");
//        }
        getCarnet();
        citaRecycler = (RecyclerView) view.findViewById(R.id.recycleViewCarnet);
        citaRecycler.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(getContext(), 1);
        citaRecycler.setLayoutManager(layoutManager);



        return view;
    }




    private void getCarnet(){
        Call<List<Cita>> callCita = Api.getApi().getCita();
        callCita.enqueue(new Callback<List<Cita>>() {
            @Override
            public void onResponse(Call<List<Cita>> call, Response<List<Cita>> response) {
//                Log.d("prueba", response.body().get(0).getServicio());
                cita = response.body();
                pbCarga.setVisibility(View.GONE);
                adapter = new CarnetAdapter(cita, CarnetFragment.this);
                citaRecycler.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<Cita>> call, Throwable t) {

            }
        });
    }

    @Override
    public void OnItemClicked(int id) {
        Log.d("id_select","Seleccionaste:  "+id);
        Intent i = new Intent(CarnetFragment.this.getActivity(), CarnetDetalleActivity.class);
        i.putExtra("id", id);
        startActivity(i);
    }
}
