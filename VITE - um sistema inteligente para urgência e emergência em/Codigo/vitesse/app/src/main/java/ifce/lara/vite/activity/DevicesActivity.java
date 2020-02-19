package ifce.lara.vite.activity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.orm.SugarContext;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Set;

import ifce.lara.vite.R;
import ifce.lara.vite.object.Device;

public class DevicesActivity extends AppCompatActivity {

    BluetoothAdapter adaptador;
    //Set<BluetoothDevice> dispositivos;
    BluetoothDevice get_dispositivo;



    ListView lvDevices;
    BluetoothAdapter bluetoothAdapter;
    Set<BluetoothDevice> devices;
    ArrayList<String> list;
    ArrayAdapter<String> adapter;
    IntentFilter filter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devices);
        enableBluetooth01();

        /////////////////////////// alterado ///////////////////////////////////////

        AlertDialog.Builder builder = new
                AlertDialog.Builder(DevicesActivity.this);
        builder.setTitle("Bluetooth")
                .setPositiveButton("Conectar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                Device dev = null; try {

                                    dev = Device.findById(Device.class,1);

                                    ConnectionBluetooth connectionBluetooth = new ConnectionBluetooth(dev.mac,DevicesActivity.this);
                                    Thread th = new Thread(connectionBluetooth);
                                    th.start();
                                    th.setPriority(1);

                                } catch (Exception e) {

                                    Toast.makeText(DevicesActivity.this, "Selecione um dispositivo", Toast.LENGTH_SHORT).show();
                                    e.printStackTrace();
                                }

                                //Toast.makeText(DevicesActivity.this, "", Toast.LENGTH_SHORT).show();
                            }})
                .setNegativeButton("Fechar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                Toast.makeText(DevicesActivity.this, "Cancelado", Toast.LENGTH_SHORT).show();
                            }});
        builder.create().show();






        ////////////////////////////////////////////////////////////////////////////




















        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(DevicesActivity.this, BluetoothSearch.class));
                //discoveringDevices();


                /////////////////////////// alterado ///////////////////////////////////////

                AlertDialog.Builder builder = new
                        AlertDialog.Builder(DevicesActivity.this);
                builder.setTitle("Habilitar")
                        .setPositiveButton("Selecionar",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                        enableBluetooth();

                                        //Toast.makeText(DevicesActivity.this, "", Toast.LENGTH_SHORT).show();
                                    }})
                        .setNegativeButton("Procurar",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {


                                        startActivity(new Intent(DevicesActivity.this,BluetoothDiscoverActivity.class));
                                        Toast.makeText(DevicesActivity.this, "Procurando...", Toast.LENGTH_SHORT).show();
                                    }});
                builder.create().show();
            }

            ////////////////////////////////////////////////////////////////////////////

        });


        ///list = new ArrayList<String>();
        /// bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        ////lvDevices = (ListView) findViewById(R.id.lv_devices);

        //pedir ao suario para ligar bluetooth


        ///try {
        /// if (testBluetooth()){
        /// getDevices();
        ///   }
        //// } catch (IOException e) {
        ///  e.printStackTrace();
        /// }
    }

    public void enableBluetooth() {
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter != null) {
            if (!mBluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, 1);

            }
            else{

                getDevices();

            }
        }

    }

    public void enableBluetooth01() {
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter != null) {
            if (!mBluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivity(enableBtIntent);

            }
        }

    }






    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // testa resultado da requisicao bluetooth
        System.out.println("Veio fora");
        if(resultCode==RESULT_OK){
            System.out.println("Veio dentro");
            getDevices();

        }
    }

    public void getDevices(){
//        System.out.println(bluetoothAdapter.toString());
        startActivity(new Intent(DevicesActivity.this,BluetoothSearch.class));
    }

    public void discoveringDevices() {
        // Create a BroadcastReceiver for ACTION_FOUND
        final BroadcastReceiver mReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                Log.d("Entrou", "action");
                // When discovery finds a device
                if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                    Log.d("Entrou", "found");
                    // Get the BluetoothDevice object from the Intent
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    // Add the name and address to an array adapter to show in a ListView
                    Log.d("TT", device.getName() + "\n" + device.getAddress());
                }
            }
        };
        // Register the BroadcastReceiver
        filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, filter); // Don't forget to unregister during onDestroy

        if (bluetoothAdapter.startDiscovery()) {
            // pesquisa por dispositivos
        }
    }

    private void iniciaBluetooth(){

        ArrayAdapter<String> listaAdapter;
        ArrayList<String> listaDispositivos;

        listaDispositivos = new ArrayList<String>();
        adaptador = BluetoothAdapter.getDefaultAdapter();
        ///dispositivos = new HashSet<BluetoothDevice>();

        if(adaptador!=null){
            if(!adaptador.isEnabled()){ /////Se estiver desabilitado
                Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(intent, 1);
            }
            else{  /// se estiver ligado


            }
        }
    }




    @Override
    public void finish() {
        super.finish();
//        unregisterReceiver();
    }
}
