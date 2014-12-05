package me.pratama.foursquaregrab.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import me.pratama.foursquaregrab.BaseActivity;
import me.pratama.foursquaregrab.BaseApplication;
import me.pratama.foursquaregrab.R;
import me.pratama.foursquaregrab.Webservice;
import me.pratama.foursquaregrab.entity.ResponseDetailLocation;

public class VenueDetailActivity extends BaseActivity {
    @InjectView(R.id.thumb)
    ImageView thumb;
    @InjectView(R.id.textCategories)
    TextView textCategories;
    @InjectView(R.id.textAddress)
    TextView textAddress;
    @InjectView(R.id.btnCall)
    ImageView btnCall;
    @InjectView(R.id.btnDirections)
    ImageView btnDirections;


    private GoogleMap map;
    private String venueID;
    private String phoneNumber;
    private double lat, lng;

    private ResponseDetailLocation response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venue_detail);
        ButterKnife.inject(this);

        venueID = getIntent().getExtras().getString("id");

        getVenueDetail(Webservice.getVenueDetail(venueID));

        setupMapIfNeeded();
    }

    private void setupMapIfNeeded() {
        if (map == null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            SupportMapFragment supportMapFragment = (SupportMapFragment) fragmentManager
                    .findFragmentById(R.id.maps);
            map = supportMapFragment.getMap();
            if (map != null)
                setupMap();
        }
    }

    private void setupMap() {
        map.getUiSettings().setZoomControlsEnabled(false);
        map.getUiSettings().setScrollGesturesEnabled(false);
    }

    /**
     * get venue detail
     *
     * @param URL
     */
    private void getVenueDetail(String URL) {
        JsonObjectRequest request = new JsonObjectRequest(URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Log.d("tag", "res -> " + jsonObject.toString());
                        response = new Gson().fromJson(jsonObject.toString(), ResponseDetailLocation.class);
                        showView(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                });
        BaseApplication.getInstance().addToRequestQueue(request, "tag");
    }

    private void showView(ResponseDetailLocation response) {
        getSupportActionBar().setTitle(response.getResponse().getVenue().getName());

        showPhotos(response.getResponse().getVenue().getPhotos().getGroupsList().get(0));
        // show categories
        textCategories.setText("" + response.getResponse().getVenue().getCategoriesList().get(0).getName());
        //show address
        textAddress.setText("Alamat : " + response.getResponse().getVenue().getLocation().getAddress());
        // show map
        lat = response.getResponse().getVenue().getLocation().getLat();
        lng = response.getResponse().getVenue().getLocation().getLng();
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 11));
        map.addMarker(new MarkerOptions().position(new LatLng(lat, lng)));

        // assign phone number;
        phoneNumber = response.getResponse().getVenue().getContact().getPhone();

    }

    @OnClick(R.id.btnCall)
    public void btnCallOnClick() {
        startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber)));
    }

    @OnClick(R.id.btnDirections)
    public void btnDirectionClick() {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?daddr=" + lat + "," + lng)));
    }

    private void showPhotos(ResponseDetailLocation.Groups groups) {
        ResponseDetailLocation.Items photosItem = groups.getItemsList().get(0);

        // build url photo
        // prefix + size + suffix
        String size = photosItem.getWidth() + "x" + photosItem.getHeight();
        String urlPhoto = photosItem.getPrefix() + size + photosItem.getSuffix();
        Log.d("tag", "urlphoto -> " + urlPhoto);
        Picasso.with(this).load(urlPhoto).into(thumb);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_venue_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
