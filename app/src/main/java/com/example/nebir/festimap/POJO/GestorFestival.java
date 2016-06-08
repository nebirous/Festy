package com.example.nebir.festimap.POJO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.nebir.festimap.BD.Ayudante;
import com.example.nebir.festimap.BD.Contrato;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nebir on 06/06/2016.
 */
public class GestorFestival {

    private Ayudante adb;
    private SQLiteDatabase db;

    public GestorFestival (Context c){
        adb = new Ayudante(c);
        db = adb.getWritableDatabase();
    }

    public void open(){

    }
    public void openRead(){
        db = adb.getReadableDatabase();
    }
    public void close(){
        adb.close();
    }

    public Festival getRow(Cursor c){
        Festival f = new Festival();
        f.setId(c.getInt(c.getColumnIndex(Contrato.TablaFestival._ID)));
        f.setNombre(c.getString(c.getColumnIndex(Contrato.TablaFestival.NOMBRE)));
        f.setDescripcion(c.getString(c.getColumnIndex(Contrato.TablaFestival.DESCRIPCION)));
        f.setCiudad(c.getString(c.getColumnIndex(Contrato.TablaFestival.CIUDAD)));
        f.setEnd(c.getString(c.getColumnIndex(Contrato.TablaFestival.FINAL)));
        f.setStart(c.getString(c.getColumnIndex(Contrato.TablaFestival.INICIO)));
        f.setLogo(c.getInt(c.getColumnIndex(Contrato.TablaFestival.LOGO)));
        f.setAcampada(c.getInt(c.getColumnIndex(Contrato.TablaFestival.ACAMPADA)));
        return f;
    }
    public Festival getRow(int id){
        Cursor c = getCursor("_id = ?", new String[]{id+""});
        return getRow(c);
    }
    public Cursor getCursor(){return getCursor(null, null);}
    public Cursor getCursor(String condicion, String[] parametros){
        Cursor cursor = db.query(
                Contrato.TablaFestival.TABLA, null, condicion, parametros, null,
                null, Contrato.TablaFestival.NOMBRE+", "+Contrato.TablaFestival.DESCRIPCION
        );
        return cursor;
    }
    public List<Festival> select(String condicion, String[] parametros){
        List<Festival> la = new ArrayList<>();
        Cursor cursor = getCursor(condicion, parametros);
        Festival r;
        while (cursor.moveToNext()){
            r = getRow(cursor);
            la.add(r);
        }
        cursor.close();
        return la;
    }
    public List<Festival> select(){ return select(null, null);}

}
