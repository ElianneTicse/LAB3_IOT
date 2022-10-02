package edu.pe.pucp.lab3_iot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AddressComponent;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.maps.android.PolyUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EmergenciaActivity extends AppCompatActivity implements OnMapReadyCallback {


    private LatLng destino; //es null si el distrito no está dentro de la cobertura
    private int minutos;
    private Polyline rutaPolyLine;
    private String apiKey;
    private GoogleMap map;
    private Marker ambulanciaMarker = null;
    private final String LOGTAG = "msgAS";
    private List<LatLng> rutaPoints;
    private List<LatLng> rutaToPolyLine = null;
    private int nPoints;
    private boolean enCamino = false;
    private final LatLng ORIGEN = new LatLng(-12.084538, -77.031396);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergencia);
        //Obtener API KEY
        ApplicationInfo ai = null;
        try {
            ai = this.getPackageManager().getApplicationInfo(this.getPackageName(), PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        apiKey = (String) ai.metaData.get("com.google.android.geo.API_KEY");
        //Inicializa Places
        Places.initialize(getApplicationContext(), apiKey);
        PlacesClient placesClient = Places.createClient(this);
        //Obtiene el mapa
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        //Procesa el autocompletado del origen
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentDestino);
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS_COMPONENTS));
        autocompleteFragment.setLocationBias(RectangularBounds.newInstance(
                new LatLng(-12.118965, -77.018579), new LatLng(-12.061950, -77.096827)));
        autocompleteFragment.setCountries("PE");
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onError(@NonNull Status status) {
                destino = null;
            }

            @Override
            public void onPlaceSelected(@NonNull Place place) {
                List<AddressComponent> addressComponents = place.getAddressComponents().asList();
                boolean enCobertura = false;
                for ( AddressComponent addressComponent : addressComponents){
                    if (addressComponent.getShortName().contains("Lince")){
                        Log.i(LOGTAG,"Eres de Lince");
                        enCobertura = true;
                        minutos = 10;
                        break;
                    }else if(addressComponent.getShortName().contains("San Isidro")){
                        Log.i(LOGTAG,"Eres de San isidro");
                        minutos = 15;
                        enCobertura = true;
                        break;
                    }else if(addressComponent.getShortName().contains("Magdalena")){
                        Log.i(LOGTAG,"Eres de Magdalenaa");
                        minutos = 20;
                        enCobertura = true;
                        break;
                    }else if(addressComponent.getShortName().contains("Jesús María")){
                        Log.i(LOGTAG,"Eres de Jesus maria");
                        minutos = 25;
                        enCobertura = true;
                        break;
                    }
                }
                destino = enCobertura? place.getLatLng() : null;
            }
        });
    }

    public void calcularRuta(View view){
        if(enCamino){
            Toast.makeText(EmergenciaActivity.this, "La ambulancia ya está en camino", Toast.LENGTH_SHORT).show();
            return;
        }
        if(destino==null){
            Toast.makeText(EmergenciaActivity.this, "Lo sentimos, no estás en cobertura :(", Toast.LENGTH_SHORT).show();
            return;
        }
        enCamino = true;
        map.addMarker(new MarkerOptions().position(destino).title("Destino"));

        //TODO: validar y obtener DNI, agregar a historial
        ContadorViewModel contadorViewModel =
                new ViewModelProvider(this).get(ContadorViewModel.class);

        String url = getUrl(ORIGEN,destino);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                url,
                response -> {
                    try {
                        JSONObject responseJson = new JSONObject(response);
                        String strPoints = ((JSONObject)((JSONObject) responseJson.getJSONArray("routes").get(0)).get("overview_polyline")).getString("points");
                        List<LatLng> latlngPoints = PolyUtil.decode(strPoints);
                        nPoints = minutos*60/5;
                        rutaPoints = latlngPoints;
                        contadorViewModel.contarNal0(minutos*60);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Log.e("error volley", error.getMessage()));
        requestQueue.add(stringRequest);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        Marker clinicaMarker = googleMap.addMarker(new MarkerOptions().position(ORIGEN).title("Clínica Mascotitas"));
        clinicaMarker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.vetmarker));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ORIGEN,12));
        map = googleMap;
        ContadorViewModel contadorViewModel =
                new ViewModelProvider(this).get(ContadorViewModel.class);
        TextView contadorTV = findViewById(R.id.contadorMinutos);
        contadorViewModel.getContador().observe(this,
                contador -> {
                    //TODO: pasar el contador de segundos a minutos
                    if(contador>0){
                        contadorTV.setText("La ambulancia llegará en "+ contador + " segundos");
                    }else{
                        contadorTV.setText("La ambulancia ha llegado");
                    }

                    if (contador%5 == 0){
                        int pointCount = contador/5;
                        int rutaSize = rutaPoints.size();
                        float fIndex = (rutaSize-1)*(1-pointCount/(float)nPoints);
                        int gIndex = Math.max(Math.min((int) Math.ceil(fIndex), rutaSize-1),0);
                        int lIndex = Math.min(Math.max((int) Math.floor(fIndex),0),rutaSize-1);
                        float ratio = fIndex - lIndex;
                        double lat = rutaPoints.get(lIndex).latitude + (rutaPoints.get(gIndex).latitude - rutaPoints.get(lIndex).latitude)*ratio;
                        double lng = rutaPoints.get(lIndex).longitude + (rutaPoints.get(gIndex).longitude - rutaPoints.get(lIndex).longitude)*ratio;
                        LatLng actual = new LatLng(Math.round(lat*1e6)/1e6,Math.round(lng*1e6)/1e6);
                        rutaToPolyLine = new ArrayList<>();
                        rutaToPolyLine.addAll(rutaPoints.subList(gIndex,rutaSize));
                        rutaToPolyLine.add(0, actual);
                        if(ambulanciaMarker==null){
                            rutaPolyLine = googleMap.addPolyline(new PolylineOptions().addAll(rutaToPolyLine));
                            rutaPolyLine.setColor(EmergenciaActivity.this.getResources().getColor(R.color.md_theme_dark_background));
                            ambulanciaMarker = googleMap.addMarker(new MarkerOptions().position(ORIGEN).title("Ambulancia"));
                            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ORIGEN, 17));
                            ambulanciaMarker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ambulance));
                        }else{
                            ambulanciaMarker.setPosition(actual);
                            ambulanciaMarker.setRotation((float) getBearing(rutaPoints.get(lIndex).latitude,rutaPoints.get(lIndex).longitude,
                                    rutaPoints.get(gIndex).latitude,rutaPoints.get(gIndex).longitude));
                            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(actual, 17));
                            rutaPolyLine.setPoints(rutaToPolyLine);
                        }
                    }
                });
    }

    private String getUrl(LatLng origen, LatLng destino){
        return "https://maps.googleapis.com/maps/api/directions/json?origin="+origen.latitude+","+origen.longitude+
                "&destination="+destino.latitude+","+destino.longitude+"&travel_mode=DRIVING&key="+apiKey;
    }

    private static double getBearing(double lat1, double lon1, double lat2, double lon2){
        double longitude1 = lon1;
        double longitude2 = lon2;
        double latitude1 = Math.toRadians(lat1);
        double latitude2 = Math.toRadians(lat2);
        double longDiff= Math.toRadians(longitude2-longitude1);
        double y= Math.sin(longDiff)*Math.cos(latitude2);
        double x=Math.cos(latitude1)*Math.sin(latitude2)-Math.sin(latitude1)*Math.cos(latitude2)*Math.cos(longDiff);

        return (Math.toDegrees(Math.atan2(y, x))+360)%360;
    }
}