package com.pissiphany.matterexplorer.volley.event;

import com.pissiphany.matterexplorer.volley.response.ApiResponse;

/**
 * Created by kierse on 15-09-13.
 */
public class GetAndSaveEvent implements NetworkEvent {
    private Class<? extends ApiResponse> mResponseClass;

    public GetAndSaveEvent(Class<? extends ApiResponse> clazz) {
        this.mResponseClass = clazz;
    }

    public Class<? extends ApiResponse> getResponseClass() {
        return mResponseClass;
    }

    public static class Response {
        public ApiResponse mResponse;

        public Response(ApiResponse response) {
            this.mResponse = response;
        }

        public ApiResponse getResponse() {
            return mResponse;
        }
    }
}

//@AutoParcel
//public abstract class FetchAndSaveEvent {
//    public abstract Class<? extends ApiResponse> getResponseClass();
//
//    FetchAndSaveEvent() { }
//
//    public static Builder builder() { return new AutoParcel_FetchAndSaveEvent.Builder(); }
//
//    @AutoParcel.Builder
//    public abstract static class Builder {
//        public abstract Builder setResponseClass(Class<? extends ApiResponse> clazz);
//
//        public abstract FetchAndSaveEvent build();
//    }
//}
