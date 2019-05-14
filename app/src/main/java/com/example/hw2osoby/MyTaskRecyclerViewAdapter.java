package com.example.hw2osoby;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hw2osoby.TaskFragment.OnListFragmentClickInteraction;
import com.example.hw2osoby.tasks.TaskListContent.Task;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Task} and makes a call to the
 * specified {@link TaskFragment.OnListFragmentClickInteraction}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyTaskRecyclerViewAdapter extends RecyclerView.Adapter<MyTaskRecyclerViewAdapter.ViewHolder> {

    private final List<Task> mValues;
    private final OnListFragmentClickInteraction mListener;

    public MyTaskRecyclerViewAdapter(List<Task> items, OnListFragmentClickInteraction listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_task, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Task task = mValues.get(position);
        holder.mItem = task;
        holder.mContentView.setText(task.name);
        final String picPath = task.picPath;
        Context context = holder.mView.getContext();
        if (picPath != null && !picPath.isEmpty()){
            if (picPath.contains("avatar")){
                Drawable taskDrawable;
                switch (picPath){
                    case "avatar1":
                        taskDrawable = context.getResources().getDrawable(R.drawable.avatar1);
                        break;
                    case "avatar2":
                        taskDrawable = context.getResources().getDrawable(R.drawable.avatar2);
                        break;
                    case "avatar3":
                        taskDrawable = context.getResources().getDrawable(R.drawable.avatar3);
                        break;
                    case "avatar4":
                        taskDrawable = context.getResources().getDrawable(R.drawable.avatar4);
                        break;
                    case "avatar5":
                        taskDrawable = context.getResources().getDrawable(R.drawable.avatar5);
                        break;
                    case "avatar6":
                        taskDrawable = context.getResources().getDrawable(R.drawable.avatar6);
                        break;
                    case "avatar7":
                        taskDrawable = context.getResources().getDrawable(R.drawable.avatar7);
                        break;
                    case "avatar8":
                        taskDrawable = context.getResources().getDrawable(R.drawable.avatar8);
                        break;
                    case "avatar9":
                        taskDrawable = context.getResources().getDrawable(R.drawable.avatar9);
                        break;
                    case "avatar10":
                        taskDrawable = context.getResources().getDrawable(R.drawable.avatar10);
                        break;
                    default:
                        taskDrawable = context.getResources().getDrawable(R.drawable.avatar1);
                }
                holder.mItemImageView.setImageDrawable(taskDrawable);
            }else{
                Bitmap cameraImage = PicUtils.decodePic(task.picPath, 60, 60);
                holder.mItemImageView.setImageBitmap(cameraImage);
            }
        }else {
            holder.mItemImageView.setImageDrawable(context.getResources().getDrawable(R.drawable.avatar1));
        }
        holder.mDeleteView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener){
                    mListener.onListFragmentClickInteraction(holder.mItem,position, true);
                }
            }
        });
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener){
                    mListener.onListFragmentClickInteraction(holder.mItem, position, false);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mContentView;
        public final ImageView mItemImageView;
        public final ImageButton mDeleteView;
        public Task mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mItemImageView = view.findViewById(R.id.item_image);
            mContentView = view.findViewById(R.id.content);
            mDeleteView = (ImageButton) view.findViewById(R.id.removeButton);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
