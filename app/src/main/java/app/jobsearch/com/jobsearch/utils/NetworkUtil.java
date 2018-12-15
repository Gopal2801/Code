package app.jobsearch.com.jobsearch.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public class NetworkUtil {

    public static int TYPE_WIFI = 1;
    public static int TYPE_MOBILENETWORK = 2;
    public static int TYPE_NOT_CONNECTED = 0;
    private static boolean aResult;

    public static int getConnectivityStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return TYPE_WIFI;

            if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return TYPE_MOBILENETWORK;
        }
        return TYPE_NOT_CONNECTED;
    }

    public static boolean isInternetOn(Context aContext) {

        ConnectivityManager aConnecMan = (ConnectivityManager) aContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        //
        if ((aConnecMan.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTED)
                || (aConnecMan.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTING)
                || (aConnecMan.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTING)
                || (aConnecMan.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTED)) {

            aResult = true;

        } else if ((aConnecMan.getNetworkInfo(0).getState() == NetworkInfo.State.DISCONNECTED)
                || (aConnecMan.getNetworkInfo(1).getState() == NetworkInfo.State.DISCONNECTED)) {

            aResult = false;
        }

        return aResult;
    }


}
