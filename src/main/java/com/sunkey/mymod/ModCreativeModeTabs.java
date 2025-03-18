package com.sunkey.mymod;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.Collection;


/**
 * @ClassName ModCreativeModeTabs
 * @Description 创造模式标签
 * @Author Haoran_Jiang
 * @Date 2025/3/17 16:59
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModCreativeModeTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MyMod.MOD_ID);

    // 注册创造模式物品栏
    public static final RegistryObject<CreativeModeTab> MYMOD_TAB = CREATIVE_MODE_TABS.register("mymod_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("main.mymod.mymod_tab")) // 这里的键 "main.mymod.mymod_tab" 需要在 lang 文件里定义
                    .icon(() -> new ItemStack(ModItems.SUPER_DIAMOND.get())) // 物品栏图标
                    .displayItems((params, output) -> { // 在物品栏中显示哪些物品
                        output.accept(ModItems.SUPER_DIAMOND.get());
                        output.accept(ModItems.SUPER_WEAPON.get());
                        output.accept(ModItems.SUPER_FOOD.get());
                        output.accept(ModItems.SUPER_POTION.get());
                    })
                    .build());

}
