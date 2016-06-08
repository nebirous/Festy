package com.example.nebir.festimap;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.nebir.festimap.Otros.Adaptador;
import com.example.nebir.festimap.POJO.Festival;
import com.example.nebir.festimap.POJO.GestorFestival;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private GestorFestival gf;
    private Adaptador adr;
    private ListView lv;
    private Cursor c;
    private List<Festival> festis = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gf = new GestorFestival(this);
        lv = (ListView) findViewById(R.id.listView);
        init();

    }

    private void init(){
        generarAdaptador();
        final Context ctx = this;
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(ctx, Individual.class);
                Bundle b = new Bundle();
                b.putInt("Posicion", position);
                b.putString("nombre", festis.get(position).getNombre());
                b.putInt("id", festis.get(position).getId());
                i.putExtras(b);
                startActivity(i);
            }
        });

        registerForContextMenu(lv);
    }

    public void generarAdaptador(){
        festis = gf.select();
        adr = new Adaptador(this, R.layout.item, festis);
        lv.setAdapter(adr);
    }

}
