package com.christiencdev.todolist;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class FileHelper {

    //will save filename onto device memory to save list data
    public static final String FILENAME = "listinfo.dat";

    //method to write list data onto listinfo.dat
    public static  void writeData(ArrayList<String> item, Context context)
    {
        try {
            //will create a file in device memory and open it
            FileOutputStream fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);

            //two classes are used to write data as a file
            ObjectOutputStream oas = new ObjectOutputStream(fos);
            //writes list item to file
            oas.writeObject(item);
            //closes file
            oas.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //method to read list data from listinfo.dat
    public static ArrayList<String> readData(Context context)
    {
        ArrayList<String> itemList = null;

        try {
            FileInputStream fis = context.openFileInput(FILENAME);
            ObjectInputStream ois = new ObjectInputStream(fis);
            itemList = (ArrayList<String>) ois.readObject();
        } catch (FileNotFoundException e) {
            itemList = new ArrayList<>();
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return itemList;
    }

}
