package ifce.lara.vite.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.ParcelUuid;

import java.io.IOException;
import java.util.UUID;

import ifce.lara.vite.management.AlarmeManager;

/**
 * Created by karin on 05/10/2016.
 */

public class ConnectionBluetooth extends Thread{


    //private static final String app = "Savavidas";
    int cont = 0;
    BluetoothSocket soket_temporario;
    BluetoothAdapter adaptador;
    BluetoothDevice dispositivo;
    String btEcolhido;
    Activity activite = new  Activity();
    Context context;

    public ConnectionBluetooth(String escolhido, Context c) {

        setBtEcolhido(escolhido);
        context=c;
    }

    public String getBtEcolhido() {
        return btEcolhido;
    }


    public void setBtEcolhido(String escolhido) {
        this.btEcolhido = escolhido;
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
    @Override
    public void run() {

        BluetoothAdapter adaptador = BluetoothAdapter.getDefaultAdapter();
        BluetoothDevice dispositivo;
        soket_temporario = null;
        BluetoothSocket Servesoket;
        BluetoothSocket soket;
        UUID id_arduino = null;
        final UUID ID_smartphone=UUID.fromString("8af0aeea-3574-4ce5-85d7-e17527b13fe7");


        try {
            dispositivo = adaptador.getRemoteDevice(getBtEcolhido());
            Intent intent = new Intent();

            String b = null;
            for (ParcelUuid a : dispositivo.getUuids()) {

                if (dispositivo.getAddress().equals(getBtEcolhido()) && cont < 1) {
                    b = a.toString();
                    id_arduino = UUID.fromString(b);
                    cont++;
                }
            }
            Servesoket = dispositivo.createRfcommSocketToServiceRecord(id_arduino);
            ///Servesoket.connect();
            if(!Servesoket.isConnected()){

                Servesoket.connect();

            }
            System.out.println("Conexão estabelecida");
            int d;
            while (true) {
                byte byt[] = new byte[64];
                d = Integer.valueOf(Servesoket.getInputStream().read()).intValue();
                if(d>0){
                    System.out.println("Valor recebido = " +d);

                    if(d==49){

                        ProtocolEmergencyActivity.teste1=true;
                        ProtocolEmergencyActivity.teste2=true;
                        Intent inte = new Intent(context,ProtocolEmergencyActivity.class);
                        inte.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(inte);
                    }
                    else if(d==50){

                        AlarmeManager al = new AlarmeManager(context);
                        al.rum();
                        ProtocolEmergencyActivity.teste1=true;
                        ProtocolEmergencyActivity.teste2=true;
                        Intent inte = new Intent(context,ProtocolEmergencyActivity.class);
                        inte.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(inte);
                    }
                    else if(d==51&&ProtocolEmergencyActivity.teste1==true){
                        ProtocolEmergencyActivity.teste1 = false;
                        ProtocolEmergencyActivity.teste2 = false;
                        MainActivity.mp.pause();
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("ERRO NO BLUETOOTH!");
            e.printStackTrace();
        }
        //try {
        /////System.out.println("------------------------------------------Desconectou");
        ////   finalize();
        ///} catch (Throwable throwable) {
        ///throwable.printStackTrace();
        ///}
    }

    public void cancel() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
                soket_temporario.close();
            }
        } catch (IOException e) {
        }
    }

}
