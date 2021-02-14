package com.alexander_rodriguez.mihogar.adapters;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class mRecycler extends RecyclerView {
    public mRecycler(@NonNull Context context) {
        super(context);
    }

    public mRecycler(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public mRecycler(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean showContextMenuForChild(View originalView) {
        return super.showContextMenuForChild(originalView);
    }
}
