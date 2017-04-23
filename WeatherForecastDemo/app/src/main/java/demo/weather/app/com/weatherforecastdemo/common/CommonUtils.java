package demo.weather.app.com.weatherforecastdemo.common;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.security.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by phamvietsontay on 4/23/17.
 */
public class CommonUtils {

    public static String kelvinToCelcius(double kelvin){
        return String.valueOf(Math.round(kelvin - 273.15));
    }

    public static String convertUTCtoLocalTime(long time){
        Date date = new Date(time*1000);
        DateFormat df = new SimpleDateFormat("EEE");
        return df.format(date);
    }

    public static LatLng reverseGeocoding(Context context, String locationName){
        if(!Geocoder.isPresent()){
            Log.w("zebia", "Geocoder implementation not present !");
        }
        Geocoder geoCoder = new Geocoder(context, Locale.getDefault());

        try {
            List<Address> addresses = geoCoder.getFromLocationName(locationName, 5);
            int tentatives = 0;
            while (addresses.size()==0 && (tentatives < 5)) {
                addresses = geoCoder.getFromLocationName("<address goes here>", 1);
                tentatives ++;
            }


            if(addresses.size() > 0){
                Log.d("zebia", "reverse Geocoding : locationName " + locationName + "Latitude " + addresses.get(0).getLatitude() );
                return new LatLng(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());
            }else{
                //use http api
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
