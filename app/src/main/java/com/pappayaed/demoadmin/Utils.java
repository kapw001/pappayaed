package com.pappayaed.demoadmin;

import android.content.Context;
import android.graphics.Color;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by yasar on 7/2/18.
 */

public class Utils {

    final static int[] MY_COLORS = {Color.parseColor("#FF6C88"), Color.parseColor("#A49BF8")};

    final static int[] MY_COLORSBAR = {Color.parseColor("#00DEE3"), Color.parseColor("#FF6540"), Color.parseColor("#FBD06A")};

    public static String loadJSONFromAsset(Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open("districtdata.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
