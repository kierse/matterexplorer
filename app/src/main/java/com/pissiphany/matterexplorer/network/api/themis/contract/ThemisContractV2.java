package com.pissiphany.matterexplorer.network.api.themis.contract;

import android.net.Uri;
import android.support.annotation.NonNull;

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
