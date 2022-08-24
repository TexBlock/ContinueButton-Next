package com.texstudio.continue_button.forge;

import com.texstudio.continue_button.ContinueButtonMod;
import me.shedaniel.architectury.platform.forge.EventBuses;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(ContinueButtonMod.MOD_ID)
public class ContinueButtonModForge {
    public ContinueButtonModForge() {
        // Submit our event bus to let architectury register our content on the right time
        EventBuses.registerModEventBus(ContinueButtonMod.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        ContinueButtonMod.init();
    }
}
