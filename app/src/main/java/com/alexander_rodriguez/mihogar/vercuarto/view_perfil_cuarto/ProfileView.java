package com.alexander_rodriguez.mihogar.vercuarto.view_perfil_cuarto;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;

import com.alexander_rodriguez.mihogar.HeaderView;
import com.alexander_rodriguez.mihogar.R;
import com.google.android.material.appbar.AppBarLayout;

import java.io.File;

public class ProfileView extends CoordinatorLayout implements AppBarLayout.OnOffsetChangedListener {
    HeaderView toolbarHeaderView;
    HeaderView floatHeaderView;
    AppBarLayout appBarLayout;
    Toolbar toolbar;
    NestedScrollView nestedCuerpo;
    ImageView ivPerfil;
    private boolean isHideToolbarView = false;

    public ProfileView(@NonNull Context context) {
        super(context);
    }

    public ProfileView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ProfileView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initViews();
        initUi();
    }

    private void initViews() {
        toolbarHeaderView = findViewById(R.id.toolbar_header_view);
        floatHeaderView = findViewById(R.id.float_header_view);
        appBarLayout = findViewById(R.id.appbar);
        toolbar = findViewById(R.id.toolbar);
        nestedCuerpo = findViewById(R.id.nestedCuerpo);
        ivPerfil = findViewById(R.id.ivPerfil);
    }

    public void addToCuerpo(View view){
        nestedCuerpo.addView(view);
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    private void initUi() {
        appBarLayout.addOnOffsetChangedListener(this);
    }

    public void setTitle(String title){
        toolbarHeaderView.setNameText(title);
        floatHeaderView.setNameText(title);
    }
    public void setSubTitle(String subTitle){
        toolbarHeaderView.setNameText(subTitle);
        floatHeaderView.setNameText(subTitle);
    }

    public void reloadPhoto(String path){
        if (path != null && !path.equals("")) {
            File f = new File(path);
            if(f.exists()) {
                Bitmap bm = BitmapFactory.decodeFile(path);
                ivPerfil.setImageBitmap(bm);
            }
        }
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(offset) / (float) maxScroll;

        if (percentage == 1f && isHideToolbarView) {
            toolbarHeaderView.setVisibility(View.VISIBLE);
            isHideToolbarView = !isHideToolbarView;

        } else if (percentage < 1f && !isHideToolbarView) {
            toolbarHeaderView.setVisibility(View.GONE);
            isHideToolbarView = !isHideToolbarView;
        }
    }

    public ImageView getIvPhoto() {
        return ivPerfil;
    }
}
