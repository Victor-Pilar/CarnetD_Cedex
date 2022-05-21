package vpj.tucarnetdigital.com.View.Activity.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vpj.tucarnetdigital.com.Data.Api.Api;
import vpj.tucarnetdigital.com.Data.Model.Cita;
import vpj.tucarnetdigital.com.R;
import vpj.tucarnetdigital.com.View.Activity.CarnetDetalleActivity;
import vpj.tucarnetdigital.com.View.Adapter.CarnetAdapter;

public class FragmentDetalleCarnet extends Fragment  implements CarnetAdapter.CarnetAdapterListener {
    private ProgressDialog pdDialogo;
    private List<Cita> cita;
    private ProgressBar pbCarga;
    private RecyclerView citaRecycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    TextView txt_edad, txt_nns, txt_curp, txt_dom, txt_enfed, txt_fnacimiento, txt_lnacimiento;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detalle_carnet, container, false);
        pbCarga = view.findViewById(R.id.pbCarga);

        cargarCarnet();
        txt_edad = view.findViewById(R.id.txt_edad);
        txt_nns = view.findViewById(R.id.txt_nns);
        txt_curp = view.findViewById(R.id.txt_curp);
        txt_dom = view.findViewById(R.id.txt_dom);
        txt_enfed = view.findViewById(R.id.txt_enfed);
        txt_fnacimiento = view.findViewById(R.id.txt_enfed);
        txt_lnacimiento = view.findViewById(R.id.txt_lnacimiento);
        Log.d("pruebatest2", "prueba si entra aqui");




        return view;


    }

    public void cargarCarnet(){
        Call<Cita> callCita = Api.getApi().getCarnet(getActivity().getIntent().getIntExtra("id", 0));
        callCita.enqueue(new Callback<Cita>() {
            @Override
            public void onResponse(Call<Cita> call, Response<Cita> response) {


                Log.d("prueba_fragment", response.body().getNss());
                pbCarga.setVisibility(View.GONE);

                txt_edad.setText("Edad: "+response.body().getEdad());
                txt_nns.setText("NSS: "+response.body().getNss());
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
        Intent i = new Intent(FragmentDetalleCarnet.this.getActivity(), CarnetDetalleActivity.class);
        i.putExtra("id", id);
        startActivity(i);
    }
}
