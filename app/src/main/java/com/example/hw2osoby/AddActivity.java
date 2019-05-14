package com.example.hw2osoby;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.hw2osoby.tasks.TaskListContent;

import java.util.Objects;

public class AddActivity extends AppCompatActivity /*implements TaskFragment.OnListFragmentClickInteraction*/{

    private String taskPicPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        taskPicPath = "";
    }

    public void addClick (View view){

        Intent data = new Intent();

        EditText taskNameEditTxt = findViewById(R.id.taskName);
        EditText taskDateOfBirthEditTxt = findViewById(R.id.taskDateOfBirth);
        EditText taskDescriptionEditTxt = findViewById(R.id.taskDescription);
        String taskName = taskNameEditTxt.getText().toString();
        String taskDateOfBirth = taskDateOfBirthEditTxt.getText().toString();
        String taskDescriprion = taskDescriptionEditTxt.getText().toString();


        if (taskPicPath == "") {
            int random = (int) (Math.random() * ((10 - 1) + 1));

            switch (random) {                                                                     //Losowanie zdjęcia kontaktu
                case 1:
                    taskPicPath = "avatar1";
                    break;
                case 2:
                    taskPicPath = "avatar2";
                    break;
                case 3:
                    taskPicPath = "avatar3";
                    break;
                case 4:
                    taskPicPath = "avatar4";
                    break;
                case 5:
                    taskPicPath = "avatar5";
                    break;
                case 6:
                    taskPicPath = "avatar6";
                    break;
                case 7:
                    taskPicPath = "avatar7";
                    break;
                case 8:
                    taskPicPath = "avatar8";
                    break;
                case 9:
                    taskPicPath = "avatar9";
                    break;
                case 10:
                    taskPicPath = "avatar10";
                    break;
                default:
                    taskPicPath = "avatar1";
            }
        }

        if (taskName.isEmpty() && taskDateOfBirth.isEmpty() && taskDescriprion.isEmpty()){
            TaskListContent.addItem(new TaskListContent.Task("Task." + TaskListContent.ITEMS.size() + 1,
                    getString(R.string.default_Name), getString(R.string.default_DateOfBirth),
                    getString(R.string.default_Description), taskPicPath));
        }else {
            if (taskName.isEmpty())
                taskName = getString(R.string.default_Name);
            if (taskDateOfBirth.isEmpty())
                taskDateOfBirth = getString(R.string.default_DateOfBirth);
            if (taskDescriprion.isEmpty())
                taskDescriprion = getString(R.string.default_Description);

            TaskListContent.addItem(new TaskListContent.Task("Task." + TaskListContent.ITEMS.size() +1,
                    taskName, taskDateOfBirth, taskDescriprion, taskPicPath));
        }

        taskNameEditTxt.setText("");
        taskDateOfBirthEditTxt.setText("");
        taskDescriptionEditTxt.setText("");

        InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        im.hideSoftInputFromWindow(view.getWindowToken(), 0);

        setResult(RESULT_OK, data);
        finish();
    }

    public void setTaskPicPath (String val){
        taskPicPath = val;
    }

    public void PictureTakenCanceled (){
        Intent data = new Intent();
        setResult(RESULT_CANCELED, data);
        finish();
    }
}
