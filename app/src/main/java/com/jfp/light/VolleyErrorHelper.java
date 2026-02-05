package com.jfp.light;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

public class VolleyErrorHelper {

    public static String getMessage(Object error, Context context) {
        if (error instanceof TimeoutError) {
            return "Time out";
        } else if (isServerProblem(error)) {
            return handleServerError(error, context);
        } else if (isNetworkProblem(error)) {
            return "No Internet Connection";
        }
        return "Something happened for internet";
    }

    private static boolean isNetworkProblem(Object error) {
        return (error instanceof NetworkError) || (error instanceof NoConnectionError);
    }

    private static boolean isServerProblem(Object error) {
        return (error instanceof ServerError) || (error instanceof AuthFailureError);
    }

    private static String handleServerError(Object err, Context context) {
        VolleyError error = (VolleyError) err;

        NetworkResponse response = error.networkResponse;

        if (response != null) {
            switch (response.statusCode) {
                case 404: {
                    return "Not Found";
                }
                case 422: {
                    return "Unprocessable Entity";
                }
                case 401: {
                    return " Unauthorized error ";
                }

                default:
                    return "Server Error";
            }
        }
        return "Server Error";
    }
}