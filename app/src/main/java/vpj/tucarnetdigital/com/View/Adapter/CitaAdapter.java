package vpj.tucarnetdigital.com.View.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vpj.tucarnetdigital.com.Data.Model.Cita;
import vpj.tucarnetdigital.com.Data.preferences.SessionPreferences;
import vpj.tucarnetdigital.com.R;

public class CitaAdapter extends RecyclerView.Adapter<CitaAdapter.MyViewHolder>{

    private List<Cita> cita;
    private SessionPreferences prefs;

    public CitaAdapter(List<Cita> cita) {
        this.cita = cita;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_citas, parent, false);
        return new MyViewHolder(itemview);



    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Cita objCita = cita.get(position);


        holder.tv_fecha.setText("Fecha:  "+objCita.getFecha_p());
        holder.tv_horario.setText("Horario:  "+objCita.getHora_p());
        holder.tv_tipo.setText("Tipo:  "+objCita.getServicio());
    }

    @Override
    public int getItemCount() {
        int i=0;
        if(cita.size()>0){
            i = cita.size();
        }
        return  i;

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_fecha, tv_horario, tv_tipo;

        public MyViewHolder(View view){
            super(view);

            tv_fecha = view.findViewById(R.id.tv_fecha);
            tv_horario = view.findViewById(R.id.tv_horario);
            tv_tipo = view.findViewById(R.id.tv_tipo);
        }

    }
}
