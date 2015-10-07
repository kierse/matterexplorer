package com.pissiphany.matterexplorer.network.api.themis.contract;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kierse on 15-10-01.
 */
public class ThemisContractV2 {
    private static final String API_PATH = "api/v2";

    private static final String CONTENT_TYPE_HEADER = "Content-Type";
    private static final String CONTENT_TYPE_VALUE = "application/json";

    private static final String ACCEPT_HEADER = "Accept";
    private static final String ACCEPT_VALUE = "application/json";

    public static final ImmutableMap<String, String> DEFAULT_HEADERS =
            new ImmutableMap.Builder<String, String>()
                    .put(CONTENT_TYPE_HEADER,CONTENT_TYPE_VALUE)
                    .put(ACCEPT_HEADER, ACCEPT_VALUE)
                    .build();

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

    public static Uri getUriForEndpoint(@NonNull Uri root, @NonNull Endpoints endpoint) {
        return getAp1V2Uri(root).buildUpon().appendPath(endpoint.getEndpointPath()).build();
    }

    public static Uri getUriForEndpoint(@NonNull Uri root, @NonNull Endpoints endpoint, @NonNull Long id) {
        return getUriForEndpoint(root, endpoint).buildUpon().appendPath(id.toString()).build();
    }

    private static Uri getAp1V2Uri(Uri root) {
        return root.buildUpon().appendEncodedPath(API_PATH).build();
    }
}
