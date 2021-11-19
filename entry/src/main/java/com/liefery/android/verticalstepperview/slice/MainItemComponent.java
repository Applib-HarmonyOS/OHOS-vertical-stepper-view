package com.liefery.android.verticalstepperview.slice;

import ohos.agp.components.AttrSet;
import ohos.agp.components.DirectionalLayout;
import ohos.agp.components.LayoutScatter;
import ohos.app.Context;
import com.liefery.android.verticalstepperview.ResourceTable;

/**
 * MainItemView.
 */
public class MainItemComponent extends DirectionalLayout {

    //#region constructor
    public MainItemComponent(Context context) {
        super(context);
        initialize(context);
    }

    public MainItemComponent(Context context, AttrSet attrSet) {
        super(context, attrSet);
        initialize(context);
    }

    public MainItemComponent(Context context, AttrSet attrSet, String styleName) {
        super(context, attrSet, styleName);
        initialize(context);
    }
    //#endregion constructor

    private void initialize(Context context) {
        setClipEnabled(true);
        setOrientation(VERTICAL);
        LayoutScatter.getInstance(context).parse(ResourceTable.Layout_item, this, true);
    }
}
