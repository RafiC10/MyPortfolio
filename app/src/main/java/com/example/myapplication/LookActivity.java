package com.example.myapplication;



import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
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

public class LookActivity extends AppCompatActivity implements View.OnClickListener {

    FirebaseDatabase database;
    DatabaseReference MyRefToUsers, myReflookStock, refToDelete;
    private FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    View.OnClickListener onItemClickListenerInLook;
    ArrayList<LookingStock> look = new ArrayList<>();
    RecyclerView recyclerViewOfLook;
    LookingStocksAdapter lookingStocksAdapter;
    Button btnCheckDataInLook, btnAddToPortfolioInLook;
    EditText nameOfStock;
    String keyOfLookStock;
    static String nameForService;
    static Double priceForService;
    Dialog dialogLook;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look);

        nameOfStock = findViewById(R.id.looktvTextNameOfStock);
        btnAddToPortfolioInLook = (Button) findViewById(R.id.lookbtnadd);
        btnAddToPortfolioInLook.setOnClickListener(this);
        btnCheckDataInLook = (Button) findViewById(R.id.lookbtncheck);
        btnCheckDataInLook.setOnClickListener(this);
        database = FirebaseDatabase.getInstance();
        MyRefToUsers = database.getReference("Users");
        mAuth = FirebaseAuth.getInstance();
        myReflookStock = database.getReference("ToLook");
        firebaseUser = mAuth.getCurrentUser();
        readFromDataStock();


        onItemClickListenerInLook = new View.OnClickListener() {
            public void onClick(View view) {
                RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
                int poisition = viewHolder.getAdapterPosition();
                LookingStock userItem = look.get(poisition);
                keyOfLookStock = userItem.getKey();
                createInfromationDialog(look, poisition);
                String ap = "https://financialmodelingprep.com/api/v3/quote-short/" + userItem.getName().toString() + "?apikey=d477f4211cca3f702244eaf9a9539b0d";
                DownLoadData t = new DownLoadData(LookActivity.this);
                t.execute(ap.toString());
            }
        };


        recyclerViewOfLook = findViewById(R.id.recyclestlook1);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewOfLook.setLayoutManager(layoutManager);
        lookingStocksAdapter = new LookingStocksAdapter(look);
        recyclerViewOfLook.setAdapter(lookingStocksAdapter);
        lookingStocksAdapter.setOnItemClickListener1(onItemClickListenerInLook);
    }
    protected void onStart() {
        super.onStart();
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        if (firebaseUser.getUid() != null) {
            MyRefToUsers.child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    User currentUser = dataSnapshot.getValue(User.class);
                }
                @Override
                public void onCancelled(DatabaseError error) {

                }
            });
        }
    }

//    private void readFromData() {
//        FirebaseAuth mAuth;
//        mAuth = FirebaseAuth.getInstance();
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        MyRefToUsers.child(currentUser.getUid()).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                User user = dataSnapshot.getValue(User.class);
//            }
//            @Override
//            public void onCancelled(DatabaseError error) {
//            }
//        });
//    }

    private void readFromDataStock() {
        FirebaseAuth mAuth;
         mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        myReflookStock.child(currentUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    LookingStock lookingStock = ds.getValue(LookingStock.class);
                    look.add(new LookingStock(lookingStock.getName(),lookingStock.getPriceNow(),lookingStock.getKey()));
                    recyclerViewOfLook.setAdapter(lookingStocksAdapter);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

        public boolean onCreateOptionsMenu (Menu menu){
            getMenuInflater().inflate(R.menu.menu1, menu);
            return true;
        }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuport) {
            startActivity(new Intent(LookActivity.this, PortActivity2.class));
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
    public void onClick(View view) {
        if (btnCheckDataInLook == view&&nameOfStock.getText().length()>0) {
            String ap = "https://financialmodelingprep.com/api/v3/quote-short/" + nameOfStock.getText().toString() + "?apikey=d477f4211cca3f702244eaf9a9539b0d";
            DownLoadData t = new DownLoadData(LookActivity.this);
            t.execute(ap.toString());
        }
        if (btnAddToPortfolioInLook == view && DownLoadData.EveryThingIsFine == true) {
            FirebaseUser currentUser = mAuth.getCurrentUser();
            myReflookStock = database.getReference("ToLook").child(currentUser.getUid()).push();
            LookingStock lookingStock = new LookingStock(nameOfStock.getText().toString(), DownLoadData.TheRealPrice, myReflookStock.getKey());
            priceForService = DownLoadData.TheRealPrice;
            nameForService = nameOfStock.getText().toString();
            myReflookStock.setValue(lookingStock);
            startService(new Intent(this,MyService.class));
            Intent intent = new Intent(LookActivity.this, LookActivity.class);
            startActivity(intent);
        }
        if (btnAddToPortfolioInLook == view && DownLoadData.EveryThingIsFine != true) {
            Toast.makeText(LookActivity.this, "אתה צריך ללחוץ על בדיקת נתונים", Toast.LENGTH_LONG).show();

        }

    }

//    class DownLoadText extends AsyncTask<String, Integer, String> {
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//        }
//        @Override
//        protected String doInBackground(String... params) {
//            // TODO Auto-generated method stub
//            String line = "";
//            HttpURLConnection urlConnection = null;
//            URL url = null;
//            try {
//                URL myURL = new URL(params[0]);
//                URLConnection ucon = myURL.openConnection();
//                InputStream in = ucon.getInputStream();
//                byte[] buffer = new byte[4096];
//                in.read(buffer);
//                line = new String(buffer);
//            } catch (Exception e) {
//                line = e.getMessage();
//            }
//            return line;
//        }
//
//        @Override
//        protected void onProgressUpdate(Integer... values) {
//            // TODO Auto-generated method stub
//            super.onProgressUpdate(values);
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            // TODO Auto-generated method stub
//            super.onPostExecute(result);
//            String s = result;
//            String[]parts=s.split("volume");
//            s=parts[0];
//            s = s.replaceAll("[^\\d.]", "");
//            if (!s.isEmpty()&&Double.valueOf(s)!=0.34774){
//                TheRealPrice = Double.valueOf(s);
//                EveryThingIsFine =true;
//                Toast.makeText(LookActivity.this, "עכשיו אתה יכול ללחוץ על הוספה/עדכן" , Toast.LENGTH_LONG).show();
//            }
//            else {
//                Toast.makeText(LookActivity.this, "שם מנייה לא נכון", Toast.LENGTH_LONG).show();
//            }
//        }
//
//    }
    public void createInfromationDialog(ArrayList<LookingStock> look, int poisition) {
        LookingStock userItem = look.get(poisition);
        keyOfLookStock = userItem.getKey();
        dialogLook = new Dialog(this);
        dialogLook.setContentView(R.layout.dialog_look);
        Button btnUpdate = dialogLook.findViewById(R.id.buttonUpdateInDialogLook);
        Button btnDelete = dialogLook.findViewById(R.id.buttonDeleteInDialogLook);
        TextView whatToDo = dialogLook.findViewById(R.id.tvWhatToDoWithStock);
        String nameOfLook= userItem.getName();
        whatToDo.setText( "מה תרצה לעשות עם מניית - "+nameOfLook);
        dialogLook.setCancelable(true);
        dialogLook.show();
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (btnDelete==v){
                  deleteansupdae1();
                    startActivity(new Intent(LookActivity.this, LookActivity.class));

                }
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (btnUpdate==v&& DownLoadData.EveryThingIsFine ==true){
                    refToDelete = FirebaseDatabase.getInstance().getReference("ToLook").child(firebaseUser.getUid()).child(String.valueOf(keyOfLookStock));
                    refToDelete.removeValue();
                    FirebaseUser currentUser = mAuth.getCurrentUser();
                    myReflookStock = database.getReference("ToLook").child(currentUser.getUid()).push();
                    LookingStock lookingStock = new LookingStock(nameOfLook.toString(),DownLoadData.TheRealPrice, myReflookStock.getKey());
                    myReflookStock.setValue(lookingStock);
                    startActivity(new Intent(LookActivity.this, LookActivity.class));
                    Toast.makeText(LookActivity.this, "עודכן בהצלחה!", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    private void deleteansupdae1() {

        refToDelete = FirebaseDatabase.getInstance().getReference("ToLook").child(firebaseUser.getUid()).child(String.valueOf(keyOfLookStock));
        refToDelete.removeValue();
        Toast.makeText(LookActivity.this, "נמחק בהצלחה!", Toast.LENGTH_SHORT).show();
    }


}
