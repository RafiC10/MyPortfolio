package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity implements View.OnContextClickListener {

    Button btnFinish;
    EditText user_name;
    EditText mail;
    EditText password;
    private FirebaseDatabase database;
    private DatabaseReference myRefToUsers;
    private FirebaseAuth mAuth;

    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_register);
            user_name=findViewById(R.id.etUserNameIRegister);
            mail=findViewById(R.id.etMailInRegister);
            password = findViewById(R.id.etPassInRegister);
            btnFinish = (Button)findViewById(R.id.btnRegister);
            btnFinish.setOnContextClickListener(this);
            database = FirebaseDatabase.getInstance();
            myRefToUsers = database.getReference("Users");
            mAuth = FirebaseAuth.getInstance();

    }

            public void giser (View v){
                if (btnFinish == v&&password.getText().length()>=6) {
                    mAuth.createUserWithEmailAndPassword(mail.getText().toString(), password.getText().toString())
                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        //
                                        FirebaseUser currentUser = mAuth.getCurrentUser();
                                        User user = new User(user_name.getText().toString(),
                                                mail.getText().toString());
                                        myRefToUsers.child(currentUser.getUid()).setValue(user);
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(RegisterActivity.this, "הרשמה נכשלה",
                                                Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                    Intent intent = new Intent(this, PortActivity2.class);
                    startActivity(intent);

                }
                else {
                Toast.makeText(RegisterActivity.this, "הרשמה נכשלה",
                        Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);

    }
    }
        public boolean onContextClick(View view) {
            return false;
    }
}