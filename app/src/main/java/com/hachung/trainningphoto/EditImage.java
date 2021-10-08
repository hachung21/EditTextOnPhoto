package com.hachung.trainningphoto;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.Layout;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hachung.trainningphoto.adapter.ColorAdapter;
import com.hachung.trainningphoto.adapter.DecorationAdapter;
import com.hachung.trainningphoto.adapter.FontAdapter;
import com.hachung.trainningphoto.adapter.ImageAdapter;
import com.hachung.trainningphoto.databinding.ActivityEditImageBinding;

import com.hachung.trainningphoto.model.Color;
import com.hachung.trainningphoto.model.Decoration;
import com.hachung.trainningphoto.model.Font;
import com.hachung.trainningphoto.model.Image;
import com.hachung.trainningphoto.onclick.OnClickChangeStickerListener;
import com.hachung.trainningphoto.onclick.OnClickChangeTextListener;

import com.hachung.trainningphoto.viewmodel.EditImageModel;
import com.xiaopo.flying.sticker.DrawableSticker;
import com.xiaopo.flying.sticker.TextSticker;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class EditImage extends AppCompatActivity implements View.OnClickListener, OnClickChangeTextListener, OnClickChangeStickerListener {
    private static final int SELECT_IMAGE_GALLERY = 1;
    private static final String TEXT = "TEXT";
    private static final String BACKGROUD = "BACKGROUD";
    private static final String FILTER = "FILTER";
    private static final String DECO = "DECO";
    private static final String FONT = "FONT";
    private static final String STYLE = "STYLE";
    private static final String COLOR = "COLOR";
    private static final String EDITTEXT = "EDITTEXT";
    private static final String STICKER = "STICKER";
    private static final String TYPO = "TYPO";
    private ActivityEditImageBinding binding;
    private ColorAdapter colorAdapter;
    private DecorationAdapter typoAdapter;
    private FontAdapter fontAdapter;
    private Uri myUri;
    private TextSticker textSticker;
    private Typeface typeface;
    private CustomAnimation customAnimation = new CustomAnimation();
    private boolean checkOut = false;
    private boolean checkpess = false;
    private String noteCheck = "";
    private EditImageModel editImageModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_image);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_image);
        editImageModel = new ViewModelProvider(this).get(EditImageModel.class);
        Bundle extras = getIntent().getExtras();
        myUri = Uri.parse(extras.getString("Uri"));
        binding.imageView3.setImageURI(myUri);
        onClick();


    }


    public void onClick() {
        binding.imageView4.setOnClickListener(this);
        binding.view8.setOnClickListener(this);
        binding.imageView2.setOnClickListener(this);
        binding.button2.setOnClickListener(this);
        binding.button3.setOnClickListener(this);
        binding.lFilter.imageView7.setOnClickListener(this);
        binding.text.buttonStyle.setOnClickListener(this);
        binding.styleText.txtBold.setOnClickListener(this);
        binding.styleText.txtR.setOnClickListener(this);
        binding.styleText.txtItalic.setOnClickListener(this);
        binding.text.buttonEdit.setOnClickListener(this);
        binding.editText.editNhap.setOnClickListener(this);
        binding.text.buttonColor.setOnClickListener(this);
        binding.text.buttonFont.setOnClickListener(this);
        binding.button.setOnClickListener(this);
        binding.button4.setOnClickListener(this);
        binding.decoration.txtTypo.setOnClickListener(this);
        binding.decoration.txtSticker.setOnClickListener(this);

    }

    public void customDiaLog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Thông Báo")
                .setMessage("Bạn muốn thoát không  ???")
                .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);

                    }
                })
                .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).setCancelable(false);
        builder.create().show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageView2:// back
                customDiaLog();

                break;
            case R.id.view8: //add text
                customAnimation.scrolledUp(binding.text.getRoot());
                customAnimation.scrolledDown(binding.view6);
                binding.StickerView.configDefaultIcons();
                textSticker = new TextSticker(this);
                textSticker.setDrawable(ContextCompat.getDrawable(this, R.drawable.sticker_transparent_background));
                textSticker.setText("Yeeu Myyy");
                textSticker.resizeText();
                textSticker.setTextAlign(Layout.Alignment.ALIGN_CENTER);
                binding.StickerView.addSticker(textSticker);
                checkpess = true;
                noteCheck = TEXT;
                break;
            case R.id.imageView4: //save image
                saveImage();
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.button: // text
                slideDownOption();
                customAnimation.scrolledUp(binding.text.getRoot());
                checkpess = true;
                noteCheck = TEXT;
                break;
            case R.id.button2:// backgroud
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_IMAGE_GALLERY);
                checkpess = true;
                noteCheck = BACKGROUD;
                break;
            case R.id.button3: //filter
                checkpess = true;
                noteCheck = FILTER;
                break;
            case R.id.button4: //DECORATION
                slideDownOption();
                customAnimation.scrolledUp(binding.decoration.getRoot());
                checkpess = true;
                noteCheck = DECO;
                break;
            case R.id.buttonEdit:
                slideDownText();
                customAnimation.scrolledUp(binding.editText.getRoot());
                showKeyboard();
                checkpess = true;
                noteCheck = EDITTEXT;
                break;
            case R.id.editNhap:
                String text = binding.editText.editNhap.getText().toString();
                textSticker.setText(text);
                textSticker.resizeText();
                binding.StickerView.replace(textSticker);
                break;
            case R.id.buttonColor:
                slideDownText();
                customAnimation.scrolledUp(binding.colorText.getRoot());
                loadColor();
                checkpess = true;
                noteCheck = COLOR;
                break;
            case R.id.buttonFont:
                slideDownText();
                customAnimation.scrolledUp(binding.fontText.getRoot());
                loadFont();
//                getListAssets("font");
                checkpess = true;
                noteCheck = FONT;
                break;
            case R.id.buttonStyle:
                slideDownText();
                customAnimation.scrolledUp(binding.styleText.getRoot());
                checkpess = true;
                noteCheck = STYLE;
                break;
            case R.id.txtBold:
                Typeface tp = Typeface.create(typeface, Typeface.BOLD);
                textSticker.setTypeface(tp);
                updateSticker();
                break;
            case R.id.txtItalic:
                Typeface tp2 = Typeface.create(typeface, Typeface.ITALIC);
                textSticker.setTypeface(tp2);
                updateSticker();
                break;
            case R.id.txtTypo:
                slideDownDec();
                customAnimation.scrolledUp(binding.typo.getRoot());
                loadTypo();
                checkpess = true;
                noteCheck = TYPO;
                break;
            case R.id.txtSticker:
                slideDownDec();
                customAnimation.scrolledUp(binding.typo.getRoot());
                loadSticker();
                checkpess = true;
                noteCheck = STICKER;
                break;
        }
    }


    public void slideDownOption() {
        customAnimation.scrolledDown(binding.view6);
    }

    public void slideDownText() {
        customAnimation.scrolledDown(binding.text.getRoot());
    }

    public void slideDownFilter() {
        customAnimation.scrolledDown(binding.lFilter.getRoot());
    }

    public void slideDownDec() {
        customAnimation.scrolledDown(binding.decoration.getRoot());
    }

    public void BackToOption() {
        slideDownText();
        customAnimation.scrolledUp(binding.view6);
    }

    public void BackToText() {
        slideDownText();
        customAnimation.scrolledUp(binding.text.getRoot());

    }

    public void BackToDec() {
        slideDownDec();
        customAnimation.scrolledUp(binding.decoration.getRoot());
    }

    public void slideUpOption() {
        BackToOption();
        checkpess = false;
    }

    public void slideUpText() {
        BackToText();
        noteCheck = TEXT;
    }

    public void slieUpDec() {
        BackToDec();
        noteCheck = DECO;
    }


    private void loadFont() {
//        try {
//            String font[] = this.getAssets().list("font");
//            ArrayList<Font> fonts = new ArrayList<>();
//            for (int i = 0; i < font.length; i++) {
//                fonts.add(new Font(font[i]));
//            } fontAdapter = new FontAdapter(mFont, this);
//
//
//
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
////
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        //editImageModel = new ViewModelProvider(this).get(EditImageModel.class);
        editImageModel.getTextFont(EditImage.this, "font");

        editImageModel.mutableLiveData.observe(this, strings -> {
            List<Font> mFonts = new ArrayList<>();
            for (int i = 0; i < strings.size(); i++) {

                mFonts.add(new Font(strings.get(i)));
            }
            fontAdapter = new FontAdapter(mFonts, this);
            binding.fontText.rvFont.setLayoutManager(layoutManager);
            binding.fontText.rvFont.setAdapter(fontAdapter);
        });
//
    }


    @Override
    public void onBackPressed() {
        if (checkpess) {
            if (checkOut) {
                super.onBackPressed();
            } else if (noteCheck.equals(TEXT)) {
                customAnimation.scrolledDown(binding.text.getRoot());
                slideUpOption();

            } else if (noteCheck.equals(FONT)) {
                customAnimation.scrolledDown(binding.fontText.getRoot());
                slideUpText();
            } else if (noteCheck.equals(STYLE)) {
                customAnimation.scrolledDown(binding.styleText.getRoot());
                slideUpText();
            } else if (noteCheck.equals(COLOR)) {
                slideUpText();
                customAnimation.scrolledDown(binding.colorText.getRoot());
            } else if (noteCheck.equals(EDITTEXT)) {
                slideUpText();
                customAnimation.scrolledDown(binding.editText.getRoot());
            } else if (noteCheck.equals(DECO)) {
                slideUpOption();
                customAnimation.scrolledDown(binding.decoration.getRoot());
            } else if (noteCheck.equals(STICKER)) {
                customAnimation.scrolledDown(binding.typo.getRoot());
                slieUpDec();
            } else if (noteCheck.equals(TYPO)) {
                customAnimation.scrolledDown(binding.typo.getRoot());
                slieUpDec();
            }
        }else if (!checkOut && !checkpess) {
            customDiaLog();
        }
    }


    private void loadColor() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        // editImageModel = new ViewModelProvider(this).get(EditImageModel.class);
        editImageModel.getTextColor(this);
        editImageModel.mutableLiveDataColor.observe(this, colors -> {
            colorAdapter = new ColorAdapter(colors, this);
            binding.colorText.rvListColor.setAdapter(colorAdapter);
            binding.colorText.rvListColor.setLayoutManager(layoutManager);
        });
    }

    public void loadTypo() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        // editImageModel = new ViewModelProvider(this).get(EditImageModel.class);
        editImageModel.getDecorationImage(this, "typo");
        editImageModel.mutableLiveDataDeco.observe(this, decorations -> {
            typoAdapter = new DecorationAdapter(decorations, this);
            binding.typo.rvListTypo.setAdapter(typoAdapter);
            binding.typo.rvListTypo.setLayoutManager(gridLayoutManager);
        });

    }

    public void loadSticker() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        // editImageModel = new ViewModelProvider(this).get(EditImageModel.class);
        editImageModel.getDecorationImage(this, "sticker");
        editImageModel.mutableLiveDataDeco.observe(this, decorations -> {
            typoAdapter = new DecorationAdapter(decorations, this);
            binding.typo.rvListTypo.setAdapter(typoAdapter);
            binding.typo.rvListTypo.setLayoutManager(gridLayoutManager);
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_IMAGE_GALLERY && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            binding.imageView3.setImageURI(uri);
        }
    }

    private void saveImage() {

        Bitmap bitmap = getBitmapFromView(binding.StickerView);
        FileOutputStream fos = null;
        String root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
        File SDCard = new File(root + File.separator + "/TranningPhoto");
        if (!SDCard.exists()) {
            SDCard.mkdir();
        }
        String path = String.format("%d.jpg", System.currentTimeMillis());
        File file = new File(SDCard, path);
        try {
            fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Bitmap getBitmapFromView(View view1) {
        view1.setDrawingCacheEnabled(true);
        Bitmap returnedBitmap = Bitmap.createBitmap(view1.getDrawingCache());
        view1.setDrawingCacheEnabled(false);
        return returnedBitmap;

    }

    public void showKeyboard() {
        binding.editText.editNhap.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(binding.editText.editNhap, InputMethodManager.SHOW_IMPLICIT);
    }

    public void closeKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }


    @Override
    public void onClickColor(String color) {
        textSticker.setTextColor(android.graphics.Color.parseColor(color));
        updateSticker();
    }

    @Override
    public void onClickFont(String font) {
        Typeface typeface = Typeface.createFromAsset(this.getAssets(), "font/" + font);
        textSticker.setTypeface(typeface);
        updateSticker();
    }

    public void updateSticker() {
        binding.StickerView.replace(textSticker);
    }


    @Override
    public void onClickTypo(Bitmap Typo) {
        Drawable d = new BitmapDrawable(getResources(), Typo);
        DrawableSticker drawableSticker = new DrawableSticker(d);
        binding.StickerView.addSticker(drawableSticker);
    }
}