package com.hachung.trainningphoto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import com.hachung.trainningphoto.databinding.ActivityImageInformationBinding;
import com.hachung.trainningphoto.model.Image;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.Format;

public class ImageInformation extends AppCompatActivity implements View.OnClickListener {
    private ActivityImageInformationBinding binding;
    private String img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_information);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_image_information);
        getInfor();
        binding.imgEdit.setOnClickListener(this);
        binding.imgDelete.setOnClickListener(this);
        binding.imgBack.setOnClickListener(this);
        binding.imgShare.setOnClickListener(this);
        binding.imgConvert.setOnClickListener(this);
    }

    private void getInfor() {
        Bundle bundle = getIntent().getExtras();
        img = bundle.getString("img");
        binding.image.setImageURI(Uri.parse(img));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgEdit:
                Intent intent = new Intent(this, EditImage.class);
                intent.putExtra("Uri", img);
                startActivity(intent);
                break;
            case R.id.imgBack:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.imgDelete:
                File file = new File(img);
                if (file.exists()) {
                    file.delete();
                    Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, MainActivity.class));
                }
                break;
            case R.id.imgShare:
//                Intent share = new Intent(Intent.ACTION_SEND);
//                share.setType("image/*");
//                Uri uri = Uri.parse(img);
//                share.putExtra(Intent.EXTRA_STREAM, uri);
//                startActivity(Intent.createChooser(share, "Share Image"));
//                break;
                sendImage(img);
                break;
            case R.id.imgConvert:
                BitmapDrawable bitmapDrawable = (BitmapDrawable) binding.image.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();
                WallpaperManager myWallpaperManager
                        = WallpaperManager.getInstance(getApplicationContext());
                try {
                    myWallpaperManager.setBitmap(bitmap);
                    Toast.makeText(this, "Set Backgroud Done ", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                break;

        }
    }
    protected void sendImage(final String imagePath) {
        MediaScannerConnection.MediaScannerConnectionClient mediaScannerClient = new MediaScannerConnection.MediaScannerConnectionClient() {
            private MediaScannerConnection msc = null;
            {
                msc = new MediaScannerConnection(getApplicationContext(), this);
                msc.connect();
            }

            public void onMediaScannerConnected() {
                msc.scanFile(imagePath, null);
            }

            public void onScanCompleted(String path, Uri uri) {
                Intent sendIntent = new Intent(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_STREAM, uri);
                sendIntent.setType("image/*");
                startActivity(sendIntent);
                msc.disconnect();
            }
        };
    }
}