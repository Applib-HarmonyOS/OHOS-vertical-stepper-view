package com.liefery.android.vertical_stepper_view.slice;


import ohos.agp.colors.RgbColor;
import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.agp.components.element.ShapeElement;
import ohos.agp.utils.Color;
import ohos.app.Context;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import com.liefery.android.vertical_stepper_view.ResourceTable;
import com.liefery.android.vertical_stepper_view.VerticalStepperItemProvider;

/**
 * MainStepperAdapter.
 */
public class MainStepperItemProvider extends VerticalStepperItemProvider {

    Context context;

    public MainStepperItemProvider(Context context) {
        super(context);
        this.context = context;
    }


    @Override
    public CharSequence getTitle(int position) {
        return "Title: " + position;
    }

    @Override
    public CharSequence getSummary(int position) {
        return "Summary: " + position;
    }

    @Override
    public boolean isEditable(int position) {
        return position == 0;
    }

    @Override
    public int getCount() {
        return 7;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public Component onCreateContentView(Context context, int position) {
        this.context = context;
        Component content = new MainItemComponent(context);
        Button actionContinue = (Button) content.findComponentById(ResourceTable.Id_action_continue);
        setBtnColorBasedOnState(actionContinue, (position < getCount() - 1));

        actionContinue.setClickedListener(component -> next());

        Button actionBack = (Button) content.findComponentById(ResourceTable.Id_action_back);
        setBtnColorBasedOnState(actionBack, position > 0);
        actionBack.setClickedListener(component -> previous());

        return content;
    }


    private ShapeElement getShapeElementFormResource(int resourceId) {
        int color = context.getColor(resourceId);
        ShapeElement shapeElement = new ShapeElement();
        shapeElement.setRgbColor(RgbColor.fromArgbInt(color));
        return shapeElement;
    }

    private void setBtnColorBasedOnState(Button btn, boolean active) {
        if (active) {
            setBtnActive(btn);
        } else {
            setBtnInActive(btn);
        }
    }

    private void setBtnActive(Button btn) {
        ShapeElement backgroundColor = getShapeElementFormResource(ResourceTable.Color_btn_active_color);
        Color textColor = new Color(context.getColor(ResourceTable.Color_btn_active_text_color));

        btn.setBackground(backgroundColor);
        btn.setTextColor(textColor);
        btn.setClickable(true);
    }

    private void setBtnInActive(Button btn) {
        ShapeElement backgroundColor = getShapeElementFormResource(ResourceTable.Color_btn_inactive_color);
        Color textColor = new Color(context.getColor(ResourceTable.Color_btn_inactive_text_color));

        btn.setBackground(backgroundColor);
        btn.setTextColor(textColor);
        btn.setClickable(false);
    }


}
