package com.example.submission_3;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class AboutActivity extends AppCompatActivity implements View.OnClickListener {
    Button igBtn;
    Button fbBtn;
    Button ghBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_about);
        igBtn = findViewById(R.id.btn_ig);
        igBtn.setOnClickListener(this);
        fbBtn = findViewById(R.id.btn_fb);
        fbBtn.setOnClickListener(this);
        ghBtn = findViewById(R.id.btn_gh);
        ghBtn.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_ig)
        {
            String url = "https://www.instagram.com/arrdelon/";
            Intent mIntent = new Intent(Intent.ACTION_VIEW);
            mIntent.setData(Uri.parse(url));
            startActivity(mIntent);
        }
        else if(v.getId()==R.id.btn_fb)
        {
            String url = "https://www.facebook.com/chandra.delon.77";
            Intent mIntent = new Intent(Intent.ACTION_VIEW);
            mIntent.setData(Uri.parse(url));
            startActivity(mIntent);
        }
        else if(v.getId() == R.id.btn_gh)
        {
            String url = "https://github.com/vinix997";
            Intent mIntent = new Intent(Intent.ACTION_VIEW);
            mIntent.setData(Uri.parse(url));
            startActivity(mIntent);
        }
    }
}
