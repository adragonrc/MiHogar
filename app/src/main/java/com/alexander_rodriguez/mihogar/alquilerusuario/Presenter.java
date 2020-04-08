package com.alexander_rodriguez.mihogar.alquilerusuario;

import com.alexander_rodriguez.mihogar.Base.BasePresenter;
import com.alexander_rodriguez.mihogar.TableCursor;
import com.alexander_rodriguez.mihogar.UTILIDADES.TAlquiler;
import com.alexander_rodriguez.mihogar.UTILIDADES.TAlquilerUsuario;

import java.util.ArrayList;

public class Presenter extends BasePresenter<Interface.Vista> implements Interface.Presentador {
    private int dni;

    public Presenter(Interface.Vista view, int dni) {
        super(view);
        this.dni = dni;
    }

    @Override
    public void iniciarComandos(){
        view.mostrarRecycleView(getList());
    }

    private ArrayList<Item> getList(){
        ArrayList<Item> arrayList = new ArrayList<>();
       /* TableCursor tc = db.getAllAlquileres(TAlquiler.ID + ", " + TAlquiler.MOTIVO, TAlquilerUsuario.DNI, dni);
        for (int i = 0; i < tc.getCount(); i++) {
            String id = tc.getValue(i, TAlquiler.ID);
            String motivo = tc.getValue(i, TAlquiler.MOTIVO);
            arrayList.add(new Item(id, motivo));
        }*/
        return arrayList;
    }
}
