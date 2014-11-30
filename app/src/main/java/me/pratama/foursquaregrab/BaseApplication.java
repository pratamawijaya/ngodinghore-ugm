package me.pratama.foursquaregrab;

import android.app.Application;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by pratama on 11/15/14.
 */
public class BaseApplication extends Application {

    private RequestQueue requestQueue;
    private static final int TIMEOUT = 2500;
    private static BaseApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static synchronized BaseApplication getInstance() {
        return instance;
    }

    /**
     * call requestqueue from volley
     *
     * @return requestQueue
     */
    private RequestQueue getRequestQueue() {
        if (requestQueue == null)
            requestQueue = Volley.newRequestQueue(instance);
        return requestQueue;
    }

    /**
     * add Request to Queue, make it generic
     *
     * @param request<T>
     * @param tag
     */
    public <T> void addToRequestQueue(Request<T> request, String tag) {
        request.setTag(tag);
        Log.d("tagging", "request URL -> " + request.getUrl());
        request.setRetryPolicy(new DefaultRetryPolicy(
                TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        getRequestQueue().add(request);
    }

    /**
     * cancel all request from Tag
     *
     * @param tag
     */
    public void cancelPendingRequest(String tag) {
        if (requestQueue != null)
            requestQueue.cancelAll(tag);
    }


}
