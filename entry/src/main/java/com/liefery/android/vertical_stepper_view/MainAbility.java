package com.liefery.android.vertical_stepper_view;

import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;
import com.liefery.android.vertical_stepper_view.slice.MainAbilitySlice;

/**
 * MainAbility.
 */
public class MainAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(MainAbilitySlice.class.getName());
    }
}
