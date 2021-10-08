package com.hachung.trainningphoto;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;


import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.view.MenuItem;


import com.google.android.material.navigation.NavigationView;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;
import com.hachung.trainningphoto.adapter.ImageAdapter;
import com.hachung.trainningphoto.databinding.ActivityMainBinding;

import com.hachung.trainningphoto.viewmodel.ImageViewModel;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;


import java.util.List;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, ImageAdapter.OnClickImageListener {
    private ActivityMainBinding binding;
    private ImageViewModel imageViewModel;
    private ImageAdapter imageAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle actionBarDrawerToggle =
                new ActionBarDrawerToggle(this, binding.drawerLayout, toolbar, R.string.navigation_draw_open,
                        R.string.navigation_draw_close
                );
        binding.drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        binding.navigationView.setNavigationItemSelectedListener(this);
        binding.floatingActionButton.setOnClickListener(v -> {
            CropImage.activity().setGuidelines(CropImageView.Guidelines.ON)
                    .start(this);
        });
        checkpermission();
        loadImage();
    }

    private void loadImage() {
        binding.rvList.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        imageViewModel = new ViewModelProvider(this).get(ImageViewModel.class);
        imageViewModel.getImage();
        imageViewModel.listMutableLiveData.observe(this,images -> {
            imageAdapter=new ImageAdapter(images, this);
            binding.rvList.setAdapter(imageAdapter);
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                Intent intent = new Intent(this, EditImage.class);
                intent.putExtra("Uri", resultUri.toString());
                startActivity(intent);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    private void checkpermission() {
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
            }
        };
        TedPermission.create()
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_image_gralley:
                //loadImage();
                return true;
            case R.id.menu_checkupdate:
                return true;
            case R.id.menu_rate_us:
                return true;
            default:
                return false;
        }

    }


    @Override
    public void onClickItem(String image) {
        Intent intent = new Intent(getApplicationContext(), ImageInformation.class);
        intent.putExtra("img", image);
        startActivity(intent);
    }
}