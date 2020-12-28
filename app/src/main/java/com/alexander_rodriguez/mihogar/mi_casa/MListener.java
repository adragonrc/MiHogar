package com.alexander_rodriguez.mihogar.mi_casa;

import com.alexander_rodriguez.mihogar.Adapters.Models.ModelCuartoView;
import com.alexander_rodriguez.mihogar.DataBase.models.TRental;
import com.alexander_rodriguez.mihogar.MyAdminDate;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;

import java.text.ParseException;

public class MListener <T extends DocumentSnapshot>  implements OnSuccessListener<T> {
    ModelCuartoView modelCuartoView;
    public MListener(ModelCuartoView modelCuartoView) {
        this.modelCuartoView = modelCuartoView;
    }

    @Override
    public void onSuccess(T t) {
        TRental rental = t.toObject(TRental.class);
        if (rental != null) {
            String entryDate = rental.getEntryDate();
            try {
                String nextPaymentDate = MyAdminDate.adelantarPorMeses(entryDate, rental.getPaymentsNumber());
                modelCuartoView.setPaymentDate(nextPaymentDate);
            } catch (ParseException e) {
                modelCuartoView.setPaymentDate(null);
            }
        }
    }

}
