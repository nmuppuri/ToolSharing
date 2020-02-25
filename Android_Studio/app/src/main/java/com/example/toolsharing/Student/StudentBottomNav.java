package com.example.toolsharing.Student;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.toolsharing.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class StudentBottomNav extends AppCompatActivity {


    /*final Fragment studentToolSearch = new StudentToolSearch();
    final Fragment toolDetails = new ToolDetailsNOrder();
    final Fragment studentMyTools = new StudentMyTools();
    final FragmentManager fm = getSupportFragmentManager();
    Fragment active = studentToolSearch;*/
    private String stdid;
    private Fragment fragment = null;
    private Bundle b = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_bottom_nav);

        Bundle bundle = getIntent().getExtras();
        stdid = bundle.getString("stdId");

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.s_bottom_nav);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        b.putString("sId", stdid);
        fragment = new StudentToolSearch();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frag_stu, fragment);
        fragment.setArguments(b);
        ft.commit();

        //fm.beginTransaction().add(R.id.frag_stu, studentMyTools, "3").hide(studentMyTools).commit();
        //fm.beginTransaction().add(R.id.frag_stu, toolDetails, "2").hide(toolDetails).commit();
        //fm.beginTransaction().add(R.id.frag_stu, studentToolSearch, "1").commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            if(item.getItemId() == R.id.sbottom_search) {
                //fm.beginTransaction().hide(active).show(studentToolSearch).commit();
                //active = studentToolSearch;
                fragment = new StudentToolSearch();
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.frag_stu, fragment);
                fragment.setArguments(b);
                ft.commit();
            } else if(item.getItemId() == R.id.sbottom_tools) {
                    /*fm.beginTransaction().hide(active).show(studentMyTools).commit();
                    active = studentMyTools;
                    studentMyTools.setArguments(b);*/
                fragment = new StudentMyTools();
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.frag_stu, fragment);
                fragment.setArguments(b);
                ft.commit();
            }else if(item.getItemId() == R.id.sbottom_profile) {
                fragment = new StudentProfile();
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.frag_stu, fragment);
                fragment.setArguments(b);
                ft.commit();
            }else if(item.getItemId() == R.id.sbottom_favorite){
                fragment = new StudentToolFavorite();
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.frag_stu, fragment);
                fragment.setArguments(b);
                ft.commit();

            }else if(item.getItemId() == R.id.sbottom_message){
                fragment = new Message();
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.frag_stu, fragment);
                fragment.setArguments(b);
                ft.commit();
            }
            return true;
        }
    };

    /*@Override
    public void onBackPressed() {
        //super.onBackPressed();
    }*/
}
