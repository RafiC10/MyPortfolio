package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * The type Main activity.
 */
/*מחלקת מסך אשר דרכה היוזר מתחבר לחשבונו או לחילופין עובר למסך הרישום במידה ואין לו חשבון*/
public class MainActivity extends AppCompatActivity
{//מחלקת מסך אשר דרכה היוזר מתחבר לחשבונו או לחילופין עובר למסך הרישום במידה ואין לו חשבון
    /**
     * The Et mail in main.
     */
    EditText EtMailInMain;//תיבת טקסט בה היוזר מכניס מייל
    /**
     * The Et passworld in main.
     */
    EditText EtPassworldInMain;//תיבת טקסט בה היוזר מכניס סיסמה
    /**
     * The Btn con in main.
     */
    Button btnConInMain;//כפתור על מנת להמשיך אחרי מיולי הפרטים
    /**
     * The Btn dont in main.
     */
    Button btnDontInMain;//בפתור לרישום במידה ואין חשבון מעביר ל register activity
    /**
     * The Image view 1.
     */
    ImageView imageView1, /**
 * The Image view 2.
 */
imageView2, /**
 * The Image view 3.
 */
imageView3, /**
 * The Image view 4.
 */
imageView4, /**
 * The Image view 5.
 */
imageView5, /**
 * The Image view 6.
 */
imageView6, /**
 * The Image view 7.
 */
imageView7, /**
 * The Image view 8.
 */
imageView8, /**
 * The Image view 9.
 */
imageView9, /**
 * The Image view 10.
 */
imageView10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView1 = findViewById(R.id.gif1);
        imageView2 = findViewById(R.id.gif2);
        imageView3 = findViewById(R.id.gif3);
        imageView4 = findViewById(R.id.gif4);
        imageView5 = findViewById(R.id.gif5);
        imageView6 = findViewById(R.id.gif6);
        imageView7 = findViewById(R.id.gif7);
        imageView8 = findViewById(R.id.gif8);
        imageView9 = findViewById(R.id.gif9);
        imageView10 = findViewById(R.id.gif10);
        Glide.with(this).load(R.drawable.graphgif3).into(imageView1);
        Glide.with(this).load(R.drawable.graphgif3).into(imageView2);
        Glide.with(this).load(R.drawable.graphgif3).into(imageView3);
        Glide.with(this).load(R.drawable.graphgif3).into(imageView4);
        Glide.with(this).load(R.drawable.graphgif3).into(imageView5);
        Glide.with(this).load(R.drawable.graphgif3).into(imageView6);
        Glide.with(this).load(R.drawable.graphgif3).into(imageView7);
        Glide.with(this).load(R.drawable.graphgif3).into(imageView8);
        Glide.with(this).load(R.drawable.graphgif3).into(imageView9);
        Glide.with(this).load(R.drawable.graphgif3).into(imageView10);
        EtPassworldInMain = findViewById(R.id.etPas);
        EtMailInMain = findViewById(R.id.etName);
        btnConInMain = (Button) findViewById(R.id.btnOk);
        btnDontInMain = (Button) findViewById(R.id.btnd);
    }

    /**
     * On.
     *
     * @param v the v
     */
    public void ON(View v) {//תגובה ללחיצה על כפתור המשך במידה וכל נכון יועבר למסך החשבון אם לא יקבל על כך התראה
        if (btnConInMain == v&& EtPassworldInMain.getText().toString().length()>=6) {//לפני שאני בודק אם הנתונים בכלל נכונים בודק שהם הגיונים
            FirebaseAuth.getInstance().signInWithEmailAndPassword(EtMailInMain.getText().toString(), EtPassworldInMain.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {//אם המייל והסיסמה מתאימים נכונים יועבר ל port activity
                                Intent intent = new Intent(MainActivity.this, PortfolioActivity.class);
                                startActivity(intent);
                            } else {//אנימיציה במידה וההתחברות נכשלה
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
        else {//אנימיצה במידה וההתחברות נכשלת
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


    /**
     * On 1.
     *
     * @param v the v
     */
    public void ON1(View v) {// תגובה ללחציה במידה ולחץ על כפתור אין חשבון יועבר למסך רישום
        if (btnDontInMain == v) {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);//מעביר register activity

        }
    }
}