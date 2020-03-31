package com.alexander_rodriguez.mihogar.listalquileres;

import android.content.ContentValues;
import android.graphics.drawable.Drawable;

import com.alexander_rodriguez.mihogar.Base.BasePresenter;
import com.alexander_rodriguez.mihogar.R;
import com.alexander_rodriguez.mihogar.TableCursor;
import com.alexander_rodriguez.mihogar.UTILIDADES.TAlquiler;
import com.alexander_rodriguez.mihogar.UTILIDADES.TCuarto;
import com.alexander_rodriguez.mihogar.UTILIDADES.TUsuario;
import com.alexander_rodriguez.mihogar.Adapters.Models.ModelAlquilerView;

import java.util.ArrayList;

public class Presentador extends BasePresenter<Interface.Vista>{
    private String numeroCuarto;
    public Presentador(Interface.Vista view, String numeroCuarto) {
        super(view);
        this.numeroCuarto = numeroCuarto;
    }

    @Override
    public void iniciarComandos() {
        view.mostarListAlquileres(getListAlquileres());
    }
    private ArrayList<ModelAlquilerView> getListAlquileres(){
        ArrayList<ModelAlquilerView> list;
        String columnas = "*";
        TableCursor tcAlquileres = db.getAllAlquileres(columnas, TCuarto.NUMERO, numeroCuarto);
        list = new ArrayList<>();
        for (int i = 0; i < tcAlquileres.getCount(); i++) {
            ContentValues nombres = db.getFilaInUsuariosOf(TUsuario.NOMBRES+ ", " + TUsuario.APELLIDO_PAT, tcAlquileres.getValue(i, TAlquiler.DNI));
            Drawable drawable;
            if (tcAlquileres.getValue(i,TAlquiler.VAL).equals("0"))
            drawable = view.getContext().getResources().getDrawable(R.drawable.circle_blue);
            else drawable = view.getContext().getResources().getDrawable(R.drawable.circle_red);
            list.add(new ModelAlquilerView(tcAlquileres.getValue(i,TAlquiler.ID),
                    tcAlquileres.getValue(i, TUsuario.DNI),
                    nombres.getAsString(TUsuario.NOMBRES) + " " +nombres.getAsString(TUsuario.APELLIDO_PAT),
                    tcAlquileres.getValue(i, TAlquiler.FECHA_C),
                    tcAlquileres.getValue(i, TAlquiler.NUMERO_C), drawable));
        }
        return list;
    }
}
