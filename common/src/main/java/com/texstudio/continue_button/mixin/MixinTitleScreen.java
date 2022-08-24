package com.texstudio.continue_button.mixin;

//import com.umollu.continuebutton.ContinueButtonMod;
import com.mojang.blaze3d.vertex.PoseStack;
import com.texstudio.continue_button.ContinueButtonMod;
import net.minecraft.SharedConstants;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.*;
import net.minecraft.client.gui.screens.multiplayer.ServerSelectionList;
import net.minecraft.client.gui.screens.worldselection.CreateWorldScreen;
import net.minecraft.client.gui.screens.worldselection.EditWorldScreen;
import net.minecraft.client.multiplayer.ServerList;
import net.minecraft.client.multiplayer.ServerStatusPinger;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.storage.LevelStorageException;
import net.minecraft.world.level.storage.LevelSummary;
import net.minecraft.client.gui.screens.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Mixin(TitleScreen.class)
public class MixinTitleScreen {
//    @Inject(at = @At("HEAD"), method = "init()V")
//    private void init(CallbackInfo info) {
//        System.out.println("Hello from example architectury common mixin!");
//    }
private LevelSummary level = null;
    private  LevelSummary level = null;
    private int width;

    protected MixinTitleScreen(TextComponent title) {
        super(title);
    }

    @Inject(at = @At("RETURN"), method = "initWidgetsNormal(II)V")
    public void drawMenuButton(int y, int spacingY, CallbackInfo info) {
        this.drawMenuButton(new Button(this.width / 2 - 100, y, 98, 20, new TranslatableComponent("continuebutton.continueButtonTitle"), button -> {

            LevelStorage levelStorage = this.level.getLevelId();
            List<LevelSummary> levels = null;
            try {
                levels = levelStorage.getLevelList();
            } catch (LevelStorageException e) {
                e.printStackTrace();
            }
            if (levels.isEmpty()) {
                this.level.openScreen(CreateWorldScreen.method_31130((Screen)null));
            } else {
                Collections.sort(levels);
                level =  levels.get(0);

                if (!level.isLocked()) {
                    if (level.isOutdatedLevel()) {
                        TextComponent text = new TranslatableComponent("selectWorld.backupQuestion");
                        TextComponent text2 = new TranslatableComponent("selectWorld.backupWarning", new Object[]{level.getVersion(), SharedConstants.getGameVersion().getName()});
                        this.level.openScreen(new BackupPromptScreen(this, (bl, bl2) -> {
                            if (bl) {
                                String string = level.getName();

                                try {
                                    LevelStorage.Session session = this.level.getLevelId().createSession(string);
                                    Throwable var5 = null;

                                    try {
                                        EditWorldScreen.backupLevel(session);
                                    } catch (Throwable var15) {
                                        var5 = var15;
                                        throw var15;
                                    } finally {
                                        if (session != null) {
                                            if (var5 != null) {
                                                try {
                                                    session.close();
                                                } catch (Throwable var14) {
                                                    var5.addSuppressed(var14);
                                                }
                                            } else {
                                                session.close();
                                            }
                                        }

                                    }
                                } catch (IOException var17) {
                                    SystemToast.addWorldAccessFailureToast(this.level, string);
                                    //WorldListWidget.LOGGER.error("Failed to backup level {}", string, var17);
                                }
                            }

                            start();
                        }, text, text2, false));
                    } else if (level.isFutureLevel()) {
                        this.level.openScreen(new ConfirmScreen((bl) -> {
                            if (bl) {
                                try {
                                    start();
                                } catch (Exception var3) {
                                    //WorldListWidget.LOGGER.error("Failure to open 'future world'", var3);
                                    this.level.openScreen(new NoticeScreen(() -> {
                                        this.level.openScreen(this);
                                    }, new TranslatableText("selectWorld.futureworld.error.title"), new TranslatableText("selectWorld.futureworld.error.text")));
                                }
                            } else {
                                this.level.openScreen(this);
                            }

                        }, new TranslatableText("selectWorld.versionQuestion"), new TranslatableText("selectWorld.versionWarning", new Object[]{this.level.getVersion(), new TranslatableText("selectWorld.versionJoinButton"), ScreenTexts.CANCEL})));
                    } else {
                        start();
                    }

                }
            }
        }));
    }

    private void start() {
        this.client.getSoundManager().play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
        if (this.client.getLevelStorage().levelExists(this.level.getName())) {
            this.method_29990();
            this.client.startIntegratedServer(this.level.getName());
        }


    }

    private void method_29990() {
        this.client.method_29970(new SaveLevelScreen(new TranslatableText("selectWorld.data_read")));
    }
}