package com.texstudio.continue_button.forge;

import com.texstudio.continue_button.ContinueButtonExpectPlatform;
import net.minecraftforge.fml.loading.FMLPaths;

import java.nio.file.Path;

public class ContinueButtonExpectPlatformImpl {
    /**
     * This is our actual method to {@link ContinueButtonExpectPlatform#getConfigDirectory()}.
     */
    public static Path getConfigDirectory() {
        return FMLPaths.CONFIGDIR.get();
    }
}
