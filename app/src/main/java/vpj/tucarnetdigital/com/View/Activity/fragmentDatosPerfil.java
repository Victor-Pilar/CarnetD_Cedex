package vpj.tucarnetdigital.com.View.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vpj.tucarnetdigital.com.Data.Api.Api;
import vpj.tucarnetdigital.com.Data.Model.Cita;
import vpj.tucarnetdigital.com.Data.Model.User;
import vpj.tucarnetdigital.com.Data.preferences.SessionPreferences;
import vpj.tucarnetdigital.com.R;
import vpj.tucarnetdigital.com.View.Activity.Fragments.FragmentDetalleCarnet;
import vpj.tucarnetdigital.com.View.Adapter.CarnetAdapter;

public class fragmentDatosPerfil extends Fragment implements CarnetAdapter.CarnetAdapterListener{
    EditText txt_tel, txt_cel, txt_curp, txt_edad, txt_nss, txt_cyn, txt_cyl, txt_dym, txt_enf, txt_sex, txt_fechan,  txt_entidadN;
    Button btnEditar;
    private SessionPreferences prefs;
    private Cita user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_datos_perfil, container, false);
        prefs = new SessionPreferences(getContext());


        getDatos();

        txt_tel = view.findViewById(R.id.txt_tel);
        txt_cel = view.findViewById(R.id.txt_cel);
        txt_curp = view.findViewById(R.id.txt_curp);
        txt_edad = view.findViewById(R.id.txt_edad);
        txt_nss = view.findViewById(R.id.txt_nss2);
        txt_cyn = view.findViewById(R.id.txt_cyn);
        txt_cyl = view.findViewById(R.id.txt_cyl);
        txt_dym = view.findViewById(R.id.txt_dym);
        txt_enf = view.findViewById(R.id.txt_enf);
        txt_sex = view.findViewById(R.id.txt_sex);
        txt_fechan = view.findViewById(R.id.txt_fechan);
        txt_entidadN = view.findViewById(R.id.txt_entidadN);









        return view;

    }

    private void getDatos(){
        Call<Cita> callUser = Api.getApi().getCarnet(getActivity().getIntent().getIntExtra("id", 0));
        callUser.enqueue(new Callback<Cita>() {
            @Override
            public void onResponse(Call<Cita> call, Response<Cita> response) {
                user = response.body();

                txt_tel.setText(user.getTel());
                txt_cel.setText(user.getCel());
                txt_curp.setText(user.getCurp());
                txt_edad.setText(user.getEdad());
                txt_nss.setText(user.getNss());
                txt_cyn.setText(user.getCyn());
                txt_cyl.setText(user.getCoylo());
                txt_dym.setText(user.getDeymu());
                txt_enf.setText(user.getEfed());
                txt_sex.setText(user.getSex());
                txt_fechan.setText(user.getFechanac());
                txt_entidadN.setText(user.getEntidad());



            }

            @Override
            public void onFailure(Call<Cita> call, Throwable t) {

            }
        });


    }

    @Override
    public void OnItemClicked(int id) {
        Intent i = new Intent(fragmentDatosPerfil.this.getActivity(), CarnetDetalleActivity.class);
        i.putExtra("id", id);
        startActivity(i);
    }
}
