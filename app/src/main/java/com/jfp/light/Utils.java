package com.jfp.light;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.content.res.Resources;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import androidx.core.content.ContextCompat;

import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class Utils {

    Context context;

    public Utils(Context context) {
        this.context = context;
    }

    public static void fullScreen(Activity activity) {

        int currentApiVersion = Build.VERSION.SDK_INT;
        final int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

        if (currentApiVersion >= Build.VERSION_CODES.KITKAT) {
            activity.getWindow().getDecorView().setSystemUiVisibility(flags);
            final View decorView = activity.getWindow().getDecorView();
            decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
                @Override
                public void onSystemUiVisibilityChange(int visibility) {
                    if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                        decorView.setSystemUiVisibility(flags);
                    }
                }
            });
        }

    }

    public static MediaPlayer playMp3(Context context, String name) {
        MediaPlayer m = null;
        try {
            m = new MediaPlayer();
            if (m.isPlaying()) {
                m.stop();
                m.release();
                m = new MediaPlayer();
            }
            AssetFileDescriptor descriptor = context.getAssets().openFd(name);
            m.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
            descriptor.close();

            m.prepare();
            m.setVolume(1f, 1f);
            m.setLooping(false);
            m.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return m;
    }

    public static String getMacAddress() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(Integer.toHexString(b & 0xFF) + ":");
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
        }
        return "02:00:00:00:00:00";
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    // TODO: 11/24/2019 i have to delete  Banned_Package & checkCrackingTools because it's only for make byte from string !

    public String[] Banned_Package = {
            "net.egordmitriev.cheatsheets", "droidmate.appopsinstaller",
            "com.jowi81.propeditor", "rs.pedjaapps.alogcatroot.app",
            "stericson.busybox", "com.modsinstallernet.mods.installer",
            "com.technohacker.app", "com.modcheatxapp.modcheat.app",
            "com.felixheller.sharedprefseditor", "com.felixheller.sharedprefseditor.pro",
            "com.gmail.heagoo.apkeditor.pro", "com.gmail.heagoo.apkeditor",
            "app.greyshirts.sslcapture", "com.guoshi.httpcanary",
            "com.minhui.networkcapture", "com.packagesniffer.frtparlak",
            "com.evbadroid.proxymon", "com.minhui.wifianalyzer",
            "frtparlak.rootsniffer", "com.evbadroid.wicapdemo",
            "com.echozio.packethandler", "com.egorovandreyrm.pcapremote",
            "org.sandroproxy", "com.antispycell.connmonitor",
            "com.evbadroid.iwscan", "net.techet.netanalyzerlite.an",
            "packetGenrator.edu.ae", "com.imhotepisinvisible.sslsift",
            "com.eakteam.networkmanager", "com.aconno.blesniffer",
            "com.stealthcopter.portdroid", "com.evbadroid.netwometer",
            "appsniffer.online", "p2i6.packetinjection",
            "com.netacad.PacketTracerM", "org.sandroproxy.drony",
            "com.dimonvideo.luckypatcher", "com.chelpus.lackypatch",
            "com.android.vending.billing.InAppBillingService.LACK"
    };

    public String[] banned = {
            "[97, 112, 112, 46, 103, 114, 101, 121, 115, 104, 105, 114, 116, 115, 46, 115, 115, 108, 99, 97, 112, 116, 117, 114, 101]",
            "[99, 111, 109, 46, 103, 117, 111, 115, 104, 105, 46, 104, 116, 116, 112, 99, 97, 110, 97, 114, 121]",
            "[99, 111, 109, 46, 109, 105, 110, 104, 117, 105, 46, 110, 101, 116, 119, 111, 114, 107, 99, 97, 112, 116, 117, 114, 101]",
            "[99, 111, 109, 46, 112, 97, 99, 107, 97, 103, 101, 115, 110, 105, 102, 102, 101, 114, 46, 102, 114, 116, 112, 97, 114, 108, 97, 107]",
            "[99, 111, 109, 46, 101, 118, 98, 97, 100, 114, 111, 105, 100, 46, 112, 114, 111, 120, 121, 109, 111, 110]",
            "[99, 111, 109, 46, 109, 105, 110, 104, 117, 105, 46, 119, 105, 102, 105, 97, 110, 97, 108, 121, 122, 101, 114]",
            "[102, 114, 116, 112, 97, 114, 108, 97, 107, 46, 114, 111, 111, 116, 115, 110, 105, 102, 102, 101, 114]",
            "[99, 111, 109, 46, 101, 118, 98, 97, 100, 114, 111, 105, 100, 46, 119, 105, 99, 97, 112, 100, 101, 109, 111]",
            "[99, 111, 109, 46, 101, 99, 104, 111, 122, 105, 111, 46, 112, 97, 99, 107, 101, 116, 104, 97, 110, 100, 108, 101, 114]",
            "[99, 111, 109, 46, 101, 103, 111, 114, 111, 118, 97, 110, 100, 114, 101, 121, 114, 109, 46, 112, 99, 97, 112, 114, 101, 109, 111, 116, 101]",
            "[111, 114, 103, 46, 115, 97, 110, 100, 114, 111, 112, 114, 111, 120, 121]",
            "[99, 111, 109, 46, 97, 110, 116, 105, 115, 112, 121, 99, 101, 108, 108, 46, 99, 111, 110, 110, 109, 111, 110, 105, 116, 111, 114]",
            "[99, 111, 109, 46, 101, 118, 98, 97, 100, 114, 111, 105, 100, 46, 105, 119, 115, 99, 97, 110]",
            "[110, 101, 116, 46, 116, 101, 99, 104, 101, 116, 46, 110, 101, 116, 97, 110, 97, 108, 121, 122, 101, 114, 108, 105, 116, 101, 46, 97, 110]",
            "[112, 97, 99, 107, 101, 116, 71, 101, 110, 114, 97, 116, 111, 114, 46, 101, 100, 117, 46, 97, 101]",
            "[99, 111, 109, 46, 105, 109, 104, 111, 116, 101, 112, 105, 115, 105, 110, 118, 105, 115, 105, 98, 108, 101, 46, 115, 115, 108, 115, 105, 102, 116]",
            "[99, 111, 109, 46, 101, 97, 107, 116, 101, 97, 109, 46, 110, 101, 116, 119, 111, 114, 107, 109, 97, 110, 97, 103, 101, 114]",
            "[99, 111, 109, 46, 97, 99, 111, 110, 110, 111, 46, 98, 108, 101, 115, 110, 105, 102, 102, 101, 114]",
            "[99, 111, 109, 46, 115, 116, 101, 97, 108, 116, 104, 99, 111, 112, 116, 101, 114, 46, 112, 111, 114, 116, 100, 114, 111, 105, 100]",
            "[99, 111, 109, 46, 101, 118, 98, 97, 100, 114, 111, 105, 100, 46, 110, 101, 116, 119, 111, 109, 101, 116, 101, 114]",
            "[97, 112, 112, 115, 110, 105, 102, 102, 101, 114, 46, 111, 110, 108, 105, 110, 101]",
            "[112, 50, 105, 54, 46, 112, 97, 99, 107, 101, 116, 105, 110, 106, 101, 99, 116, 105, 111, 110]",
            "[99, 111, 109, 46, 110, 101, 116, 97, 99, 97, 100, 46, 80, 97, 99, 107, 101, 116, 84, 114, 97, 99, 101, 114, 77]",
            "[111, 114, 103, 46, 115, 97, 110, 100, 114, 111, 112, 114, 111, 120, 121, 46, 100, 114, 111, 110, 121]",
            "[99, 111, 109, 46, 100, 105, 109, 111, 110, 118, 105, 100, 101, 111, 46, 108, 117, 99, 107, 121, 112, 97, 116, 99, 104, 101, 114]",
            "[99, 111, 109, 46, 99, 104, 101, 108, 112, 117, 115, 46, 108, 97, 99, 107, 121, 112, 97, 116, 99, 104]",
            "[99, 111, 109, 46, 97, 110, 100, 114, 111, 105, 100, 46, 118, 101, 110, 100, 105, 110, 103, 46, 98, 105, 108, 108, 105, 110, 103, 46, 73, 110, 65, 112, 112, 66, 105, 108, 108, 105, 110, 103, 83, 101, 114, 118, 105, 99, 101, 46, 76, 65, 67, 75]",
            "[110, 101, 116, 46, 101, 103, 111, 114, 100, 109, 105, 116, 114, 105, 101, 118, 46, 99, 104, 101, 97, 116, 115, 104, 101, 101, 116, 115]",
            "[100, 114, 111, 105, 100, 109, 97, 116, 101, 46, 97, 112, 112, 111, 112, 115, 105, 110, 115, 116, 97, 108, 108, 101, 114]",
            "[99, 111, 109, 46, 106, 111, 119, 105, 56, 49, 46, 112, 114, 111, 112, 101, 100, 105, 116, 111, 114]",
            "[114, 115, 46, 112, 101, 100, 106, 97, 97, 112, 112, 115, 46, 97, 108, 111, 103, 99, 97, 116, 114, 111, 111, 116, 46, 97, 112, 112]",
            "[115, 116, 101, 114, 105, 99, 115, 111, 110, 46, 98, 117, 115, 121, 98, 111, 120]",
            "[99, 111, 109, 46, 109, 111, 100, 115, 105, 110, 115, 116, 97, 108, 108, 101, 114, 110, 101, 116, 46, 109, 111, 100, 115, 46, 105, 110, 115, 116, 97, 108, 108, 101, 114]",
            "[99, 111, 109, 46, 116, 101, 99, 104, 110, 111, 104, 97, 99, 107, 101, 114, 46, 97, 112, 112]",
            "[99, 111, 109, 46, 109, 111, 100, 99, 104, 101, 97, 116, 120, 97, 112, 112, 46, 109, 111, 100, 99, 104, 101, 97, 116, 46, 97, 112, 112]",
            "[99, 111, 109, 46, 102, 101, 108, 105, 120, 104, 101, 108, 108, 101, 114, 46, 115, 104, 97, 114, 101, 100, 112, 114, 101, 102, 115, 101, 100, 105, 116, 111, 114]",
            "[99, 111, 109, 46, 102, 101, 108, 105, 120, 104, 101, 108, 108, 101, 114, 46, 115, 104, 97, 114, 101, 100, 112, 114, 101, 102, 115, 101, 100, 105, 116, 111, 114, 46, 112, 114, 111]",
            "[99, 111, 109, 46, 103, 109, 97, 105, 108, 46, 104, 101, 97, 103, 111, 111, 46, 97, 112, 107, 101, 100, 105, 116, 111, 114, 46, 112, 114, 111]",
            "[99, 111, 109, 46, 103, 109, 97, 105, 108, 46, 104, 101, 97, 103, 111, 111, 46, 97, 112, 107, 101, 100, 105, 116, 111, 114]"


    };

    public static String GUID(Context context) {
        int size = getMacAddress().length();
        String mac = getMacAddress();
        String result = "";
        for (int i = 0; i < size; i++) {
            char mac_ch = mac.charAt(i);
            char obfuscated = (char) (mac_ch + Integer.parseInt(context.getString(R.string.key)));
            result += obfuscated;
        }
        return result;

    }

    public List<String> checkApp() {
        List<String> pack = new ArrayList<>();
        for (int i = 0; i < banned.length; i++) {
            int size = banned[i].split(",").length;
            byte[] myb = new byte[size];
            for (int j = 0; j < size; j++) {
                myb[j] = Byte.parseByte(banned[i].split(",")[j].replace("[", "").replace("]", "").replace(" ", ""));
            }
            Log.i("package", new String(myb));
            String po = new String(myb);
            if (isPackageExisted(po)) {
                pack.add(po);
            }
        }
        return pack;
    }

    public void checkCrackingTools() {
        for (int i = 0; i < Banned_Package.length; i++) {
            byte[] b;
            b = Banned_Package[i].getBytes();
            byte[] myb = new byte[Arrays.toString(b).split(",").length];
            for (int j = 0; j < Arrays.toString(b).split(",").length; j++) {
                myb[j] = Byte.parseByte(Arrays.toString(b).split(",")[j].replace("[", "").replace("]", "").replace(" ", ""));
            }
            String wow = "";
            Log.i("package", new String(myb));
            Log.i("json (" + i + ")", Arrays.toString(b) + "");
        }
    }

    public boolean isPackageExisted(String targetPackage) {
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo info = pm.getPackageInfo(targetPackage, PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
        return true;
    }

    public static void changeStatusBar(Activity activity) {
        Window window = activity.getWindow();

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        window.setStatusBarColor(ContextCompat.getColor(activity, android.R.color.black));
    }

    public static String getDisplaySize(Activity activity) {
        double x = 0, y = 0;
        int mWidthPixels, mHeightPixels;
        try {
            WindowManager windowManager = activity.getWindowManager();
            Display display = windowManager.getDefaultDisplay();
            DisplayMetrics displayMetrics = new DisplayMetrics();
            display.getMetrics(displayMetrics);
            Point realSize = new Point();
            Display.class.getMethod("getRealSize", Point.class).invoke(display, realSize);
            mWidthPixels = realSize.x;
            mHeightPixels = realSize.y;
            DisplayMetrics dm = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
            x = Math.pow(mWidthPixels / dm.xdpi, 2);
            y = Math.pow(mHeightPixels / dm.ydpi, 2);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return String.format(Locale.US, "%.2f", Math.sqrt(x + y));
    }

    public static int convertPixelsToDp(float px, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        int dp = (int) (px / (metrics.densityDpi / 160f));
        return dp;
    }
    public static void hideKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
