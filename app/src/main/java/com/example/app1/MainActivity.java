package com.example.app1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private ViewPager myViewPager;
    private TabLayout myTabLayout;
    private SekmeErisimAdapter mySekmeErisimAdapter;

    //FİREBASE
    private FirebaseUser mevcutKullanici;
    private FirebaseAuth mYetki;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar=findViewById(R.id.ana_sayfa_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Whatsapp");
        myViewPager = findViewById(R.id.ana_sekmeler_pager);
        mySekmeErisimAdapter = new SekmeErisimAdapter(getSupportFragmentManager());
        myViewPager.setAdapter(mySekmeErisimAdapter);

        myTabLayout = findViewById(R.id.ana_sekmeler);
        myTabLayout.setupWithViewPager(myViewPager);

        //FİREBASE
        mYetki=FirebaseAuth.getInstance();
        mevcutKullanici=mYetki.getCurrentUser();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mevcutKullanici==null){
            KullaniciyiLoginActivityeGonder();

        }
    }
    private void KullaniciyiLoginActivityeGonder(){
        Intent loginIntent = new Intent(MainActivity.this,LoginActivity.class);
        startActivity(loginIntent);

    }
}
