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

    TabLayout tabLayout;
    GetDataServiceInterface service;
    Toolbar toolbar;
    Bundle bundle = new Bundle();

    String sid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_my_tools, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bundle.putString("SID", getArguments().getString("sId"));

        tabLayout = view.findViewById(R.id.tab_mytools);
        tabLayout.addTab(tabLayout.newTab().setText("Owned"));
        tabLayout.addTab(tabLayout.newTab().setText("Borrowed"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        toolbar = view.findViewById(R.id.stu_tools);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.addtool:

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
        //tabLayout.getTabAt(0).setIcon(R.drawable.ic_profile_black_24dp);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tabLayout.getSelectedTabPosition() == 0) {

                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    final StudentOwned studentOwned = new StudentOwned();
                    fragmentTransaction.replace(R.id.fragment_mytools_container, studentOwned, "OwnTools");
                    fragmentTransaction.addToBackStack(null);
                    studentOwned.setArguments(bundle);
                    fragmentTransaction.commit();
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if(tabLayout.getSelectedTabPosition() == 0) {

                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    final StudentOwned studentOwned = new StudentOwned();
                    fragmentTransaction.replace(R.id.fragment_mytools_container, studentOwned, "OwnTools");
                    fragmentTransaction.addToBackStack(null);
                    studentOwned.setArguments(bundle);
                    fragmentTransaction.commit();
                }

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                if(tabLayout.getSelectedTabPosition() == 0) {

                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    final StudentOwned studentOwned = new StudentOwned();
                    fragmentTransaction.replace(R.id.fragment_mytools_container, studentOwned, "OwnTools");
                    fragmentTransaction.addToBackStack(null);
                    studentOwned.setArguments(bundle);
                    fragmentTransaction.commit();
                }

            }
        });
    }

    /*public void ownedTools()
    {
        service = RetrofitClientInstance.getRetrofitInstance().create(GetDataServiceInterface.class);
        Call<StatusMessage_Pojo> call = service.getAllTools();

        System.out.println("URL Tools: " + call);

        call.enqueue(new Callback<StatusMessage_Pojo>() {
            @Override
            public void onResponse(Call<StatusMessage_Pojo> call, Response<StatusMessage_Pojo> response) {
                final StatusMessage_Pojo statusMessage_pojo = response.body();
                String status = statusMessage_pojo.getStatus();
                System.out.println("URL Student recycler Called!: " + status);

                if(!status.equalsIgnoreCase("error")) {
                    toolsList_pojos = new ArrayList<>(statusMessage_pojo.getToolsList());
                    toolsListRecylerAdapter = new ToolsListRecylerAdapter(toolsList_pojos, getActivity().getApplicationContext());
                    @SuppressLint("WrongConstant") LinearLayoutManager linearLayout = new LinearLayoutManager(getActivity().getApplicationContext(),LinearLayoutManager.VERTICAL,false);
                    recyclerView = getView().findViewById(R.id.recycler_student);
                    //empty_view.setVisibility(View.GONE);
                    recyclerView.setLayoutManager(linearLayout);
                    recyclerView.setAdapter(toolsListRecylerAdapter);

                    toolsListRecylerAdapter.setOnItemClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //view.startAnimation(buttonClick);
                            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
                            int position = viewHolder.getAdapterPosition();

                            String toolName = statusMessage_pojo.getToolsList().get(position).getToolName();
                            String toolImg = statusMessage_pojo.getToolsList().get(position).getToolImg();

                            System.out.println("URL toolName: " + toolName);
                            System.out.println("URL toolImg: " + toolImg);
                        }
                    });

                }
                else {
                    //recyclerView.setVisibility(View.INVISIBLE);
                    //empty_view.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<StatusMessage_Pojo> call, Throwable t) {

                System.out.println("URL Failure Called! :" + t.getMessage());

            }
        });
    }*/
}
