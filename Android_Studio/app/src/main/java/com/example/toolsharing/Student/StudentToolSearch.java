package com.example.toolsharing.Student;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toolsharing.PojoClasses.SearchToolsList_Pojo;
import com.example.toolsharing.PojoClasses.StatusMessage_Pojo;
import com.example.toolsharing.R;
import com.example.toolsharing.Utils.GetDataServiceInterface;
import com.example.toolsharing.Utils.RetrofitClientInstance;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentToolSearch extends Fragment {

    View view;
    GetDataServiceInterface service;
    ToolsListSearchRecylerAdapter toolsListSearchRecylerAdapter;
    ArrayList<SearchToolsList_Pojo> searchToolsList_pojos;
    RecyclerView recyclerView;
    Toolbar toolbar;
    String str;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_student_tool_search, container, false);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        searchToolList();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        MenuInflater menuInflater = new MenuInflater(view.getContext());
        menuInflater.inflate(R.menu.sort, menu);

        MenuItem searchItem = menu.findItem(R.id.toolsearch);

        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                toolsListSearchRecylerAdapter.getFilter().filter(s);
                //recyclerView.setAdapter(toolsListSearchRecylerAdapter);
                return true;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }



    public void searchToolList()
    {
        service = RetrofitClientInstance.getRetrofitInstance().create(GetDataServiceInterface.class);
        Call<StatusMessage_Pojo> call = service.getSearchTools();

        System.out.println("URL Tools: " + call);

        call.enqueue(new Callback<StatusMessage_Pojo>() {
            @Override
            public void onResponse(Call<StatusMessage_Pojo> call, Response<StatusMessage_Pojo> response) {
                final StatusMessage_Pojo statusMessage_pojo = response.body();
                String status = statusMessage_pojo.getStatus();
                System.out.println("URL Student recycler Called!: " + status);

                if(!status.equalsIgnoreCase("error")) {
                    searchToolsList_pojos = new ArrayList<>(statusMessage_pojo.getSearchToolsList());
                    toolsListSearchRecylerAdapter = new ToolsListSearchRecylerAdapter(searchToolsList_pojos, getActivity().getApplicationContext());
                    @SuppressLint("WrongConstant") LinearLayoutManager linearLayout = new LinearLayoutManager(getActivity().getApplicationContext(),LinearLayoutManager.VERTICAL,false);
                    recyclerView = getView().findViewById(R.id.recycler_student_search);
                    //empty_view.setVisibility(View.GONE);
                    recyclerView.setLayoutManager(linearLayout);
                    //toolsListSearchRecylerAdapter.getFilter().filter(s);
                    recyclerView.setAdapter(toolsListSearchRecylerAdapter);

                    toolsListSearchRecylerAdapter.setOnItemClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //view.startAnimation(buttonClick);
                            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
                            int position = viewHolder.getAdapterPosition();

                            int psid = statusMessage_pojo.getSearchToolsList().get(position).getPostedStudentId();
                            int toolId = statusMessage_pojo.getSearchToolsList().get(position).getPostedToolId();
                            String toolName = statusMessage_pojo.getSearchToolsList().get(position).getToolName();
                            String toolImg = statusMessage_pojo.getSearchToolsList().get(position).getToolImg();
                            String toolDesc = statusMessage_pojo.getSearchToolsList().get(position).getToolDesc();
                            int trating = statusMessage_pojo.getSearchToolsList().get(position).getToolRating();
                            int availableInDays = statusMessage_pojo.getSearchToolsList().get(position).getToolAvailableInDays();

                            Bundle bundle = new Bundle();
                            bundle.putString("psId", String.valueOf(psid));
                            bundle.putString("lsid", getArguments().getString("sId"));
                            bundle.putString("tId", String.valueOf(toolId));
                            bundle.putString("tN", toolName);
                            bundle.putString("tImg", toolImg);
                            bundle.putString("tD", toolDesc);
                            bundle.putString("tr", String.valueOf(trating));
                            bundle.putString("avail", String.valueOf(availableInDays));

                            FragmentManager fragmentManager = getFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            final ToolDetailsNOrder toolDetailsNOrder = new ToolDetailsNOrder();
                            fragmentTransaction.replace(R.id.frag_stu, toolDetailsNOrder);
                            fragmentTransaction.addToBackStack(null);
                            toolDetailsNOrder.setArguments(bundle);
                            fragmentTransaction.commit();

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
    }
}
