package com.bihe0832.android.framework.ui.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bihe0832.android.framework.R;
import com.bihe0832.android.framework.ui.BaseFragment;
import com.bihe0832.android.lib.log.ZLog;
import com.bihe0832.android.lib.ui.view.BottomBar;
import com.bihe0832.android.lib.ui.view.BottomBarTab;
import java.util.ArrayList;

/**
 * @author hardyshi code@bihe0832.com
 *         Created on 2020/8/3.
 *         Description: Description
 */
public abstract class CommonMainFragment extends BaseFragment {

    protected BottomBar bottomBar = null;
    private int currentPosition = getDefaultTabID();
    private ArrayList<BaseFragment> mFragments = new ArrayList<>();
    private ArrayList<BottomBarTab> mBottomBarTabs = new ArrayList<>();

    protected abstract int getDefaultTabID();

    protected abstract ArrayList<BaseFragment> getFragments();

    protected abstract ArrayList<BottomBarTab> getBottomBarTabs();

    protected abstract void loadMultipleRootFragmentByResId(int containerId);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.common_fragment_with_bottom, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBottomBarTabs = getBottomBarTabs();
        bottomBar = view.findViewById(R.id.main_fragment_bottomBar);
        for (int i = 0; i < mBottomBarTabs.size(); i++) {
            bottomBar.addItem(mBottomBarTabs.get(i));
        }
        bottomBar.setOnTabSelectedListener(new BottomBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, int prePosition) {
                currentPosition = position;
                showHideFragment(mFragments.get(position), mFragments.get(prePosition));
                for (int i = 0; i < mFragments.size(); i++) {
                    if (i == currentPosition) {
                        mFragments.get(i).setUserVisibleHint(true);
                    } else {
                        mFragments.get(i).setUserVisibleHint(false);
                    }
                }
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        });
        bottomBar.setCurrentItem(getDefaultTabID());
    }

    protected void changeTab(final int position) {
        if (position < bottomBar.getChildCount()) {
            bottomBar.setCurrentItem(position);
        }
    }

    protected BottomBar getBottomBar() {
        return bottomBar;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        ZLog.d("setUserVisibleHint:$isVisibleToUser");
        if (mFragments.size() > currentPosition && null != mFragments.get(currentPosition)) {
            mFragments.get(currentPosition).setUserVisibleHint(isVisibleToUser);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mFragments = getFragments();
        loadMultipleRootFragmentByResId(R.id.main_fragment_content);
    }
}
