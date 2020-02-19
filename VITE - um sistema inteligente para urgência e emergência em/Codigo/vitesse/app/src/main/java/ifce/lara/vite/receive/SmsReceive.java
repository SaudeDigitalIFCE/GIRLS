package ifce.lara.vite.receive;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;

import ifce.lara.vite.activity.MainActivity;
import ifce.lara.vite.activity.MapsActivity;

public class SmsReceive extends BroadcastReceiver {
    private static final String queryString = "@socorro";
    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    String msg1;
    private static double lat = 0;
    private static double lon = 0;



    public SmsReceive() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {


        System.out.println("Aplicacao cliente recebe a mensagem!");



        ///////////////////////// habilita wifi  se estiver desabilitado/////////////////////////////////////

        WifiManager manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if(manager.isWifiEnabled()==false){

            manager.setWifiEnabled(true);
        }

        ////////////////////////// Desencapsula o sms ///////////////////////////

        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            Object[] pdus = (Object[]) bundle.get("pdus");
            String format = bundle.getString("format");
            SmsMessage[] messages = new SmsMessage[pdus.length];
            for (int i = 0; i < pdus.length; i++) {


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i],format);
                }
                else{
                    messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                }
            }
            for (SmsMessage message : messages) {
                String msg = message.getMessageBody();
                String out = msg;

                String dado[] = new String[3];


                if (out.contains("socorro")){

                    dado = out.split(",");
                    msg1 = dado[0];
                    setLat(Double.parseDouble(dado[1]));
                    setLon(Double.parseDouble(dado[2]));

                    System.out.println(" Nao deu erro está ok");
                if (msg1.equals("socorro") && getLat() != 0 && getLon() != 0) {

                    //////////////////////////////// habilita o alarme chamando a thread /////////////////////////////////////////////
                    if(!MainActivity.mp.equals(null)) {
                        MainActivity.mp.start();
                    }
                    intent = new Intent(context, MapsActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                }
            }
            }
        }
    }

    public static double getLat() {
        return lat;
    }

    public static void setLat(double late) {
        lat = late;
    }

    public static double getLon() {
        return lon;
    }

    public static void setLon(double longi){
        lon = longi;
    }


}

