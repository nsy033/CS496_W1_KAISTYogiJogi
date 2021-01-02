package com.example.helloworld;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Page3Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Page3Fragment extends Fragment {

    static final int department[] = {
            R.drawable.aerospace, R.drawable.brain, R.drawable.business, R.drawable.chemistry,
            R.drawable.civil, R.drawable.computer, R.drawable.design, R.drawable.dna,
            R.drawable.electrical, R.drawable.math, R.drawable.mechanic, R.drawable.nuclear,
            R.drawable.physics, R.drawable.system, R.drawable.biochem, R.drawable.material
    };
    static final String name[] = {
            "AE", "BBE", "BTM", "CH", "CEE", "CS", "ID", "BS", "EE", "MS", "ME", "NQE", "PH", "ISE", "CBE", "MSE"
    };
    static final String des[] = {
            "Aerospace Engineering", "Bio and Brain Engineering", "Business Technology Management", "Chemistry",
            "Civil and Environmental Engineering", "Computer Science", "Industrial Design", "Biological Sciences", "Electrical Engineering",
            "Mathematical Sciences", "Mechanical Engineering", "Nuclear and Quantum Engineering", "Physics", "Industrial and Systems Engineering",
            "Chemical and Biomolecular Engineering", "Materials Sciences and Engineering"
    };

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Page3Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment3.
     */
    // TODO: Rename and change types and number of parameters
    public static Page3Fragment newInstance(String param1, String param2) {
        Page3Fragment fragment = new Page3Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page3, null) ;
        GridViewAdapterForKaist adapter = new GridViewAdapterForKaist();
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, LIST_MENU)
        GridView gridview = (GridView) view.findViewById(R.id.gridView_third_tab);
        gridview.setAdapter(adapter);

        adapter = add_item_to_gridviewadapter(adapter);

        // 화면 전환 프래그먼트 선언 및 초기 화면 설정
        //FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        //fragmentTransaction.add(R.id.parent_id, new Childfragment()).commit();

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                Fragment newFragment = new Childfragment(position, department, name);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.parent_id, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return view ;
    }

    public GridViewAdapterForKaist add_item_to_gridviewadapter(GridViewAdapterForKaist myadapter) {
        for (int i=0; i<department.length;i++){
            myadapter.addItem(ContextCompat.getDrawable(getActivity(), department[i]), name[i]);
        }
        return myadapter;
    }
}