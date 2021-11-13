package com.liefery.android.vertical_stepper_view;

import ohos.agp.components.AttrSet;
import ohos.agp.components.Image;
import ohos.agp.components.LayoutScatter;
import ohos.agp.components.StackLayout;
import ohos.agp.components.Text;
import ohos.agp.components.element.Element;
import ohos.agp.components.element.ElementScatter;
import ohos.app.Context;

/**
 * VerticalStepperItemCircleComponent.
 */
public class VerticalStepperItemCircleComponent extends StackLayout {
    Util util = new Util(getContext());

    Text number;
    Image icon;
    int size;

    //#region constructor
    public VerticalStepperItemCircleComponent(Context context) {
        super(context);
        init(context);
    }

    public VerticalStepperItemCircleComponent(Context context, AttrSet attrSet) {
        super(context, attrSet);
        init(context);
    }

    public VerticalStepperItemCircleComponent(Context context, AttrSet attrSet, String styleName) {
        super(context, attrSet, styleName);
        init(context);
    }
    //#endregion constructor


    /**
     * initialize the component after the constructor call.
     *
     * @param context application context
     */
    public void init(Context context) {
        size = util.dpToPx(24);
        setComponentSize(size, size);

        LayoutScatter.getInstance(context).parse(ResourceTable.Layout_vertical_stepper_view_item_circle, this, true);
        number = (Text) findComponentById(ResourceTable.Id_vertical_stepper_view_item_circle_number);
        icon = (Image) findComponentById(ResourceTable.Id_vertical_stepper_view_item_circle_icon);
    }

    /**
     * sets the background of the circle active.
     */
    public void setBackgroundActive() {
        Element shapeElement = ElementScatter.getInstance(getContext())
                .parse(ResourceTable.Graphic_vertical_stepper_view_item_circle_active);
        setBackground(shapeElement);
    }

    /**
     * sets the background of the circle inactive.
     */
    public void setBackgroundInactive() {
        Element shapeElement = ElementScatter.getInstance(getContext())
                .parse(ResourceTable.Graphic_vertical_stepper_view_item_circle_inactive);
        setBackground(shapeElement);
    }

    /**
     * sets the number of the circle component.
     *
     * @param value value for the circle component text
     */
    public void setNumber(int value) {
        icon.setVisibility(HIDE);
        number.setVisibility(VISIBLE);
        number.setText(String.valueOf(value));
    }

    public void setIconCheck() {
        setIconResource(ResourceTable.Media_icon_check_white_18dp);
    }

    public void setIconEdit() {
        setIconResource(ResourceTable.Media_icon_edit_white_18dp);
    }

    /**
     * set the icon in place of the circle number.
     *
     * @param id resource id for the icon resource
     */
    public void setIconResource(int id) {
        number.setVisibility(HIDE);
        icon.setVisibility(VISIBLE);
        icon.setImageAndDecodeBounds(id);
    }
}
