package org.example.androidsdk.letsgo.helper;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.example.androidsdk.letsgo.interfaces.GetResponseCallback;
import org.example.androidsdk.letsgo.interfaces.GetStringCallback;
import org.example.androidsdk.letsgo.model.Place;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ServerRequest {
    Context context;
    public static final int CONNECTION_TIMEOUT = 15 * 1000;
    ProgressDialog progressDialog;
    HashMap<String,String> params;
    String url;
    String SERVER_ADDRESS = "http://ec2-54-201-185-34.us-west-2.compute.amazonaws.com/api/";

    public ServerRequest(Context context) {
        this.context = context;
        params = new HashMap<>();
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Processing...");
        progressDialog.setMessage("Please Wait...");
    }

    public void fetchPlaces(String userId,GetResponseCallback getResponseCallback){
        progressDialog.show();
        url = "eventfeed?userid="+userId;
        serverRequest(url, getResponseCallback);
    }

    public void serverRequest(String url, final GetResponseCallback getResponseCallback){
        url = SERVER_ADDRESS + url;
        JsonArrayRequest request = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
//                        Log.d("response", response.toString());
                        ArrayList<String> favlist = new ArrayList<>();
                        ArrayList<Place> placeList = new ArrayList<>();
                        try{
                            JSONArray events = response.getJSONArray(0);
                            JSONArray favourites = response.getJSONArray(1);
                            for(int i = 0; i < favourites.length(); i++){
                                favlist.add(favourites.getString(i));
                            }
                            for (int i = 0; i < events.length(); i++) {
                                    JSONObject obj = events.getJSONObject(i);
                                    Place place = new Place();
                                    place.setTitle(obj.getString("name"));
                                    place.setThumbnailUrl(obj.getJSONArray("imageurl").getString(0));
                                    place.setLocation(obj.getString("location"));
                                    place.setRating(Double.parseDouble(obj.getJSONObject("rating").getString("value")));
                                    place.setPrice(Integer.parseInt(obj.getString("price")));
                                    place.setVisibility(true);
                                    place.setEventId(obj.getString("_id"));
                                    place.setLatitude(Double.parseDouble(obj.getString("lat")));
                                    place.setLongitude(Double.parseDouble(obj.getString("long")));
                                    if(favlist.contains(obj.get("_id")))
                                        place.setFavourite(true);
                                    else
                                        place.setFavourite(false);
                                    placeList.add(place);
                            }
                        }
                        catch (JSONException e){
                            e.printStackTrace();
                        }
                        getResponseCallback.callback(placeList);
                        progressDialog.dismiss();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("response", "Error: " + error.getMessage());
                progressDialog.dismiss();
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(CONNECTION_TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(request);
    }

    public void fetchDetails(String eventId,GetStringCallback getStringCallback){
        progressDialog.show();
        url = "getevent?eventid="+eventId;
        jsonObjserverReq(url, getStringCallback);
    }

    public void jsonObjserverReq(String url, final GetStringCallback getStringCallback){
        url = SERVER_ADDRESS + url;
        JsonObjectRequest request = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                getStringCallback.callback(jsonObject.toString());
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                progressDialog.dismiss();
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(CONNECTION_TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(request);
    }

    public void getFavourites(String userId,GetResponseCallback getResponseCallback){
        progressDialog.show();
        url = "getfavourites?userid="+userId;
        jsonArrayserverReq(url, getResponseCallback);
    }

    public void getRecents(String userId,GetResponseCallback getResponseCallback){
        progressDialog.show();
        url = "getrecent?userid="+userId;
        jsonArrayserverReq(url, getResponseCallback);
    }

    public void jsonArrayserverReq(String url, final GetResponseCallback getResponseCallback){
        url = SERVER_ADDRESS + url;
        JsonArrayRequest request = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray events) {
                        Log.d("favourites", events.toString());
                        ArrayList<Place> placeList = new ArrayList<>();
                        for (int i = 0; i < events.length(); i++) {
                            try {
                                JSONObject obj = events.getJSONObject(i);
                                Place place = new Place();
                                place.setTitle(obj.getString("name"));
                                place.setThumbnailUrl(obj.getJSONArray("imageurl").getString(0));
                                place.setLocation(obj.getString("location"));
                                place.setRating(Double.parseDouble(obj.getJSONObject("rating").getString("value")));
                                place.setPrice(Integer.parseInt(obj.getString("price")));
                                place.setEventId(obj.getString("_id"));
                                place.setFavourite(true);
                                place.setVisibility(false);
                                placeList.add(place);
                                //searchList.add(place);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        getResponseCallback.callback(placeList);
                        progressDialog.dismiss();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Error: " + error.getMessage());
                progressDialog.dismiss();
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(CONNECTION_TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(request);
    }

    public void addFavourite(String userId,String eventId,GetStringCallback getStringCallback){
        url = "addfavourites";
        HashMap<String,String> params = new HashMap<String, String>();
        params.put("userid", userId);
        params.put("eventid", eventId);
        serverRequest(url, params, getStringCallback);
    }

    public void removeFavourite(String userId,String eventId,GetStringCallback getStringCallback){
        url = "removefavourites";
        HashMap<String,String> params = new HashMap<String, String>();
        params.put("userid", userId);
        params.put("eventid", eventId);
        serverRequest(url, params, getStringCallback);
    }

    public void addToRecent(String userId,String eventId,GetStringCallback getStringCallback){
        url = "addrecent";
        HashMap<String,String> params = new HashMap<String, String>();
        params.put("userid", userId);
        params.put("eventid", eventId);
        serverRequest(url, params, getStringCallback);
    }

    public void serverRequest(String url,HashMap<String,String> params, final GetStringCallback getStringCallback){
        url = SERVER_ADDRESS + url;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    VolleyLog.v("Response:%n%s", response.toString(4));
                    if(response.getBoolean("success"))
                        getStringCallback.callback("success");
                }
                catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(CONNECTION_TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(request);
    }
}
