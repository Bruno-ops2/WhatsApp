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

public class KayitActivity extends AppCompatActivity {

    private Button KayitOlusturmaButtonu;

    private EditText KullaniciMail, KullaniciSifre;
    private TextView ZatenHesabımVar;
    private FirebaseAuth mYetki;

    private ProgressDialog yukleniyorDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayit);
        //FİRE TANIMLAMASII
        mYetki=FirebaseAuth.getInstance();

        //KONTROL TANIMLAMALARI
        KayitOlusturmaButtonu =findViewById(R.id.kayit_butonu);
        KullaniciMail=findViewById(R.id.kayit_email);
        KullaniciSifre=findViewById(R.id.kayit_sifre);
        ZatenHesabımVar=findViewById(R.id.zaten_hesap_var);

        yukleniyorDialog= new ProgressDialog(this);

        ZatenHesabımVar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v) {
                Intent loginActivityIntent= new Intent(KayitActivity.this,LoginActivity.class);
                startActivity(loginActivityIntent);
            }
        });
        KayitOlusturmaButtonu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                YeniHesapOlustur();

            }
        });
    }
    private void YeniHesapOlustur(){
        String email= KullaniciMail.getText().toString();
        String sifre = KullaniciSifre.getText().toString();
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Mail boş olamaz", Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(sifre)){
            Toast.makeText(this, "Şifre boş olamaz", Toast.LENGTH_SHORT).show();
        }
        else{

            yukleniyorDialog.setTitle("Yeni Hesap Olusturuluyor");
            yukleniyorDialog.setMessage("Lutfen Bekleyin");
            yukleniyorDialog.setCanceledOnTouchOutside(true);
            yukleniyorDialog.show();


            mYetki.createUserWithEmailAndPassword(email,sifre)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull final Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Intent girisSayfasi = new Intent(KayitActivity.this,LoginActivity.class);
                                startActivity(girisSayfasi);
                                Toast.makeText(KayitActivity.this, "Yeni Hesap Oluşturuldu", Toast.LENGTH_SHORT).show();

                                yukleniyorDialog.dismiss();


                            }
                            else{
                                String mesaj=task.getException().toString();
                                Toast.makeText(KayitActivity.this, "Bilgileri Kontrol Ediniz", Toast.LENGTH_SHORT).show();
                            yukleniyorDialog.dismiss();
                            }


                        }
                    });

        }

    }
}
