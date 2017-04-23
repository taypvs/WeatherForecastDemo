package demo.weather.app.com.weatherforecastdemo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import demo.weather.app.com.weatherforecastdemo.R;
import demo.weather.app.com.weatherforecastdemo.common.CommonUtils;
import demo.weather.app.com.weatherforecastdemo.models.Info;

/**
 * Created by phamvietsontay on 4/23/17.
 */
public class DailyTempAdapter extends BaseAdapter {

    private ArrayList<Info> info;
    private Context context;

    public DailyTempAdapter(Context context, ArrayList<Info> info){
        this.info = info;
        this.context = context;
    }

    @Override
    public int getCount() {
        return info.size();
    }

    @Override
    public Object getItem(int i) {
        return info.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;

        if(view == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            view = layoutInflater.inflate(R.layout.daily_temp_adapter, null, false);
            viewHolder = new ViewHolder();
            viewHolder.date = (TextView)view.findViewById(R.id.date);
            viewHolder.status = (TextView)view.findViewById(R.id.status);
            viewHolder.min = (TextView)view.findViewById(R.id.min);
            viewHolder.max = (TextView)view.findViewById(R.id.max);
            view.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder)view.getTag();
        }

        viewHolder.date.setText(CommonUtils.convertUTCtoLocalTime(info.get(i).dt));
        viewHolder.status.setText(info.get(i).weather.main);
        viewHolder.min.setText(String.format(context.getResources().getString(R.string.celcius_txt), CommonUtils.kelvinToCelcius(info.get(i).temp.max)));
        viewHolder.max.setText(String.format(context.getResources().getString(R.string.celcius_txt), CommonUtils.kelvinToCelcius(info.get(i).temp.min)));

        return view;
    }

    private class ViewHolder {
        TextView date, status, max, min;
    }

}
