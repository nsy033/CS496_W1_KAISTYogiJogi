package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.DrawableRes;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

public class Childfragment extends Fragment {

    public int position;
    public int[] dep_icon;

    public Childfragment() {
        // Required empty public constructor
    }

    public Childfragment(int position, int[] dep_icon){
        this.position = position;
        this.dep_icon = dep_icon;
    }

    public static Childfragment newInstance() {
        Childfragment fragment = new Childfragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**
         LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(getContext().LAYOUT_INFLATER_SERVICE);

         View nowView = inflater.inflate(R.layout.childfragment,null);
         ImageView mapImageView = (ImageView) nowView.findViewById(R.id.imageView_map);
         TextView building_numTextView = (TextView) nowView.findViewById(R.id.building_num);
         TextView king_phoneTextView = (TextView) nowView.findViewById(R.id.king_phone_number);
         TextView king_emailTextView = (TextView) nowView.findViewById(R.id.king_email);
         TextView admin_phoneTextView = (TextView) nowView.findViewById(R.id.admin_phone_number);
         TextView admin_emailTextView = (TextView) nowView.findViewById(R.id.admin_email);

         mapImageView.setImageResource(R.drawable.a1);
         building_numTextView.setText("N1");
         king_phoneTextView.setText("010-4423-1123");
         king_emailTextView.setText("gsg@naver.com");
         admin_phoneTextView.setText("010-4425-5677");
         admin_emailTextView.setText("11ag@naver.com");
        */
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.childfragment, null) ;
        ChildfragmentAdapter adapter = new ChildfragmentAdapter();
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, LIST_MENU)
        ListView listview = (ListView) view.findViewById(R.id.listview_forchildfragment);
        listview.setAdapter(adapter);

        adapter = add_item_to_CFadapter(adapter, position);

        return view;
    }

    public ChildfragmentAdapter add_item_to_CFadapter(ChildfragmentAdapter myadapter, int position){

        String dep_num = "N11";
        String king_phone_number = "010-4245-2323";
        String king_email = "gugu@kaist.ac.kr";
        String admin_phone_number = "010-1344-2353";
        String admin_email = "khiw@kasit.ac.kr";

        myadapter.addItem(ContextCompat.getDrawable(getActivity(), dep_icon[position]), dep_num, king_phone_number,king_email,admin_phone_number,admin_email);
        return myadapter;
    }
}