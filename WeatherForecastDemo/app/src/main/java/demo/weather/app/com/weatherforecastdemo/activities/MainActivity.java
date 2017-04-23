package demo.weather.app.com.weatherforecastdemo.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import org.json.JSONArray;
import org.json.JSONObject;

import demo.weather.app.com.weatherforecastdemo.R;
import demo.weather.app.com.weatherforecastdemo.apiservices.LoadWeatherWs;
import demo.weather.app.com.weatherforecastdemo.common.CommonUtils;
import demo.weather.app.com.weatherforecastdemo.common.Constanst;
import demo.weather.app.com.weatherforecastdemo.common.PreferencesUtils;
import demo.weather.app.com.weatherforecastdemo.models.ForeCastInfo;
import demo.weather.app.com.weatherforecastdemo.network.ResponseCallbackInterface;


public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, ResponseCallbackInterface {

    private GoogleApiClient mGoogleApiClient;
    private LoadWeatherWs loadWeatherWs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create an instance of GoogleAPIClient.
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        ((Button)findViewById(R.id.switchToWeekBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, WeekForecastActivity.class));
                finish();
            }
        });

    }

    @Override
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
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
            startActivity(new Intent(MainActivity.this, SettingActivity.class));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if(PreferencesUtils.getString(getBaseContext(), PreferencesUtils.SAVE_CUSTOM).equals("")) {
            if (lastLocation != null) {
                loadWeatherWs = new LoadWeatherWs(this, getBaseContext(), String.valueOf(lastLocation.getLatitude()), String.valueOf(lastLocation.getLongitude()), "2");
            } else {
                loadWeatherWs = new LoadWeatherWs(this, getBaseContext(), String.valueOf(10.815414), String.valueOf(106.713481), "2");
            }
        } else {
            loadWeatherWs = new LoadWeatherWs(this, getBaseContext(), String.valueOf(PreferencesUtils.getFloat(getBaseContext(), PreferencesUtils.SAVE_LAT)),
                                                                        String.valueOf(PreferencesUtils.getFloat(getBaseContext(), PreferencesUtils.SAVE_LON)),
                                                                        "2");
        }
        loadWeatherWs.doLoadAPI();
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if(PreferencesUtils.getString(getBaseContext(), PreferencesUtils.SAVE_CUSTOM).equals("")) {
            showDialogErrorLocation();
            loadWeatherWs = new LoadWeatherWs(this, getBaseContext(), String.valueOf(10.815414), String.valueOf(106.713481), "2");
            loadWeatherWs.doLoadAPI();
        } else {
            loadWeatherWs = new LoadWeatherWs(this, getBaseContext(), String.valueOf(PreferencesUtils.getFloat(getBaseContext(), PreferencesUtils.SAVE_LAT)),
                    String.valueOf(PreferencesUtils.getFloat(getBaseContext(), PreferencesUtils.SAVE_LON)),
                    "2");
            loadWeatherWs.doLoadAPI();
        }
    }

    @Override
    public void onResultSuccess(Object result, String TAG) {
        switch (TAG) {
            case Constanst.TAG_API_WEATHER:
                ForeCastInfo foreCastInfo = new ForeCastInfo((JSONObject)result);
                initWeatherInfo(foreCastInfo);
                break;
        }
    }

    @Override
    public void onResultFail(Object resultFail, String TAG) {

    }

    private void initWeatherInfo(ForeCastInfo foreCastInfo){
        ((TextView)findViewById(R.id.city)).setText(foreCastInfo.city.name);
        ((TextView)findViewById(R.id.today_temperature)).setText(String.format(getResources().getString(R.string.celcius_txt), CommonUtils.kelvinToCelcius(foreCastInfo.info.get(0).temp.day)));
        ((TextView)findViewById(R.id.today_status)).setText(foreCastInfo.info.get(0).weather.description);
        ((TextView)findViewById(R.id.tomorrow_temperature)).setText(String.format(getResources().getString(R.string.celcius_txt), CommonUtils.kelvinToCelcius(foreCastInfo.info.get(1).temp.day)));
        ((TextView)findViewById(R.id.tomorrow_status)).setText(foreCastInfo.info.get(1).weather.description);
    }

    public void showDialogErrorLocation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Error!!!");
        builder.setMessage(getResources().getString(R.string.location_error_message));
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
