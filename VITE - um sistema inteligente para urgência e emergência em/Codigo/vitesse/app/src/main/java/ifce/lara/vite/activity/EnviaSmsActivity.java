package ifce.lara.vite.activity;

import android.app.PendingIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;

import java.io.IOException;

public class EnviaSmsActivity extends AppCompatActivity {

    static String mensagemCompleta;
    static SmsManager smsManager;
    private static final String sms = "socorro";


    public void enviasms(double latitude, double longitude, String endereco) throws SecurityException, IOException, InterruptedException {
        //// PendingIntent sentPI = PendingIntent.getBroadcast(getApplicationContext(),0,inten,0);
        System.out.println("Esta recbendo ok!");
        //mensagemCompleta = sms + "http://maps.google.com/maps?q=loc:"+latitude+","+longitude;
        mensagemCompleta = sms + "," + latitude + "," + longitude;
        smsManager = SmsManager.getDefault();
        System.out.println("E enviou para " + endereco);
        smsManager.sendTextMessage(endereco, null, mensagemCompleta, null, null);
        ///Envia o mesmo sms para os contatos cadastrados
    }
}
