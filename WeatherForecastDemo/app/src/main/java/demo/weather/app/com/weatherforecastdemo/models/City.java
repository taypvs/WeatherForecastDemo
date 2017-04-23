package demo.weather.app.com.weatherforecastdemo.models;

import org.json.JSONObject;

/**
 * Created by phamvietsontay on 4/23/17.
 */
public class City {
    public int id;
    public String name;
    public Coordinates coord;
    public String country;
    public int population;

    public City(JSONObject jsonObject){
        id = jsonObject.optInt("id", 0);
        name = jsonObject.optString("name", "");
        coord = new Coordinates(jsonObject.optJSONObject("coord"));
        country = jsonObject.optString("country", "");
        population = jsonObject.optInt("population", 0);
    }
}
