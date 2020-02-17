package com.example.toolsharing.Admin;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.toolsharing.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AdminBottomNav extends AppCompatActivity {


    private final Fragment adminDashboard = new AdminDashboard();
    //final Fragment fragmentProfile = new Profile();
    private final FragmentManager fm = getSupportFragmentManager();
    private Fragment active = adminDashboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_bottom_nav);


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_nav);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //fm.beginTransaction().add(R.id.main_container, fragmentProfile, "3").hide(fragmentProfile).commit();
        fm.beginTransaction().add(R.id.frag_admin, adminDashboard, "1").commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            if (item.getItemId() == R.id.abottom_home) {
                fm.beginTransaction().hide(active).show(adminDashboard).commit();
                active = adminDashboard;
                return true;

                /*case R.id.navigation_profile:
                    fm.beginTransaction().hide(active).show(fragmentProfile).commit();
                    active = fragmentProfile;
                    return true;*/
            }
            return false;
        }
    };
}
