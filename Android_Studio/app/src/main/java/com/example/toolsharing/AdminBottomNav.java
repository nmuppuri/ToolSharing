package com.example.toolsharing;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.toolsharing.Admin.AdminDashboard;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AdminBottomNav extends AppCompatActivity {


    final Fragment adminDashboard = new AdminDashboard();
    //final Fragment fragmentProfile = new Profile();
    final FragmentManager fm = getSupportFragmentManager();
    Fragment active = adminDashboard;

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
            switch (item.getItemId()) {
                case R.id.abottom_home:
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
