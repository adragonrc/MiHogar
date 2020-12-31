package com.alexander_rodriguez.mihogar.vercuarto.view_perfil_cuarto;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;

import com.alexander_rodriguez.mihogar.HeaderView;
import com.alexander_rodriguez.mihogar.R;
import com.google.android.material.appbar.AppBarLayout;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileView extends CoordinatorLayout implements AppBarLayout.OnOffsetChangedListener {
    @BindView(R.id.toolbar_header_view)
    protected HeaderView toolbarHeaderView;

    @BindView(R.id.float_header_view)
    protected HeaderView floatHeaderView;

    @BindView(R.id.appbar)
    protected AppBarLayout appBarLayout;

    @BindView(R.id.toolbar)
    protected Toolbar toolbar;

    @BindView(R.id.nestedCuerpo)
    protected NestedScrollView nestedCuerpo;

    @BindView(R.id.ivPerfil)
    protected ImageView ivPerfil;
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
        ButterKnife.bind(this);
        initUi();
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
        toolbarHeaderView.setLastSeenText(subTitle);
        floatHeaderView.setLastSeenText(subTitle);
    }

    public void setPhotoImage(String path){
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
