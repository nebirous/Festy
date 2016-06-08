package com.example.nebir.festimap.Otros;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nebir.festimap.POJO.Festival;
import com.example.nebir.festimap.R;

import java.util.List;

/**
 * Created by nebir on 23/05/2016.
 */
public class Adaptador extends ArrayAdapter<Festival>{

    private List<Festival> lista;
    private Context contexto;
    private int res;
    private LayoutInflater i;

    public Adaptador(Context context, int resource, List<Festival> lista) {
        super(context, resource);
        this.contexto = context;
        this.res = resource;
        this.lista = lista;
        i = (LayoutInflater)contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    static class GuardarLista{
        public TextView tv1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        GuardarLista gv = new GuardarLista();

        if(convertView==null){
            convertView = i.inflate(res, null);
            TextView tv = (TextView)convertView.findViewById(R.id.textView);
            gv.tv1= tv;
            convertView.setTag(gv);
        }else gv = (GuardarLista) convertView.getTag();


        gv.tv1.setText("" + lista.get(position).getNombre());

        return convertView;
    }

    @Override
    public int getCount() {
        return lista.size();
    }
}
