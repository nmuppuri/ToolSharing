package com.example.toolsharing.Student;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

public class StudentToolFavorite extends Fragment implements ToolsListSearchRecylerAdapter.SelectedTool {

    private View view;
    private GetDataServiceInterface service;
    private ToolsListSearchRecylerAdapter toolsListFavRecylerAdapter;
    private ArrayList<SearchToolsList_Pojo> searchToolsList_pojos;
    private RecyclerView recyclerView;
    Toolbar toolbar;
    String str;
    TextView empty_view;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_student_tool_favorite, container, false);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        favList(Integer.parseInt(getArguments().getString("sId")));
        empty_view = view.findViewById(R.id.fav_empty_view);
    }


    private void favList(int lsid)
    {
        service = RetrofitClientInstance.getRetrofitInstance().create(GetDataServiceInterface.class);
        Call<StatusMessage_Pojo> call = service.favoriteTools(lsid);

        System.out.println("URL Tools: " + call);

        call.enqueue(new Callback<StatusMessage_Pojo>() {
            @Override
            public void onResponse(Call<StatusMessage_Pojo> call, Response<StatusMessage_Pojo> response) {
                final StatusMessage_Pojo statusMessage_pojo = response.body();
                String status = statusMessage_pojo.getStatus();
                System.out.println("URL Student recycler Called!: " + status);
                recyclerView = view.findViewById(R.id.recycler_student_fav);
                if(!status.equalsIgnoreCase("error")) {
                    searchToolsList_pojos = new ArrayList<>(statusMessage_pojo.getSearchToolsList());
                    toolsListFavRecylerAdapter = new ToolsListSearchRecylerAdapter(searchToolsList_pojos, StudentToolFavorite.this, getActivity().getApplicationContext());
                    @SuppressLint("WrongConstant") LinearLayoutManager linearLayout = new LinearLayoutManager(getActivity().getApplicationContext(),LinearLayoutManager.VERTICAL,false);

                    //empty_view.setVisibility(View.GONE);
                    recyclerView.setLayoutManager(linearLayout);
                    //toolsListFavRecylerAdapter.getFilter().filter(s);
                    recyclerView.setAdapter(toolsListFavRecylerAdapter);

                    /*toolsListFavRecylerAdapter.setOnItemClickListener(new View.OnClickListener() {
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
                            //String fromdate = statusMessage_pojo.getSearchToolsList().get(position).getFromDate().toString();
                            float trating = statusMessage_pojo.getSearchToolsList().get(position).getToolRating();
                            int availableFromInDays = statusMessage_pojo.getSearchToolsList().get(position).getToolAvailableFromInDays();
                            int availableTillInDays = statusMessage_pojo.getSearchToolsList().get(position).getToolAvailableTillInDays();
                            int availability = statusMessage_pojo.getSearchToolsList().get(position).getToolAvailability();
                            int fav = statusMessage_pojo.getSearchToolsList().get(position).getToolFavorite();



                            Bundle bundle = new Bundle();
                            bundle.putString("psId", String.valueOf(psid));
                            bundle.putString("lsid", getArguments().getString("sId"));
                            bundle.putString("favor", "1");
                            bundle.putString("tId", String.valueOf(toolId));
                            bundle.putString("tN", toolName);
                            bundle.putString("tImg", toolImg);
                            bundle.putString("tD", toolDesc);
                            bundle.putString("tr", String.valueOf(trating));
                            bundle.putString("availF", String.valueOf(availableFromInDays));
                            bundle.putString("availT", String.valueOf(availableTillInDays));
                            bundle.putString("availability", String.valueOf(availability));
                            bundle.putString("fav", String.valueOf(fav));
                            //bundle.putString("fromDate", fromdate);

                            FragmentManager fragmentManager = getFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            ToolDetailsNOrder toolDetailsNOrder = new ToolDetailsNOrder();
                            fragmentTransaction.replace(R.id.frag_stu, toolDetailsNOrder);
                            fragmentTransaction.addToBackStack(null);
                            toolDetailsNOrder.setArguments(bundle);
                            fragmentTransaction.commit();

                            System.out.println("URL psid: " + psid);
                            System.out.println("URL toolImg: " + toolImg);
                        }
                    });*/

                }
                else {
                    recyclerView.setVisibility(View.INVISIBLE);
                    empty_view.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<StatusMessage_Pojo> call, Throwable t) {

                System.out.println("URL Failure Called! :" + t.getMessage());

            }
        });
    }

    @Override
    public void selectedTool(SearchToolsList_Pojo searchToolsList_pojo) {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

        Bundle bundle = new Bundle();
        bundle.putString("lsid", getArguments().getString("sId"));
        bundle.putString("favor" , "1");
        bundle.putSerializable("data", searchToolsList_pojo);

        Fragment fragment = new ToolDetailsNOrder();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frag_stu, fragment);
        fragment.setArguments(bundle);
        ft.addToBackStack(null);
        ft.commit();
    }
}
