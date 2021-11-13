package com.liefery.android.vertical_stepper_view;

import ohos.agp.components.AttrSet;
import ohos.agp.components.BaseItemProvider;
import ohos.agp.components.ListContainer;
import ohos.app.Context;

/**
 * VerticalStepperComponent.
 */
public class VerticalStepperComponent extends ListContainer {
    //#region constructor
    public VerticalStepperComponent(Context context) {
        super(context);
        initialize();
    }

    public VerticalStepperComponent(Context context, AttrSet attrSet) {
        super(context, attrSet);
        initialize();
    }

    public VerticalStepperComponent(Context context, AttrSet attrSet, String styleName) {
        super(context, attrSet, styleName);
        initialize();
    }
    //#endregion constructor

    private void initialize() {
        setItemClickedListener((listContainer, component, position, id) -> getItemProvider().jumpTo(position));
    }

    @Override
    public VerticalStepperItemProvider getItemProvider() {
        return (VerticalStepperItemProvider) super.getItemProvider();
    }

    @Override
    public void setItemProvider(BaseItemProvider itemProvider) {
        if (!(itemProvider instanceof VerticalStepperItemProvider)) {
            throw new IllegalArgumentException("Must be a VerticalStepperAdapter");
        }
        super.setItemProvider(itemProvider);
    }


    public void setStepperAdapter(VerticalStepperItemProvider adapter) {
        setItemProvider(adapter);
    }
}
