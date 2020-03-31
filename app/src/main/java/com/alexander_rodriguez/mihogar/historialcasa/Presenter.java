package com.alexander_rodriguez.mihogar.historialcasa;

import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.drawable.Drawable;

import com.alexander_rodriguez.mihogar.Adapters.Models.ModelCuartoView;
import com.alexander_rodriguez.mihogar.Base.BasePresenter;
import com.alexander_rodriguez.mihogar.MyAdminDate;
import com.alexander_rodriguez.mihogar.R;
import com.alexander_rodriguez.mihogar.TableCursor;
import com.alexander_rodriguez.mihogar.UTILIDADES.TAlquiler;
import com.alexander_rodriguez.mihogar.UTILIDADES.TUsuario;
import com.alexander_rodriguez.mihogar.Adapters.Models.ModelAlquilerView;
import com.alexander_rodriguez.mihogar.Adapters.Models.ModelUserView;

import java.util.ArrayList;

public class Presenter extends BasePresenter<Interface.View> implements Interface.Presenter {
    private MyAdminDate myAdminDate;
    public Presenter(Interface.View view) {
        super(view);
    }

    @Override
    public void iniciarComandos(){
        myAdminDate = new MyAdminDate();
        mostrarUsuarios();
    }

    @Override
    public void mostrarUsuarios() {
        view.mostarListUsuarios(getListUsuarios());
    }
    private ArrayList<ModelUserView> getListUsuarios(){
        String columnas = "*";
        Cursor c = db.getAllUsuariosADDAlert(columnas);
        return ModelUserView.createListModel(c);

    }

    @Override
    public void mostrarAlquileres() {
        view.mostarListAlquileres(getListAlquileres());
    }

    private ArrayList<ModelAlquilerView> getListAlquileres(){
        ArrayList<ModelAlquilerView> list= new ArrayList<>();
        String columnas = "*";
        TableCursor tcAlquileres = db.getAllAlquileres(columnas);
        for (int i = 0; i < tcAlquileres.getCount(); i++) {
            ContentValues nombres = db.getFilaInUsuariosOf(TUsuario.NOMBRES+ ", " + TUsuario.APELLIDO_PAT, tcAlquileres.getValue(i,TAlquiler.DNI));

            String fechac = tcAlquileres.getValue(i, TAlquiler.FECHA_C);
            String id =tcAlquileres.getValue(i,TAlquiler.ID);
            String dni = tcAlquileres.getValue(i, TUsuario.DNI);
            String noombres = nombres.getAsString(TUsuario.NOMBRES) + " " +nombres.getAsString(TUsuario.APELLIDO_PAT);
            String numeroCuarto = tcAlquileres.getValue(i, TAlquiler.NUMERO_C);
            Drawable drawable = view.getContext().getResources().getDrawable(R.drawable.circle_blue);

            list.add(new ModelAlquilerView(id, dni, noombres, fechac,numeroCuarto, drawable));
        }
        return list;
    }
}
