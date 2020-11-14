package com.example.readnews;

import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;


public class NewsDownloadManager {

    public static String download(String link, News news) {
        try {
            String path = Environment.getExternalStorageDirectory().getPath();
            path += "/ReadNews/" + news.getUrl() + ".html";
            URL url = new URL(link);
            URLConnection con = url.openConnection();
            InputStream input = con.getInputStream();
            byte[] b = new byte[1024];
            int count = input.read(b);
            File f = new File(path);
            f.getParentFile().mkdirs();
            f.createNewFile();
            FileOutputStream output = new FileOutputStream(path);
            while (count > 0) {
                output.write(b, 0, count);
                count = input.read(b);
            }
            input.close();
            output.close();
            return path;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
