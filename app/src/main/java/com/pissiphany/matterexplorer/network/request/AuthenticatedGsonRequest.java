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
    private Class<? extends T> mKlass;
    private String mBody;
    private Map<String, String> mHeaders;
    private Map<String, String> mParams;
    private Response.Listener<T> mListener;

    private Priority mPriority;

    private Gson mGson;

    private AuthenticatedGsonRequest(Builder<T> b) {
        super(b.mMethod, b.mUrl, b.mErrorListener);

        this.mKlass = b.mKlass;
        this.mListener = b.mListener;
        this.mBody = b.mBody; // TODO do something with body!!!
        this.mPriority = b.mPriority;
        this.mHeaders = b.mHeaders;
        this.mParams = b.mParams;

        this.mGson = b.mGson;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return mHeaders;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return mParams;
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
                    mGson.fromJson(json, mKlass),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }

    public static class Builder<S> {
        // REQUIRED
        private int mMethod = -2; // is < Method.DEPRECATED_GET_OR_POST
        private String mUrl;
        private Response.ErrorListener mErrorListener;
        private Class<? extends S> mKlass;
        private Response.Listener<S> mListener;

        // OPTIONAL
        private String mBody = "";
        private Priority mPriority = Priority.NORMAL;
        private Map<String, String> mHeaders = new HashMap<>();
        private Map<String, String> mParams = new HashMap<>();

        private Gson mGson;

        @Inject
        public Builder(Gson gson) {
            this.mGson = gson;
        }

        public Builder<S> setMethod(int method) {
            this.mMethod = method;
            return this;
        }

        public Builder<S> setUrl(String url) {
            this.mUrl = url;
            return this;
        }

        public Builder<S> setResponseClass(Class<? extends S> klass) {
            this.mKlass = klass;
            return this;
        }

        public Builder<S> setListener(Response.Listener<S> listener) {
            this.mListener = listener;
            return this;
        }

        public Builder<S> setErrorListener(Response.ErrorListener errorListener) {
            this.mErrorListener = errorListener;
            return this;
        }

        public Builder<S> setBody(String body) {
            this.mBody = body;
            return this;
        }

        public Builder<S> setPriority(Priority priority) {
            this.mPriority = priority;
            return this;
        }

        public Builder<S> setHeaders(@NonNull Map<String, String> headers) {
            this.mHeaders = headers;
            return this;
        }

        public Builder<S> setParams(@NonNull Map<String, String> params) {
            this.mParams = params;
            return this;
        }

        public AuthenticatedGsonRequest<S> build() {
            if (mGson == null) throw new NullPointerException("must provide an instance of Gson");

            if (mMethod < Method.DEPRECATED_GET_OR_POST) {
                throw new NullPointerException("must define a request method");
            }
            if (mUrl == null) throw new NullPointerException("must define a request url");
            if (mKlass == null) throw new NullPointerException("must declare a response class");
            if (mListener == null) throw new NullPointerException("must provide a success callback");
            if (mErrorListener == null) throw new NullPointerException("must provide an error callback");

            return new AuthenticatedGsonRequest<>(this);
        }
    }
}
