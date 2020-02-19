package ifce.lara.vite.activity;

import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.orm.SugarContext;

import java.util.ArrayList;
import java.util.Set;

import ifce.lara.vite.object.Device;

public class BluetoothSearch extends ListActivity {

    ListView lvDevices;
    BluetoothAdapter adapter;
    Set<BluetoothDevice> devices;
    ArrayList<String> list;
    ArrayAdapter<String> arrayAdapter;
    BluetoothDevice dispositivos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SugarContext.init(this);

        list = new ArrayList<String>();
        adapter = BluetoothAdapter.getDefaultAdapter();
        devices = adapter.getBondedDevices();
        for(BluetoothDevice b:devices){

            list.add(b.getName() + "\n" + b.getAddress());

        }
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);

        setListAdapter(arrayAdapter);
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        String getdevice[] = new String[3];
        String d = this.getListAdapter().getItem(position).toString();
        getdevice = d.split("\n");
        System.out.println(getdevice[0]+" "+getdevice[1]);
        Device device =  new Device(getdevice[0],getdevice[1]);
        device.save();

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
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, filter); // Don't forget to unregister during onDestroy
    }
}