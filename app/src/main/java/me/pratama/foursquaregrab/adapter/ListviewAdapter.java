package me.pratama.foursquaregrab.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import me.pratama.foursquaregrab.R;
import me.pratama.foursquaregrab.entity.ResponseFoursquare;

/**
 * Created by pratama on 11/15/14.
 * <p/>
 * Adapter for list location grabbed from foursquare API
 */
public class ListviewAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<ResponseFoursquare.Item> listItem;
    private Context context;

    public ListviewAdapter(Context context, List<ResponseFoursquare.Item> listItem) {
        this.listItem = listItem;
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public int getCount() {
        return listItem.size();
    }

    @Override
    public Object getItem(int i) {
        return listItem.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    /**
     * holder pattern class
     */
    static class Holder {
        @InjectView(R.id.imgThumbnail)
        ImageView thumbnail;
        @InjectView(R.id.textTitle)
        TextView title;
        @InjectView(R.id.textRating)
        TextView rating;
        @InjectView(R.id.textCategories)
        TextView categories;
        @InjectView(R.id.textDistance)
        TextView distance;
        @InjectView(R.id.textAddress)
        TextView address;

        Holder(View v) {
            ButterKnife.inject(this, v);
        }
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Holder holder;
        if (view == null) {
            view = inflater.inflate(R.layout.item_listview, viewGroup, false);
            holder = new Holder(view);
            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }

        // diplay text
        holder.title.setText(listItem.get(i).getVenue().getName());
        holder.address.setText(listItem.get(i).getVenue().getLocation().getAddress());
        holder.categories.setText(listItem.get(i).getVenue().getCategories().get(0).getName());
        holder.rating.setText("" + listItem.get(i).getVenue().getRating());
        holder.distance.setText("" + listItem.get(i).getVenue().getLocation().getDistance() + " meters");

        // set background color for rating
        holder.rating.setBackgroundColor(((Color.parseColor("#" + listItem.get(i).getVenue().getRatingColor()))));

        // build url photo
        // prefix + size + suffix
        String prefix = listItem.get(i).getVenue().getPhotos().getListGroups().get(0).getListPhoto().get(0).getPrefix();
        String size = listItem.get(i).getVenue().getPhotos().getListGroups().get(0).getListPhoto().get(0).getWidth() + "x" + listItem.get(i).getVenue().getPhotos().getListGroups().get(0).getListPhoto().get(0).getHeight();
        String suffix = listItem.get(i).getVenue().getPhotos().getListGroups().get(0).getListPhoto().get(0).getSuffix();
        String urlPhoto = prefix + size + suffix;

        // load photo into ImageView use Picasso
        Picasso.with(context).load(urlPhoto).into(holder.thumbnail);
        return view;
    }
}
