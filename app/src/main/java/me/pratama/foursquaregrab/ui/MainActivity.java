package me.pratama.foursquaregrab.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import me.pratama.foursquaregrab.Ads;
import me.pratama.foursquaregrab.BaseActivity;
import me.pratama.foursquaregrab.BaseApplication;
import me.pratama.foursquaregrab.R;
import me.pratama.foursquaregrab.Webservice;
import me.pratama.foursquaregrab.adapter.ListviewAdapter;
import me.pratama.foursquaregrab.entity.ResponseFoursquare;


public class MainActivity extends BaseActivity implements AdapterView.OnItemClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    @InjectView(R.id.listview)
    ListView listView;
    @InjectView(R.id.containerAds)
    LinearLayout containerAds;

    private ListviewAdapter adapterListView;
    private ResponseFoursquare response;
    private List<ResponseFoursquare.Item> listItem;
    private ProgressDialog progressDialog;

    // location listener
    private Location location;
    private GoogleApiClient googleApiClient;
    private boolean isGpsActived = false;

    // for ads
    private AdView ads;
    private AdRequest request;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        Log.d("tag", "MainActivity");
        listItem = new ArrayList<ResponseFoursquare.Item>();
        adapterListView = new ListviewAdapter(this, listItem);

        listView.setAdapter(adapterListView);
        listView.setOnItemClickListener(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");

        // cek gps active
        isGpsActived = true;
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        loadAdsBanner();
    }

    /**
     * load ads for banner
     */
    private void loadAdsBanner() {
        ads = new AdView(this);
        ads.setAdSize(AdSize.BANNER);
        ads.setAdUnitId(Ads.ADS_ID_BANNER);

        containerAds.addView(ads);

        request = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        ads.loadAd(request);
    }

    /**
     * request json for listing nearby location via Foursquare API
     *
     * @param URL
     */
    private void getListPlace(String URL) {
        JsonObjectRequest request = new JsonObjectRequest(
                URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        progressDialog.dismiss();
                        try {
                            /*
                            * read/transform json using GsonLibrary
                             */
                            response = new Gson().fromJson(jsonObject.getJSONObject("response").toString(), ResponseFoursquare.class);

                            // add item into list for generate data into adapter listview
                            for (ResponseFoursquare.Item item : response.getListGroup().get(0).getListItem())
                                listItem.add(item);

                            // notify adapter for new data
                            adapterListView.notifyDataSetChanged();

                        } catch (JSONException e) {
                            Log.d("tag", "error : " + e.getMessage());
                            Toast.makeText(MainActivity.this, "error " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        progressDialog.dismiss();
                        Toast.makeText(MainActivity.this, "error" + volleyError.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
        );
        progressDialog.show();
        BaseApplication.getInstance().addToRequestQueue(request, "menu");
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        ResponseFoursquare.Item item = (ResponseFoursquare.Item) adapterListView.getItem(i);
        startActivity(new Intent(this, VenueDetailActivity.class).putExtra("id", item.getVenue().getId()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    @Override
    protected void onStart() {
        super.onStart();
        if (isGpsActived) {
            Log.d("tag", "connect");
            googleApiClient.connect();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isGpsActived)
            googleApiClient.disconnect();
    }


    @Override
    public void onConnected(Bundle bundle) {
        if (location == null) {
            Log.d("tag", "onconnected");
            if (isGpsActived) {
                location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
                Log.d("tag", "get loc " + location.getLatitude() + "," + location.getLongitude());

                if (location != null) {
                    getListPlace(Webservice.getExploreArea(new LatLng(location.getLatitude(), location.getLongitude())));
                } else {
                    Toast.makeText(this, "can't get gps position", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Toast.makeText(this, "Faield get Location " + connectionResult.getErrorCode(), Toast.LENGTH_SHORT).show();
    }
}
