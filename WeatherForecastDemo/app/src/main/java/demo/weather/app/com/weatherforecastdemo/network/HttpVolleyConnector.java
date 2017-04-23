package demo.weather.app.com.weatherforecastdemo.network;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import demo.weather.app.com.weatherforecastdemo.common.Constanst;

/**
 * Created by phamvietsontay on 11/27/16.
 */
public class HttpVolleyConnector {

    public RequestQueue mRequestQueue;
    public Context mContext;
    public String url;

    public ResponseCallbackInterface responeCallback;

    public void doConnectingApi(String method, final String TAG){
        int methodRq = 0;
        if(method.equals(Constanst.GET))
            methodRq = Request.Method.GET;
        else if(method.equals(Constanst.POST))
            methodRq = Request.Method.POST;

        jsonObjectRequest(methodRq, TAG);
    }

    private void jsonObjectRequest(int methodRq, final String TAG){
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (methodRq, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        responeCallback.onResultSuccess(response, TAG);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.e("TayPVS", "TayPVS error : " + error.toString());
                        responeCallback.onResultFail(error, TAG);
                    }

                })
        {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
//                        headers.put("apiKey", "xxxxxxxxxxxxxxx");
                return headers;
            }
        };

        mRequestQueue.add(jsObjRequest);
    }

    public void doLoadAPI(){

    }

}
