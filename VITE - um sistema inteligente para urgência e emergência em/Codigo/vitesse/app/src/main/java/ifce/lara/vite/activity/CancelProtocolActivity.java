package ifce.lara.vite.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

public class CancelProtocolActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(this,"PROCEDIMENTO CANCELADO", Toast.LENGTH_SHORT).show();
        ProtocolEmergencyActivity.teste1 = false;
        ProtocolEmergencyActivity.teste2 = false;
        //AlarmeQueda alarme = new AlarmeQueda(this);
        ///alarme.stop();
        // System.out.println("KKKKKKKKKKKKKKKKKKKKKKKKK12KKKKKKKKKKKKKKKKKKKKKKKKKKKKKKk");

        System.exit(0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
