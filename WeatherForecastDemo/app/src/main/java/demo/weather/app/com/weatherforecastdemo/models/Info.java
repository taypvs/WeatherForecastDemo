package demo.weather.app.com.weatherforecastdemo.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by phamvietsontay on 4/23/17.
 */
public class Info {
    public long dt;
    public Temperature temp;
    public double pressure;
    public int humidity;
    public Weather weather;
    public double speed;
    public int deg;
    public int clouds;
    public double rain;

    public Info(JSONObject jsonObject){
        dt = jsonObject.optLong("dt", 0);
        temp = new Temperature(jsonObject.optJSONObject("temp"));
        pressure = jsonObject.optDouble("pressure", 0);
        humidity = jsonObject.optInt("humidity", 0);
        JSONArray jsonArrayWeather = jsonObject.optJSONArray("weather");
        weather = new Weather(jsonArrayWeather.optJSONObject(0));
        speed = jsonObject.optDouble("speed", 0);
        deg = jsonObject.optInt("deg", 0);
        clouds = jsonObject.optInt("clouds", 0);
        rain = jsonObject.optInt("rain", 0);
    }
}
