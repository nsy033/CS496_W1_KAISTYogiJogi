package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import org.w3c.dom.Text;
import java.util.ArrayList;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class Childfragment extends Fragment implements OnMapReadyCallback {

    public int position;
    public int[] dep_icon;
    public String[] dep_name;
    public ArrayList<ChildFragmentItem> departmentlist;
    public GoogleMap mMap;
    public MapView mapView;
    public Childfragment() {
        // Required empty public constructor
    }

    public Childfragment(int position, int[] dep_icon, String[] dep_name, ArrayList<ChildFragmentItem> departmentlist){
        this.position = position;
        this.dep_icon = dep_icon;
        this.dep_name = dep_name;
        this.departmentlist = departmentlist;
    }

    public static Childfragment newInstance() {
        Childfragment fragment = new Childfragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.childfragment, null) ;

        ImageView iv_map = (ImageView) view.findViewById(R.id.imageView_map);
        TextView tv_building_num = (TextView) view.findViewById(R.id.building_num);
        TextView tv_king_phone_number = (TextView) view.findViewById(R.id.king_phone_number);
        TextView tv_king_email = (TextView) view.findViewById(R.id.king_email);
        TextView tv_admin_phone_number = (TextView) view.findViewById(R.id.admin_phone_number);
        TextView tv_admin_email = (TextView) view.findViewById(R.id.admin_email);

        //setting information of department
        iv_map.setImageResource(dep_icon[position]);
        tv_building_num.setText(departmentlist.get(position).getDep_num() + " | " +dep_name[position]);
        tv_king_phone_number.setText(departmentlist.get(position).getDep_king_phone());
        tv_king_email.setText(departmentlist.get(position).getDep_king_email());
        tv_admin_phone_number.setText(departmentlist.get(position).getDep_admin_phone());
        tv_admin_email.setText(departmentlist.get(position).getDep_admin_email());


        //to use google maps
        mapView = (MapView) view.findViewById(R.id.map);
        mapView.getMapAsync(this);
        //SupportMapFragment mapFragment = (SupportMapFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.map);
        //mapFragment.getMapAsync(this);
        /**
        int what = departmentlist.size();
        iv_map.setImageResource(dep_icon[position]);
        tv_building_num.setText("112||" + what);
        tv_king_phone_number.setText("12");
        tv_king_email.setText("12");
        tv_admin_phone_number.setText("12");
        tv_admin_email.setText("12");
         */
        /**
        ChildfragmentAdapter adapter = new ChildfragmentAdapter();
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, LIST_MENU)
        ListView listview = (ListView) view.findViewById(R.id.listview_forchildfragment);
        listview.setAdapter(adapter);
        adapter = add_item_to_CFadapter(adapter, position);
        */
        return view;
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {

        mMap = googleMap;

        LatLng IT_BUILDING = new LatLng(36.37425, 127.36563);
        LatLng KAIST = new LatLng(36.37246, 127.36040);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(IT_BUILDING);
        markerOptions.title("KAIST");
        markerOptions.snippet("김병호김삼열 IT융합 빌딩(N1)");
        mMap.addMarker(markerOptions);

        // 기존에 사용하던 다음 2줄은 문제가 있습니다.

        // CameraUpdateFactory.zoomTo가 오동작하네요.
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(SEOUL));
        //mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(KAIST, 14));

    }
    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }
    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onLowMemory();
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//액티비티가 처음 생성될 때 실행되는 함수
        if(mapView != null)
        {
            mapView.onCreate(savedInstanceState);
        }
    }

}