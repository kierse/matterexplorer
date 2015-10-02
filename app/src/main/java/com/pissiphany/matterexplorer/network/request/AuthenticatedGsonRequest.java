package com.pissiphany.matterexplorer.network.request;

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
    private Class<? extends T> mClazz;
    private String mBody;
    private Map<String, String> mHeaders = new HashMap<>();
    private Map<String, String> mParams = new HashMap<>();
    private Response.Listener<T> mListener;

    private Priority mPriority = Priority.NORMAL;

    @Inject
    Gson sGson;

    // GET
    public AuthenticatedGsonRequest(int method, String url, Class<? extends T> clazz,
                            Response.Listener<T> listener, Response.ErrorListener errorListener) {
        this(method, url, clazz, null, listener, errorListener);
    }

    // POST
    private AuthenticatedGsonRequest(int method, String url, Class<? extends T> clazz, String body,
                            Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.mClazz = clazz;
        this.mBody = body; // TODO do something with body!!
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

    public void setPriority(Priority priority) {
        mPriority = priority;
    }

    @Override
    public Priority getPriority() {
        return mPriority;
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

//    private AuthenticatedGsonRequest(Builder<T> b) {
//        super(b.mMethod, b.mUrl, b.mErrorListener);
//        this.mClazz = b.mClazz;
//        this.mListener = b.mListener;
//        this.mBody = b.mBody; // TODO do something with body!!!
//        this.mPriority = b.mPriority;
//    }
//
//    public static class Builder<S> {
//        private int mMethod;
//        private String mUrl;
//        private Class<? extends S> mClazz;
//        private Response.Listener<S> mListener;
//        private Response.ErrorListener mErrorListener;
//
//        private String mBody;
//        private Priority mPriority = Priority.NORMAL;
//
//        public Builder(int method, String url, Class<? extends S> clazz,
//                       Response.Listener<S> listener, Response.ErrorListener errorListener) {
//            this.mMethod = method;
//            this.mUrl = url;
//            this.mClazz = clazz;
//            this.mListener = listener;
//            this.mErrorListener = errorListener;
//        }
//
//        public Builder<S> setBody(String body) {
//            this.mBody = body;
//            return this;
//        }
//
//        public Builder<S> setPriority(Priority priority) {
//            this.mPriority = priority;
//            return this;
//        }
//
//        public AuthenticatedGsonRequest<S> build() {
//            return new AuthenticatedGsonRequest<>(this);
//        }
//    }
}
