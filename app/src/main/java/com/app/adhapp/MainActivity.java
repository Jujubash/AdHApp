package com.app.adhapp;

import android.net.nsd.NsdManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.app.adhapp.NSD.nsdHelper;

public class MainActivity extends AppCompatActivity {
    public static void main(String[] args) {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nsdHelper NsdServiceInfo = new nsdHelper();
        System.out.println(NsdServiceInfo); // Just do it
    }
}