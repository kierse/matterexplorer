package com.pissiphany.matterexplorer.volley.request;

import android.support.annotation.NonNull;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

/**
 * Created by kierse on 15-09-13.
 *
 * I basically stole this from: https://developer.android.com/training/volley/request-custom.html
 */
public class AuthenticatedGsonRequest<T> extends Request<T> {
    private Class<T> mClazz;
    private Map<String, String> mHeaders = new HashMap<>();
    private Map<String, String> mParams = new HashMap<>();
    private Response.Listener<T> mListener;

    @Inject
    Gson sGson;

    public AuthenticatedGsonRequest(int method, String url, Class<T> clazz,
                            Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.mClazz = clazz;
        this.mListener = listener;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return mHeaders;
    }

    public AuthenticatedGsonRequest<T> addHeader(@NonNull String key, String value) {
        mHeaders.put(key, value);
        return this;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return mParams;
    }

    public AuthenticatedGsonRequest<T> addParam(@NonNull String key, String value) {
        mParams.put(key, value);
        return this;
    }

    @Override
    protected void deliverResponse(T response) {
        mListener.onResponse(response);
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(
                    response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            return Response.success(
                    sGson.fromJson(json, mClazz),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }
}
