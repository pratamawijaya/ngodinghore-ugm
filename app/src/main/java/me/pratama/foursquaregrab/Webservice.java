package me.pratama.foursquaregrab;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by pratama on 11/15/14.
 */
public class Webservice {
    private static final String CLIENT_ID = "client_id=YOUR_CLIENT_ID";
    private static final String CLIENT_SECRET = "client_secret=YOUR_CLIENT_SECRET";
    private static final String VERSION = "v=20141011";
    private static final String BUILD_REQUEST = CLIENT_ID + "&" + CLIENT_SECRET + "&" + VERSION;
    private static final String MOCK_LOCATION = "ll=-7.78028,110.3725859";
    private static final String API_ENDPOINT = "https://api.foursquare.com/v2/";

    private static final String VENUE_EXPLORE = "venues/explore";
    private static final String VENUE_DETAIL = "venues/";

    /**
     * get Explore URL without latlng
     *
     * @return
     */
    public static String getExploreArea() {
        return API_ENDPOINT + VENUE_EXPLORE + "?" + BUILD_REQUEST + "&" + MOCK_LOCATION + "&venuePhotos=1";
    }

    /**
     * get Explore URL with LatLng User
     *
     * @param latLng
     * @return
     */
    public static String getExploreArea(LatLng latLng) {
        return API_ENDPOINT + VENUE_EXPLORE + "?" + BUILD_REQUEST + "&" + "ll=" + latLng.latitude + "," + latLng.longitude + "&venuePhotos=1&section=food";
    }

    /**
     * get venue detail URL
     *
     * @param venueID
     * @return
     */
    public static String getVenueDetail(String venueID) {
        return API_ENDPOINT + VENUE_DETAIL + venueID + "?" + BUILD_REQUEST;
    }
}
