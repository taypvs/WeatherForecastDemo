package demo.weather.app.com.weatherforecastdemo.models;

import org.json.JSONObject;

/**
 * Created by phamvietsontay on 4/23/17.
 */
public class Temperature {
    public double day;
    public double min;
    public double max;
    public double night;
    public double eve;
    public double morn;

    public Temperature(JSONObject jsonObject){
        day = jsonObject.optDouble("day", 0.0);
        min = jsonObject.optDouble("min", 0.0);
        max = jsonObject.optDouble("max", 0.0);
        night = jsonObject.optDouble("night", 0.0);
        eve = jsonObject.optDouble("eve", 0.0);
        morn = jsonObject.optDouble("morn", 0.0);
    }

}
