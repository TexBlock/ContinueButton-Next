package com.texstudio.continue_button.fabric;

import com.texstudio.continue_button.ContinueButtonMod;
import net.fabricmc.api.ModInitializer;

public class ContinueButtonModFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        ContinueButtonMod.init();
    }
}
