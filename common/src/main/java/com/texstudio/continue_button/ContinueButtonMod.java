package com.texstudio.continue_button;

import me.shedaniel.architectury.registry.CreativeTabs;
import me.shedaniel.architectury.registry.DeferredRegister;
import me.shedaniel.architectury.registry.Registries;
import me.shedaniel.architectury.registry.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ContinueButtonMod {
    public static final String MOD_ID = "continue_button";
    // We can use this if we don't want to use DeferredRegister
    public static final LazyLoadedValue<Registries> REGISTRIES = new LazyLoadedValue<>(() -> Registries.get(MOD_ID));
    // Registering a new creative tab
    public static final CreativeModeTab EXAMPLE_TAB = CreativeTabs.create(new ResourceLocation(MOD_ID, "example_tab"), () ->
            new ItemStack(ContinueButtonMod.EXAMPLE_ITEM.get()));

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(MOD_ID, Registry.ITEM_REGISTRY);
    public static final RegistrySupplier<Item> EXAMPLE_ITEM = ITEMS.register("example_item", () ->
            new Item(new Item.Properties().tab(ContinueButtonMod.EXAMPLE_TAB)));

    public static void init() {
        ITEMS.register();

        System.out.println(ContinueButtonExpectPlatform.getConfigDirectory().toAbsolutePath().normalize().toString());
    }
}