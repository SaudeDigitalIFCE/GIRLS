package ifce.lara.vite.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.geojson.GeoJsonLayer;

import org.json.JSONObject;

import ifce.lara.vite.receive.SmsReceive;
import ifce.lara.vite.singleton.MySingleton;
import ifce.lara.vite.R;

import static android.widget.Toast.LENGTH_LONG;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private final String UBS = "http://192.168.0.142:8080/dengoso/services/unidadesaude/geojson/search?lat=-37.7759999&lng=-4.4669999";
    private final String DRUGSTORY = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.map, menu);
        super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id==R.id.action_ubs) {
            updateMapGeoJson(this.UBS);
            return true;
        } else if (id==R.id.action_drugstory){
            updateMapGeoJson(this.DRUGSTORY);
            return true;
        }
        return super.onOptionsItemSelected(item);
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
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        updateMapGeoJson(this.UBS);


        SmsReceive smsReceive = new SmsReceive();
        Toast.makeText(this, "lat "+smsReceive.getLat()  + " lon " +smsReceive.getLon(), LENGTH_LONG).show();
        LatLng local = new LatLng(smsReceive.getLat(), smsReceive.getLon());
        mMap.addMarker(new
                MarkerOptions().position(local).title("Local da Emergência")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.alvo))
        );

        mMap.addCircle(new CircleOptions().center(local).radius(300));
        mMap.setIndoorEnabled(true);
        mMap.getProjection();
        mMap.getMaxZoomLevel();
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(local, 15));










    }

    public void updateMapGeoJson(String url) {
        //GeoJsonLayer layer = new GeoJsonLayer(getMap(), geoJsonData);

        JsonObjectRequest jsObjectResquest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println("Response: " + response.toString());
                GeoJsonLayer geoJsonLayer = new GeoJsonLayer(mMap, response);
                geoJsonLayer.addLayerToMap();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // fazer alguma coisa pra tratar os erros
                System.out.println("erro: "+error);
            }
        });

        MySingleton.getInstance(this).addToRequestQueue(jsObjectResquest);
    }
}
