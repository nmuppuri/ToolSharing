package com.example.toolsharing.Admin;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.toolsharing.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AdminBottomNav extends AppCompatActivity {

    private String alid;
    private Fragment fragment = null;
    private Bundle b = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_bottom_nav);

        Bundle bundle = getIntent().getExtras();
        alid = bundle.getString("alid");


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_nav);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        b.putString("alid", alid);
        fragment = new AdminDashboard();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frag_admin, fragment);
        fragment.setArguments(b);
        ft.commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch(item.getItemId()){

                default:
                    fragment = new AdminDashboard();
                    break;

                case R.id.abottom_profile:
                    fragment = new AdminProfile();

            }
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.frag_admin, fragment);
            fragment.setArguments(b);
            ft.commit();
            return true;
        }
    };

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}
