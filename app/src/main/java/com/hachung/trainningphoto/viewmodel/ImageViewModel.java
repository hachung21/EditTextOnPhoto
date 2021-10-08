package com.hachung.trainningphoto.viewmodel;

import android.os.Environment;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hachung.trainningphoto.model.Image;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

public class ImageViewModel  extends ViewModel {
    public  MutableLiveData<List<Image>> listMutableLiveData=new MutableLiveData<>();


    public ImageViewModel() {

    }
    public static  ArrayList<Image> getData() {
        ArrayList<Image> mImageList = new ArrayList<>();
        String root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
        File SDCard = new File(root + File.separator + "/TranningPhoto");
        if (SDCard.exists()) {
            File[] files = SDCard.listFiles();
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                Image image = new Image();
                image.setAvatar(file.getAbsolutePath());
                mImageList.add(image);
            }
        }
        return mImageList;
    }

    public void getImage(){
        getImageGalley().subscribe(new SingleObserver<List<Image>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onSuccess(@NonNull List<Image> images) {
                listMutableLiveData.postValue(images);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }
        });
    }
    public Single<List<Image>> getImageGalley(){
        return Single.fromCallable(() -> getData());
    }
}
