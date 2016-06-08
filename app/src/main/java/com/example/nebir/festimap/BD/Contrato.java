package com.example.nebir.festimap.BD;

import android.provider.BaseColumns;

import java.util.Date;

/**
 * Created by nebir on 23/05/2016.
 */
public class Contrato {

    private Contrato (){
    }

    public static abstract class TablaFestival implements BaseColumns {
        public static final String TABLA = "festival";
        //public static final String ID = "id";
        public static final String NOMBRE = "nombre";
        public static final String DESCRIPCION = "descripcion";
        public static final String CIUDAD= "ciudad";
        public static final String ACAMPADA= "acampada";
        public static final String LOGO= "logo";
        public static final String INICIO= "inicio";
        public static final String FINAL= "final";

    }



}
