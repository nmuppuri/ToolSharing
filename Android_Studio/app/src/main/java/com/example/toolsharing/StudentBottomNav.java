package com.example.toolsharing;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.toolsharing.Student.StudentToolSearch;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class StudentBottomNav extends AppCompatActivity {


    final Fragment studentDashboard = new StudentToolSearch();
    final Fragment studentMyTools = new StudentMyTools();
    final FragmentManager fm = getSupportFragmentManager();
    Fragment active = studentDashboard;
    String stdid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_bottom_nav);

        Bundle bundle = getIntent().getExtras();
        stdid = bundle.getString("stdId");

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.s_bottom_nav);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        fm.beginTransaction().add(R.id.frag_stu, studentMyTools, "2").hide(studentMyTools).commit();
        fm.beginTransaction().add(R.id.frag_stu, studentDashboard, "1").commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.sbottom_search:
                    fm.beginTransaction().hide(active).show(studentDashboard).commit();
                    active = studentDashboard;
                    return true;

                case R.id.sbottom_tools:
                    Bundle b = new Bundle();
                    b.putString("sId", stdid);
                    fm.beginTransaction().hide(active).show(studentMyTools).commit();
                    active = studentMyTools;
                    studentMyTools.setArguments(b);
                    return true;
            }
            return false;
        }
    };
}
