package ifce.lara.vite.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.orm.SugarContext;

import ifce.lara.vite.R;
import ifce.lara.vite.object.Contact;

public class AddContactActivity extends AppCompatActivity {

    TextView tvContactsList;
    EditText etName;
    EditText etPhone;
    Long idContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact_list);

        SugarContext.init(AddContactActivity.this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            idContact = bundle.getLong("idContact");
        }

        //busca do banco
        etName = (EditText) findViewById(R.id.et_name);
        etPhone = (EditText) findViewById(R.id.et_phone);

        tvContactsList = (TextView) findViewById(R.id.tv_contacts_list);
        tvContactsList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddContactActivity.this, ContactsDeviceActivity.class);
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK){
                etName.setText(data.getStringExtra("name"));
                etPhone.setText(data.getStringExtra("phone"));
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (idContact != null) {
            getMenuInflater().inflate(R.menu.upddel, menu);
            setContact();
        } else {
            getMenuInflater().inflate(R.menu.add, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add) {
            Contact contact = new Contact(etName.getText().toString(), etPhone.getText().toString());
            contact.save();
            finish();
            return true;
        } else if (id == R.id.action_update) {
            Contact contact = Contact.findById(Contact.class, idContact);
            contact.name = etName.getText().toString();
            contact.phone = etPhone.getText().toString();
            contact.save();
            finish();
            return true;
        } else if (id == R.id.action_delete) {
            Contact contact = Contact.findById(Contact.class, idContact);
            contact.delete();
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setContact() {
        Contact contact = Contact.findById(Contact.class, idContact);
        etName.setText(contact.name);
        etPhone.setText(contact.phone);
    }
}
