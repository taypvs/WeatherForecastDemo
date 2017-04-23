package demo.weather.app.com.weatherforecastdemo.models;

import org.json.JSONObject;

/**
 * Created by phamvietsontay on 4/23/17.
 */
public class Weather {
    public int id;
    public String main;
    public String description;
    public String icon;

    public Weather(JSONObject jsonObject){
        id = jsonObject.optInt("id");
        main = jsonObject.optString("main","");
        description = jsonObject.optString("description","");
        icon = jsonObject.optString("icon","");
    }
}
