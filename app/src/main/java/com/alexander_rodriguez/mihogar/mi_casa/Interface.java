package com.alexander_rodriguez.mihogar.mi_casa;

import com.alexander_rodriguez.mihogar.Base.BaseView;
import com.alexander_rodriguez.mihogar.Base.IBasePresenter;
import com.alexander_rodriguez.mihogar.Adapters.Models.ModelCuartoView;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;

public interface Interface{
    interface Presenter extends IBasePresenter {
        void terminarAlquiler(String motivo, String id);
        void verTodos();
        void verCuartosAlquilados();
        void verCuartosLibres();
        void ordenarPorFecha();

        ArrayList<ModelCuartoView> getList();
    }

    interface View extends BaseView {
        void mostratCuartos(ArrayList<ModelCuartoView> list);
        //     void mostrarAlquileres(ArrayList<ModelAlquilerView> list);
        //void mostrarUsuarios(ArrayList<ModelUserView> list);

        void showDialogOptions(String idAlquiler);
        void showDialogImput(String idAlquiler);

        void notifyChangedOn(int posList);

        void nothingHere();
    }
}
