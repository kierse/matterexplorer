package com.pissiphany.matterexplorer.network.request;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;

import java.util.Map;

/**
 * Created by kierse on 16-02-07.
 */
public class AuthenticatedStringRequest extends StringRequest {
    private Map<String, String> mHeaders;
    private Request.Priority mPriority;

    public AuthenticatedStringRequest(
            int method,
            String url,
            Map<String, String> headers,
            Request.Priority priority,
            Response.Listener<String> listener,
            Response.ErrorListener errorListener
    ) {
        super(method, url, listener, errorListener);
        mHeaders = headers;
        mPriority = priority;

        setShouldCache(false);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return mHeaders;
    }

    @Override
    public Priority getPriority() {
        return mPriority;
    }
}
