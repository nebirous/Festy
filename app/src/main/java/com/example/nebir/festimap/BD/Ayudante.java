package com.example.nebir.festimap.BD;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.nebir.festimap.R;

/**
 * Created by nebir on 23/05/2016.
 */
public class Ayudante extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "festimap.sqlite";
    public static final int DATABASE_VERSION= 2;

    public Ayudante (Context ctx){
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql="drop table if exists "+ Contrato.TablaFestival.TABLA;
        db.execSQL(sql);
        onCreate(db);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql;
        sql="create table "+Contrato.TablaFestival.TABLA+
                " ("+
                Contrato.TablaFestival._ID + " integer primary key autoincrement, "+
                Contrato.TablaFestival.NOMBRE+" text, "+
                Contrato.TablaFestival.DESCRIPCION +" text," +
                Contrato.TablaFestival.CIUDAD+" text, "+
                Contrato.TablaFestival.ACAMPADA+" text, "+
                Contrato.TablaFestival.LOGO+" text,"+
                Contrato.TablaFestival.INICIO+" date,"+
                Contrato.TablaFestival.FINAL+" date "+
                ")";

        db.execSQL(sql);

        ContentValues v = new ContentValues();
        v.put(Contrato.TablaFestival.NOMBRE, "Resurrection Fest");
        v.put(Contrato.TablaFestival.DESCRIPCION, "Festival de Hardcore y metal extremo con más de 11 años de antiguedad." +
                "En la edición de este año actuarán bandas como Iron Maiden, Slayer, Anthrax y Gojira, entre otras");
        v.put(Contrato.TablaFestival.CIUDAD, "Viveiro (Lugo)");
        v.put(Contrato.TablaFestival.ACAMPADA, R.drawable.resucamp);
        v.put(Contrato.TablaFestival.LOGO, R.drawable.resulog);
        v.put(Contrato.TablaFestival.INICIO, "5 de Agosto");
        v.put(Contrato.TablaFestival.FINAL, "9 de Agosto");

        System.out.println("wea"+v.toString());

        db.insert(Contrato.TablaFestival.TABLA, null, v);

        v = new ContentValues();
        v.put(Contrato.TablaFestival.NOMBRE, "Viña Rock");
        v.put(Contrato.TablaFestival.DESCRIPCION, "Festival de Rock con más de 13 años de antiguedad." +
                "En la edición de este año actuarán bandas como Camela, Los Chichos o SFDK");
        v.put(Contrato.TablaFestival.CIUDAD, "Villarobledo (Albacete)");
        v.put(Contrato.TablaFestival.ACAMPADA, R.drawable.vincamp);
        v.put(Contrato.TablaFestival.LOGO, R.drawable.vinlog);
        v.put(Contrato.TablaFestival.INICIO, "16 de Abril");
        v.put(Contrato.TablaFestival.FINAL, "20 de Abril");
        db.insert(Contrato.TablaFestival.TABLA, null, v);

        v = new ContentValues();
        v.put(Contrato.TablaFestival.NOMBRE, "Leyendas del Rock");
        v.put(Contrato.TablaFestival.DESCRIPCION, "Festival de metal con más de 20 años de antiguedad." +
                "En la edición de este año actuarán bandas como Iron Maiden, Slayer, Anthrax y Gojira, entre otras");
        v.put(Contrato.TablaFestival.CIUDAD, "Villena (Alicante)");
        v.put(Contrato.TablaFestival.ACAMPADA, R.drawable.leycamp);
        v.put(Contrato.TablaFestival.LOGO, R.drawable.leylog);
        v.put(Contrato.TablaFestival.INICIO, "11 de Julio");
        v.put(Contrato.TablaFestival.FINAL, "15 de Julio");

        db.insert(Contrato.TablaFestival.TABLA, null, v);
        
    }
}
