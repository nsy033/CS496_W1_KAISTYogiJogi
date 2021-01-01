package com.example.myapplication;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment3#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment3 extends Fragment {

    public int dep_icon[] = {  R.drawable.civil,R.drawable.business,R.drawable.mechanic,R.drawable.physics,
                        R.drawable.brain,R.drawable.system,R.drawable.dna,R.drawable.biochem,
                        R.drawable.design,R.drawable.math,R.drawable.material,R.drawable.nuclear,
                        R.drawable.electrical,R.drawable.computer,R.drawable.chemistry,R.drawable.aerospace};

    public String dep_name[] = {  "건설환경공학과", "기술경영학과", "기계공학과", "물리학과", "바이오 및\n뇌공학과",
                        "산업 및\n시스템공학과", "생명과학과", "생명화학공학과", "산업디자인학과", "수리과학과",
                        "신소재공학과", "원자력 및\n양자공학과", "전기 및\n전자공학과", "전산학과", "화학과", "항공우주공학과" };

    public fragment3() { }

    // TODO: Rename and change types and number of parameters
    public static fragment3 newInstance() {
        fragment3 fragment = new fragment3();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment3, null) ;
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
                Fragment newFragment = new Childfragment(position, dep_icon, dep_name);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.parent_id, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return view ;
    }

    public GridViewAdapterForKaist add_item_to_gridviewadapter(GridViewAdapterForKaist myadapter){

            for(int i=0; i<dep_icon.length;i++){
                myadapter.addItem(ContextCompat.getDrawable(getActivity(), dep_icon[i]), dep_name[i]); //건설환경공학과
            }

        return myadapter;
    }
}