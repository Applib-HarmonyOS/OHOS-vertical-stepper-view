package com.liefery.android.verticalstepperview;

import ohos.agp.components.AttrHelper;
import ohos.app.Context;

/**
 * utility class for pixel value manipulation.
 */
class Util {
    Context context;

    public Util(Context context) {
        this.context = context;
    }

    /**
     * convert the vp value to the pixel value.
     *
     * @param vp vp value
     * @return pixel value
     */
    public int dpToPx(float vp) {
        return AttrHelper.vp2px(vp, context);
    }

}
