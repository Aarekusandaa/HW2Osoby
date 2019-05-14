package com.example.hw2osoby;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.hw2osoby.tasks.TaskListContent;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;


public class MainActivity extends AppCompatActivity implements TaskFragment.OnListFragmentClickInteraction,
                                        DeleteDialog.OnDeleteDialogInteractionListener{

    public static final String taskExtra = "taskExtra";
    private static int REQUEST_ADD = 1;
    private int currentItemPosition = -1;

    private TaskListContent.Task currentTask;
    private final String CURRENT_TASK_KEY = "CurrentTask";

    public static final String takePhoto = "takePhoto";
    private final String ITEMS_JSON_FILE = "items.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null){
            currentTask = savedInstanceState.getParcelable(CURRENT_TASK_KEY);
        }

        FloatingActionButton fabAdd = findViewById(R.id.FABadd);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent add = new Intent(getApplicationContext(), AddActivity.class);
                startActivityForResult(add, REQUEST_ADD);
            }
        });

        FloatingActionButton fabCamera = findViewById(R.id.FABcamera);
        fabCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent add = new Intent(getApplicationContext(), AddActivity.class);
                add.putExtra(takePhoto, true);
                startActivityForResult(add, REQUEST_ADD);
            }
        });

        restoreFromJson ();

    }

    /*public void DeleteItem (int position){
        showDeleteDialog();
        currentItemPosition = position;
    }*/

    @Override
    protected void onSaveInstanceState (Bundle outState){
        if (currentTask != null){
            outState.putParcelable(CURRENT_TASK_KEY, currentTask);
            super.onSaveInstanceState(outState);
        }
    }

    private void showDeleteDialog (){
        DeleteDialog.newInstance().show(getSupportFragmentManager(), getString(R.string.delete_dialog_tag));
    }

    private void startTaskInfoActivity (TaskListContent.Task task, int position){

        Intent intentTask = new Intent(this, TaskInfoActivity.class);
        intentTask.putExtra(taskExtra, task);
        startActivity(intentTask);
    }

    @Override
    public void onListFragmentClickInteraction (TaskListContent.Task task, int position, boolean deleteButton){
        if (deleteButton){
            showDeleteDialog();
            currentItemPosition = position;
        }else {
            currentTask = task;
            Toast.makeText(this, getString(R.string.item_selected_msg), Toast.LENGTH_SHORT).show();
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                displayTaskInFragment(task);
            }else {
                startTaskInfoActivity(task, position);
            }
        }
    }

    /*
    private void displayAddInFragment (AddFragment addFrag){
        AddFragment addFragment = ((AddFragment)getSupportFragmentManager().findFragmentById(R.id.displayFragment));
        if (addFragment != null){
            startAddActivity();
        }
    }*/

    private void displayTaskInFragment (TaskListContent.Task task){
        TaskInfoFragment taskInfoFragment = ((TaskInfoFragment)getSupportFragmentManager().findFragmentById(R.id.displayFragment));
        if (taskInfoFragment != null){
            taskInfoFragment.displayTask(task);
        }
    }

    @Override
    public void onActivityResult (int requestCode, int resultCode, Intent data){
        if (resultCode == RESULT_OK){
            if (requestCode == REQUEST_ADD){
                ((TaskFragment)getSupportFragmentManager().findFragmentById(R.id.taskFragment)).notifyDataChange();
            }
        }else if (resultCode == RESULT_CANCELED){
            Toast.makeText(getApplicationContext(), "Action canceled", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        if (currentItemPosition != -1 && currentItemPosition <TaskListContent.ITEMS.size()){
            TaskListContent.removeItem(currentItemPosition);
            ((TaskFragment)getSupportFragmentManager().findFragmentById(R.id.taskFragment)).notifyDataChange();
        }
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        View v = findViewById(R.id.addButton);
        if (v != null){
            Snackbar.make(v, getString(R.string.delete_cancel_msg), Snackbar.LENGTH_LONG).setAction(
                    getString(R.string.retry_msg), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showDeleteDialog();
                        }
                    }
            ).show();
        }
    }

    @Override
    protected void onResume (){
        super.onResume();
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            if (currentTask != null)
                displayTaskInFragment(currentTask);
        }
    }

    @Override
    protected void onDestroy (){
        saveItemsToJson ();
        super.onDestroy();
    }

    @Override
    protected void onStop (){
        saveItemsToJson ();
        super.onStop();
    }

    @Override
    public void onPause(){
        super.onPause();
    }

    private void restoreFromJson (){
        FileInputStream inputStream;
        int DEFAULT_BUFFER_SIZE = 10000;
        Gson gson = new Gson();
        String readJson;

        try{
          inputStream = openFileInput(ITEMS_JSON_FILE);
          FileReader reader = new FileReader(inputStream.getFD());
          char[] buf = new char[DEFAULT_BUFFER_SIZE];
          int n;
          StringBuilder builder = new StringBuilder();

          while ((n = reader.read(buf)) >= 0){
              String tmp = String.valueOf(buf);
              String substring = (n < DEFAULT_BUFFER_SIZE) ? tmp.substring(0, n) : tmp;
              builder.append(substring);
          }

          reader.close();
          readJson = builder.toString();
          Type collectionType = new TypeToken<List<TaskListContent.Task>>(){
          }.getType();
          List<TaskListContent.Task> o = gson.fromJson (readJson, collectionType);

          if (o != null){
              TaskListContent.clearList();
              for (TaskListContent.Task task : o){
                  TaskListContent.addItem(task);
              }
          }
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void saveItemsToJson (){
        Gson gson = new Gson();
        String listJson = gson.toJson(TaskListContent.ITEMS);
        FileOutputStream outputStream;
        try{
            outputStream = openFileOutput(ITEMS_JSON_FILE, MODE_PRIVATE);
            FileWriter writer = new FileWriter(outputStream.getFD());
            writer.write(listJson);
            writer.close();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
