package vpj.tucarnetdigital.com.View.Activity.Fragments;

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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vpj.tucarnetdigital.com.Data.Api.Api;
import vpj.tucarnetdigital.com.Data.Model.Cita;
import vpj.tucarnetdigital.com.R;
import vpj.tucarnetdigital.com.View.Activity.CarnetDetalleActivity;
import vpj.tucarnetdigital.com.View.Adapter.CarnetAdapter;

public class FragmentDetalleCarnet4 extends Fragment implements CarnetAdapter.CarnetAdapterListener{
    private ProgressBar pbCarga;

    TextView txt_fecha, txt_ob, txt_peso, txt_estatura, txt_cintura, txt_imc, txt_hiper, txt_precion;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detalle_carnet4, container, false);
        pbCarga = view.findViewById(R.id.pbCarga2);
        cargarCarnetTab3();

        txt_peso = view.findViewById(R.id.txt_peso);
        txt_estatura = view.findViewById(R.id.txt_estatura);
        txt_cintura = view.findViewById(R.id.txt_cintura);
        txt_imc = view.findViewById(R.id.txt_imc);


        return view;


    }

    public void cargarCarnetTab3(){
        Call<Cita> callCita = Api.getApi().getCarnet(getActivity().getIntent().getIntExtra("id", 0));
        callCita.enqueue(new Callback<Cita>() {
            @Override
            public void onResponse(Call<Cita> call, Response<Cita> response) {
                pbCarga.setVisibility(View.GONE);

                txt_peso.setText("Peso: "+response.body().getPeso());
                txt_estatura.setText("Estatura: "+ response.body().getEstatura());
                txt_cintura.setText("Cintura: "+ response.body().getCintura());
                txt_imc.setText("IMC: "+response.body().getImc());

            }

            @Override
            public void onFailure(Call<Cita> call, Throwable t) {

            }
        });
    }

    @Override
    public void OnItemClicked(int id) {
        Log.d("id_select2","Seleccionaste:  "+id);
        Intent i = new Intent(FragmentDetalleCarnet4.this.getActivity(), CarnetDetalleActivity.class);
        i.putExtra("id", id);
        startActivity(i);
    }
}
