package com.liefery.android.verticalstepperview;

import static com.liefery.android.verticalstepperview.VerticalStepperItemComponent.STATE_ACTIVE;
import static com.liefery.android.verticalstepperview.VerticalStepperItemComponent.STATE_COMPLETE;
import static com.liefery.android.verticalstepperview.VerticalStepperItemComponent.STATE_INACTIVE;
import ohos.agp.components.BaseItemProvider;
import ohos.agp.components.Component;
import ohos.agp.components.ComponentContainer;
import ohos.agp.components.ComponentParent;
import ohos.app.Context;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.utils.PlainArray;
import java.util.Optional;

/**
 * VerticalStepperItemProvider to for the ListContainer.
 */
public abstract class VerticalStepperItemProvider extends BaseItemProvider {
    private static final HiLogLabel LABEL_LOG = new HiLogLabel(HiLog.LOG_APP, 0x00201, "-MainAbility-");
    private final PlainArray<Component> contentViews = new PlainArray<>(getCount());
    private int focus = 0;

    /**
     * constructor for the VerticalStepperItemProvider.
     *
     * @param context application context
     */
    protected VerticalStepperItemProvider(Context context) {
        for (int i = 0; i < getCount(); i++) {
            getContentView(context, i);
        }
    }

    /**
     * get stepper state by it's position.
     *
     * @param position position of the stepper item
     * @return state of the stepper item
     */
    public int getState(int position) {
        if (position == focus) {
            return STATE_ACTIVE;
        } else if (position < focus) {
            return STATE_COMPLETE;
        } else {
            return STATE_INACTIVE;
        }
    }


    private int getCircleNumber(int position) {
        return position + 1;
    }

    private boolean showConnectorLine(int position) {
        return position < getCount() - 1;
    }

    private Component getContentView(Context context, int position) {
        int id = (int) getItemId(position);
        Optional<Component> contentView = contentViews.get(id);

        if (!contentView.isPresent()) {
            Component contentView2 = onCreateContentView(context, position);
            contentViews.put(id, contentView2);
            return contentView2;
        }

        return contentView.get();
    }

    /**
     * remove stepper item form the stepper.
     *
     * @param position position of the stepper item
     */
    public void invalidateContentView(int position) {
        int id = (int) getItemId(position);
        contentViews.remove(id);
        notifyDataSetItemChanged(position);
    }

    private void applyData(Context context, VerticalStepperItemComponent itemView, int position) {
        Component currentContentView = itemView.getContentView();
        Component contentView = getContentView(context, position);

        if (currentContentView != contentView) {
            // Make sure the content view isn't attached to a foreign parent
            ComponentParent parent = contentView.getComponentParent();
            if (parent != null) {
                parent.removeComponent(contentView);
            }

            itemView.setContentView(contentView);
        }

        itemView.setState(getState(position));
        itemView.setCircleNumber(getCircleNumber(position));
        itemView.setTitle(getTitle(position));
        itemView.setSummary(getSummary(position));
        itemView.setEditable(isEditable(position));
        itemView.setShowConnectorLine(showConnectorLine(position));
    }


    public int getFocus() {
        return focus;
    }

    /**
     * jump to the stepper item by it's position.
     *
     * @param position position of the stepper item
     */
    public void jumpTo(int position) {
        if (focus != position) {
            focus = position;
            notifyDataChanged();
        }
    }

    public boolean hasPrevious() {
        return focus > 0;
    }


    public boolean hasNext() {
        return focus < getCount() - 1;
    }


    /**
     * Go to the previous stepper item.
     */
    public void previous() {
        if (hasPrevious()) {
            HiLog.warn(LABEL_LOG, "VerticalStepperAdapter: previous");
            jumpTo(focus - 1);
        }
    }

    /**
     * Go to the next stepper item.
     */
    public void next() {
        if (hasNext()) {
            HiLog.warn(LABEL_LOG, "VerticalStepperAdapter: next");
            jumpTo(focus + 1);
        }
    }


    //#region abstract
    public abstract Component onCreateContentView(Context context, int position);

    public abstract CharSequence getTitle(int position);

    public abstract CharSequence getSummary(int position);

    public abstract boolean isEditable(int position);
    //#endregion abstract
    //#region overrides


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Component getComponent(int position, Component convertComponent, ComponentContainer parent) {
        Context context = parent.getContext();
        VerticalStepperItemComponent itemView;
        if (convertComponent == null) {
            itemView = new VerticalStepperItemComponent(context);
        } else {
            itemView = (VerticalStepperItemComponent) convertComponent;
        }
        applyData(context, itemView, position);
        return itemView;
    }
    //#endregion overrides

}
