package com.example.tarun.thomso2k16;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tarun on 05-08-2016.
 */
public class DBhelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NAME="THOMSO.db";
    private static final String TABLE_EVENTS = "EVENT_DETAILS";
    private static final String COLUMN_id="_id";
    private static final String COLUMN_EventName="EventName";
    private static final String COLUMN_EventDescription="EventDescription";
    private static final String COLUMN_EventTime="EventTime";
    private static final String COLUMN_EventDate="EventDate";
    private static final String COLUMN_EventVenue="EventVenue";
    public DBhelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query= "CREATE TABLE " + TABLE_EVENTS + "( " +COLUMN_id+" INTEGER PRIMARY KEY AUTOINCREMENT,"+ COLUMN_EventName + " TEXT,"
                + COLUMN_EventDescription + " TEXT,"+ COLUMN_EventDate + " TEXT," + COLUMN_EventTime + " TEXT," + COLUMN_EventVenue + " TEXT "
                + ");";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_EVENTS);
        onCreate(db);
    }
    public void getInput(Events_pojo text){
        ContentValues values = new ContentValues();
        values.put (COLUMN_EventName,text.getEventName());
        values.put(COLUMN_EventDescription,text.getEventDescription());
        values.put (COLUMN_EventDate,text.getEventDate());
        values.put (COLUMN_EventTime,text.getEventTime());
        values.put (COLUMN_EventVenue,text.getEventVenue());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_EVENTS, null, values);
        db.close();
    }
    public int getEventsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_EVENTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        // return count
        return cursor.getCount();
    }
    public List<Events_pojo> showEvents(){
        List<Events_pojo> events=new ArrayList<Events_pojo>();
        String selectQuery = "SELECT * FROM " + TABLE_EVENTS + " ;";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        do{
            try {
                Events_pojo event_detail = new Events_pojo();
                event_detail.setEventName(cursor.getString(1));
                event_detail.setEventDescription(cursor.getString(2));
                event_detail.setEventDate(cursor.getString(3));
                event_detail.setEventTime(cursor.getString(4));
                event_detail.setEventVenue(cursor.getString(5));
                events.add(event_detail);
            }catch(Exception e){

            }
        }while(cursor.moveToNext());
        db.close();
        return events;
    }
}
