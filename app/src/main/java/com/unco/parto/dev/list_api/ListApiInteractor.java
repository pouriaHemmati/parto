package com.unco.parto.dev.list_api;



import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.unco.parto.base.BaseActivity;
import com.unco.parto.model.JListApi;
import com.unco.parto.utilis.URLStore;
import com.unco.parto.utilis.UrlManager;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.UnsupportedEncodingException;


public class ListApiInteractor implements IListApiInteractor {
    private String url = "";
    JListApi jListApi;

    @Override
    public void callListApi(final String page, final IListApiFinishedListener iListApiFinishedListener) {
        url = UrlManager.getUrl(URLStore.LIST_API) + "?page="+ page + "&perPage=5" ;
        RequestQueue requestQueue = BaseActivity.getInstance().getRequestQueue();

        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject responseJSONObject = new JSONObject(response);
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
        };
        RetryPolicy mRetryPolicy = new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(mRetryPolicy);
        requestQueue.add(stringRequest);

    }

    private JListApi fetchData(JSONObject response) {

        try {

            jListApi = new JListApi();
            if (!response.isNull("id")) {
                jListApi.setId(response.getInt("id"));
            }
            if (!response.isNull("name")) {
                jListApi.setName(response.getString("name"));
            }
            if (!response.isNull("picture")) {
                jListApi.setPicture(response.getString("picture"));
            }
            if (!response.isNull("type")) {
                jListApi.setType(response.getString("type"));
            }
            if (!response.isNull("score")) {
                jListApi.setScore(response.getLong("score"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jListApi;
    }
}



