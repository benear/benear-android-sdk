package me.benear.examples;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import me.benear.benearqr.BenearQRClient;
import me.benear.core.BenearAPI;
import me.benear.model.BeResource;
import me.benear.model.OnDetectionCallback;

public class ExampleActivity extends AppCompatActivity {

    private BenearQRClient mQRClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Create benearAPI
        BenearAPI benearAPI = new BenearAPI.BenearBuilder()
                //.enableOffLine()
                //.anotherConfigs
                .build();

        //Create clients
        mQRClient = new BenearQRClient.BenearQRBuilder()
                .addOnDetectionCallbackForType("mesa", new OnDetectionCallback() {
                    @Override
                    public void onDetected(List<BeResource> resources) {
                        show(String.valueOf(resources.size()));
                    }
                })
                .build();


        //add clients
        mQRClient.setBenearProvider(benearAPI);
    }

    private void show(String s) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }

    public void scan(View view) {
        mQRClient.scanQR();
    }
}
