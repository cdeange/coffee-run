package com.deange.coffeerun.util;

import android.text.TextUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public final class Utils {

    private Utils() { }

    public static String streamToString(final InputStream in) {
        final StringBuilder sb = new StringBuilder();
        try {
            final Reader reader = new InputStreamReader(in);
            int c;
            while ((c = reader.read()) != -1) {
                sb.append((char) c);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        final String result = sb.toString();
        return TextUtils.isEmpty(result) ? null : result;
    }

}
