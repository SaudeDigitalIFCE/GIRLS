package ifce.lara.vite.activity;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.orm.SugarContext;

import ifce.lara.vite.R;

public class MainActivity extends AppCompatActivity

        implements NavigationView.OnNavigationItemSelectedListener {

        public static MediaPlayer mp;
        Service_Geolocation service_Geolocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SugarContext.init(MainActivity.this);

        mp = MediaPlayer.create(MainActivity.this, R.raw.sirene);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ProtocolEmergencyActivity.class));
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

   /*
    public void onToggleClicked(View v) {
        boolean on = ((ToggleButton) v).isChecked();

        if (on){
            service_Geolocation = new Service_Geolocation(MainActivity.this);
            service_Geolocation.get_Location();
            ///Toast.makeText(this,"Ligado", Toast.LENGTH_SHORT).show();
        } else{

            service_Geolocation = new Service_Geolocation(MainActivity.this);
            service_Geolocation.cancel();
            Toast.makeText(this,"Desligado", Toast.LENGTH_SHORT).show();
        }
    }
*/

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /// Handle action bar item clicks here. The action bar will
        /// automatically handle clicks on the Home/Up button, so long
        /// as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        ///noinspection SimplifiableIfStatement

        /*
        if(id == R.id.off_audio){
            Player.getPlayer(this).stop();
            return true;
        }

        */

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_devices) {
            startActivity(new Intent(this, DevicesActivity.class));
        } else if (id == R.id.nav_contacts) {
            startActivity(new Intent(this, ContactsActivity.class));
        } else if (id == R.id.nav_user_info) {
            startActivity(new Intent(this, UserInfoActivity.class));
        } else if (id == R.id.nav_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
        } else if (id == R.id.nav_service) {
            // servico background
        } else if (id == R.id.nav_reset) {
            // reseta banco
        } else if (id == R.id.nav_map) {
            startActivity(new Intent(this, MapsActivity.class));
        }

        else if (id == R.id.prontuario) {
        startActivity(new Intent(this, NotificationActivity.class));
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}