package com.example.app1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private Button girisButonu, telefonlaGirisButonu;
    private EditText KullaniciMail, KullaniciSifre;
    private TextView YeniHesapAlma, SifreUnutmaBaglanti;
    //FİREBASEE
    private FirebaseUser mevcutKullanici;
    private FirebaseAuth mYetki;

    //Progress
    ProgressDialog girisDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //KONTROL TANIMLAMASI
        girisButonu = findViewById(R.id.giris_butonu);
        telefonlaGirisButonu=findViewById(R.id.telefonla_giris_butonu);
        KullaniciMail=findViewById(R.id.giris_email);
        KullaniciSifre=findViewById(R.id.giris_sifre);

        YeniHesapAlma=findViewById(R.id.yeni_hesap_alma);
        SifreUnutmaBaglanti=findViewById(R.id.sifre_unutma_baglantisi);
        //PROGRESS
        girisDialog = new ProgressDialog(this);
        // FireBase
        mYetki = FirebaseAuth.getInstance();
        mevcutKullanici = mYetki.getCurrentUser();


        YeniHesapAlma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent kayitAktivityIntent = new Intent(LoginActivity.this,KayitActivity.class);
                startActivity(kayitAktivityIntent);

            }
        });
        girisButonu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KullaniciyiGirisİzniVer();

            }
        });
    }
    private void KullaniciyiGirisİzniVer(){
        String email =KullaniciMail.getText().toString();
        String sifre= KullaniciSifre.getText().toString();
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Email boş olamaz!", Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(sifre)){
            Toast.makeText(this, "Sifre Boş Olamaz!", Toast.LENGTH_SHORT).show();
        }
        else{
            //PROGRESS
            girisDialog.setTitle("Giriş Yapılıyor");
            girisDialog.setMessage("Lütfen Bekleyin");
            girisDialog.setCanceledOnTouchOutside(true);
            girisDialog.show();

                //GİRİS
            mYetki.signInWithEmailAndPassword(email,sifre)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull final Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Intent anaSayfa=new Intent(LoginActivity.this,MainActivity.class);
                                startActivity(anaSayfa);
                                Toast.makeText(LoginActivity.this, "Giriş Başarılıı", Toast.LENGTH_SHORT).show();
                                girisDialog.dismiss();
                            }
                            else{
                                String mesaj=task.getException().toString();
                                Toast.makeText(LoginActivity.this, "Hata:"+mesaj+"bilgileri kontrol ediniz", Toast.LENGTH_SHORT).show();
                                girisDialog.dismiss();
                            }
                        }
                    });


        }

    }


    @Override
    protected void onStart() {
        super.onStart();

        if(mevcutKullanici!= null){
            KullaniciyiAnaAktivitiyeGonder();
        }
    }
    private void KullaniciyiAnaAktivitiyeGonder(){
        Intent AnaAktiviteIntent = new Intent(LoginActivity.this,MainActivity.class);
        startActivity(AnaAktiviteIntent);
    }
}
