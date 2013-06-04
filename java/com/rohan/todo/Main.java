package com.rohan.todo;

import android.content.DialogInterface;
import com.rohan.todo.R; //not unused as Android Studio says it is, needed to refer to the parts of the XML layout
import android.os.Bundle;
import android.app.Activity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.MenuItem;
import android.view.View.OnKeyListener;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ListView;
import java.util.ArrayList;
import android.widget.ArrayAdapter;
import android.widget.Toast;

public class Main extends Activity implements View.OnClickListener, DialogInterface.OnClickListener, OnKeyListener {

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
        ListView listContent = (ListView) findViewById(R.id.customlist);
        
        /*
         * Create/Open a SQLite database and fill with dummy content and close
         * it
         */
        mySQLiteAdapter = new SQLiteAdapter(this);
        mySQLiteAdapter.openToWrite();
        // mySQLiteAdapter.deleteAll();
        mySQLiteAdapter = new SQLiteAdapter(this);
        mySQLiteAdapter.openToRead();
        final Cursor cursor = mySQLiteAdapter.queueAll();
        startManagingCursor(cursor);
        String[] from = new String[] { SQLiteAdapter.KEY_CONTENT };
        int[] to = new int[] { R.id.text };
        final SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(this,
            R.layout.row, cursor, from, to);
        listContent.setAdapter(cursorAdapter);
        mySQLiteAdapter.close();
    
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

    public boolean onOptionsItemSelected(MenuItem item){
        super.onOptionsItemSelected(item);
        if(item.hasSubMenu() == false){
            if(item.getTitle() == "Remove Selected Task"){
                int index = listItems.getSelectedItemPosition();
                this.deleteItem(index);
            }
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuItem item;
        item = menu.add("Remove Selected Task");
        return true;
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

    public void onClick(DialogInterface dialog, int item){
        // ToDo
    }

}
