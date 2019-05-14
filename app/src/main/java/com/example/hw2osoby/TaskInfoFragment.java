package com.example.hw2osoby;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hw2osoby.tasks.TaskListContent;


/**
 * A simple {@link Fragment} subclass.
 */
public class TaskInfoFragment extends Fragment {

    private TaskListContent.Task mDisplayedTask;

    public TaskInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_task_info, container, false);
    }

    public void displayTask (TaskListContent.Task task){
        FragmentActivity activity = getActivity();

        TextView taskInfoName = activity.findViewById(R.id.taskInfoName);
        TextView taskInfoDateOfBirth = activity.findViewById(R.id.taskInfoDateOfBirth);
        TextView taskInfoDescription = activity.findViewById(R.id.taskInfoDescription);
        final ImageView taskInfoImage = activity.findViewById(R.id.taskInfoImage);

        taskInfoName.setText(task.name);
        taskInfoDateOfBirth.setText(task.dateOfBirth);
        taskInfoDescription.setText(task.details);
        if (task.picPath != null && !task.picPath.isEmpty()){
            if (task.picPath.contains("avatar")) {
                Drawable taskDrawable;
                switch (task.picPath) {
                    case "avatar1":
                        taskDrawable = activity.getResources().getDrawable(R.drawable.avatar1);
                        break;
                    case "avatar2":
                        taskDrawable = activity.getResources().getDrawable(R.drawable.avatar2);
                        break;
                    case "avatar3":
                        taskDrawable = activity.getResources().getDrawable(R.drawable.avatar3);
                        break;
                    case "avatar4":
                        taskDrawable = activity.getResources().getDrawable(R.drawable.avatar4);
                        break;
                    case "avatar5":
                        taskDrawable = activity.getResources().getDrawable(R.drawable.avatar5);
                        break;
                    case "avatar6":
                        taskDrawable = activity.getResources().getDrawable(R.drawable.avatar6);
                        break;
                    case "avatar7":
                        taskDrawable = activity.getResources().getDrawable(R.drawable.avatar7);
                        break;
                    case "avatar8":
                        taskDrawable = activity.getResources().getDrawable(R.drawable.avatar8);
                        break;
                    case "avatar9":
                        taskDrawable = activity.getResources().getDrawable(R.drawable.avatar9);
                        break;
                    case "avatar10":
                        taskDrawable = activity.getResources().getDrawable(R.drawable.avatar10);
                        break;
                    default:
                        taskDrawable = activity.getResources().getDrawable(R.drawable.avatar1);
                }
                taskInfoImage.setImageDrawable(taskDrawable);
            }else {
                Handler handler = new Handler();

                taskInfoImage.setVisibility(View.INVISIBLE);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        taskInfoImage.setVisibility(View.VISIBLE);
                        Bitmap cameraImage = PicUtils.decodePic(mDisplayedTask.picPath, taskInfoImage.getWidth(), taskInfoImage.getHeight());
                        taskInfoImage.setImageBitmap(cameraImage);
                    }
                }, 200);
            }
        }else{
            taskInfoImage.setImageDrawable(activity.getResources().getDrawable(R.drawable.avatar1));
        }
        mDisplayedTask = task;
    }

    @Override
    public void onActivityCreated (@Nullable Bundle savesInstanceState){
        super.onActivityCreated(savesInstanceState);
        Intent intent = getActivity().getIntent();
        if (intent != null){
            TaskListContent.Task receivedTask = intent.getParcelableExtra(MainActivity.taskExtra);
            if (receivedTask != null){
                displayTask(receivedTask);
            }
        }
    }
}
