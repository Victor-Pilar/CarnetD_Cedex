package vpj.tucarnetdigital.com.View.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.location.LocationManager;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import vpj.tucarnetdigital.com.R;

////////////////////////////////////////////////////////////////////////
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.Objects;
////////////////////////////////////////////////////////////////////////

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

//    private GoogleMap mMap;

    private static final int REQUEST_CHECK_SETTINGS = 0x1;
    private static GoogleApiClient mGoogleApiClient;
    private static final int ACCESS_FINE_LOCATION_INTENT_ID = 3;
    private static final String BROADCAST_ACTION = "android.location.PROVIDERS_CHANGED";

    private SupportMapFragment mapFragment;
    private GoogleMap mGoogleMap;
    private LocationRequest mLocationRequest;
    private Location location;

    private Marker mCurrLocationMarker, markerClient;
    private BitmapDescriptor iconUser, iconClient;
    private LatLng latLng, latLngOtro;

    private CameraPosition camera;

    private boolean stateMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        initGoogleAPIClient();  //Init Google API Client
        checkPermissions();     //Check Permission

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //ESTOS SON LOS ICONOS PERSONALIZABLES (SUSTITUYELOS)
//        iconUser = BitmapDescriptorFactory.fromResource(R.drawable.user_icon);
//        iconClient = BitmapDescriptorFactory.fromResource(R.drawable.user_icon_1);

        stateMap = false;
        //Este handler ser?? ejecutado 5 segundos despu??s, lo uso ya que me ha pasado que a??n dandole los permisos de gps por primera vez no los reconoce y no detecta nuestra ubicaci??n, pero los puedes omitir .
        new Handler().postDelayed(new Runnable() {
            public void run() {
                if (!stateMap) {
                    initGoogleAPIClient();  //Init Google API Client
                    checkPermissions();     //Check Permission
                }
            }

            ;
        }, 10000); //timer
    }

    private void initGoogleAPIClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(MapsActivity.this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    private void checkPermissions() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(MapsActivity.this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED)
                requestLocationPermission();
            else
                showSettingDialog();
        } else
            showSettingDialog();
    }

    private void requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(MapsActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION)) {
            ActivityCompat.requestPermissions(MapsActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, ACCESS_FINE_LOCATION_INTENT_ID);
        } else {
            ActivityCompat.requestPermissions(MapsActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, ACCESS_FINE_LOCATION_INTENT_ID);
        }
    }

    private void showSettingDialog() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(30 * 1000);
        locationRequest.setFastestInterval(5 * 1000);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                final LocationSettingsStates state = result.getLocationSettingsStates();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        Log.e("TAG", "SUCCESS");
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        Log.e("TAG", "RESOLUTION_REQUIRED");
                        try {
                            status.startResolutionForResult(MapsActivity.this, REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            e.printStackTrace();
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        Log.e("TAG", "GPS NO DISPONIBLE");
                        break;
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case RESULT_OK:
                        Log.e("Settings", "Result OK");
                        break;
                    case RESULT_CANCELED:
                        Log.e("Settings", "Result Cancel, La aplicaci??n se cerrar??");
                        finish();
                        break;
                }
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(gpsLocationReceiver, new IntentFilter(BROADCAST_ACTION));//Registrar el receptor de difusi??n para comprobar el estado del GPS.
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (gpsLocationReceiver != null)
            unregisterReceiver(gpsLocationReceiver);
    }

    //Ejecutar en la interfaz de usuario
    private Runnable sendUpdatesToUI = new Runnable() {
        public void run() {
            showSettingDialog();
        }
    };

    private BroadcastReceiver gpsLocationReceiver= new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //Si la acci??n es la ubicaci??n
            if (intent.getAction().matches(BROADCAST_ACTION)) {
                LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
                //Compruebe si el GPS est?? encendido o apagado
                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    Log.e("About GPS", "GPS is Enabled in your device");
                } else {
                    //Si el GPS est?? apagado, muestre el di??logo de ubicaci??n
                    new Handler().postDelayed(sendUpdatesToUI, 10);
                    Log.e("About GPS", "GPS is Disabled in your device");
                    finish();
                }

            }
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.e("TAG", "onRequestPermissionsResult");
        switch (requestCode) {
            case ACCESS_FINE_LOCATION_INTENT_ID: {
                // Si se cancela la solicitud, las matrices de resultados est??n vac??as.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Si el permiso otorgado muestra el cuadro de di??logo de ubicaci??n si APIClient no es nulo
                    if (mGoogleApiClient == null) {
                        initGoogleAPIClient();
                        showSettingDialog();
                    } else
                        showSettingDialog();


                } else {
                    Toast.makeText(MapsActivity.this, "Location Permission denied.", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }



    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    @RequiresApi (api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.e("TAG", "onMapReady");
        mGoogleMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mGoogleMap.setMyLocationEnabled(true);
        buildGoogleApiClient();
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            } else {
                Toast.makeText(this, "No cuentas con los permisos necesarios, cierra y abre de nuevo la aplicaci??n", Toast.LENGTH_SHORT).show();
            }
        } else {
            buildGoogleApiClient();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    protected synchronized void buildGoogleApiClient() {
        Log.e("TAG", "buildGoogleApiClient");
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.reconnect();
        } else {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
            mGoogleApiClient.connect();
        }
    }

    @RequiresApi (api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.e("TAG", "onConnected");
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(32000);
        mLocationRequest.setFastestInterval(32000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        try {
            if (ContextCompat.checkSelfPermission(Objects.requireNonNull(this), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.e("TAG", "onConnectionSuspended");
        mGoogleApiClient.connect(i);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e("TAG", "onConnectionSuspended");
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.e("TAG", "onLocationChanged");
        this.location = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
            markerClient.remove();
        }
        stateMap=true;//en el caso de que se ejecute el Handler y entre a onLocationChanged va volver verdadero stateMap y no volvera a pedir permisos de GPS
        ///////////////////////////////////////ESTE ES EL MARCADOR DE TU UBICACI??N ACTUAL///////////////////////////////////////
        latLng = new LatLng(location.getLatitude(), location.getLongitude());
//        MarkerOptions markerOptions = new MarkerOptions();
//        markerOptions.position(latLng);
//        markerOptions.title("Posici??n Actual");
//        markerOptions.icon(iconUser);
//        mCurrLocationMarker = mGoogleMap.addMarker(markerOptions);
//        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));//AQUI MODIFICA EL ZOOM AL MAPA SEGUN TUS NECESIDADES
        zoomToLocation(location);

        ///////////////////////////////////////ESTE ES EL MARCADOR DEL DESTINO (ES UN MARCADOR CON UNA POCISION ESTATICA) ///////////////////////////////////////
        latLngOtro = new LatLng(19.7682745, -97.2487502);
        MarkerOptions markerOptions2 = new MarkerOptions();
        markerOptions2.position(latLngOtro);
        markerOptions2.title("ESTE ES TU DESTINO");
        markerOptions2.icon(iconClient);
        markerClient = mGoogleMap.addMarker(markerOptions2);

        mGoogleMap.addPolyline(new PolylineOptions()
                .add(latLng)
                .add(latLngOtro)
                .width(6f)
                .color(Color.BLUE));

        mGoogleMap.addCircle(new CircleOptions()
                .center(latLngOtro).radius(50.0).strokeWidth(3f).strokeColor(Color.rgb(210, 220, 219 )).fillColor(Color.rgb(227, 234, 234)));

    }

    private void zoomToLocation(Location location) {
        camera = new CameraPosition.Builder()
                .target(new LatLng(location.getLatitude(), location.getLongitude()))
                .zoom(16)           // limit -> 21
                .bearing(32)         // 0 - 365??
                .tilt(64)           // limit -> 90
                .build();
        mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(camera));

    }
}
