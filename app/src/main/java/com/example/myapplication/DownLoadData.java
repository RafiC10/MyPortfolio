package com.example.myapplication;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;


import java.net.HttpURLConnection;

/**
 * The type Down load data.
 */
public class DownLoadData extends AsyncTask<String, Integer, String>
{//מחלקה אשר תפקידה הוא לקבל מאתר אינטרנט מידע גבי מנייה אשר מופיעה בקישור דרכו מועפלת הפנקוציה ,
    /**
     * The constant TheRealPrice.
     */
// הפעולה תווודאי שאכן יש מנייה כזאת ותעדכן את מחיר המנייה ותשנה את הערך הבוולאיני של האימות לאמת
        static Double TheRealPrice = -0.2 ;//מחיר המנייה בסטטי על מנת שמחקלות אחרות יוכלו לקבלו
    /**
     * The Every thing is fine.
     */
    static Boolean EveryThingIsFine =false;//ערך בולייאני שמוודא שאכן נלחץ כפתור "בידקת נתונים" ואכן יש מנייה
    /**
     * The Context.
     */
    Context context;

    /**
     * Instantiates a new Down load data.
     *
     * @param context the context
     */
    public DownLoadData(Context context) {//באני
                this.context= context;
        }

        @Override
protected void onPreExecute() {
        super.onPreExecute();
        }

@Override
protected String doInBackground(String... params) {//פעולה השולחת בקשה לאתר האינטרנט ומקבלת בחזרה את התוכן המבוקש בתור String
        String line = "";
        HttpURLConnection urlConnection = null;
        URL url = null;
        try {
        URL myURL = new URL(params[0]);
        URLConnection ucon = myURL.openConnection();
        InputStream in = ucon.getInputStream();
        byte[] buffer = new byte[4096];
        in.read(buffer);
        line = new String(buffer);
        } catch (Exception e) {
        line = e.getMessage();
        }
        return line;
        }

@Override
protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        }

@Override
protected void onPostExecute(String result) {
                //פעולה אשר מקבלת את המידע וחותכת רק את מה שהיא צריכה ממנו (מחיר המנייה)
        //החיתוך עצמו מתבע לפי לקיחת החלק הראשון עד volume ואז מחיקת כל מה שלא מספר ואז בדיקה שאכן נשאר משהו ושלא המשתמש הכניס שם לא הגיוני במידה וכן יקבל על המשתמש התראה
        //אם הכל נכון מחיר המנייה יתעדכן וגם הערך הבולייאני והמתמש יקבל התראה שהוא יכול לעדכן/להוסיף את המנייה
        super.onPostExecute(result);
        String EditResult = result;
        String[] parts = EditResult.split("volume");
        EditResult = parts[0];
        EditResult = EditResult.replaceAll("[^\\d.]", "");
        if (!EditResult.isEmpty()) {
                // אם  הכל נכון ויש מנייה המחיר יתעדכן במשתנה הסטטי למחיר המנייה והערך הבולאיני יתשנה לאמת
        TheRealPrice = Double.valueOf(EditResult);
        EveryThingIsFine = true;

        Toast.makeText(context,"עכשיו אתה יכול ללחוץ על הוספה/עדכן", Toast.LENGTH_LONG).show();
        } else {
                //במידה ושם המנייה לא נכון והתקבלה שיגאה יקבל על כך היוזר התראה
        Toast.makeText(context, "שם מנייה לא נכון", Toast.LENGTH_LONG).show();
        }
        }
        }