package com.alexander_rodriguez.mihogar;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by anton on 11/12/15.
 */

public class HeaderView extends LinearLayout {

    @BindView(R.id.name)
    protected TextView name;

    @BindView(R.id.last_seen)
    protected TextView lastSeen;

    public HeaderView(Context context) {
        super(context);
    }

    public HeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public HeaderView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }

    public void bindTo(String name, String lastSeen) {
        this.name.setText(name);
        this.lastSeen.setText(lastSeen);
    }

    public void setNameText(String nameText) {
        name.setText(nameText);
    }

    public void setLastSeenText(String lastSeenText) {
        lastSeen.setText(lastSeenText);
    }

    public void setTextSize(float size) {
        name.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
    }
}
