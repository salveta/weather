package salva.perez.weather.app.ui.forecast.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import salva.perez.weather.R;
import salva.perez.weather.app.utils.DateUtil;
import salva.perez.weather.domain.api.Api;
import salva.perez.weather.domain.model.forecast.ForecastList;

import static salva.perez.weather.app.ui.main.MainActivity.WEATHER_CONDITION_ID;

public class ForecastAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements StickyRecyclerHeadersAdapter {

    private List<ForecastList> mData;
    private Context mContext;

    public ForecastAdapter(List <ForecastList> data, Context ForecastList){
        this.mData = data;
        this.mContext = ForecastList;
    }

    protected class ForecastViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tx_hour)         TextView tx_hour;
        @BindView(R.id.tx_degrees)      TextView tx_degrees;
        @BindView(R.id.im_icon_forecast) ImageView im_icon_forecast;


        public ForecastViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder (ViewGroup parent,int viewType){
        View view;

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_weather, parent, false);
        return new ForecastAdapter.ForecastViewHolder(view);
    }

        @Override
        public void onBindViewHolder (RecyclerView.ViewHolder holder,int position){
            ForecastAdapter.ForecastViewHolder mHolder = (ForecastAdapter.ForecastViewHolder) holder;
            ForecastList mForecast = mData.get(position);

            mHolder.tx_degrees.setText(mContext.getString(R.string.degrees, String.valueOf(mForecast.getMain().getTempMax())));
            mHolder.tx_hour.setText(DateUtil.parseHour(mForecast.getDt()));

            Picasso.with(mContext)
                    .load(Api.ICON_URL + mForecast.getWeather().get(WEATHER_CONDITION_ID).getIcon() + Api.IMAGE_FORMAT)
                    .into(mHolder.im_icon_forecast);
    }

    public void updateList (List <ForecastList> list) {
        mData = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount () {
        return mData != null ? mData.size() : 0;
    }

    @Override
    public int getItemViewType ( int position){
        return position;
    }

    @Override
    public long getHeaderId(int position) {
        DateUtil.parseDate(mData, position);

        if ( mData.get(position).getDifDays() == 0) {
            return 0;
        } else if(mData.get(position).getDifDays() == -1){
            return 1;
        }else if(mData.get(position).getDifDays() == -2){
            return 2;
        }else if(mData.get(position).getDifDays() == -3){
            return 3;
        }else if(mData.get(position).getDifDays() == -4){
            return 4;
        }else if(mData.get(position).getDifDays() == -5){
            return 5;
        }else{
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View view) {
            super(view);
        }
    }

    public class ViewMessageDateHolder extends ViewHolder {
        @BindView(R.id.tx_data)         TextView tx_data;

        ViewMessageDateHolder(View v) {
            super(v);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_separator_recycler, parent, false);
        return  new ViewMessageDateHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewMessageDateHolder holderData = (ViewMessageDateHolder) holder;
        ForecastList mForecastList = mData.get(position);

        if ( mData.get(position).getDifDays() == 0) {
            holderData.tx_data.setText(mContext.getString(R.string.today));
        } else if(mData.get(position).getDifDays() == -1){
            holderData.tx_data.setText(mContext.getString(R.string.tomorrow));
        }else if(mData.get(position).getDifDays() == -2){
            holderData.tx_data.setText(DateUtil.parseDateToDayAndMonth(mForecastList.getDt()));
        }else if(mData.get(position).getDifDays() == -3){
            holderData.tx_data.setText(DateUtil.parseDateToDayAndMonth(mForecastList.getDt()));
        }else if(mData.get(position).getDifDays() == -4){
            holderData.tx_data.setText(DateUtil.parseDateToDayAndMonth(mForecastList.getDt()));
        }else if(mData.get(position).getDifDays() == -5){
            holderData.tx_data.setText(DateUtil.parseDateToDayAndMonth(mForecastList.getDt()));
        }else{
            holderData.tx_data.setText(DateUtil.parseDateToDayAndMonth(mForecastList.getDt()));
        }
    }
}
