package com.example.rlta.tracar;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by Tiago on 28/10/2015.
 */
public class CreateActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner spinnerVehicles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        spinnerVehicles.setSelection(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }

    // Hiding keyboard on touch outside
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.
                INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }


    public void save(View view) {

        EditText txtbrand = (EditText)findViewById(R.id.txtBrand);
        EditText txtmodel = (EditText)findViewById(R.id.txtModel);
        EditText txtlicense = (EditText)findViewById(R.id.txtLicense);
        EditText txtnumber = (EditText)findViewById(R.id.txtNumber);
        EditText txtpin = (EditText)findViewById(R.id.txtPin);
        Spinner spinner = (Spinner)findViewById(R.id.spinner);


        if (txtbrand.length() == 0 || txtmodel.length() == 0 || txtlicense.length() == 0 || txtnumber.length() == 0 || txtpin.length() == 0) {
            Toast.makeText(getApplicationContext(), getString(R.string.errorEmpty), Toast.LENGTH_LONG).show();

        } else {
            String brand = txtbrand.getText().toString();
            String model = txtmodel.getText().toString();
            String license = txtlicense.getText().toString();
            String number = txtnumber.getText().toString();
            String pin = txtpin.getText().toString();
            String vehicle = spinner.getSelectedItem().toString();

            // Read Shared Preferences
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            String num = preferences.getString("NUM", "");


            if (num.equals("")) {
                // Write Shared Preferences
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("veh1", brand + "," + model + "," + license + "," + number + "," + pin + "," + vehicle);
                editor.putString("NUM", "1");
                editor.apply();
                Toast.makeText(getApplicationContext(), getString(R.string.infoSaved), Toast.LENGTH_LONG).show();
                super.onBackPressed();

            } else if (num.equals("1")) {
                // Write Shared Preferences
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("veh2", brand + "," + model + "," + license + "," + number + "," + pin + "," + vehicle);
                editor.putString("NUM", "2");
                editor.apply();
                Toast.makeText(getApplicationContext(), getString(R.string.infoSaved), Toast.LENGTH_LONG).show();
                super.onBackPressed();

            } else if (num.equals("2")) {
                // Write Shared Preferences
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("veh3", brand + "," + model + "," + license + "," + number + "," + pin + "," + vehicle);
                editor.putString("NUM", "3");
                editor.apply();
                Toast.makeText(getApplicationContext(), getString(R.string.infoSaved), Toast.LENGTH_LONG).show();
                super.onBackPressed();

            } else if (num.equals("3")) {
                // Write Shared Preferences
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("veh4", brand + "," + model + "," + license + "," + number + "," + pin + "," + vehicle);
                editor.putString("NUM", "4");
                editor.apply();
                Toast.makeText(getApplicationContext(), getString(R.string.infoSaved), Toast.LENGTH_LONG).show();
                super.onBackPressed();

            } else if (num.equals("4")) {
                // Write Shared Preferences
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("veh5", brand + "," + model + "," + license + "," + number + "," + pin + "," + vehicle);
                editor.putString("NUM", "5");
                editor.apply();
                Toast.makeText(getApplicationContext(), getString(R.string.infoSaved), Toast.LENGTH_LONG).show();
                super.onBackPressed();

            } else if (num.equals("5")) {
                Toast.makeText(getApplicationContext(), getString(R.string.errorFull), Toast.LENGTH_LONG).show();
            }
        }




    }
}
