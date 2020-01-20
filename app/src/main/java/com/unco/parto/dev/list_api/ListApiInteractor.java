package com.unco.parto.dev.list_api;



import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.unco.parto.base.BaseActivity;
import com.unco.parto.model.JListApi;
import com.unco.parto.utilis.SharedPreferences;
import com.unco.parto.utilis.URLStore;
import com.unco.parto.utilis.UrlManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ListApiInteractor implements IListApiInteractor {
    private String url = "";
    JListApi jListApi;
    SharedPreferences sharedPreferences;

    @Override
    public void callListApi(final int page, final IListApiFinishedListener iListApiFinishedListener) {
        url = UrlManager.getUrl(URLStore.LIST_API) + "?page="+ page + "&perPage=5" ;
        RequestQueue requestQueue = BaseActivity.getInstance().getRequestQueue();

        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray responseJSONObject = new JSONArray(response);
                            iListApiFinishedListener.successListApi(ListApiInteractor.this.fetchData(responseJSONObject));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        try {
                            if (error.networkResponse.statusCode == 401){
                                iListApiFinishedListener.errorTokenListApi("token");
                            }
                            String responseBody = new String(error.networkResponse.data, "utf-8");
                            JSONObject data = new JSONObject(responseBody);
                            String message = data.getString("error");
                            iListApiFinishedListener.errorListApi(message);
                        } catch (JSONException | UnsupportedEncodingException e) {

                        }


                    }
                }
        ) {


            @Override
            public String getBodyContentType() {
                return "application/json";
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                sharedPreferences = SharedPreferences.getCashoutsSaveFilter();
                sharedPreferences.loadToken();
                headers.put("Authorization", "Bearer "+sharedPreferences.getToken());
                return headers;
            }
        };
        RetryPolicy mRetryPolicy = new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(mRetryPolicy);
        requestQueue.add(stringRequest);

    }

    private ArrayList<JListApi> fetchData(JSONArray response) {
        ArrayList<JListApi> jListApiArrayList = new ArrayList<>();

        try {
            JSONArray data;
            if (response != null){
                data = response;
                for (int i =0 ; i < data.length(); i ++){
                    JSONObject dataObject =data.getJSONObject(i);
                    jListApi = new JListApi();
                    if (!dataObject.isNull("id")) {
                        jListApi.setId(dataObject.getInt("id"));
                    }
                    if (!dataObject.isNull("name")) {
                        jListApi.setName(dataObject.getString("name"));
                    }
                    if (!dataObject.isNull("picture")) {
                        jListApi.setPicture(dataObject.getString("picture"));
                    }
                    if (!dataObject.isNull("type")) {
                        jListApi.setType(dataObject.getString("type"));
                    }
                    if (!dataObject.isNull("score")) {
                        jListApi.setScore(dataObject.getDouble("score"));
                    }
                    jListApiArrayList.add(jListApi);

                }
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jListApiArrayList;
    }
}



