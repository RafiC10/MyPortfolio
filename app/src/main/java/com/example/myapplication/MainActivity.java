package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements View.OnContextClickListener {
    EditText EtMailInMain;
    EditText EtPassworldInMain;
    Button btnConInMain;
    Button btnDontInMain;
    //private FirebaseDatabase database;
    //private DatabaseReference MyRefToUsers;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EtPassworldInMain = findViewById(R.id.etPas);
        EtMailInMain = findViewById(R.id.etName);
        btnConInMain = (Button) findViewById(R.id.btnOk);
        btnDontInMain = (Button) findViewById(R.id.btnd);
        btnConInMain.setOnContextClickListener(this);
        mAuth = FirebaseAuth.getInstance();
      //  database = FirebaseDatabase.getInstance();
        //MyRefToUsers = database.getReference("Users");
    }

    public void ON(View v) {
        if (btnConInMain == v&& EtPassworldInMain.getText().toString().length()>=6) {
            mAuth.signInWithEmailAndPassword(EtMailInMain.getText().toString(), EtPassworldInMain.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(MainActivity.this, PortActivity2.class);
                                startActivity(intent);
                                FirebaseUser user = mAuth.getCurrentUser();
                            } else {
                                YoYo.with(Techniques.Tada)
                                        .duration(700)
                                        .repeat(2)
                                        .playOn(findViewById(R.id.etName));
                                YoYo.with(Techniques.Tada)
                                        .duration(700)
                                        .repeat(2)
                                        .playOn(findViewById(R.id.etPas));

                                Toast.makeText(MainActivity.this, "ההתחברות נכשלה",
                                        Toast.LENGTH_SHORT).show();

                            }
                        }
                    });

            }
        else {
            Toast.makeText(MainActivity.this, "ההתחברות נכשלה",
                    Toast.LENGTH_SHORT).show();
            YoYo.with(Techniques.Tada)
                    .duration(700)
                    .repeat(2)
                    .playOn(findViewById(R.id.etName));
            YoYo.with(Techniques.Tada)
                    .duration(700)
                    .repeat(2)
                    .playOn(findViewById(R.id.etPas));
        }
    }


    public void ON1(View v) {
        if (btnDontInMain == v) {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);

        }
    }

    @Override
    public boolean onContextClick(View view) {
        return false;
    }
}