package vpj.tucarnetdigital.com.Data.Api;

import android.util.Log;

public class Api {

    private static final String BASE_URL = "https://tucarnetdigital.com/api/";

    public static ApiRoutes getApi(){
        return RetrofitClient.getClient(BASE_URL).create(ApiRoutes.class);
    }
}
