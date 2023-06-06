package com.example.comicuniverse.BroadReserver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.widget.Toast;

public class BroadcastReceiverComic extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
            if (!isNetWord(context)) {
                Toast.makeText(context, "Mất kết nối internet", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean isNetWord(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
         Network network=connectivityManager.getActiveNetwork();
         if (network==null){
             return false;
         }
            NetworkCapabilities networkCapabilities=connectivityManager.getNetworkCapabilities(network);
         return networkCapabilities!=null&&(networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI
         )||networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR));
        } else {
            NetworkInfo networkInfor = connectivityManager.getActiveNetworkInfo();
            return networkInfor != null && networkInfor.isConnected();
        }
    }
}
