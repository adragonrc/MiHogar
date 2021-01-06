package com.alexander_rodriguez.mihogar.DataBase.parse;

import android.os.Parcel;
import android.os.Parcelable;

import com.alexander_rodriguez.mihogar.DataBase.items.ItemUser;
import com.alexander_rodriguez.mihogar.DataBase.models.TUser;

public class ParceTenant  extends ItemUser implements Parcelable{

    public ParceTenant(ItemUser user) {
        super(user);
        dni = user.getDni();
    }

    protected ParceTenant(Parcel in) {
        dni = in.readString();
        name = in.readString();
        apellidoPat = in.readString();
        apellidoMat = in.readString();
        path = in.readString();
        alerted = in.readByte() != 0x00;
        main = in.readByte() != 0x00;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(dni);
        dest.writeString(name);
        dest.writeString(apellidoPat);
        dest.writeString(apellidoMat);
        dest.writeString(path);
        dest.writeByte((byte) (alerted ? 0x01 : 0x00));
        dest.writeByte((byte) (main ? 0x01 : 0x00));
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ParceTenant> CREATOR = new Parcelable.Creator<ParceTenant>() {
        @Override
        public ParceTenant createFromParcel(Parcel in) {
            return new ParceTenant(in);
        }

        @Override
        public ParceTenant[] newArray(int size) {
            return new ParceTenant[size];
        }
    };

}
