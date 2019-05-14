package com.example.hw2osoby;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.hw2osoby.tasks.TaskListContent;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddFragment extends Fragment  {

    public static final int REQUEST_IMAGE_CAPTURE = 1;
    private String mCurrentPicPath;

    public AddFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add, container, false);
    }

    @Override
    public void onActivityCreated (@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        FragmentActivity activity = getActivity();

        activity.findViewById(R.id.displayFragment).setVisibility(View.INVISIBLE);
        Intent received_intent = activity.getIntent();
        boolean takePhoto = received_intent.getBooleanExtra(MainActivity.takePhoto, false);
        if (takePhoto){
            takePicture();
        }
        getActivity().findViewById(R.id.displayFragment).setVisibility(View.VISIBLE);
    }

    @Override
    public void onActivityResult (int requestCode, int resultCode, Intent data){
        FragmentActivity hold = getActivity();
        if (hold != null){
            if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK){
                ((AddActivity) hold).setTaskPicPath(mCurrentPicPath);
            }else{
                ((AddActivity) hold).PictureTakenCanceled();
            }
        }
    }

    private void takePicture(){
        Intent picture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (picture.resolveActivity(getActivity().getPackageManager()) != null){
            File photoFile = null;
            try {
                photoFile = createImageFile();
            }catch (IOException e){
                e.printStackTrace();
            }

            if (photoFile != null){
                Uri photoUri = FileProvider.getUriForFile(getActivity(), getString(R.string.myFileprovider), photoFile);
                picture.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(picture, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    private File createImageFile () throws IOException{
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = ("TaskPicture" + TaskListContent.ITEMS.size() + 1) + timeStamp + " ";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        mCurrentPicPath = image.getAbsolutePath();
        return image;
    }
}
