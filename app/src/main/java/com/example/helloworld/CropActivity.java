package com.example.helloworld;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import androidx.appcompat.app.AppCompatActivity;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.IOException;

import static com.example.helloworld.MainActivity.img;
import static com.example.helloworld.Page2Fragment.REQUEST_CROP_PHOTO;
import static com.example.helloworld.Page2Fragment.adapter;

public class CropActivity extends AppCompatActivity {

    Uri imageUri, resultUri;
    String str;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.str = getIntent().getExtras().getString("path");
        imageUri = Uri.fromFile(new File(str));

        // start cropping activity for pre-acquired image saved on the device
        CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(this);
    }

    public void onBackPressed(){
        finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CROP_PHOTO:
                CropImage.ActivityResult result = CropImage.getActivityResult(data);

                if (resultCode == RESULT_OK) {
                    resultUri = result.getUri();

                    String absolutePathOfImage = null;
                    String path = "";
                    Bitmap bitmap = null;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), resultUri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    MediaStore.Images.Media.insertImage(this.getContentResolver(), bitmap, null, null);

                    Uri uri;
                    Cursor cursor;
                    int column_index_data, column_index_folder_name;
                    uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

                    String[] projection = {MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME};
                    cursor = this.getContentResolver().query(uri, projection, null, null, null);

                    column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
                    column_index_folder_name = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
                    while (cursor.moveToNext()) {
                        absolutePathOfImage = cursor.getString(column_index_data);
                        File files = new File(absolutePathOfImage);

                        Bitmap myBitmap = BitmapFactory.decodeFile(files.getAbsolutePath());
                        GalleryImage gi = new GalleryImage();

                        int w = myBitmap.getWidth();
                        int h = myBitmap.getHeight();
                        Bitmap resized = Bitmap.createScaledBitmap(myBitmap, w / 5, h / 5, true);

                        gi.setRd(resized);
                        gi.setD(myBitmap);
                        gi.setPath(absolutePathOfImage);
                        boolean flag = true;
                        for (int i = 0; i < img.size(); i++) {
                            if (img.get(i).getPath().equals(gi.getPath())) {
                                flag = false;
                                break;
                            }
                        }
                        if (flag) {
                            img.add(gi);
                            adapter.notifyDataSetChanged();
                        }

                    }
                }else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    Exception error = result.getError();
                }


        }

        finish();
    }
}
