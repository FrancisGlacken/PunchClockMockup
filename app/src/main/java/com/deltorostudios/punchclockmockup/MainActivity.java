package com.deltorostudios.punchclockmockup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.app.AlertDialog;
import android.content.DialogInterface;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    /* Variables for Layout Objects, for the Handler
        and for the variables we need for the time. */

    EditText timeMain, timeSeconds, timeClockOut; // The editText widgets for the three timers
    Boolean boolAlpha = true; // Boolean to mess around with
    Handler myHandler; // My handler variable that allows us to run a runnable thread for the timers
    ArrayList hoursListData; //ArrayList for keeping track of past score in the main activity

    long longSeconds, timeOnClick; // longSeconds is the milliseconds from sysClock, timeOnClick helps us bring the timer up to date after app termination
    int totalSeconds = 0, seconds = 0, minutes = 0, hours = 0; // integers for listing the time on the editText widgets, totalSeconds is saved on termination
    int clockedTime, clockedSeconds, clockedMinutes, clockedHours; // more integer variables, but these are for the "timeClockOut" widget


    // onCreate method
    //
    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Creating Layout Object and Handler Variable stuff
        timeMain = (EditText) findViewById(R.id.TimeMain);
        timeSeconds = (EditText) findViewById(R.id.TimeSeconds);
        timeClockOut = (EditText) findViewById(R.id.TimeClockOut);
        myHandler = new Handler();

        /* Loads totalSeconds/timeOnClick from SharPref, or sets to 0 for default*/
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        timeOnClick = preferences.getLong("timeOnClick", 0);
        totalSeconds = preferences.getInt("totalSeconds", 0);
        clockedTime = preferences.getInt("clockedTime", 0);

                /* Only is in use on initial startup of program
           Lists "00:00:00" in "Time" on creation, and the adjusted time on restoreInstance
           Will put a zero in front of each number in "hours:minutes:seconds"
           if it is less then 10, keeping the "00:00:00" format */
        if (boolAlpha = true) {
            if (seconds <= 9 && minutes <= 9 && hours <= 9) {
                timeMain.setText("0" + hours + ":0" + minutes + ":0" + seconds);
            } else if (minutes <= 9 && hours <= 9) {
                timeMain.setText("0" + hours + ":0" + minutes + ":" + seconds);

            } else if (seconds <= 9 && hours <= 9) {
                timeMain.setText("0" + hours + ":0" + minutes + ":" + seconds);

            } else {
                timeMain.setText(hours + ":" + minutes + ":" + seconds);
            }

            boolAlpha = false;
        }


        /* In use after onDestroy to updated timeClockOut
           Lists "00:00:00" in "TimeClockOut" on creation, and the adjusted time on restoreInstance
           Will put a zero in front of each number in "hours:minutes:seconds"
           if it is less then 10, keeping the "00:00:00" format */
        if (clockedTime > 0) {

            // Converting clockedTime into workable analog-esque numbers
            clockedSeconds = clockedTime % 60;
            clockedMinutes = clockedTime / 60 % 60;
            clockedHours = clockedTime / 3600 % 60;

            // Posting converted clockedTime to "timeClockOut" editText
            if (clockedSeconds <= 9 && clockedMinutes <= 9 && clockedHours <= 9) {
                timeClockOut.setText("0" + clockedHours + ":0" + clockedMinutes + ":0" + clockedSeconds);
            } else if (minutes <= 9 && hours <= 9) {
                timeClockOut.setText("0" + clockedHours + ":0" + clockedMinutes + ":" + clockedSeconds);
            } else if (seconds <= 9 && hours <= 9) {
                timeClockOut.setText("0" + clockedHours + ":0" + clockedMinutes + ":" + clockedSeconds);

            } else {
                timeClockOut.setText(clockedHours + ":" + clockedMinutes + ":" + clockedSeconds);
            }
        }


        /* Runs runny on restoreInstanceState
           (Which is automatically called to restore EditText widgets) */
        if (totalSeconds > 0) {

            myHandler.postDelayed(runny, 0);
        }


        // Methods for activating buttons
        ButtonPunchIn();
        ButtonClockOut();
        ButtonSave();
        ButtonToHoursList();
        ButtonReset();

    }
    // End of onCreate


    // onDestroy Method - Used to save variables in SharPref which survice app termination
    //
    //
    @Override
    public void onDestroy() {
        super.onDestroy();


        storeTimeOnClick(timeOnClick);
        storeTotalSeconds(totalSeconds);
        storeHours(clockedTime);

    }


    // Runnable "runny"
    // Runner time baby, which allows for the timer, basically looping itself.
    // longseconds becomes zero, then .0001, then .0002, then .0003...
    //
    //
    public Runnable runny = new Runnable() {

        public void run() {

            longSeconds = SystemClock.elapsedRealtime() - timeOnClick;

            // Converting millis to total seconds/minutes/hours
            totalSeconds = (int) (longSeconds / 1000);

            // Converting total seconds/minutes/hours into analog clock
            seconds = totalSeconds % 60;
            minutes = totalSeconds / 60 % 60;
            hours = totalSeconds / 3600;

            /* Starts the count in "Time" textView
                Will put a zero in front of each number in "hours:minutes:seconds"
                if it is less then 10, keeping the "00:00:00" format */
            if (seconds <= 9 && minutes <= 9 && hours <= 9) {
                timeMain.setText("0" + hours + ":0" + minutes + ":0" + seconds);

            } else if (minutes <= 9 && hours <= 9) {
                timeMain.setText("0" + hours + ":0" + minutes + ":" + seconds);

            } else if (seconds <= 9 && hours <= 9) {
                timeMain.setText("0" + hours + ":0" + minutes + ":" + seconds);

            } else {
                timeMain.setText(hours + ":" + minutes + ":" + seconds);
            }


            timeSeconds.setText(" Seconds: " + totalSeconds);

            myHandler.postDelayed(this, 0);
        }
    }; // Runny end


    // storeTimeOnClick Method - saves the time on click for "punch" button in SharPref
    //                           allowing us to keep track of the time past app termination
    //
    public void storeTimeOnClick(long z) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong("timeOnClick", z);
        editor.apply();
    }

    // storeTotalSeconds Method - saves the total number of seconds for if statements and
    //
    //
    public void storeTotalSeconds(int z) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("totalSeconds", z);
        editor.apply();
    }

    // storeHours Method - saves total number of seconds after clockedTime
    //
    //
    public void storeHours(int z) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("clockedTime", z);
        editor.apply();
    }

    // ButtonPunchIn Method for buttonPunch onClickListener
    //
    //       We grab SystemClock time value to subtract from the new one
    //      in runny, allowing us to get the value back to zero to begin counting
    //      elapsedRealtime() = time since the system as booted in millis
    //
    //
    public void ButtonPunchIn() {
        Button punch = (Button) findViewById(R.id.buttonPunch);
        punch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timeOnClick = SystemClock.elapsedRealtime();
                myHandler.postDelayed(runny, 0);
            }
        });
    }

    // ButtonClockOut method for "buttonClock" onClickListener
    //                  - Fills the timeClockOut widget with your timer when the user clicks buttonClock
    //                  - Resets all other variables and timers to zero, and stops the "runny" runnable
    //                  - Uses AlertDialog.Buider to create a yes/no prompt for the user
    //
    //
    public void ButtonClockOut() {
        Button clock = (Button) findViewById((R.id.buttonClock));
        clock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder altdial = new AlertDialog.Builder(MainActivity.this);
                altdial.setMessage("Are you sure you want to stop the timer?").setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                clockedTime = totalSeconds;

                                if (seconds <= 9 && minutes <= 9 && hours <= 9) {
                                    timeClockOut.setText("0" + hours + ":0" + minutes + ":0" + seconds);

                                } else if (minutes <= 9 && hours <= 9) {
                                    timeClockOut.setText("0" + hours + ":0" + minutes + ":" + seconds);

                                } else if (seconds <= 9 && hours <= 9) {
                                    timeClockOut.setText("0" + hours + ":0" + minutes + ":" + seconds);

                                } else {
                                    timeClockOut.setText(hours + ":" + minutes + ":" + seconds);
                                }

                                myHandler.removeCallbacks(runny);
                                timeOnClick = 0;
                                longSeconds = 0;
                                totalSeconds = 0;
                                seconds = 0;
                                minutes = 0;
                                hours = 0;

                                timeMain.setText("00:00:00");
                                timeSeconds.setText(" Seconds: " + totalSeconds);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = altdial.create();
                alert.setTitle("Dialog Header");
                alert.show();

            }
        });

    }

    // ButtonSave method for "buttonSave" onClickListener
    //
    //
    public void ButtonSave() {
        Button save = (Button) findViewById(R.id.buttonSave);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Call addHoursListData method
                addHoursListData();
            }
        });

    }

    // ButtonToHoursList method for "buttonSummonHoursList" onClickListener
    //
    //
    public void ButtonToHoursList() {
        Button hoursList = (Button) findViewById(R.id.buttonSummonHoursList);
        hoursList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, HoursListActivity.class));
            }
        });
    }

    // ButtonReset method for "buttonReset" onClickListener
    //                  - Same thing as ButtonClockOut method except different text and no save to timeCLocked
    //
    public void ButtonReset() {
        Button reset = (Button) findViewById(R.id.buttonReset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder altdial = new AlertDialog.Builder(MainActivity.this);
                altdial.setMessage("Are you sure you want to reset the timer?").setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                myHandler.removeCallbacks(runny);
                                timeOnClick = 0;
                                longSeconds = 0;
                                totalSeconds = 0;
                                seconds = 0;
                                minutes = 0;
                                hours = 0;

                                timeMain.setText("00:00:00");
                                timeSeconds.setText(" Seconds: " + totalSeconds);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = altdial.create();
                alert.setTitle("Dialog Header");
                alert.show();
            }
        });
    }

    // addHoursListData method - Takes time from TimeClocked, adds it to array "hoursListData"
    //                           and then uses an intent to send it to HoursListActivity
    //
    public void addHoursListData() {

        // Creating an ArrayList to store data - IS NOT BEING USED YET
        hoursListData = new ArrayList<String>();

        EditText timeClockOut = (EditText) findViewById(R.id.TimeClockOut);
        hoursListData.add(timeClockOut.getText().toString());


        Intent myIntent = new Intent(this, HoursListActivity.class);
        myIntent.putExtra("hoursListData", hoursListData);
        startActivity(myIntent);
    }
}


