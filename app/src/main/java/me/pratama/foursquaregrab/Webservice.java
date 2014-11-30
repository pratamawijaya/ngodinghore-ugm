package me.pratama.foursquaregrab;

/**
 * Created by pratama on 11/15/14.
 */
public class Webservice {
    private static final String CLIENT_ID = "client_id=DPACEFIE3DDJCQVZOHQQTTGZF1RXYKI2NKPRQCEVKT0CHDKP";
    private static final String CLIENT_SECRET = "client_secret=5C3N2PQ2JG54CC0A5SALTAS4NGYMMXBUPRTW0UQSU5WHGZZF";
    private static final String VERSION = "v=20141011";

    private static final String BUILD_REQUEST = CLIENT_ID + "&" + CLIENT_SECRET + "&" + VERSION;

    private static final String MOCK_LOCATION = "ll=-7.78028,110.3725859";
    private static final String API_ENDPOINT = "https://api.foursquare.com/v2/";

    private static final String VENUE_EXPLORE = "venues/explore";
    private static final String VENUE_DETAIL = "venues/";

    public static String getExploreArea() {
        return API_ENDPOINT + VENUE_EXPLORE + "?" + BUILD_REQUEST + "&" + MOCK_LOCATION + "&venuePhotos=1";
    }

    public static String getVenueDetail(String venueID) {
        return API_ENDPOINT + VENUE_DETAIL + venueID + "?" + BUILD_REQUEST;
    }
}
