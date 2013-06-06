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

        // make the button and text box have listenrs to watch their state
        btnAdd.setOnClickListener(this);
        txtItem.setOnKeyListener(this);

        toDoItems = new ArrayList<String>();
        aa = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, toDoItems);
        listItems.setAdapter(aa);
        
        // make items in the listview clickable and then make it so when one is pressed, it deletes the item
        listItems.setClickable = true;
        lv.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                this.deleteItem(position);
            }
        });
    }

    /*
     * Method that adds the item of concents "item" to the ArrayList and ListView
    */
    private void addItem(String item){
        if(item.length() > 0){
            this.toDoItems.add(item);
            this.aa.notifyDataSetChanged();
            this.txtItem.setText("");
        }
    }

    /*
     * Method that deletes the item at position "itemId" in the ArrayList and ListView
    */
    private void deleteItem(int itemId){
        if (itemId >= 0){
            String itemName = (String)listItems.getItemAtPosition(itemId);
            Toast.makeText(getApplicationContext(), itemName + " deleted", Toast.LENGTH_SHORT).show();
            this.toDoItems.remove(itemId);
            aa.notifyDataSetChanged();
        }
    }

    /*
     * Implementation of OnClickListener for the button, so that it adds the text in txtItem
     * to the ListView and ArrayList using the addItem() method
    */
    public void onClick(View v){
        if(v == this.btnAdd){
            this.addItem(this.txtItem.getText().toString());
        }
    }
    
    /*
     * Implementation of onKeyListener so that devices with a physical enter button can just
     * press the center button to add an item instead of pressing the button
    */
    public boolean onKey(View v, int keyCode, KeyEvent event){
        if(event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DPAD_CENTER){
            this.addItem(this.txtItem.getText().toString());
        }
        return false;
    }

}
