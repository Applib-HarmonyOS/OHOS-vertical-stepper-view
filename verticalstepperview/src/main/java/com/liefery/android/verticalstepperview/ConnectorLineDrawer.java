package com.liefery.android.verticalstepperview;

import ohos.agp.render.Canvas;
import ohos.agp.render.Paint;
import ohos.agp.utils.Color;
import ohos.agp.utils.RectFloat;
import ohos.app.Context;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

/**
 * ConnectorLineDrawer.
 */
class ConnectorLineDrawer {
    private static final HiLogLabel LABEL_LOG = new HiLogLabel(HiLog.LOG_APP, 0x00201, "-MainAbility-");

    private final Util util;
    private final Paint paint = new Paint();
    private final RectFloat line = new RectFloat();

    int oldHeight = -1;
    int oldWidth = -1;
    boolean shouldRedraw = true;

    ConnectorLineDrawer(Context context) {
        util = new Util(context);
        int grey = context.getColor(ResourceTable.Color_vertical_stepper_view_grey_100);
        paint.setColor(new Color(grey));
    }

    /**
     * adjust the Line Drawer Connector on width change.
     *
     * @param width width of the stepper item
     * @param height height of the stepper item
     */
    void adjust(int width, int height) {
        if (oldHeight != height || oldWidth != width) {
            shouldRedraw = true;
        }
        oldHeight = height;
        oldWidth = width;

        line.left = util.dpToPx(19.5f);
        line.right = util.dpToPx(21.5f);
        line.top = util.dpToPx(40);
        line.bottom = height;
    }

    /**
     * draw the connector line drawer.
     *
     * @param canvas canvas
     */
    void draw(Canvas canvas) {
        if (shouldRedraw) {
            HiLog.warn(LABEL_LOG, "ConnectorLineDrawer: re-draw");
            canvas.drawRect(line, paint);
        } else {
            HiLog.warn(LABEL_LOG, "ConnectorLineDrawer: skip-draw");
        }
    }
}
