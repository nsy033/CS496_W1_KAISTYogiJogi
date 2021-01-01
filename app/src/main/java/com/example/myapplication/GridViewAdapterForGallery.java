package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class GridViewAdapterForGallery extends BaseAdapter {
    Context context;
    int layout;
    int img[];
    LayoutInflater inf;

    public GridViewAdapterForGallery(Context context, int layout, int[] img) {
        this.context = context;
        this.layout = layout;
        this.img = img;
        inf = (LayoutInflater) context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return img.length;
    }

    @Override
    public Object getItem(int position) {
        return img[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Context context = parent.getContext();

        ImageView imageView = new ImageView(context);
        imageView.setLayoutParams(new GridView.LayoutParams(350,300));

/**
        Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), img[position]);
        bmp = Bitmap.createScaledBitmap(bmp, 10, 10, false);
        imageView.setAdjustViewBounds(true);
        imageView.setImageBitmap(bmp);
*/
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setPadding(5,5,5,5);
        imageView.setImageResource(img[position]);


        /**
        final int pos = position;
        imageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                View dialogView = (View) View.inflate( parent.getContext(),
                        R.layout.dialog, null);
                AlertDialog.Builder dlg = new AlertDialog.Builder(parent.getContext());
                ImageView ivPoster = (ImageView)dialogView.findViewById(R.id.imageView2);
                ivPoster.setImageResource(img[pos]);
                dlg.setTitle("큰 포스터");
                dlg.setIcon(R.drawable.ic_launcher_foreground);
                dlg.setView(dialogView);
                dlg.setNegativeButton("닫기", null);
                dlg.show();
            }
        });

        return imageView;
         */
        return imageView;
    }
}