package com.alexander_rodriguez.mihogar.table_activity;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TableRow;
import android.widget.TextView;

import com.alexander_rodriguez.mihogar.R;

public class TableRowView extends TableRow {
    private TextView tvId;
    private TextView tvFecha;
    private TextView tvMonto;

    public TableRowView(Context context) {
        super(context);
    }

    public TableRowView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        tvId = findViewById(R.id.tvId);
        tvFecha = findViewById(R.id.tvFecha);
        tvMonto = findViewById(R.id.tvMonto);
    }

    public void setText(String idPago, String fecha, String monto){
        tvId.setText(idPago);
        tvMonto.setText(monto);
        tvFecha.setText(fecha);
    }

    public void setTextId(String text) {
        this.tvId.setText(text);
    }

    public void setTextFecha(String text) {
        this.tvFecha.setText(text);
    }

    public void setTextMonto(String text) {
        this.tvMonto.setText(text);
    }

    public String getTextId() {
        return tvId.getText().toString();
    }
    public String getTextFecha() {
        return tvFecha.getText().toString();
    }
    public String getTextMonto() {
        return tvMonto.getText().toString();
    }
}
