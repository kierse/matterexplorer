package com.pissiphany.matterexplorer.network.api.themis.contract;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.common.collect.ImmutableMap;

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

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String AUTHORIZATION_VALUE = "Bearer ";

    public static final ImmutableMap<String, String> DEFAULT_HEADERS =
            new ImmutableMap.Builder<String, String>()
                    .put(CONTENT_TYPE_HEADER, CONTENT_TYPE_VALUE)
                    .put(ACCEPT_HEADER, ACCEPT_VALUE)
                    .build();

    public enum Endpoints {
        BILL_DOWNLOAD("bills/%d.pdf"),

        MATTERS("matters"),

        USER_AVATAR_DOWNLOAD("users/%d/avatar");

        private String mPath;

        Endpoints(String path) {
            this.mPath = path;
        }
    }

    public static Map.Entry<String, String> getAuthorizationHeader(@NonNull final String token) {
        return new Map.Entry<String, String>() {
            @Override
            public String getKey() {
                return AUTHORIZATION_HEADER;
            }

            @Override
            public String getValue() {
                return AUTHORIZATION_VALUE + token;
            }

            @Override
            public String setValue(String object) {
                return null;
            }
        };
    }

    public static Uri getUriForEndpoint(@NonNull Uri root, @NonNull Endpoints endpoint) {
        return getApiV2Uri(root).buildUpon().appendPath(endpoint.mPath).build();
    }

    public static Uri getUriForEndpoint(@NonNull Uri root, @NonNull Endpoints endpoint, long id) {
        return getApiV2Uri(root)
                .buildUpon()
                .appendEncodedPath(buildEncodedPath(endpoint, id))
                .build();
    }

    private static Uri getApiV2Uri(Uri root) {
        return root.buildUpon().appendEncodedPath(API_PATH).build();
    }

    private static String buildEncodedPath(Endpoints endpoint, long id) {
        return String.format(endpoint.mPath, id);
    }
}
