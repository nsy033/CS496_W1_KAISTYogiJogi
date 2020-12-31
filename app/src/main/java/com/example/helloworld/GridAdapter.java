package com.example.helloworld;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;

import static com.example.helloworld.MainActivity.department;
import static com.example.helloworld.MainActivity.des;
import static com.example.helloworld.MainActivity.name;

public class GridAdapter extends BaseAdapter {

    Context context;
    ArrayList<GridViewItem> list;
    Point size;

    LayoutInflater inflater;
    AlertDialog dialog;

    public GridAdapter(Context context, ArrayList<GridViewItem> list, Point size) {
        this.context = context;
        this.list = list;
        this.size = size;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void addDTO(GridViewItem dto) {
        list.add(dto);
    }
    public void delDTO(int position) {
        list.remove(position);
    }
    public void removeDTOs() {
        list.clear();
    }

    @Override
    public int getCount() {
        return list.size();
    }
    @Override
    public Object getItem (int position) {
        return list.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        DepViewHolder viewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.gridview_item, parent, false);
            viewHolder = new DepViewHolder();
            viewHolder.tv1 = convertView.findViewById(R.id.text_gridview);
            viewHolder.imageView = convertView.findViewById(R.id.image_gridview);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (DepViewHolder) convertView.getTag();
        }

        GridViewItem dto = list.get(position);
        String des = dto.getDescrip();
        String pos = dto.getPosition();
        String tel = dto.getTel();
        String fax = dto.getFax();
        int resId = dto.getResId();

        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, dto.getDescrip(), Toast.LENGTH_SHORT).show();
                popupImgXml(list.get(position).getResId(), list.get(position).getDescrip());
            }
        });

        return convertView;
    }

    public class DepViewHolder {
        public ImageView imageView;
        public TextView tv1, tv2, tv3, tv4;
    }

    public void popupImgXml(int resId, String name) {
        //일단 res에 popupimg.xml 만든다
        //그 다음 화면을 inflate 시켜 setView 한다

        //팝업창에 xml 붙이기///////////////
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.popupimg, null);
        ImageView imageView = view.findViewById(R.id.pop_imageView);
        TextView textView = view.findViewById(R.id.pop_textView);

        imageView.setImageResource(resId);
        textView.setTextSize(35);
        textView.setText(name + "\n");
        textView.append(name + "\n" + name + "\n" + name + "\n" + name + "\n" + name + "\n" + name + "\n" + name + "\n" + name + "\n");
        /////////////////////////

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("이미지 띄우기")
                .setView(view);

        builder.setNegativeButton("종료", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(dialog != null) {
                    dialog.dismiss();
                }
            }
        });
        dialog = builder.create();
        dialog.show();

        //디바이스 사이즈를 받아 팝업 크기창을 조절한다.
        int sizeX = size.x;
        int sizeY = size.y;

        //AlertDialog에서 위치 크기 수정
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();

        params.x = (int) Math.round(sizeX * 0.005); // X위치
        params.y = (int) Math.round(sizeY * 0.01); // Y위치
        params.width = (int) Math.round(sizeX * 0.9);
        params.height = (int) Math.round(sizeY * 0.8);
        dialog.getWindow().setAttributes(params);
    }

}
