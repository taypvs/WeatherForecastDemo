package demo.weather.app.com.weatherforecastdemo.models;

import org.json.JSONObject;

/**
 * Created by phamvietsontay on 4/23/17.
 */
public class Coordinates {
    public double lon;
    public double lat;

    public Coordinates(JSONObject jsonObject){
        lon = jsonObject.optDouble("lon", 0);
        lat = jsonObject.optDouble("lat", 0);
    }
}
