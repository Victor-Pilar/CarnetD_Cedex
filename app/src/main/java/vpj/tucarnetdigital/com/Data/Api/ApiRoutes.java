package vpj.tucarnetdigital.com.Data.Api;


import vpj.tucarnetdigital.com.Data.Api.Api;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import vpj.tucarnetdigital.com.Data.Model.Carnet;
import vpj.tucarnetdigital.com.Data.Model.Cita;
import vpj.tucarnetdigital.com.Data.Model.LoginBody;
import vpj.tucarnetdigital.com.Data.Model.User;

public interface ApiRoutes {

  @POST("login")
  Call<User> login(@Body LoginBody loginBody);

  @POST("register")
  Call<User> registrar(@Body User user);

  @GET("citas")
  Call<List<Cita>> getCita();

  @GET("cita/{id}")
  Call<Cita> getCarnet(@Path("id") int id);

  @GET("user_id/{user_id}")
  Call<Cita> getUser(@Path("user_id") int id);







 
}
