package com.liefery.android.verticalstepperview;

import static ohos.agp.components.ComponentContainer.LayoutConfig.MATCH_CONTENT;
import static ohos.agp.components.ComponentContainer.LayoutConfig.MATCH_PARENT;
import ohos.agp.components.AttrSet;
import ohos.agp.components.Component;
import ohos.agp.components.ComponentContainer;
import ohos.agp.components.DirectionalLayout;
import ohos.agp.components.LayoutScatter;
import ohos.agp.components.StackLayout;
import ohos.agp.components.Text;
import ohos.agp.render.Canvas;
import ohos.agp.text.Font;
import ohos.agp.utils.Color;
import ohos.app.Context;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

/**
 * VerticalStepperItemComponent.
 */
public class VerticalStepperItemComponent extends StackLayout implements Component.DrawTask,
        Component.LayoutRefreshedListener {
    private static final HiLogLabel LABEL_LOG = new HiLogLabel(HiLog.LOG_APP, 0x00201, "-MainAbility-");
    private final Util util = new Util(getContext());

    //#region static variables
    public static final int STATE_ACTIVE = 1;
    public static final int STATE_COMPLETE = 2;
    public static final int STATE_INACTIVE = 0;
    //#endregion static variables

    //#region internal state
    private boolean showConnectorLine = true;
    private boolean editable = false;
    private int state = STATE_INACTIVE;
    private int number;
    //#endregion internal state

    //#region components
    private VerticalStepperItemCircleComponent circle;
    private DirectionalLayout wrapper;
    private Text title;
    private Text summary;
    private StackLayout contentWrapper;
    private ConnectorLineDrawer connector;
    //#endregion components

    //#region constructor
    public VerticalStepperItemComponent(Context context) {
        super(context);
        initialize(context);
    }

    public VerticalStepperItemComponent(Context context, AttrSet attrSet) {
        super(context, attrSet);
        initialize(context);
    }

    public VerticalStepperItemComponent(Context context, AttrSet attrSet, String styleName) {
        super(context, attrSet, styleName);
        initialize(context);
    }
    //#endregion constructor

    private void initialize(Context context) {
        setClipEnabled(false);
        addDrawTask(this);
        setLayoutRefreshedListener(this);


        int padding = util.dpToPx(8);
        setPadding(padding, padding, padding, 0);

        LayoutScatter.getInstance(context)
                .parse(ResourceTable.Layout_vertical_stepper_view_item, this, true);

        circle = (VerticalStepperItemCircleComponent) findComponentById(
                ResourceTable.Id_vertical_stepper_view_item_circle
        );
        wrapper = (DirectionalLayout) findComponentById(ResourceTable.Id_vertical_stepper_view_item_wrapper);
        title = (Text) findComponentById(ResourceTable.Id_vertical_stepper_view_item_title);
        summary = (Text) findComponentById(ResourceTable.Id_vertical_stepper_view_item_summary);
        contentWrapper = (StackLayout) findComponentById(ResourceTable.Id_vertical_stepper_view_item_content_wrapper);

        connector = new ConnectorLineDrawer(context);

    }

    public boolean getShowConnectorLine() {
        return showConnectorLine;
    }

    public void setShowConnectorLine(boolean show) {
        showConnectorLine = show;
        setMarginBottom(state == STATE_ACTIVE);
    }

    public boolean isEditable() {
        return editable;
    }

    /**
     * set the stepper item editable status.
     *
     * @param editable if the item is editable
     */
    public void setEditable(boolean editable) {
        this.editable = editable;

        if (state == STATE_COMPLETE) {
            if (isEditable()) {
                circle.setIconEdit();
            } else {
                circle.setIconCheck();
            }
        }
    }

    /**
     * set the stepper item circle number.
     *
     * @param number number for the stepper item circle
     */
    public void setCircleNumber(int number) {
        this.number = number;

        if (state != STATE_COMPLETE) {
            circle.setNumber(number);
        }
    }

    public void setTitle(CharSequence title) {
        this.title.setText(title.toString());
    }

    /**
     * set the summery for the stepper item.
     *
     * @param summary summery text
     */
    public void setSummary(CharSequence summary) {
        this.summary.setText(summary.toString());

        if (state == STATE_COMPLETE) {
            this.summary.setVisibility(VISIBLE);
        }
    }

    public Component getContentView() {
        return contentWrapper.getComponentAt(0);
    }

    /**
     * set the main content of the stepper item.
     *
     * @param component component of the stepper item
     */
    public void setContentView(Component component) {
        contentWrapper.removeAllComponents();
        contentWrapper.addComponent(component, MATCH_PARENT, MATCH_CONTENT);
    }

    public int getState() {
        return state;
    }

    /**
     * set the activation state of the stepper item.
     *
     * @param state activation state of the stepper item
     */
    public void setState(int state) {
        this.state = state;

        if (state == STATE_INACTIVE) {
            setStateInactive();
        } else if (state == STATE_ACTIVE) {
            setStateActive();
        } else {
            setStateComplete();
        }
    }

    /**
     * make the stepper item active.
     */
    private void setStateInactive() {
        circle.setIconEdit();
        setMarginBottom(false);
        circle.setNumber(number);
        circle.setBackgroundInactive();
        title.setTextColor(new Color(getContext().getColor(ResourceTable.Color_vertical_stepper_view_black_38)));
        Font regularFont = new Font.Builder(title.getFont().getName()).setWeight(Font.REGULAR).build();
        title.setFont(regularFont);
        summary.setVisibility(INVISIBLE);
        contentWrapper.setVisibility(HIDE);
    }

    /**
     * make the stepper item in active.
     */
    private void setStateActive() {
        circle.setIconEdit();
        setMarginBottom(true);
        circle.setNumber(number);
        circle.setBackgroundActive();
        title.setTextColor(new Color(getContext().getColor(ResourceTable.Color_vertical_stepper_view_black_87)));
        Font regularFont = new Font.Builder(title.getFont().getName()).setWeight(Font.BOLD).build();
        title.setFont(regularFont);
        summary.setVisibility(HIDE);
        contentWrapper.setVisibility(VISIBLE);
    }

    /**
     * make the stepper item complete.
     */
    private void setStateComplete() {
        setMarginBottom(false);
        circle.setBackgroundActive();

        if (isEditable()) {
            circle.setIconEdit();
        } else {
            circle.setIconCheck();
        }

        title.setTextColor(new Color(getContext().getColor(ResourceTable.Color_vertical_stepper_view_black_87)));
        Font regularFont = new Font.Builder(title.getFont().getName()).setWeight(Font.BOLD).build();
        title.setFont(regularFont);
        summary.setVisibility(summary.getText().isEmpty() ? HIDE : VISIBLE);
        contentWrapper.setVisibility(HIDE);
    }

    /**
     * enable/disable stepper item margin bottom.
     *
     * @param active is the margin bottom active
     */
    private void setMarginBottom(boolean active) {
        ComponentContainer.LayoutConfig params = wrapper.getLayoutConfig();
        if (!getShowConnectorLine()) {
            params.setMarginBottom(0);
        } else if (active) {
            params.setMarginBottom(util.dpToPx(48));
        } else {
            params.setMarginBottom(util.dpToPx(40));
        }
    }


    @Override
    public void onDraw(Component component, Canvas canvas) {
        HiLog.warn(LABEL_LOG, "VerticalStepperItemView: onDraw");
        if (showConnectorLine) {
            connector.draw(canvas);
        }
    }

    @Override
    public void onRefreshed(Component component) {
        HiLog.warn(LABEL_LOG, "VerticalStepperItemView: onRefreshed");
        connector.adjust(component.getWidth(), component.getHeight());
        component.invalidate();
    }
}
