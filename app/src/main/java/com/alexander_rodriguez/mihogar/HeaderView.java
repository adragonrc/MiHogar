package com.alexander_rodriguez.mihogar;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.alexander_rodriguez.mihogar.databinding.WidgetHeaderViewBinding;

public class HeaderView extends LinearLayout {

    private WidgetHeaderViewBinding binding;

    public HeaderView(Context context) {
        super(context);
    }

    public HeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        binding = WidgetHeaderViewBinding.bind(this);
    }

    public void bindTo(String name, String lastSeen) {
        if (binding != null) {
            binding.name.setText(name);
            binding.lastSeen.setText(lastSeen);
        }
    }

    public void setNameText(String nameText) {
        if (binding != null) {
            binding.name.setText(nameText);
        }
    }

    public void setLastSeenText(String lastSeenText) {
        if (binding != null) {
            binding.lastSeen.setText(lastSeenText);
        }
    }

    public void setTextSize(float size) {
        if (binding != null) {
            binding.name.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
        }
    }
}
