package com.example.repeatlayoutman;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.storage.StorageManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.repeatlayoutman.receiver.UsbStatusChangeEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

public class MainActivity extends AppCompatActivity {
    private MyAdapter adapter;
    private String strFilePath;
    public static TextView mtext;
    private List<Product> productList=new ArrayList<>();
    private  ImageView image;
    private UsbStatusChangeEvent usb2;
    private JZVideoPlayerStandard jzVideoPlayerStandard;
    private StorageManager mStorageManager;
    static ArrayList<File> allFile = new ArrayList<>();
    static ArrayList<String> b = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(this, "onCreate", Toast.LENGTH_SHORT).show();
        mtext = findViewById(R.id.sb);
        image = findViewById(R.id.sb_image);
        readFile(new File("/storage", "usb1"));//遍历整个文件夹
        for (File string : allFile) {
            if (string.getName().endsWith(".jpg")||string.getName().endsWith(".jpeg"))
            {
                b.add(string.getPath());
            }
        }
        Log.i("path", String.valueOf(b));
        Log.i("path", String.valueOf(b.size()));
        Log.i("666", getExternalStorageDirectory());
        hellodata();
        RecyclerView recyclerView =(RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager =new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        MyAdapter adapter = new MyAdapter(productList);
        recyclerView.setAdapter(adapter);
        new LinearSnapHelper().attachToRecyclerView(recyclerView);
        recyclerView.setLayoutManager(new LooperLayoutManager(RecyclerView.HORIZONTAL));
        }
    static void readFile(File directory) {
        for (File file : directory.listFiles()) {
            if (file.isDirectory())
                readFile(file);
            else
                allFile.add(file);
        }
    }
    public static String getExternalStorageDirectory () {
        String dir = new String();
        try {
            Runtime runtime = Runtime.getRuntime();
            Process proc = runtime.exec("mount");
            InputStream is = proc.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            String line;
            BufferedReader br = new BufferedReader(isr);
            while ((line = br.readLine()) != null) {
                if (line.contains("secure")) continue;
                if (line.contains("asec")) continue;

                if (line.contains("fat")) {
                    String columns[] = line.split(" ");
                    if (columns != null && columns.length > 1) {
                        dir = dir.concat(columns[1] + "\n");
                    }
                } else if (line.contains("fuse")) {
                    String columns[] = line.split(" ");
                    if (columns != null && columns.length > 1) {
                        dir = dir.concat(columns[1] + "\n");
                    }
                }
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return dir;
    }
        static String []a={"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21"};
    private  void hellodata(){
        if(b.size()>0&&b.size()<=20){
            for(int i=0;i<b.size();i++){
                Product x=new Product(a[i],b.get(i),"神里凌华");
                productList.add(x);
            }
        }
        else if(b.size()>20)
        {
            for(int i=0;i<20;i++)
            {
                Product x = new Product(a[i], b.get(i), "神里凌华");
                productList.add(x);
            }
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNetworkChangeEvent(UsbStatusChangeEvent event) {
    }
    @Override
    public void onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }
    @Override
    protected void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }


    


}