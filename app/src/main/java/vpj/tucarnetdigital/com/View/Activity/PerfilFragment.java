package vpj.tucarnetdigital.com.View.Activity;


import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vpj.tucarnetdigital.com.Data.Api.Api;
import vpj.tucarnetdigital.com.Data.Model.Cita;
import vpj.tucarnetdigital.com.Data.preferences.SessionPreferences;
import vpj.tucarnetdigital.com.R;
import vpj.tucarnetdigital.com.View.Activity.Fragments.FragmentDetalleCarnet;
import vpj.tucarnetdigital.com.View.Activity.Fragments.MainFragment;
import vpj.tucarnetdigital.com.View.Adapter.CarnetAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class PerfilFragment extends Fragment implements CarnetAdapter.CarnetAdapterListener{

    private List<Cita> cita;
    private ProgressBar pbCarga;
    private RecyclerView citaRecycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    TextView txt_user, txt_email, txt_edad, txt_nns, txt_curp, txt_dom, txt_enfed, txt_fnacimiento, txt_lnacimiento;
    ImageView imgEditar;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;


    private SessionPreferences prefs;


    public PerfilFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);
        // Inflate the layout for this fragment
        pbCarga = view.findViewById(R.id.pbCarga);



        getDatosUser();
        txt_edad = view.findViewById(R.id.txt_edad);

        txt_curp = view.findViewById(R.id.txt_curp);
        txt_dom = view.findViewById(R.id.txt_dom);
        txt_enfed = view.findViewById(R.id.txt_enfed);
        txt_fnacimiento = view.findViewById(R.id.txt_enfed);
        txt_lnacimiento = view.findViewById(R.id.txt_lnacimiento);

        imgEditar = view.findViewById(R.id.imgEditar);
        Log.d("pruebatest2", "prueba si entra aqui");
        prefs = new SessionPreferences(getContext());
        txt_user = view.findViewById(R.id.txt_user);
        txt_email = view.findViewById(R.id.txt_email);
        txt_user.setText(prefs.getUsuario().getName());
        txt_email.setText(prefs.getUsuario().getEmail());

        imgEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i = new Intent(PerfilFragment.this.getActivity(), fragmentDatosPerfil.class);
//                startActivity(i);
                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, new fragmentDatosPerfil());
                fragmentTransaction.commit();

            }
        });

        return view;

    }

    public void getDatosUser(){
        Call<Cita> callCita = Api.getApi().getCarnet(getActivity().getIntent().getIntExtra("id", 0));
        callCita.enqueue(new Callback<Cita>() {
            @Override
            public void onResponse(Call<Cita> call, Response<Cita> response) {



                pbCarga.setVisibility(View.GONE);

                txt_edad.setText("Edad: "+response.body().getEdad());

                txt_curp.setText("Curp: "+response.body().getCurp());
                txt_dom.setText("Domicilio: "+response.body().getCyn());
                txt_enfed.setText("Entidad Federativa: "+response.body().getEfed());
                txt_fnacimiento.setText("Fecha de Nacimiento: "+response.body().getFechanac());
                txt_lnacimiento.setText("Lugar Nac.: "+response.body().getEntidad());
            }

            @Override
            public void onFailure(Call<Cita> call, Throwable t) {

            }
        });
    }
    @Override
    public void OnItemClicked(int id) {
        Log.d("id_select2","Seleccionaste:  "+id);
        Intent i = new Intent(PerfilFragment.this.getActivity(), CarnetDetalleActivity.class);
        i.putExtra("id", id);
        startActivity(i);
    }

}
