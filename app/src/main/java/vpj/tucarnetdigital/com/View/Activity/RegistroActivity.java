package vpj.tucarnetdigital.com.View.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vpj.tucarnetdigital.com.Data.Api.Api;
import vpj.tucarnetdigital.com.Data.Model.User;
import vpj.tucarnetdigital.com.Data.preferences.SessionPreferences;
import vpj.tucarnetdigital.com.R;

public class RegistroActivity extends AppCompatActivity {

    private TextView tvLogin;
    private EditText etNombre,etEmail,etPassword;
    private Button btnRegistro;
    private ProgressDialog pdDialogo;

    private SessionPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        prefs = new SessionPreferences(getApplicationContext());

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etNombre = findViewById(R.id.etNombre);

        btnRegistro = findViewById(R.id.btnRegistrarse);

        tvLogin = findViewById(R.id.tvIniciar);
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistroActivity.this, LoginActivity.class));
            }
        });

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pdDialogo = ProgressDialog.show(RegistroActivity.this,"Creando Cuenta:","Comprobando Datos...",true,false);
                if(validar()){
                    User objUser = new User();
                    objUser.setName(etNombre.getText().toString());
                    objUser.setEmail(etEmail.getText().toString());
                    objUser.setPassword(etPassword.getText().toString());
                    registro(objUser);
                    Log.v("en2","Entra aqui");
                }else{
                    pdDialogo.dismiss();
                }
            }
        });
    }

    private boolean validar(){
        boolean valido = true;

        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        String nombre = etNombre.getText().toString();

        if(email.isEmpty() || !validateEmail(email)){
            valido =  false;
            etEmail.setError("El email es requerido");
        }else{
            etEmail.setError(null);
        }

        if(password.isEmpty()){
            valido =  false;
            etPassword.setError("El password es requerido");
        }else{
            etPassword.setError(null);
        }

        if(nombre.isEmpty()){
            valido =  false;
            etNombre.setError("El nombre es requerido");
        }else{
            etNombre.setError(null);
        }

        return valido;
    }


    public boolean validateEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    private void registro(User user){
        Call<User> callRegistro = Api.getApi().registrar(user);
        callRegistro.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                pdDialogo.dismiss();
                if(response.isSuccessful()){
                    toast("Registrado, bienvenido" );

                    prefs.guardarUsuario(response.body());
                    Intent intent = new Intent(RegistroActivity.this, NavigationDrawerC.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }else{
                    toast("No se ha podido registrar o este Email ya tiene una Cuenta adjunta");
                    etEmail.setText("");
                    etNombre.setText("");
                    etPassword.setText("");

                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                pdDialogo.dismiss();
                toast("Error al comunicarse con el servidor");
                Log.v("Test","esta no funcionando");
            }
        });
    }

    private void toast(String mensaje){
        Toast.makeText(getApplicationContext(),mensaje,Toast.LENGTH_LONG).show();
    }
}
