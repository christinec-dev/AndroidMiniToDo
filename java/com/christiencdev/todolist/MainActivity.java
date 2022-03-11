package com.christiencdev.todolist;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //variable declaration for list
    EditText item;
    Button add;
    ListView listView;

    //to do list connects listview to array, and saves list to memory
    ArrayList<String> itemList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //connects our variables to our component id's
        item = findViewById(R.id.editText);
        add = findViewById(R.id.button);
        listView = findViewById(R.id.list);

        //if there is data in listinfo.dat, we need to write it to arraylist component
        itemList = FileHelper.readData(this);

        //we then send this list via adapter
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, itemList);

        //assign this adapted to list
        listView.setAdapter(arrayAdapter);

        //event listener to button when we add new item to list
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String itemName = item.getText().toString();
                itemList.add(itemName);
                item.setText("");
                FileHelper.writeData(itemList, getApplicationContext());
                arrayAdapter.notifyDataSetChanged();
            }
        });

        //allows us to delete the to do item when clicking on item
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //alert message
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setTitle("Delete Task");
                alert.setMessage("Have you completed this task?");
                alert.setCancelable(false);

                //if user clicks no, we cancel action
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                //if user clicks yes, we delete item from list
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        itemList.remove(position);
                        arrayAdapter.notifyDataSetChanged();
                        FileHelper.writeData(itemList, getApplicationContext());
                    }
                });

                AlertDialog alertDialog = alert.create();
                alertDialog.show();
            }
        });
    }
}