package com.rohan.todo;

import com.rohan.todo.R; //not unused as Android Studio says it is, needed to refer to the parts of the XML layout
import android.os.Bundle;
import android.app.Activity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ListView;
import java.util.ArrayList;
import android.widget.ArrayAdapter;
import android.widget.Toast;

public class Main extends Activity implements View.OnClickListener, OnKeyListener {

    EditText txtItem;
    Button btnAdd;
    ListView listItems;

    ArrayList<String> toDoItems;
    ArrayAdapter<String> aa;

    String currentMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // assign variables to actual elements in the layout
        txtItem = (EditText)findViewById(R.id.txtItem);
        btnAdd = (Button)findViewById(R.id.btnAdd);
        listItems = (ListView)findViewById(R.id.listItems);

        btnAdd.setOnClickListener(this);
        txtItem.setOnKeyListener(this);

        toDoItems = new ArrayList<String>();
        aa = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, toDoItems);
        listItems.setAdapter(aa);
    }

    private void addItem(String item){
        if(item.length() > 0){
            this.toDoItems.add(item);
            this.aa.notifyDataSetChanged();
            this.txtItem.setText("");
        }
    }

    private void deleteItem(int itemId){
        if (itemId >= 0){
            String itemName = (String)listItems.getItemAtPosition(itemId);
            Toast.makeText(getApplicationContext(), itemName + " deleted", Toast.LENGTH_SHORT).show();
            this.toDoItems.remove(itemId);
            aa.notifyDataSetChanged();
        }
    }

    public void onClick(View v){
        if(v == this.btnAdd){
            this.addItem(this.txtItem.getText().toString());
        }
    }

    public boolean onKey(View v, int keyCode, KeyEvent event){
        if(event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DPAD_CENTER){
            this.addItem(this.txtItem.getText().toString());
        }
        return false;
    }

}
