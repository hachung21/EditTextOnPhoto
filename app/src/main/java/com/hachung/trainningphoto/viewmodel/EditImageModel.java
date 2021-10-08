package com.hachung.trainningphoto.viewmodel;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.MainThread;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hachung.trainningphoto.R;
import com.hachung.trainningphoto.model.Color;
import com.hachung.trainningphoto.model.Decoration;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import io.reactivex.schedulers.Schedulers;

public class EditImageModel extends ViewModel {
    public MutableLiveData<List<Color>> mutableLiveDataColor = new MutableLiveData<>();
    public MutableLiveData<List<String>> mutableLiveData = new MutableLiveData<>();
    public MutableLiveData<List<Decoration>> mutableLiveDataDeco = new MutableLiveData<>();


    public EditImageModel() {
    }

    public static ArrayList<Color> getColor(Context context) {
        String colors[] = context.getResources().getStringArray(R.array.colors_hex_code);
        ArrayList<Color> colorList = new ArrayList<>();
        for (int i = 0; i < colors.length; i++) {
            colorList.add(new Color(colors[i]));
        }
        return colorList;
    }


    Single<ArrayList<Color>> getColorText(Context context) {
        return Single.fromCallable(() -> getColor(context)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public void getTextColor(Context context) {
        getColorText(context).subscribe(new SingleObserver<List<Color>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
            }

            @Override
            public void onSuccess(@NonNull List<Color> colors) {
                mutableLiveDataColor.postValue(colors);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }
        });
    }

    public static List<String> getListAssets(Context context, String folder) {
        ArrayList<String> list = new ArrayList<>();
        try {
            String[] path = context.getAssets().list(folder);
            for (int i = 0; i < path.length; i++) {
                list.add(path[i]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    Single<List<String>> getFontText(Context context, String folder) {
        return Single.fromCallable(() -> getListAssets(context, folder)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public void getTextFont(Context context, String folder) {
        getFontText(context, folder).subscribe(new SingleObserver<List<String>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onSuccess(@NonNull List<String> strings) {
                mutableLiveData.postValue(strings);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }
        });
    }

    public static List<Decoration> getListDecoraton(Context context,String folder){
        ArrayList<Decoration> list=new ArrayList<>();
        try {
           String[] path = context.getAssets().list(folder);
            for (int i = 0; i < path.length; i++) {
                InputStream is = context.getAssets().open(folder + "/" + path[i]);
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                list.add(new Decoration(bitmap));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;

    }
    Single<List<Decoration>> getDeoration(Context context,String folder){
        return Single.fromCallable(() -> getListDecoraton(context,folder)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
    public void getDecorationImage(Context context,String folder){
         getDeoration(context,folder).subscribe(new SingleObserver<List<Decoration>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onSuccess(@NonNull List<Decoration> decorations) {
                mutableLiveDataDeco.postValue(decorations);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }
        });
    }

}
