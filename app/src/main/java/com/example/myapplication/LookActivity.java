package com.example.myapplication;



import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class LookActivity extends AppCompatActivity implements View.OnClickListener , FirebaseCallback{
//מחלקת מסך אשר אחראית על הצגת המניות לצפייהה מעין שילוב של מחלקותPortfolioActivity ן AddActivity בדף זה ניתן גם לראות גם להוסיף וגם לערוך את המניות לצפייה
    View.OnClickListener onItemClickListenerInLook;//ליסינר ללחיצה
    ArrayList<LookingStock> look = new ArrayList<>();//יArrayList שבו נשמרים המניות לצפיה של המשתמש
    RecyclerView recyclerViewOfLook;//יRecyclerView שבו יוצגו המניות לצפייה
    LookingStocksAdapter lookingStocksAdapter;//יAdapter לRecyclerView
    Button btnCheckDataInLook, //כפתור בדיקת נתונים
            btnAddToPortfolioInLook;//כפתור הוספת מנייה
    EditText nameOfStock;//תיבת טקסט בה כותב המשתמש את המנייה אותו רוצה להוסיף
    String keyOfLookStock;//סטרינג של מנייה של מנייה שאני לוחץ עליה על מנת שיהיה אפשר למחוק אותה
    static String nameForService;//שם מנייה סטטי שנשלח ל Service
    static Double priceForService;//מחיר מנייה סטטי שנשלח ל Service
    Dialog dialogLook;//הדיאלוג של המידע של המנייה
    FirebaseController firebaseController;//הפנייה לfirebaseController על מנת לייבא ולייצא את הנתונים להדאטהבייס

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look);
        nameOfStock = findViewById(R.id.looktvTextNameOfStock);
        btnAddToPortfolioInLook = (Button) findViewById(R.id.lookbtnadd);
        btnAddToPortfolioInLook.setOnClickListener(this);
        btnCheckDataInLook = (Button) findViewById(R.id.lookbtncheck);
        btnCheckDataInLook.setOnClickListener(this);
        firebaseController = new FirebaseController(this);
        firebaseController.readDataLook(this,look);

        onItemClickListenerInLook = new View.OnClickListener() {
            public void onClick(View view) {//תגובה ללחיצה על מנייה ב  כתוצאה מכך יפתח הדיאלוג עם השם של המנייה ושיאל את המשתמש מה לעשות עם המנייה וישלח בקשה לקבלת מחיר המנייה הנוכחית מכיוון שלא ניתן לשנות את שם המנייה
                RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
                int poisition = viewHolder.getAdapterPosition();
                LookingStock userItem = look.get(poisition);
                keyOfLookStock = userItem.getKey();
                createInfromationDialog(look, poisition);
                String ap = "https://financialmodelingprep.com/api/v3/quote-short/" + nameOfStock.getText().toString() + "?apikey=d477f4211cca3f702244eaf9a9539b0d";
                DownLoadData t = new DownLoadData(LookActivity.this);
                t.execute(ap.toString());
            }
        };
    }
     public void onCallbackLook(ArrayList <LookingStock> look) {//פעולה אשר בונה את ה recyclerViewOfLook באמצעות קבלה של ArrayList<LookinStock> אחרי הוספתם ב firebaseController בפעולת readDataLook
       //  הפעולה בונה את ה recyclerViewOfLook באמצעות ה LookingStocksAdapter
         recyclerViewOfLook = findViewById(R.id.recyclestlook1);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewOfLook.setLayoutManager(layoutManager);
        lookingStocksAdapter = new LookingStocksAdapter(look);
        recyclerViewOfLook.setAdapter(lookingStocksAdapter);
        lookingStocksAdapter.setOnItemClickListener1(onItemClickListenerInLook);
     }
    @Override
    public void onCallbackInvest(ArrayList<InvestStock> invest) {//פעולה חסרת משמעות שלא עושה כלום,נמצאת כי חייבים לייבא אותה כש implements FirebaseCallback
    }
        public boolean onCreateOptionsMenu (Menu menu){//ייבוא ה menu
            getMenuInflater().inflate(R.menu.menu1, menu);
            return true;
        }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {//מעבר מסכים לפי ה menu
        if (item.getItemId() == R.id.menuport) {
            startActivity(new Intent(LookActivity.this, PortfolioActivity.class));
        }
        if (item.getItemId() == R.id.menustatics) {
            startActivity(new Intent(LookActivity.this, StatisticsActivity.class));
        }
        if (item.getItemId() == R.id.menuadd) {
            startActivity(new Intent(LookActivity.this, AddActivity.class));
        }
        return true;
    }
    @Override
    public void onClick(View view) {//תגובה ללחציה על כל אחד מהכפתורים (בידקה,הוספה) אם
        if (btnCheckDataInLook == view&&nameOfStock.getText().length()>0) { //תגובה ללחיצה אם נלחץ בדיקת נתונים אז הוא שולח בקשה לקבל מחיר המנייה
            // אם הכל עובד טוב היוזר יקבל התראה על כך שהוא יכול ללחוץ על הוספה
            // במידה ולא היוזר יקבל התראה על כך שיש בעיה בנתונים
            String ap = "https://financialmodelingprep.com/api/v3/quote-short/" + nameOfStock.getText().toString() + "?apikey=d477f4211cca3f702244eaf9a9539b0d";
            DownLoadData t = new DownLoadData(LookActivity.this);
            t.execute(ap.toString());
            nameForService = nameOfStock.getText().toString();
        }
        if (btnAddToPortfolioInLook == view && DownLoadData.EveryThingIsFine == true) {//יצירת מנייה חדשה במידה והכל תקין
            FirebaseController.addToDatabaseToLook(nameForService);
            priceForService = DownLoadData.TheRealPrice;
            startService(new Intent(this,MyService.class));
            startActivity(new Intent(LookActivity.this, LookActivity.class));

        }
        if (btnAddToPortfolioInLook == view && DownLoadData.EveryThingIsFine != true) {//התראה במידה ולא לחץ על בדיקת נתונים
            Toast.makeText(LookActivity.this, "אתה צריך ללחוץ על בדיקת נתונים", Toast.LENGTH_LONG).show();
        }
    }
    public void createInfromationDialog(ArrayList<LookingStock> look, int poisition) {//יצירת דיאלוג שבו היתן לעדכן את מחיר המנייה ולמחוק אותה
        LookingStock userItem = look.get(poisition);
        keyOfLookStock = userItem.getKey();
        dialogLook = new Dialog(this);
        dialogLook.setContentView(R.layout.dialog_look);
        Button btnUpdate = dialogLook.findViewById(R.id.buttonUpdateInDialogLook);//כפתור עידכון
        Button btnDelete = dialogLook.findViewById(R.id.buttonDeleteInDialogLook);//כפתור מחיקה
        TextView whatToDo = dialogLook.findViewById(R.id.tvWhatToDoWithStock);//הצגת שם המנייה למשתמש
        whatToDo.setText( "מה תרצה לעשות עם מניית - "+ userItem.getName());
        dialogLook.setCancelable(true);
        dialogLook.show();
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (btnDelete==v){//כפתןר אשר שלוח בקשה למחיקת המנייה
                    FirebaseController.deleteLookStock(keyOfLookStock);
                    startActivity(new Intent(LookActivity.this, LookActivity.class));

                }
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//עדכון מחיר המנייה על ידי מחיקת ויצירת מחדש עם המחיר נכון
                if (btnUpdate==v&& DownLoadData.EveryThingIsFine ==true){
                    FirebaseController.deleteLookStock(keyOfLookStock);
                    FirebaseController.addToDatabaseToLook(userItem.getName().toString());
                    startActivity(new Intent(LookActivity.this, LookActivity.class));
                }
            }
        });
    }
}
