package com.example.thomas.center.util;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.thomas.center.model.Shop;

import java.util.Calendar;
import java.util.Date;


/**
 * Created by thomas on 15/05/17.
 */
public class CalendarManager {

    private Context context;

    public CalendarManager(Context context) {
        this.context = context;
    }

    private void addCalendarEvent(final Shop shop) {
        Date date = shop.getDate();
        ContentResolver cr = context.getContentResolver();
        ContentValues values = new ContentValues();
        values.put(CalendarContract.Events.CALENDAR_ID, 1);
        values.put(CalendarContract.Events.TITLE, shop.getName());
        values.put(CalendarContract.Events.DESCRIPTION, shop.getType().getTypeString());
        values.put(CalendarContract.EXTRA_EVENT_ALL_DAY, true);
        values.put(CalendarContract.Events.DTSTART, date.getTime());
        values.put(CalendarContract.Events.DTEND, date.getTime());
        values.put(CalendarContract.Events.EVENT_TIMEZONE, CalendarContract.Calendars.CALENDAR_TIME_ZONE);
        cr.insert(CalendarContract.Events.CONTENT_URI, values);
        Toast toast =Toast.makeText(context.getApplicationContext(), "Evenement " + shop.getName() +" ajouté !",Toast.LENGTH_LONG);
        toast.show();
    }

    private void deleteCalendarEvent(Shop shop){
        int entryID = listSelectedCalendars(shop.getName());
        Uri eventUri = ContentUris
                .withAppendedId(getCalendarUriBase(), entryID);
        context.getContentResolver().delete(eventUri, null, null);
        Toast toast =Toast.makeText(context.getApplicationContext(), "Evenement " + shop.getName() +" supprimé !",Toast.LENGTH_LONG);
        toast.show();
    }

    private Uri getCalendarUriBase() {
        Uri eventUri;
        if (android.os.Build.VERSION.SDK_INT <= 7) {
            eventUri = Uri.parse("content://calendar/events");
        } else {
            eventUri = Uri.parse("content://com.android.calendar/events");
        }
        return eventUri;
    }

    public void interact(Shop shop){
        if(shop.isRegistered()){
            deleteCalendarEvent(shop);
        }else{
            addCalendarEvent(shop);
        }
    }

    public boolean isEventInCal(String shopName){
       return listSelectedCalendars(shopName) != 0;
    }

    private int listSelectedCalendars(String eventTitle) {
        Uri eventUri = getCalendarUriBase();
        int result = 0;
        String projection[] = { "_id", "title" };

        Cursor cursor = context.getContentResolver().query(eventUri, null, null, null,
                null);

        assert cursor != null;
        if (cursor.moveToFirst()) {

            String calName;
            String calID;

            int nameCol = cursor.getColumnIndex(projection[1]);
            int idCol = cursor.getColumnIndex(projection[0]);
            do {
                calName = cursor.getString(nameCol);
                calID = cursor.getString(idCol);

                if (calName != null && calName.contains(eventTitle)) {
                    result = Integer.parseInt(calID);
                }

            } while (cursor.moveToNext());
            cursor.close();
        }

        return result;

    }
}
