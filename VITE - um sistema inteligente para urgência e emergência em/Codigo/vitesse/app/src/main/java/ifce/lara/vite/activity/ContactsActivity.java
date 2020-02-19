package ifce.lara.vite.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import java.util.ArrayList;
import ifce.lara.vite.R;
import android.support.v7.app.AppCompatActivity;
import ifce.lara.vite.object.Contact;
import ifce.lara.vite.object.ContactAdapter;

public class ContactsActivity extends AppCompatActivity {

    ArrayList listAdapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ContactsActivity.this, AddContactActivity.class));
            }
        });

        listView = (ListView) findViewById(R.id.lv_contacts_list);
        update();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ContactsActivity.this, AddContactActivity.class);
                Bundle bundle = new Bundle();
                Contact contact = (Contact) parent.getItemAtPosition(position);
                bundle.putLong("idContact", contact.getId());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    protected void update() {
        listAdapter = (ArrayList<Contact>) ifce.lara.vite.object.Contact.listAll(Contact.class);
        ContactAdapter adapter = new ContactAdapter(this, listAdapter);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        update();
    }
}
