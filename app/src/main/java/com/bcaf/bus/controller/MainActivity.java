package com.bcaf.bus.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import com.bcaf.bus.R;
import com.bcaf.bus.controller.ui.agency.AgencyFragment;
import com.bcaf.bus.controller.ui.bus.BusFragment;
import com.bcaf.bus.controller.ui.home.HomeFragment;
import com.bcaf.bus.controller.ui.profile.ProfileFragment;
import com.bcaf.bus.session.MySession;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity{

    private MySession session;
    private BottomNavigationView navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        session = new MySession(MainActivity.this);

        navView = findViewById(R.id.bottomNavigationView);
        navView.setOnNavigationItemSelectedListener(item -> {

            Fragment fragment = null;
            int id = item.getItemId();
            if (id == R.id.tab_home){
                fragment = new HomeFragment();
            }else if (id == R.id.tab_profile){
                fragment = new ProfileFragment();
            }else if (id == R.id.tab_bus){
                fragment = new BusFragment();
            }else if (id == R.id.tab_agency){
                fragment = new AgencyFragment();
            }
            return loadFragment(fragment);
        });

        loadFragment(new HomeFragment());
    }

    private boolean loadFragment(Fragment fragment){
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_frame, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}