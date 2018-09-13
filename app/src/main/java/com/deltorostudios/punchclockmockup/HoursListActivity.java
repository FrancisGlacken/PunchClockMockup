package com.deltorostudios.punchclockmockup;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class HoursListActivity extends AppCompatActivity {



    /*
        This page is a work in progress
     */


    ArrayAdapter<String> hoursListAdapter;
    ListView HoursList;


    // onCreate Method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hours_list);

        // Creates the arraylist-string
        ArrayList<String> hoursListData = new ArrayList<String>();


        // Get Bundle object that contain the array
        // Extract the array from the Bundle object
        Bundle bundle = getIntent().getExtras();
        hoursListData = (ArrayList<String>) bundle.getStringArrayList("hoursListData");


        //Creating the ListView object
        HoursList = (ListView) findViewById(R.id.HoursList);

        // Adapter: You need three parameters 'the context, id of the layout (it will be where the data is shown),
        // and the array that contains the data
        hoursListAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, hoursListData);
        HoursList.setAdapter(hoursListAdapter);


        ButtonToHoursList2();


    }// onCreate end

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //saveTasksToSharedPrefs();

    }


    // Creates a button that goes back to Home page, it self-terminates this activity so we don't create clones
    public void ButtonToHoursList2() {
        Button button = (Button) findViewById(R.id.buttonSummonHome);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

     /* public void saveTasksToSharedPrefs(Context context) {
        SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(hoursListData); //hoursListData is an ArrayList instance variable
        prefsEditor.putString("currentListData", json);
        prefsEditor.commit();
    }

    public List<Task> getTasksFromSharedPrefs(Context context) {
        SharedPreferences appSharedPrefs = PreferenceManager .getDefaultSharedPreferemces(context.getApplicationContext());
        Gson gson = new Gson();
        String json = appSharedPrefs.getString("currentListData", "");
        hoursListData = gson.fromJson(json, new TypeToken<ArrayList<Task>>(){}.getType());

    } */

}


