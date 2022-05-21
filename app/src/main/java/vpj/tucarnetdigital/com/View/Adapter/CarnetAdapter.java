package vpj.tucarnetdigital.com.View.Adapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vpj.tucarnetdigital.com.Data.Model.Carnet;
import vpj.tucarnetdigital.com.Data.Model.Cita;
import vpj.tucarnetdigital.com.R;
import vpj.tucarnetdigital.com.View.Activity.CarnetDetalleActivity;
import vpj.tucarnetdigital.com.View.Activity.Fragments.CarnetFragment;
import vpj.tucarnetdigital.com.View.Activity.Fragments.FragmentDetalleCarnet;

public class CarnetAdapter extends RecyclerView.Adapter<CarnetAdapter.MyViewHolder>{

    private List<Cita> cita;
    private CarnetAdapterListener listener;

    public CarnetAdapter(List<Cita> cita, CarnetAdapterListener listener) {

        this.cita = cita;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_carnet, parent, false);
        //Log.v("prueba", "finciona");
        return new MyViewHolder(itemview);


    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Cita objCita = cita.get(position);

        holder.tv_nns.setText("NNS:  "+objCita.getNss());
        holder.tv_fecha.setText("Fecha:  "+objCita.getFecha());
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

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView tv_nns, tv_fecha,  tv_tipo;

        public MyViewHolder(View view){


            super(view);


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent in = new Intent(v.getContext(), FragmentDetalleCarnet.class);
            in.putExtra("id", cita.get(getAdapterPosition()).getId());
            v.getContext().startActivity(in);
                }
            });
            tv_nns = view.findViewById(R.id.tv_nns);
            tv_fecha = view.findViewById(R.id.tv_fecha);
            tv_tipo = view.findViewById(R.id.tv_tipo);


            view.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            listener.OnItemClicked(cita.get(getAdapterPosition()).getId());
            Log.d("id_prueba", cita.get(getAdapterPosition()).getNss());
        }
    }

    public  interface CarnetAdapterListener{
        void OnItemClicked(int id);
    }
}
