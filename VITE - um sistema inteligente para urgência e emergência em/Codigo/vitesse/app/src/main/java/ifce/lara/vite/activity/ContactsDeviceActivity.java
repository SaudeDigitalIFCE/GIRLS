package ifce.lara.vite.activity;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import ifce.lara.vite.R;

import ifce.lara.vite.object.Contact;
import ifce.lara.vite.object.ContactAdapter;

import static android.app.Activity.RESULT_OK;

public class ContactsDeviceActivity extends AppCompatActivity {

    ListView lvContactsDevice;
    ArrayList contactsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_contact_device);
        lvContactsDevice = (ListView) findViewById(R.id.lv_contacts_device);
        contactsList = new ArrayList();

        showContents();
        ContactAdapter adapter = new ContactAdapter(this, contactsList);
        lvContactsDevice.setAdapter(adapter);

        lvContactsDevice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Contact contact = (Contact) adapterView.getAdapter().getItem(i);
                Intent intent = new Intent();
                intent.putExtra("name", contact.name);
                intent.putExtra("phone", contact.phone);
                setResult(RESULT_OK, intent);
                finish();
//                System.out.println("contact "+contact.name);
            }
        });
    }

    public void showContents() {
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

        while(cursor.moveToNext()){
            String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

            Cursor phoneCursor =
                    contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "= ?", new String[]{id}, null);

            while(phoneCursor.moveToNext()){
                String phoneNumber =
                        phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                contactsList.add(new Contact(name, phoneNumber));
            }
        }
    }
}
