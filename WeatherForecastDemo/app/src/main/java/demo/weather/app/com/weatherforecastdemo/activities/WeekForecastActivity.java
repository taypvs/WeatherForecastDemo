package demo.weather.app.com.weatherforecastdemo.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import org.json.JSONObject;

import java.util.ArrayList;

import demo.weather.app.com.weatherforecastdemo.adapters.DailyTempAdapter;
import demo.weather.app.com.weatherforecastdemo.R;
import demo.weather.app.com.weatherforecastdemo.apiservices.LoadWeatherWs;
import demo.weather.app.com.weatherforecastdemo.common.CommonUtils;
import demo.weather.app.com.weatherforecastdemo.common.Constanst;
import demo.weather.app.com.weatherforecastdemo.common.PreferencesUtils;
import demo.weather.app.com.weatherforecastdemo.models.ForeCastInfo;
import demo.weather.app.com.weatherforecastdemo.models.Info;
import demo.weather.app.com.weatherforecastdemo.network.ResponseCallbackInterface;

public class WeekForecastActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, ResponseCallbackInterface {

    private GoogleApiClient mGoogleApiClient;
    private LoadWeatherWs loadWeatherWs;
    private DailyTempAdapter dailyTempAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_forecast);

        // Create an instance of GoogleAPIClient.
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        ((Button)findViewById(R.id.switchToCurrentDayBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WeekForecastActivity.this, MainActivity.class));
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
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(WeekForecastActivity.this, SettingActivity.class));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if(PreferencesUtils.getString(getBaseContext(), PreferencesUtils.SAVE_CUSTOM).equals("")) {
            if (lastLocation != null) {
                loadWeatherWs = new LoadWeatherWs(this, getBaseContext(), String.valueOf(lastLocation.getLatitude()), String.valueOf(lastLocation.getLongitude()), "7");
            } else {
                showDialogErrorLocation();
                loadWeatherWs = new LoadWeatherWs(this, getBaseContext(), String.valueOf(10.815414), String.valueOf(10.815414), "7");
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
            loadWeatherWs = new LoadWeatherWs(this, getBaseContext(), String.valueOf(10.815414), String.valueOf(106.713481), "7");
        } else {
            loadWeatherWs = new LoadWeatherWs(this, getBaseContext(), String.valueOf(PreferencesUtils.getFloat(getBaseContext(), PreferencesUtils.SAVE_LAT)),
                    String.valueOf(PreferencesUtils.getFloat(getBaseContext(), PreferencesUtils.SAVE_LON)),
                    "2");
        }
        loadWeatherWs.doLoadAPI();
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
        ((TextView)findViewById(R.id.status)).setText(foreCastInfo.info.get(0).weather.description);

        ArrayList<Info> listInfo = foreCastInfo.info;
        dailyTempAdapter = new DailyTempAdapter(getBaseContext(), listInfo);
        ((ListView)findViewById(R.id.list_temp_in_week)).setAdapter(dailyTempAdapter);
    }

    public void showDialogErrorLocation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(WeekForecastActivity.this);
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
