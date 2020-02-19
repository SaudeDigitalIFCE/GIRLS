package ifce.lara.vite.receive;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ifce.lara.vite.activity.EnviaSmsActivity;
import ifce.lara.vite.object.Contact;

public class ReceiveEvent extends BroadcastReceiver {
    static String ultimocontato;
    static int contador = 0;
    static Context contexto;

    public ReceiveEvent() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        contexto = context;

        LocationProvider provader;
        final LocationManager gestaoLocal = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        final LocationListener local = new LocationListener() {


            @Override
            public void onLocationChanged(Location location) {
                System.out.println("Entrou no receive do evento");
                //GestaoBanco gs = new GestaoBanco(context);
                List<Contact> contact = Contact.listAll(Contact.class) ;
                ArrayList<String> contatos = new ArrayList<String>();


                //contatos = gs.ConsultaBanco();

                for (Contact c : contact) {



                    if (contador == 0) {
                        sendRequest(String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()));
                        contador++;
                    }
                    EnviaSmsActivity envia = new EnviaSmsActivity();
                    if (c.phone.trim().length() > 0) {

                        try {
                            envia.enviasms(location.getLatitude(),location.getLongitude(),c.phone); /// envia SMS para os contatos

                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println("___________________Longitude : " + location.getLongitude());
                        System.out.println("___________________Lantitude : " + location.getLatitude());

                        try {
                            Thread.sleep(1500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                System.exit(0);
/*
                   try {
                       System.exit(1);
                       Thread.sleep(300000*300);
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
*/

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
            //////////////////////////////////////////////////////
        };

        if (ActivityCompat.checkSelfPermission(contexto, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(contexto, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        gestaoLocal.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 800000000 * 20, 1, local);


    }

    public void sendRequest(final String latitude, final String longitude) {
        RequestQueue queue = Volley.newRequestQueue(contexto);
        String url = "http://172.20.10.3:8080/dengoso/services/notification/critical?lat="+latitude+"&lng="+longitude+"&critical=true";
        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response: ", response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error: ", error.getMessage());
                    }
                }
        );
        postRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(postRequest);
        queue.start();
    }





}
