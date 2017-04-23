package demo.weather.app.com.weatherforecastdemo.models;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by phamvietsontay on 4/23/17.
 */
public class ForeCastInfo {
    public City city;
    public String cod;
    public double message;
    public int cnt;
    public ArrayList<Info> info;

    public ForeCastInfo(JSONObject jsonObject){
        city = new City(jsonObject.optJSONObject("city"));
        cod = jsonObject.optString("cod", "");
        message = jsonObject.optDouble("message", 0);
        cnt = jsonObject.optInt("cnt", 0);
        JSONArray jsonArray = jsonObject.optJSONArray("list");
        info = new ArrayList<Info>();
        for(int i = 0; i < jsonArray.length(); i++){
            Info newInfo = new Info(jsonArray.optJSONObject(i));
            info.add(newInfo);
        }
    }
}
