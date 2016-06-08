package com.example.nebir.festimap;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.nebir.festimap.POJO.Festival;
import com.example.nebir.festimap.POJO.GestorFestival;
import com.example.nebir.festimap.pin.ui.PlanMarkerActivity;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by nebir on 06/06/2016.
 */
public class Individual extends AppCompatActivity {

    private android.widget.ImageView imageView;
    private android.widget.TextView nombreMed, ciudadMed, descripcionMed, inicioMed, finMed;
    private ArrayList<Festival> festis;
    private GestorFestival gf;
    private int acampada, pos;
    private Intent i;
    private Bundle b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.festival);

        init();

    }

    public void init(){
        this.finMed = (TextView) findViewById(R.id.finMed);
        this.inicioMed = (TextView) findViewById(R.id.inicioMed);
        this.descripcionMed = (TextView) findViewById(R.id.descripcionMed);
        this.ciudadMed = (TextView) findViewById(R.id.ciudadMed);
        this.nombreMed = (TextView) findViewById(R.id.nombreMed);
        this.imageView = (ImageView) findViewById(R.id.imageView);

        i=getIntent();
        b = i.getExtras();

        int pos = b.getInt("position");
        String nombre = b.getString("nombre");
        int id = b.getInt("id");

        String condicion = "nombre='" + nombre + "' AND _id='"+id+"'";

        gf = new GestorFestival(this);

        festis = (ArrayList<Festival>) gf.select(condicion, null);


        for(Festival f: festis){
            nombreMed.setText(f.getNombre());
            descripcionMed.setText(f.getDescripcion());
            ciudadMed.setText(f.getCiudad());
            finMed.setText(f.getEnd());
            inicioMed.setText(f.getStart());

            Bitmap icon = BitmapFactory.decodeResource(this.getResources(), f.getLogo());
            imageView.setImageBitmap(icon);

            acampada = f.getAcampada();

        }

    }

    public void acampadaMap (View v){
        Intent ac = new Intent(this, PlanMarkerActivity.class);

        ac.putExtra("IMAGE_PATH", acampada);

        startActivity(ac);

    }



}
