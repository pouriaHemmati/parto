package com.unco.parto.dev.loging;


import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.unco.parto.base.BaseActivity;
import com.unco.parto.model.JLogin;
import com.unco.parto.utilis.URLStore;
import com.unco.parto.utilis.UrlManager;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

public class LoginInteractor implements ILoginInteractor {
    private String url = "";
    JLogin jLogin;

    @Override
    public void callLogin(final String username, final String password, final ILoginFinishedListener iLoginFinishedListener) {
        url = UrlManager.getUrl(URLStore.AUTHENTICATE);
        RequestQueue requestQueue = BaseActivity.getInstance().getRequestQueue();

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject responseJSONObject = new JSONObject(response);

                                iLoginFinishedListener.successLogin(LoginInteractor.this.fetchData(responseJSONObject));


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
                            iLoginFinishedListener.errorLogin(message);
                        } catch (JSONException | UnsupportedEncodingException e) {

                        }


                    }
                }
        ) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("username", username);
                params.put("password", password);
                return new JSONObject(params).toString().getBytes();
            }

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

    private JLogin fetchData(JSONObject response) {

        try {

            jLogin = new JLogin();
            if (!response.isNull("id")) {
                jLogin.setId(response.getInt("id"));
            }
            if (!response.isNull("firstName")) {
                jLogin.setFirstName(response.getString("firstName"));
            }
            if (!response.isNull("lastName")) {
                jLogin.setLastName(response.getString("lastName"));
            }
            if (!response.isNull("username")) {
                jLogin.setUsername(response.getString("username"));
            }
            if (!response.isNull("token")) {
                jLogin.setToken(response.getString("token"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jLogin;
    }
}



