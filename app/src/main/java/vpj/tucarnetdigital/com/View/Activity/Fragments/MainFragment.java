package vpj.tucarnetdigital.com.View.Activity.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.GridLayout;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import vpj.tucarnetdigital.com.Data.Api.Api;
import vpj.tucarnetdigital.com.Data.Api.ApiRoutes;
import vpj.tucarnetdigital.com.Data.Model.Cita;
import vpj.tucarnetdigital.com.Data.preferences.SessionPreferences;
import vpj.tucarnetdigital.com.R;
import vpj.tucarnetdigital.com.View.Adapter.CitaAdapter;

public class MainFragment extends Fragment {
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
        View view = inflater.inflate(R.layout.main_fragment, container, false);
        prefs = new SessionPreferences(getContext());
        Log.d("prueba_prefs", prefs.getUsuario().getName());
        pbCarga = view.findViewById(R.id.pbCarga);
        getCita();
        citaRecycler = (RecyclerView) view.findViewById(R.id.recycleViewC);
        citaRecycler.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(getContext(), 1);
        citaRecycler.setLayoutManager(layoutManager);

        return view;
//        return inflater.inflate(R.layout.main_fragment, container, false);

    }


    private void getCita(){
        final Call<List<Cita>> callCita = Api.getApi().getCita();
        callCita.enqueue(new Callback<List<Cita>>() {
            @Override
            public void onResponse(Call<List<Cita>> call, Response<List<Cita>> response) {



                //Log.d("prueba4", response.body().get(0).getId());
                cita = response.body();
                pbCarga.setVisibility(View.GONE);
                adapter = new CitaAdapter(cita);
                citaRecycler.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Cita>> call, Throwable t) {

            }
        });
    }



}
