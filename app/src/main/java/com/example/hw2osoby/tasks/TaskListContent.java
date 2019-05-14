package com.example.hw2osoby.tasks;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class TaskListContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<Task> ITEMS = new ArrayList<Task>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, Task> ITEM_MAP = new HashMap<String, Task>();

    private static final int COUNT = 5;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createDummyItem(i));
        }
    }

    public static void addItem(Task item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static Task createDummyItem(int position) {

        switch (position){
            case 1:
                return new Task(String.valueOf(position), "Jan Kowalski", "5 październik 1954", "Daleki krewny", "avatar5");
            case 2:
                return new Task(String.valueOf(position), "Magda Nowak", "23 maj 1985", "Koleżanka z czasów studiów", "avatar10");
            case 3:
                return new Task(String.valueOf(position), "Karol Maj", "14 styczeń 1999", "Kuzyn od strony matki", "avatar7");
            case 4:
                return new Task(String.valueOf(position), "Sylwia Maj", "31 lipiec 1973", "Mama Karola Maja", "avatar3");
            case 5:
                return new Task(String.valueOf(position), "Leokadia Nowakowska", "17 grudzień 2003", "Córka przyjaciółki", "avatar9");
                default:
                   return new Task(String.valueOf(position), "Imię i nazwisko", "Data urodzenia", "Krótki opis", "avatar1");
        }
    }

    /*
    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }*/

    public static void removeItem (int position){
        String itemId = ITEMS.get(position).id;
        ITEMS.remove(position);
        ITEM_MAP.remove(itemId);
    }

    public static void clearList (){
        ITEMS.clear();
        ITEM_MAP.clear();
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class Task implements Parcelable {
        public final String id;
        public final String name;
        public final String dateOfBirth;
        public final String details;
        public String picPath;

        public Task(String id, String name, String dateOfBirth, String details) {
            this.id = id;
            this.name = name;
            this.dateOfBirth = dateOfBirth;
            this.details = details;
            this.picPath = "";
        }

        public Task(String id, String name, String dateOfBirth, String details, String picPath) {
            this.id = id;
            this.name = name;
            this.dateOfBirth = dateOfBirth;
            this.details = details;
            this.picPath = picPath;
        }

        public void setPicPath (String path){
            this.picPath = path;
        }

        protected Task(Parcel in) {
            id = in.readString();
            name = in.readString();
            dateOfBirth = in.readString();
            details = in.readString();
            picPath = in.readString();
        }

        public static final Creator<Task> CREATOR = new Creator<Task>() {
            @Override
            public Task createFromParcel(Parcel in) {
                return new Task(in);
            }

            @Override
            public Task[] newArray(int size) {
                return new Task[size];
            }
        };


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(id);
            dest.writeString(name);
            dest.writeString(dateOfBirth);
            dest.writeString(details);
            dest.writeString(picPath);
        }


        @Override
        public String toString() {
            return name;
        }
    }
}
