package com.pissiphany.matterexplorer.network.api.themis.contract;

import android.net.Uri;
import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kierse on 15-10-01.
 */
public class ThemisContractV2 {
    private static final String SCHEME = "";
    private static final String AUTHORITY = "";
    private static final String API_PATH = "api/v2";
    private static final Uri ROOT_URI = new Uri.Builder()
            .scheme(SCHEME)
            .authority(AUTHORITY)
            .appendEncodedPath(API_PATH)
            .build();

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String AUTHORIZATION_VALUE = "Bearer ";

    private static final String CONTENT_TYPE_HEADER = "Content-Type";
    private static final String CONTENT_TYPE_VALUE = "application/json";

    private static final String ACCEPT_HEADER = "Accept";
    private static final String ACCEPT_VALUE = "application/json";

    // TODO make immutable
    public static final Map<String, String> AUTHENTICATED_HEADERS = new HashMap<>();
    static {
        AUTHENTICATED_HEADERS.put(AUTHORIZATION_HEADER, AUTHORIZATION_VALUE);
        AUTHENTICATED_HEADERS.put(CONTENT_TYPE_HEADER,CONTENT_TYPE_VALUE);
        AUTHENTICATED_HEADERS.put(ACCEPT_HEADER, ACCEPT_VALUE);
    }

    public enum Endpoints {
        MATTERS("matters");

        private String endpointPath;

        Endpoints(String endpointPath) {
            this.endpointPath = endpointPath;
        }

        public String getEndpointPath() {
            return endpointPath;
        }
    }

    public static Uri getUriForEndpoint(@NonNull Endpoints endpoint) {
        return ROOT_URI.buildUpon().appendPath(endpoint.getEndpointPath()).build();
    }

    public static Uri getUriForEndpoint(@NonNull Endpoints endpoint, @NonNull Long id) {
        return getUriForEndpoint(endpoint).buildUpon().appendPath(id.toString()).build();
    }
}
