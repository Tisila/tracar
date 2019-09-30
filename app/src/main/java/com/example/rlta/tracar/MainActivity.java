package com.example.rlta.tracar;

import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.content.Context;
import android.support.v7.widget.ThemedSpinnerAdapter;
import android.content.res.Resources.Theme;

import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // Defining variables
    Integer num;
    Integer pos;
    Integer n;
    Integer nv;
    String nvs; // Number of Vehicles String
    String message;
    String[] section = {""};
    String vehicleCSV;
    String[] vehicle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        // TODO Fix this section thing!!
        // Read Shared Preferences
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        nvs = preferences.getString("NUM", "");
        if (nvs.equals("")) {
            num = 0;
            Intent intent = new Intent(this, CreateActivity.class);
            startActivity(intent);
        } else {
            num = Integer.parseInt(nvs);
            section = new String[num];

            for (n=1;n<=num;n++) {
                pos = n-1;
                vehicleCSV = preferences.getString("veh"+n, "");
                vehicle = vehicleCSV.split(",");
                section[pos] = vehicle[0];
            }
        }
        //Toast.makeText(getApplicationContext(), ""+num, Toast.LENGTH_LONG).show();


        // Setup spinner
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(new MyAdapter(toolbar.getContext(), section));

        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // When the given dropdown item is selected, show its contents in the
                // container view.
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                        .commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
        } else if (id == R.id.create) {
            // Open create screen
            Intent intent = new Intent(this, CreateActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }


    private static class MyAdapter extends ArrayAdapter<String> implements ThemedSpinnerAdapter {
        private final ThemedSpinnerAdapter.Helper mDropDownHelper;

        public MyAdapter(Context context, String[] objects) {
            super(context, android.R.layout.simple_list_item_1, objects);
            mDropDownHelper = new ThemedSpinnerAdapter.Helper(context);
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            View view;

            if (convertView == null) {
                // Inflate the drop down using the helper's LayoutInflater
                LayoutInflater inflater = mDropDownHelper.getDropDownViewInflater();
                view = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
            } else {
                view = convertView;
            }

            TextView textView = (TextView) view.findViewById(android.R.id.text1);
            textView.setText(getItem(position));

            return view;
        }

        @Override
        public void setDropDownViewTheme(Theme theme) {
            mDropDownHelper.setDropDownViewTheme(theme);
        }

        @Override
        public Theme getDropDownViewTheme() {
            return mDropDownHelper.getDropDownViewTheme();
        }
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            String veh = "veh" + getArguments().getInt(ARG_SECTION_NUMBER); // Now I have the veh callsign. EX:veh1, veh2...
            String ve;
            String[] v;

            // Read Shared Preferences
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());

            if (preferences.getString("NUM", "").equals("")) {

            } else {
                ve = preferences.getString(veh, "");
                v = ve.split(",");
                TextView brand = (TextView) rootView.findViewById(R.id.brand);
                TextView model = (TextView) rootView.findViewById(R.id.model);
                TextView license = (TextView) rootView.findViewById(R.id.licensePlate);

                brand.setText(v[0]);
                model.setText(getString(R.string.model) + " " + v[1]);
                license.setText(getString(R.string.license) + " " + v[2]);
            }

            // Write Shared Preferences
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("LIVE", veh);
            editor.apply();

            //TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            //textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    public void sendSmsCoordinates (View view) {

        // Read Shared Preferences
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String veh = preferences.getString("LIVE", "");
        String ve = preferences.getString(veh, "");
        String[] v = ve.split(",");

        // Enabling Broadcast
        ComponentName receiver = new ComponentName(this, SmsBroadcastReceiver.class);
        PackageManager pm = this.getPackageManager();
        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);

        // Send Message
        Log.i("Send SMS", "");
        String phoneNo = v[3];
        String pin = v[4];
        String text = "WHERE," + pin + "#";

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, text, null, null);
            Toast.makeText(getApplicationContext(), "SMS sent.", Toast.LENGTH_LONG).show();
        }

        catch (Exception e) {
            Toast.makeText(getApplicationContext(), "SMS faild, please try again.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    public void sendSmsMap (View view) {

        // Read Shared Preferences
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String veh = preferences.getString("LIVE", "");
        String ve = preferences.getString(veh, "");
        String[] v = ve.split(",");

        // Enabling Broadcast
        ComponentName receiver = new ComponentName(this, SmsBroadcastReceiver.class);
        PackageManager pm = this.getPackageManager();
        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);

        // Send Message
        Log.i("Send SMS", "");
        String phoneNo = v[3];
        String pin = v[4];
        String text = "URL," + pin + "#";

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, text, null, null);
            Toast.makeText(getApplicationContext(), "SMS sent.", Toast.LENGTH_LONG).show();
        }

        catch (Exception e) {
            Toast.makeText(getApplicationContext(), "SMS faild, please try again.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    // Getting the message
    @Override
    protected void onResume() {
        super.onResume();

        Bundle extras = getIntent().getExtras();

        if(extras != null) {
            if(!extras.isEmpty()) {
                message = extras.getString(SmsBroadcastReceiver.SMS_CONTENT);
            }
        }

        if(message != null) {
            // Getting the message
            String[] piece = message.split(" : ");
            // numb = piece[0];
            // mess = piece[1];

            // Disabling Broadcast
            ComponentName receiver = new ComponentName(this, SmsBroadcastReceiver.class);
            PackageManager pm = this.getPackageManager();
            pm.setComponentEnabledSetting(receiver,
                    PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                    PackageManager.DONT_KILL_APP);

            if (piece[1].contains("<")) {
                String[] str = piece[1].split(" ");
                String[] url = str[2].split(":");

                String URL = "http:" + url[1];
                // Write Shared Preferences
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("URL", URL);
                editor.apply();


                // Pop up of MAP SMS
                AlertDialog map;
                map = new AlertDialog.Builder(this).create();
                map.setTitle(getString(R.string.map));
                map.setMessage(String.format("Number:" + piece[0] + "%n" + str[0] + str[1] + "%n" + "http:" + url[1]));
                map.show();


            } else {
                String[] str = piece[1].split(",");
                // Pop up of Coordinates SMS
                AlertDialog coor;
                coor = new AlertDialog.Builder(this).create();
                coor.setTitle(getString(R.string.coordinates));
                coor.setMessage(String.format("Number:" + piece[0] + "%n" + str[0] + "%n" + str[1] + "%n" + str[2] + "%n" + str[3] + "%n" + str[4]));
                coor.show();
            }



        }
    }

    public void erase (View view) {
        // Read Shared Preferences
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String veh = preferences.getString("LIVE", "");
    }
}
