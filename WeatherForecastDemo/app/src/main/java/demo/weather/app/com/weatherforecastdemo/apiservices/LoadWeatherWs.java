package demo.weather.app.com.weatherforecastdemo.apiservices;

import android.content.Context;

import com.android.volley.toolbox.Volley;

import demo.weather.app.com.weatherforecastdemo.common.Constanst;
import demo.weather.app.com.weatherforecastdemo.network.HttpVolleyConnector;
import demo.weather.app.com.weatherforecastdemo.network.ResponseCallbackInterface;

/**
 * Created by phamvietsontay on 4/23/17.
 */
public class LoadWeatherWs extends HttpVolleyConnector {

    public LoadWeatherWs(ResponseCallbackInterface respone, Context context, String lat, String lon, String numberOfDays){
        mContext = context;
        url = Constanst.API_WEATHER + "?lat=" + lat + "&lon=" + lon + "&cnt=" + numberOfDays + "&APPID=" + Constanst.APP_ID_FORCAST;
        responeCallback = respone;
        mRequestQueue =  Volley.newRequestQueue(mContext.getApplicationContext());
    }

    public void doLoadAPI(){
        doConnectingApi(Constanst.GET, Constanst.TAG_API_WEATHER);
    }

}
