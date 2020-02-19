package ifce.lara.vite.activity;

import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.orm.SugarContext;

import ifce.lara.vite.R;
import ifce.lara.vite.object.Device;

public class BluetoothDiscoverActivity extends ListActivity {

    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SugarContext.init(this);
        ListView lv = getListView();
        LayoutInflater inflater = getLayoutInflater();
        View header = inflater.inflate(R.layout.nav_header_main, lv, false);
        ///((TextView) header.findViewById(R.id.textView2)).setText("\nDispositivos próximos\n");
        //lv.addHeaderView(header, null, false);
        lv.addHeaderView(header);


        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        setListAdapter(arrayAdapter);

        BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
        btAdapter.startDiscovery();

        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(receiver, filter);


    }



    BroadcastReceiver receiver = new BroadcastReceiver()

        /// pega cada dispositivo descoberto e coloca na lista de forma automatica
    {
        @Override
        public void onReceive(Context context, Intent intent) {

             /*  Obtem o Intent que gerou a ação.
                Verifica se a ação corresponde à descoberta de um novo dispositivo.
                Obtem um objeto que representa o dispositivo Bluetooth descoberto.
                Exibe seu nome e endereço na lista.
             */
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                System.out.println("===========Nome do dispositivo "+device.getName());
                arrayAdapter.add(device.getName() + "\n" + device.getAddress());
            }
        }


    };


    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        /*  Extrai nome e endereço a partir do conteúdo do elemento selecionado.
            Nota: position-1 é utilizado pois adicionamos um título à lista e o
        valor de position recebido pelo método é deslocado em uma unidade.
         */
        String dispositivo[] = new String[2];
        String item = (String) getListAdapter().getItem(position-1);
        dispositivo = item.split("\n");
        String devName = dispositivo[0];
        String devAddress = dispositivo[1];


        Device device = new Device(devName,devAddress);
        device.save();

        ConnectionBluetooth connectionBluetooth = new ConnectionBluetooth(devAddress,BluetoothDiscoverActivity.this);
        Thread th = new Thread(connectionBluetooth);
        th.start();
        th.setPriority(1);
        /*  Utiliza um Intent para encapsular as informações de nome e endereço.
            Informa à Activity principal que tudo foi um sucesso!
            Finaliza e retorna à Activity principal.

        Intent returnIntent = new Intent();
        returnIntent.putExtra("btDevName", devName);
        returnIntent.putExtra("btDevAddress", devAddress);
        setResult(RESULT_OK, returnIntent);

         */
        finish();
    }











    @Override
    protected void onDestroy() {

        super.onDestroy();

        /*  Remove o filtro de descoberta de dispositivos do registro.
         */
        unregisterReceiver(receiver);
    }











}
