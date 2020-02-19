package ifce.lara.vite.activity;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import static android.R.attr.data;

/**
 * Created by karin on 04/11/2016.
 */

public class Service_Geolocation {
    private static double Longitude;
    private static double Latitude;
    Emitter.Listener inicializa = new Emitter.Listener(){
        @Override
        public void call(Object... args) {
            //captura os dados lat e long
            if(mSocket.connected()){
                //Toast.makeText(context,"Conectado",Toast.LENGTH_SHORT).show();
                System.out.println("=====================================Conetado==================");
            }
            System.out.println("chegou no listene   "+getLongitude());
            //cria data (um json com {name: ‘’, lat: 31.68, lon: 46.798})

                System.out.println("--------------------------------------------------Eveio---------------");
                JSONObject data = new JSONObject();
                try {
                    data.put("name","Nicodemos");
                    data.put("lat", getLatitude());
                    data.put("lon", getLongitude());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mSocket.emit("http", data);       ///("update", data)
        }
    };
    Context context;
    LocationProvider provader;
    static com.github.nkzawa.socketio.client.Socket mSocket;

    public Service_Geolocation(Context c) {

        context = c;
        try {
            mSocket = IO.socket("http://aracatidigital.azurewebsites.net:3000");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        System.out.println("Chegou no metodo");
        mSocket.on("ok",inicializa);
        mSocket.connect();
    }

    public void cancel(){

        mSocket.disconnect();
    }

    public void get_Location() {
        System.out.println("Chegou no metodo get_Location");
        if(mSocket.connected()){
        System.out.println("============================================ ESTA CONECTADO=======");}
        final LocationManager gestaoLocal = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        final LocationListener local = new LocationListener() {

            @Override
            public void onLocationChanged(Location location) {

                setLatitude(location.getLatitude());
                setLongitude(location.getLongitude());

                System.out.println("localizacao "+getLatitude()+" "+getLongitude());
                run();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        gestaoLocal.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,100,1,local);



    }

    public static double getLongitude() {
        return Longitude;
    }

    public static void setLongitude(double longitude) {
        Longitude = longitude;
    }

    public static double getLatitude() {
        return Latitude;
    }

    public static void setLatitude(double latitude) {
        Latitude = latitude;
    }

}
