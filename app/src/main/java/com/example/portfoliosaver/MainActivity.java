package com.example.portfoliosaver;

import static java.security.AccessController.getContext;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, BottomNavigationView.OnNavigationItemSelectedListener {
    private ImageView imgShare;
    private EditText edtDescription;
    private Button btnselectresume, btndownloadresume;
    Bitmap recievedImageBitmap;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.teal_700)));
        imgShare = findViewById(R.id.imageshare);
        edtDescription = findViewById(R.id.edtdescription);
        btnselectresume = findViewById(R.id.btnselectresume);
        imgShare.setOnClickListener(MainActivity.this);
        btnselectresume.setOnClickListener(MainActivity.this);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.user:
                Intent intent1= new Intent(MainActivity.this,UserActivity.class);
                startActivity(intent1);
                break;
            case R.id.social_media:
                Intent intent = new Intent(MainActivity.this,SocialConnect.class);
                startActivity(intent);
                break;
            case R.id.menu:
                Intent intent2 =  new Intent(MainActivity.this,MenuActivity.class);
                startActivity(intent2);
                break;
        }
        return false;
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageshare:
                if ((Build.VERSION.SDK_INT >= 23) &&
                        (ActivityCompat.checkSelfPermission(MainActivity.this,
                                Manifest.permission.READ_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED)) {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1000);
                } else {
                    getChosenImage();
                }
                break;
            case R.id.btnselectresume:
                if(recievedImageBitmap!=null){
                    if(edtDescription.getText().toString().equals("")){
                        FancyToast.makeText(MainActivity.this,
                                "Error:You should sepcify a description",
                                FancyToast.LENGTH_LONG, FancyToast.ERROR,
                                true).show();

                    } else{
                        ByteArrayOutputStream byteArrayOutputStream =  new ByteArrayOutputStream();
                        recievedImageBitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
                        byte[]bytes=byteArrayOutputStream.toByteArray();
                        ParseFile parseFile = new ParseFile("faceebook.png",bytes);
                        ParseObject parseObject = new ParseObject("Photo");
                        parseObject.put("picture",parseFile);
                        parseObject.put("image_des",edtDescription.getText().toString());
                        final ProgressDialog dialog = new ProgressDialog(MainActivity.this);
                        dialog.setMessage("Loading");
                        dialog.show();
                        parseObject.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {

                                if (e == null) {
                                    FancyToast.makeText(MainActivity.this,
                                            "Done!!",
                                            FancyToast.LENGTH_LONG, FancyToast.SUCCESS,
                                            true).show();
                                }else{
                                    FancyToast.makeText(MainActivity.this,
                                            "Unknown Error" +e.getMessage(),
                                            FancyToast.LENGTH_LONG, FancyToast.ERROR,
                                            true).show();
                                }
                                dialog.dismiss();
                            }
                        });
                    }

                }else{
                    FancyToast.makeText(MainActivity.this,
                            "Error:You must select an image ",
                            FancyToast.LENGTH_LONG, FancyToast.ERROR,
                            true).show();
                }
                break;
        }
    }
    private void getChosenImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,2000);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length>0 && grantResults[0]==
                PackageManager.PERMISSION_GRANTED){
            getChosenImage();

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==2000){
            if(resultCode== Activity.RESULT_OK) {
                try {
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = MainActivity.this.getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columIndex);
                    cursor.close();
                    recievedImageBitmap= BitmapFactory.decodeFile(picturePath);
                    imgShare.setImageBitmap(recievedImageBitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}

