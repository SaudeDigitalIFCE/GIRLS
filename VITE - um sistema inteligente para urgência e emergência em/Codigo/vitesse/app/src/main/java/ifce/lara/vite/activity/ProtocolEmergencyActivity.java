package ifce.lara.vite.activity;

import android.content.Intent;
import android.os.Handler;

import android.os.CountDownTimer;

import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.orm.SugarContext;

import java.util.Locale;

import ifce.lara.vite.management.AlarmeManager;
import ifce.lara.vite.R;
import ifce.lara.vite.object.UserInfo;

public class ProtocolEmergencyActivity extends AppCompatActivity {

    static Boolean teste1=false,teste2=false;
    int contador=0;

    CountDownTimer countDownTimer;
    ImageButton btCancel;
    TextView tvCount;

    TextView name;
    TextView disiase;
    TextView allergy;
    TextView medicines;
    TextView blood;
    static TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protocol_emergency);
        SugarContext.init(ProtocolEmergencyActivity.this);


        ////////////////////////////////////////////////////////////////////

        textToSpeech = new TextToSpeech(ProtocolEmergencyActivity.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(new Locale("pt", "BR"));

                }
            }
        });

        /////////////////////////////////////////////////////////////////////



        if(teste1==true&&teste2==true && contador==0) {
            contador = 1;

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    contador = 0;
                    System.out.println("Esta em pausa");
                    if (teste1 == true && teste2 == true) {

                        Intent intencao = new Intent("START_SENSOR");
                        sendBroadcast(intencao);
                        aviso_envio();

                    }
                    else{
                        aviso_cancelado();
                    }
                }
            }, 22000);



        }

        MainActivity.mp.start();

        aviso_ok();
    }

    public void aviso_ok() {

        textToSpeech
                .speak("Protocolo será iniciado em 15 segundos", TextToSpeech.QUEUE_FLUSH, null);
    }

    public void aviso_cancelado(){


        textToSpeech
                .speak("Protocolo cancelado", TextToSpeech.QUEUE_FLUSH, null);
    }

    public void aviso_envio(){

        textToSpeech
                .speak("Protocolo Iniciado", TextToSpeech.QUEUE_FLUSH, null);
    }

}
