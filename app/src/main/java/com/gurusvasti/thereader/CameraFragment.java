package com.gurusvasti.thereader;


import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.Objects;

import static androidx.core.content.PermissionChecker.checkSelfPermission;


public class CameraFragment extends Fragment {

    View v;
    ImageView imageview;
    Uri image_uri;
    String resultText = null;

    //OutputStream outputStream;

    public static final int CAM_PERM_CODE = 100;
    public static final int STORAGE_PERM_CODE = 101;
    public static final int PICK_IMAGE_CODE = 1000;
    public static final int CAMERA_TAKE_CODE = 1001;

    public CameraFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_camera, container, false);

        ImageButton btnPickGallery = (ImageButton) v.findViewById(R.id.gellery_botton);
        ImageButton btnLuanchCamera = (ImageButton) v.findViewById(R.id.camera_botton);
        ImageButton btnConvertImgToText = (ImageButton) v.findViewById(R.id.convert_btn);
        imageview = (ImageView) v.findViewById(R.id.Image_view);

        btnLuanchCamera.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // check a permission
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                        && ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

                    requestPermissions(new String[]{Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE}, CAM_PERM_CODE);

                }else{
                    openCamera();
                }

            }
        });

        btnPickGallery.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // check a permission
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERM_CODE);

                }else{
                    pickImageFromGallery();
                }
            }
        });





        return v;
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == CAM_PERM_CODE ){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Log.d("Camera Fragment", "I am Patty");
                    openCamera();
            }else{
               Toast.makeText(getContext(),"Camera Permission is Required to Use a Camera",Toast.LENGTH_SHORT).show();
            }

        }else if(requestCode == STORAGE_PERM_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("Camera Fragment", "I am Patty");
                  pickImageFromGallery();
            } else {
                Toast.makeText(getContext(), "Camera Permission is Required to Access the Images", Toast.LENGTH_SHORT).show();

            }
        }
    }


    private void openCamera(){
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE,"The reader picture");
        image_uri = getContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,image_uri);
        startActivityForResult(cameraIntent,CAMERA_TAKE_CODE);


    }

    private void pickImageFromGallery(){
        Intent imageIntent = new Intent(Intent.ACTION_PICK);
        imageIntent.setType("image/*");
        startActivityForResult(imageIntent,PICK_IMAGE_CODE);

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

         if( resultCode == Activity.RESULT_OK) {
             // got image from camera
             if (requestCode == CAMERA_TAKE_CODE) {
                 CropImage.activity(image_uri)
                         .setGuidelines(CropImageView.Guidelines.ON)// This line enables image guidelines
                         .start(getContext(),this);

                 // imageview.setImageURI(image_uri);

                // got image from the gallery
             } else if (requestCode == PICK_IMAGE_CODE) {

                 CropImage.activity(data.getData())
                         .setGuidelines(CropImageView.Guidelines.ON)
                         .start(getContext(),this);

                // imageview.setImageURI(data.getData());

             }
         }

         // get cropped image

        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            Log.d("k","cropped method is not error");
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if(resultCode == Activity.RESULT_OK ){
                Uri result_uri = result.getUri();//get image uri
                imageview.setImageURI(result_uri);
            }
            //get drawable bitmap for text recognition
            BitmapDrawable bitmapDrawable = (BitmapDrawable)imageview.getDrawable();
            Bitmap bitmap = bitmapDrawable.getBitmap();

            TextRecognizer recognizer= new TextRecognizer.Builder(getContext()).build();

            if(!recognizer.isOperational()){
                Toast.makeText(getContext(),"error",Toast.LENGTH_SHORT).show();
            }else{
                Frame frame = new Frame.Builder().setBitmap(bitmap).build();
                SparseArray<TextBlock> items = recognizer.detect(frame);
                StringBuilder sb = new StringBuilder();
                resultText = null;
                //get text from sb until there is no text
                for(int i =0; i<items.size() ; i++){
                    TextBlock myItem = items.valueAt(i);
                    sb.append(myItem.getValue());
                    sb.append("\n");

                }
                /// set text to edit text
                resultText = sb.toString();
                ImageButton btnConvertImgToText = (ImageButton) v.findViewById(R.id.convert_btn);
                btnConvertImgToText.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        // check a permission
                        Intent intent = new Intent(getContext(),showResultActivity.class);
                        intent.putExtra("result",resultText);
                        startActivity(intent);
                    }
                });
            }
        }else if(resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
           // Exception error = result.getError();
            Log.d("k","In an error cropped method");
        }
        Log.d("k","in side out");
    }
}
