package com.example.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment2 extends Fragment {

    public fragment2() {}

    public static fragment2 newInstance() {
        fragment2 fragment = new fragment2();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment2, null) ;

        //데이터
        int img[] = { R.drawable.butterfly,R.drawable.drop,R.drawable.italy,R.drawable.owl,R.drawable.springbird,R.drawable.tiger,
                R.drawable.cat,R.drawable.horses,R.drawable.iceland,R.drawable.puppy,R.drawable.rabbit,
                R.drawable.a1,R.drawable.a2,R.drawable.a3,R.drawable.a4,R.drawable.a5,R.drawable.a6,R.drawable.a7,R.drawable.a8,R.drawable.a9,R.drawable.a10,R.drawable.a11,};

        // 커스텀 아답타 생성
        GridViewAdapterForGallery adapter = new GridViewAdapterForGallery (
                getActivity().getApplicationContext(),
                R.layout.dialog,       // GridView 항목의 레이아웃 dialog.xml
                img);    // 데이터

        GridView gv = (GridView) view.findViewById (R.id.gridView1);
        gv.setAdapter(adapter);  // 커스텀 아답타를 GridView 에 적용

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                View dialogView = (View) View.inflate(getActivity(),
                        R.layout.dialog, null);
                AlertDialog.Builder dlg = new AlertDialog.Builder(getActivity());
                ImageView ivPoster = (ImageView)dialogView.findViewById(R.id.imageView2);
                ivPoster.setImageResource(img[position]);
                dlg.setTitle("큰 포스터" );
                dlg.setIcon(R.drawable.ic_launcher_foreground);
                dlg.setView(dialogView);
                dlg.setNegativeButton("Close", null);
                dlg.show();
            }
        });

        // Inflate the layout for this fragment
        return view;
    }


}

