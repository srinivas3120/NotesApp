package com.srinivas.notesapp.activity.base;

import android.os.Build;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;

import com.srinivas.notesapp.R;

public abstract class BaseActivityController extends AbstractAppCompactController {

    private Toolbar mToolbar;

    public Toolbar setupToolbar(boolean supportStatusBarTint) {
        try {
            mToolbar = (Toolbar) findViewById(R.id.toolbar);
            if (supportStatusBarTint) {
                if (Build.VERSION.SDK_INT >= 21) {
                    int statusBarHeight = getStatusBarHeight();
                    if (statusBarHeight > 0) {
                        //AppBarLayout.LayoutParams layoutParams = (AppBarLayout.LayoutParams) mToolbar.getLayoutParams();
                        ViewGroup.LayoutParams layoutParams = (ViewGroup.LayoutParams) mToolbar.getLayoutParams();
                        layoutParams.height += statusBarHeight;
                        //new Toolbar.LayoutParams(Toolbar.LayoutParams.MATCH_PARENT, toolbar.getMinimumHeight()+getStatusBarHeight());
                        mToolbar.setLayoutParams(layoutParams);
                        mToolbar.setPadding(0, statusBarHeight, 0, 0);
                    }
                }
            }
            setSupportActionBar(mToolbar);
            return mToolbar;
        } catch (NullPointerException exception) {}
        return null;
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0)
            result = getResources().getDimensionPixelSize(resourceId);
        return result;
    }

}
