package com.liefery.android.verticalstepperview.slice;

import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import com.liefery.android.verticalstepperview.ResourceTable;
import com.liefery.android.verticalstepperview.VerticalStepperComponent;

/**
 * MainAbilitySlice.
 */
public class MainAbilitySlice extends AbilitySlice {

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        VerticalStepperComponent stepper = (VerticalStepperComponent) findComponentById(ResourceTable.Id_stepper_list);
        stepper.setItemProvider(new MainStepperItemProvider(this));
    }
}
