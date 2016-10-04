package com.example.tarun.thomso2k16;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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
    private static final String COLUMN_EventDay="EventDay";
    private static final String COLUMN_EventImage="EventImage";
    private static final String COLUMN_CoordinatorName="CoordinatorName";
    private static final String COLUMN_CoordinatorNumber="CoordinatorNumber";
    public DBhelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query= "CREATE TABLE " + TABLE_EVENTS + "( " +COLUMN_id+" INTEGER PRIMARY KEY AUTOINCREMENT,"+ COLUMN_EventName + " TEXT,"
                + COLUMN_EventDescription + " TEXT,"+ COLUMN_EventDate + " TEXT," + COLUMN_EventTime + " TEXT," + COLUMN_EventVenue + " TEXT,"
                + COLUMN_EventDay + " TEXT,"+COLUMN_EventImage +" TEXT,"+COLUMN_CoordinatorName+" TEXT,"+COLUMN_CoordinatorNumber+" TEXT "+ ");";
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
        values.put(COLUMN_EventDay,text.getEventDay());
        values.put(COLUMN_EventImage,text.getEventImage());
        values.put(COLUMN_CoordinatorName,text.getCoordinatorName());
        values.put(COLUMN_CoordinatorNumber,text.getCoordinatorNo());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_EVENTS, null, values);
        db.close();
    }
    public int getEventsCount(String query) {
        String countQuery = query;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        // return count
        return cursor.getCount();
    }
    public List<Events_pojo> showEvents(String query){
        List<Events_pojo> events=new ArrayList<Events_pojo>();
        String selectQuery = query;
        // "SELECT * FROM " + TABLE_EVENTS + " ;"
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
                event_detail.setEventImage(cursor.getString(6));
                event_detail.setCoordinatorName(cursor.getString(7));
                event_detail.setCoordinatorNo(cursor.getString(8));
                events.add(event_detail);
            }catch(Exception e){

            }
        }while(cursor.moveToNext());
        db.close();
        return events;
    }
    public Events_pojo getEventDetails(String EventName){
        String query = "SELECT * FROM "+TABLE_EVENTS+" WHERE "+COLUMN_EventName+" =\""+EventName+"\" ;";
        Log.e("query"," "+query);
        Events_pojo ep = new Events_pojo();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        Log.e("cursor",cursor.toString());
        if(cursor!=null){
            ep.setEventName(cursor.getString(1));
            ep.setEventDescription(cursor.getString(2));
            ep.setEventDate(cursor.getString(3));
            ep.setEventTime(cursor.getString(4));
            ep.setEventVenue(cursor.getString(5));
            ep.setEventDay(cursor.getString(6));
            ep.setEventImage(cursor.getString(7));
            ep.setCoordinatorName(cursor.getString(8));
            ep.setCoordinatorNo(cursor.getString(9));
        }
        return ep;
    }
}
