package com.texstudio.continue_button.fabric;

import com.texstudio.continue_button.ContinueButtonExpectPlatform;
import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Path;

public class ContinueButtonExpectPlatformImpl {
    /**
     * This is our actual method to {@link ContinueButtonExpectPlatform#getConfigDirectory()}.
     */
    public static Path getConfigDirectory() {
        return FabricLoader.getInstance().getConfigDir();
    }
}
