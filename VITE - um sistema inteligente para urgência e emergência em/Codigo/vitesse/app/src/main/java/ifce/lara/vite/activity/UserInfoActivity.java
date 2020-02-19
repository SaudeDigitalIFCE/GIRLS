package ifce.lara.vite.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import ifce.lara.vite.R;
import ifce.lara.vite.object.Contact;
import ifce.lara.vite.object.UserInfo;

public class UserInfoActivity extends AppCompatActivity {

    EditText name;
    EditText disiase;
    EditText allergy;
    EditText medicines;
    EditText blood;
    EditText phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        name = (EditText) findViewById(R.id.et_name);
        disiase = (EditText) findViewById(R.id.et_disiase);
        allergy = (EditText) findViewById(R.id.et_allergy);
        medicines = (EditText) findViewById(R.id.et_medicines);
        blood = (EditText) findViewById(R.id.et_blood);
        phone = (EditText) findViewById(R.id.et_phone);

        show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.upddel, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_update) {
            if (testFill()) {update(); finish();} else {
                Toast.makeText(this, "Preencha os campos obrigatórios*", Toast.LENGTH_LONG);
            }
            return true;
        } else if (id == R.id.action_delete) {
            delete();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void show() {
        UserInfo userInfo = UserInfo.findById(UserInfo.class, 0);
        if (userInfo != null) {
            name.setText(userInfo.name);
            disiase.setText(userInfo.disiase);
            allergy.setText(userInfo.allergy);
            medicines.setText(userInfo.medicines);
            blood.setText(userInfo.blood);
            phone.setText(userInfo.phone);



        }
    }

    private void update() {
        UserInfo userInfo = UserInfo.findById(UserInfo.class, 0);
        if (userInfo == null) {
            userInfo = new UserInfo();
            userInfo.setId((long) 0);
        }
        userInfo.name = name.getText().toString();
        userInfo.disiase = disiase.getText().toString();
       userInfo.allergy = allergy.getText().toString();
        userInfo.medicines = medicines.getText().toString();
        userInfo.blood = blood.getText().toString();
        userInfo.phone = phone.getText().toString();
        userInfo.save();

        Toast.makeText(this,"Dados Salvos",Toast.LENGTH_SHORT).show();
    }

    private void delete() {
        UserInfo userInfo = UserInfo.findById(UserInfo.class, 0);
        name.setText("");
        disiase.setText("");
        allergy.setText("");
        medicines.setText("");
        blood.setText("");
        phone.setText("");
        if (userInfo != null) {
            userInfo.delete();
        } else {
            finish();
        }
    }

    private boolean testFill() {
        if (name.getText().length() > 0
            && phone.getText().length() > 0) {
            return true;
        }
        return false;
    }
}
