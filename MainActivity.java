package edu.uiuc.cs427app.activity;
import java.util.*;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import androidx.appcompat.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import edu.uiuc.cs427app.R;

public class MainActivity extends BaseMenuActivity {
private List<String> newLocations = new ArrayList<String>(); //locations array
ListView listView;
private String deleteLoc = "";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    listView = (ListView)findViewById(R.id.listView);
    final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
            android.R.layout.simple_list_item_1, android.R.id.text1, newLocations);
    listView.setAdapter(adapter);
    Button buttonAdd = findViewById(R.id.buttonAddLocation);
    Button buttonDel = findViewById(R.id.buttonRemoveLocation);
    buttonAdd.setOnClickListener(this);
    buttonDel.setOnClickListener(this);

  }
  @Override
  public void onClick(View view) {
    Intent intent;
    switch (view.getId()) {
      //case for adding a location
      case R.id.buttonAddLocation:
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Enter new Location");
        final EditText input = new EditText(this);
        builder.setView(input);
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
          // send data from the AlertDialog to the Activity
          public void onClick(DialogInterface dialog, int which) {
            newLocations.add(input.getText().toString());
          }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        break;
      //case for removing a location
      case R.id.buttonRemoveLocation:
        AlertDialog.Builder removeDialog = new AlertDialog.Builder(this);
        removeDialog.setMessage("Enter Location to remove");
        final EditText inputRemove = new EditText(this);
        removeDialog.setView(inputRemove);
        removeDialog.setPositiveButton("Remove", new DialogInterface.OnClickListener() {
          // send data from the AlertDialog to the Activity
          public void onClick(DialogInterface dialog, int which) {
            deleteLoc = inputRemove.getText().toString();
            newLocations.remove(deleteLoc);
          }
        });
        AlertDialog alertDialo = removeDialog.create();
        alertDialo.show();
        break;
        }
    }
  }


