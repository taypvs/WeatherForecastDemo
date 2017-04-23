package demo.weather.app.com.weatherforecastdemo.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.model.LatLng;

import demo.weather.app.com.weatherforecastdemo.R;
import demo.weather.app.com.weatherforecastdemo.common.CommonUtils;
import demo.weather.app.com.weatherforecastdemo.common.PreferencesUtils;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        ((Button)findViewById(R.id.setup)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputAddress = ((EditText)findViewById(R.id.input_address)).getText().toString();
                if(inputAddress.equals("")){
                    showDialogEmptyInput();
                } else {
                    LatLng mLatLng = CommonUtils.reverseGeocoding(getBaseContext(), inputAddress);
                    if(mLatLng == null)
                        showDialogNoLocation();
                    else{
                        PreferencesUtils.saveFloat(getBaseContext(), PreferencesUtils.SAVE_LAT, (float)mLatLng.latitude);
                        PreferencesUtils.saveFloat(getBaseContext(), PreferencesUtils.SAVE_LON, (float)mLatLng.longitude);
                        PreferencesUtils.saveString(getBaseContext(), PreferencesUtils.SAVE_CUSTOM, PreferencesUtils.SAVE_CUSTOM);
                        startActivity(new Intent(SettingActivity.this, MainActivity.class));
                        finish();
                    }

                }
            }
        });

        ((Button)findViewById(R.id.reset)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PreferencesUtils.clearKeyPreferences(getBaseContext(), PreferencesUtils.SAVE_LON);
                PreferencesUtils.clearKeyPreferences(getBaseContext(), PreferencesUtils.SAVE_LAT);
                PreferencesUtils.clearKeyPreferences(getBaseContext(), PreferencesUtils.SAVE_CUSTOM);
                startActivity(new Intent(SettingActivity.this, MainActivity.class));
                finish();
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
        if (id == R.id.action_home) {
            startActivity(new Intent(SettingActivity.this, MainActivity.class));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void showDialogEmptyInput() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
        builder.setTitle("Error!!!");
        builder.setMessage(getResources().getString(R.string.empty_error_message));
        builder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                //DO TASK
                arg0.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setEnabled(false); //BUTTON1 is positive button
    }

    public void showDialogNoLocation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
        builder.setTitle("Error!!!");
        builder.setMessage(getResources().getString(R.string.no_location_error_message));
        builder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                //DO TASK
                arg0.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setEnabled(false); //BUTTON1 is positive button
    }
}
