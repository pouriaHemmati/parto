package com.unco.parto.dev.personal;


import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.unco.parto.base.BaseActivity;
import com.unco.parto.dev.loging.ILoginInteractor;
import com.unco.parto.model.JLogin;
import com.unco.parto.model.JPersonal;
import com.unco.parto.utilis.SharedPreferences;
import com.unco.parto.utilis.URLStore;
import com.unco.parto.utilis.UrlManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class PersonalInteractor implements IPersonalInteractor {
    private String url = "";
    JPersonal jPersonal;
    SharedPreferences sharedPreferences;
    @Override
    public void callPersonal(int id, final IPersonalFinishedListener iPersonalFinishedListener) {
        url = UrlManager.getUrl(URLStore.PERSONAL) + id + "/info";
        RequestQueue requestQueue = BaseActivity.getInstance().getRequestQueue();

        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject responseJSONObject = new JSONObject(response);

                                iPersonalFinishedListener.successPersonal(PersonalInteractor.this.fetchData(responseJSONObject));


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
                                iPersonalFinishedListener.errorTokenPersonal("token");
                            }
                            String responseBody = new String(error.networkResponse.data, "utf-8");
                            JSONObject data = new JSONObject(responseBody);
                            String message = data.getString("error");
                            iPersonalFinishedListener.errorPersonal(message);
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

    private JPersonal fetchData(JSONObject response) {

        try {

            jPersonal = new JPersonal();
            if (!response.isNull("id")) {
                jPersonal.setId(response.getInt("id"));
            }
            if (!response.isNull("name")) {
                jPersonal.setName(response.getString("name"));
            }
            if (!response.isNull("picture")) {
                jPersonal.setPicture(response.getString("picture"));
            }
            if (!response.isNull("type")) {
                jPersonal.setType(response.getString("type"));
            }
            if (!response.isNull("score")) {
                jPersonal.setScore(response.getDouble("score"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jPersonal;
    }
}



