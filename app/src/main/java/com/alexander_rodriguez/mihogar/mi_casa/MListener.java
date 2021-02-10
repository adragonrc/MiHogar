package com.alexander_rodriguez.mihogar.mi_casa;

import com.alexander_rodriguez.mihogar.adapters.Models.ModelRoomView;
import com.alexander_rodriguez.mihogar.DataBase.models.TRental;
import com.alexander_rodriguez.mihogar.AdminDate;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;

import java.text.ParseException;

public class MListener <T extends DocumentSnapshot>  implements OnSuccessListener<T> {
    ModelRoomView modelRoomView;
    public MListener(ModelRoomView modelRoomView) {
        this.modelRoomView = modelRoomView;
    }

    @Override
    public void onSuccess(T t) {
        TRental rental = t.toObject(TRental.class);
        if (rental != null) {
            String entryDate = AdminDate.dateToString(rental.getEntryDate().toDate());/*
            try {
               // String nextPaymentDate = AdminDate.adelantarPorMeses(entryDate, rental.getPaymentsNumber());
           //    modelRoomView.setPaymentDate(nextPaymentDate);
            } catch (ParseException e) {
                modelRoomView.setPaymentDate(null);
            }*/
        }else{
            modelRoomView.setPaymentDate(null);
        }

    }

}
