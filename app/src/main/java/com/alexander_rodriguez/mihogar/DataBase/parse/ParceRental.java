package com.alexander_rodriguez.mihogar.DataBase.parse;

import android.os.Parcel;
import android.os.Parcelable;

import com.alexander_rodriguez.mihogar.DataBase.items.ItemRental;
import com.alexander_rodriguez.mihogar.DataBase.models.TRental;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class ParceRental extends TRental implements Parcelable {
    private String id;

    public ParceRental(){}

    public ParceRental(ItemRental r) {
        super(r.getEntryDate(), r.getDepartureDate(), r.getReasonExit(), r.getRoomNumber(), r.getCurrentMP(), r.getMainTenant(), r.getTenantsNumber(), r.getPaymentsNumber(), r.getPhoneNumber(), r.getEmail());
        id = r.getId();
    }

    public ParceRental(Parcel in) {
        id = in.readString();
        entryDate = (Timestamp) in.readValue(Timestamp.class.getClassLoader());
        departureDate = (Timestamp) in.readValue(Timestamp.class.getClassLoader());
        reasonExit = in.readString();
        roomNumber = in.readString();
        String s = in.readString();
        s = s == null? "": s;
        currentMP = FirebaseFirestore.getInstance().document(s);
        mainTenant = in.readString();
        tenantsNumber = in.readInt();
        paymentsNumber = in.readInt();
        phoneNumber = in.readString();
        email = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeValue(entryDate);
        dest.writeValue(departureDate);
        dest.writeString(reasonExit);
        dest.writeString(roomNumber);
        dest.writeString(currentMP.getPath());
        dest.writeString(mainTenant);
        dest.writeInt(tenantsNumber);
        dest.writeInt(paymentsNumber);
        dest.writeString(phoneNumber);
        dest.writeString(email);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ParceRental> CREATOR = new Parcelable.Creator<ParceRental>() {
        @Override
        public ParceRental createFromParcel(Parcel in) {
            return new ParceRental(in);
        }

        @Override
        public ParceRental[] newArray(int size) {
            return new ParceRental[size];
        }
    };

}
