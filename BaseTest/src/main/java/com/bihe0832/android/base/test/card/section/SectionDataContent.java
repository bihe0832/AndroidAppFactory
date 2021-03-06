package com.bihe0832.android.base.test.card.section;

import com.bihe0832.android.base.test.R;
import com.bihe0832.android.lib.adapter.CardBaseHolder;
import com.bihe0832.android.lib.adapter.CardBaseModule;

/**
 * @author hardyshi code@bihe0832.com
 * Created on 2019-11-21.
 * Description: Description
 */
public class SectionDataContent extends CardBaseModule {

    public int getResID() {
        return R.layout.card_demo_section_content;
    }

    public Class<? extends CardBaseHolder> getViewHolderClass() {
        return SectionHolderContent.class;
    }
    public String mContentText;

    public SectionDataContent(String netType) {
        mContentText = netType;
    }
}