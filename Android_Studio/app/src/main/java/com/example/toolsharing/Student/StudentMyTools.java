package com.example.toolsharing.Student;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.toolsharing.R;
import com.example.toolsharing.Utils.GetDataServiceInterface;
import com.google.android.material.tabs.TabLayout;

public class StudentMyTools extends Fragment {

    private TabLayout tabLayout;
    GetDataServiceInterface service;
    private Toolbar toolbar;
    private Bundle bundle = new Bundle();
    View view;
    Fragment fragment = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_student_my_tools, container, false);

        bundle.putString("SID", getArguments().getString("sId"));

        fragment = new StudentOwned();
        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_mytools_container, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragment.setArguments(bundle);
        ft.commit();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bundle.putString("SID", getArguments().getString("sId"));

        tabLayout = view.findViewById(R.id.tab_mytools);
        tabLayout.addTab(tabLayout.newTab().setText("Owned"));
        tabLayout.addTab(tabLayout.newTab().setText("Rented"));
        tabLayout.addTab(tabLayout.newTab().setText("History"));
        tabLayout.addTab(tabLayout.newTab().setText("Review"));
        tabLayout.addTab(tabLayout.newTab().setText("Penalty"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        toolbar = view.findViewById(R.id.stu_tools);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.addtool) {
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    final AddTools addTools = new AddTools();
                    fragmentTransaction.replace(R.id.frag_stu, addTools, "addTools");
                    fragmentTransaction.addToBackStack(null);
                    addTools.setArguments(bundle);
                    fragmentTransaction.commit();
                    return true;
                }
                return  false;
            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        fragment = new StudentOwned();
                        break;
                    case 1:
                        fragment = new StudentBorrowed();
                        break;
                    case 2:
                        fragment = new StudentBorrowedHist();
                        break;
                    case 3:
                        fragment = new StudentBorrowedReview();
                        break;
                    case 4:
                        fragment = new StudentBorrowedPenalty();
                        break;
                    default:
                        fragment = new StudentOwned();

                }
                FragmentManager fm = getChildFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.fragment_mytools_container, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                fragment.setArguments(bundle);
                ft.commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
