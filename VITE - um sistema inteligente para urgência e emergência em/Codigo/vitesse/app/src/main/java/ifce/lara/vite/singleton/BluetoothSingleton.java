package ifce.lara.vite.singleton;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;

/**
 * Created by otonbraga on 21/09/16.
 */
public class BluetoothSingleton {

    public Intent testBluetooth() {
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter != null) {
            if (!mBluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//                startActivityForResult(enableBtIntent, RESULT_OK);
                return enableBtIntent;
            }
        }
        return null;
    }
}
