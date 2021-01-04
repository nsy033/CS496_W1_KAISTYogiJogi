package com.example.helloworld;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.example.helloworld.MainActivity.img;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Page2Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Page2Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    static MyGridAdapter adapter = null;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Page2Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Page2Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Page2Fragment newInstance(String param1, String param2) {
        Page2Fragment fragment = new Page2Fragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.page2, null);

        adapter = new MyGridAdapter(
                getActivity().getApplicationContext(),
                R.layout.dialog,       // GridView 항목의 레이아웃 row.xml
                img);    // 데이터
        adapter.notifyDataSetChanged();

        GridView gv = (GridView) view.findViewById(R.id.gridView1);
        gv.setAdapter(adapter);  // 커스텀 아답타를 GridView 에 적용

        ImageButton cam = (ImageButton) view.findViewById(R.id.camera);
        cam.setScaleType(ImageButton.ScaleType.FIT_CENTER);

        cam.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Toast.makeText(getContext(), "camera", Toast.LENGTH_LONG);
                dispatchTakePictureIntent();
                adapter.notifyDataSetChanged();
                gv.setAdapter(adapter);
            }
        });

        return view;
    }


    public class MyGridAdapter extends BaseAdapter {
        Context context;
        int layout;
        ArrayList<GalleryImage> img;
        LayoutInflater inf;

        public MyGridAdapter(Context context, int layout, ArrayList<GalleryImage> img) {
            this.context = context;
            this.layout = layout;
            this.img = img;
            inf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public int getCount() {
            return img.size();
        }

        public Object getItem(int position) {
            return img.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {

            ImageView imageView = new ImageView(context);

            Display display = getActivity().getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getRealSize(size); // or getSize(size)
            int width = size.x;

            width /= 3;
            width -= 10;

            imageView.setLayoutParams(new GridView.LayoutParams(width, width));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(5, 5, 5, 5);

            imageView.setImageBitmap(img.get(position).getRd());
            final int pos = position;

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //View dialogView = (View) View.inflate(getActivity(), R.layout.dialog, null);
                    //AlertDialog.Builder dlg = new AlertDialog.Builder(getActivity());
                    //ImageView ivPoster = (ImageView) dialogView.findViewById(R.id.ivPoster);
                    //ivPoster.setImageResource(img[pos]);
                    //dlg.setTitle("Title");
                    //dlg.setIcon(R.drawable.ic_launcher_foreground);
                    //dlg.setView(dialogView);
                    //dlg.setPositiveButton("CLOSE", null);
                    //dlg.show();
                    showDialog(pos, view);
                }
            });

            imageView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    android.app.AlertDialog.Builder adb = new AlertDialog.Builder(getActivity(), R.style.MyDialogTheme);
                    adb.setTitle("Delete")
                       .setNeutralButton("CONFIRM", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {

                               String imagePath = img.get(position).getPath();
                               img.remove(position);

                               File file = new File(imagePath).getAbsoluteFile();

                               if(file.exists()){
                                   System.gc();
                                   System.runFinalization();
                                   boolean ch = file.delete();
                                   getActivity().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + imagePath)));
                               }

                               adapter.notifyDataSetChanged();

                           }
                    })
                    .setPositiveButton("CANCEL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    AlertDialog finalDialog = adb.create();
                    finalDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                        @Override
                        public void onShow(DialogInterface arg0) {
                            finalDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#6E6557"));
                            finalDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#6E6557"));
                            finalDialog.getButton(AlertDialog.BUTTON_NEUTRAL).setTextColor(Color.parseColor("#6E6557"));
                        }
                    });
                    finalDialog.show();
                    return true;
                }
            });

            return imageView;
        }

    }

    final private static String TAG = "태그명";
    Button btn_photo;
    ImageView iv_photo;

    final static int TAKE_PICTURE = 1;

    String mCurrentPhotoPath;
    final static int REQUEST_TAKE_PHOTO = 1;

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir );
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    public void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) { }
            if(photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getContext(), "com.example.helloworld.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        try {
            switch (requestCode) {
                case REQUEST_TAKE_PHOTO: {
                    if (resultCode == RESULT_OK) {
                        File file = new File(mCurrentPhotoPath);
                        Bitmap bitmap;

                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), Uri.fromFile(file));
                            if (bitmap != null) {
                                Matrix matrix = new Matrix();
                                matrix.postRotate(90);
                                Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

                                String path = "";
                                MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), rotatedBitmap, null, null);

                                String absolutePathOfImage = null;

                                Uri uri;
                                Cursor cursor;
                                int column_index_data, column_index_folder_name;
                                uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

                                String[] projection = { MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME };

                                cursor = getActivity().getContentResolver().query(uri, projection, null,null, null);

                                column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
                                column_index_folder_name = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
                                while (cursor.moveToNext()) {
                                    absolutePathOfImage = cursor.getString(column_index_data);
                                    File files = new File(absolutePathOfImage);

                                    Bitmap myBitmap = BitmapFactory.decodeFile(files.getAbsolutePath());
                                    GalleryImage gi = new GalleryImage();

                                    int w = myBitmap.getWidth();
                                    int h = myBitmap.getHeight();
                                    Bitmap resized = Bitmap.createScaledBitmap( myBitmap, w/5, h/5, true );

                                    gi.setRd(resized);
                                    gi.setD(myBitmap);
                                    gi.setPath(absolutePathOfImage);
                                    boolean flag = true;
                                    for(int i=0; i<img.size(); i++){
                                        if(img.get(i).getPath().equals(gi.getPath())){
                                            flag = false;
                                            break;
                                        }
                                    }
                                    if(flag) {
                                        img.add(gi);
                                        adapter.notifyDataSetChanged();
                                    }
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                    break;
                }
            }
        } catch(Exception error) {
            error.printStackTrace();
        }
    }

    public void showDialog(int position, View v){
        Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.pager_layout);

        List<PagerModel> pagerArr = new ArrayList<>();

        for(int i=0;i<img.size(); i++){
            pagerArr.add(new PagerModel(""+(i+1), "Pager Item #" + i, BitmapFactory.decodeFile(img.get(position).getPath())));
        }

        TestPagerAdapter adapter = new TestPagerAdapter(getContext(), pagerArr);
        ViewPager pager = (ViewPager) dialog.findViewById(R.id.pager);
        pager.setAdapter(adapter);
        //pager.setPageTransformer(true, new ZoomOutPageTransformer());
        pager.setCurrentItem(position);

        dialog.show();

    }
/*
    public class ZoomOutPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.85f;
        private static final float MIN_ALPHA = 0.5f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();
            int pageHeight = view.getHeight();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0f);

            } else if (position <= 1) { // [-1,1]
                // Modify the default slide transition to shrink the page as well
                float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                float vertMargin = pageHeight * (1 - scaleFactor) / 2;
                float horzMargin = pageWidth * (1 - scaleFactor) / 2;
                if (position < 0) {
                    view.setTranslationX(horzMargin - vertMargin / 2);
                } else {
                    view.setTranslationX(-horzMargin + vertMargin / 2);
                }

                // Scale the page down (between MIN_SCALE and 1)
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

                // Fade the page relative to its size.
                view.setAlpha(MIN_ALPHA +
                        (scaleFactor - MIN_SCALE) /
                                (1 - MIN_SCALE) * (1 - MIN_ALPHA));

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0f);
            }
        }
    }*/
}