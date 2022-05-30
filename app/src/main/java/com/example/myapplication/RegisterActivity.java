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

/**
 * The type Register activity.
 */
public class RegisterActivity extends AppCompatActivity
{//מחלקת מסך אשר אחרית על רישום משתמשים חדשים לאפליקציה
    /**
     * The User name.
     */
    EditText user_name;// תיבת טקסט בה היוזר יוצר שם משתמש
    /**
     * The Mail.
     */
    EditText mail;//תיבת טקסט בה היוזר מכניס מייל
    /**
     * The Password.
     */
    EditText password;//תיבת טקסט בה היוזר יוצר סיסמה
    /**
     * The Btn finish.
     */
    Button btnFinish;//כפתור סיום אחרי מילוי הפרטים
    private DatabaseReference myRefToUsers;//הפנייה לדאטה בייס בסיסי של יוזרים על מנת ליצור משתמש חדש
    private FirebaseAuth mAuth;//  ניהול יוזרים (הרשמה התחברות) על מנת ליצור משתמש חדש

    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_register);
            user_name=findViewById(R.id.etUserNameIRegister);
            mail=findViewById(R.id.etMailInRegister);
            password = findViewById(R.id.etPassInRegister);
            btnFinish = (Button)findViewById(R.id.btnRegister);
            myRefToUsers = FirebaseDatabase.getInstance().getReference("Users");
            mAuth = FirebaseAuth.getInstance();
    }

    /**
     * Giser.
     *
     * @param v the v
     */
    public void giser (View v){//הגבה לכפתור סיום אחרי מילוי הפרטים במידה והכל נכון ותיקני יווצר מתשמש חדש והוא יועבר למסך חשבון
                if (btnFinish == v&&password.getText().length()>=6) {//בדיקת שהנתנונים מתאימים
                    mAuth.createUserWithEmailAndPassword(mail.getText().toString(), password.getText().toString())
                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {//בדיקה ויצירה של משתמש חדש
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        //אם הכל עבד יוצר מתשמש חדש
                                        FirebaseUser currentUser = mAuth.getCurrentUser();
                                        User user = new User(user_name.getText().toString(),
                                                mail.getText().toString(),password.getText().toString());
                                        myRefToUsers.child(currentUser.getUid()).setValue(user);
                                        Intent intent = new Intent(RegisterActivity.this, PortfolioActivity.class);
                                        startActivity(intent);
                                    } else {
                                        //אם לא עבד
                                        Toast.makeText(RegisterActivity.this, "הרשמה נכשלה",
                                                Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });

                }
                else {//במידה והססימה לא 6 תווים
                Toast.makeText(RegisterActivity.this, "הרשמה נכשלה",
                        Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);

    }
    }
}