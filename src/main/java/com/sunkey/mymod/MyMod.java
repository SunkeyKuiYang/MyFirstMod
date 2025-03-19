package com.sunkey.mymod;

import com.mojang.logging.LogUtils;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

/**
 * @ClassName MyMod
 * @Description 模组入口类
 * @Author Haoran_Jiang
 * @Date 2025/3/12 15:47
 */

@Mod(MyMod.MOD_ID)//标记Forge识别的Mod ID
public class MyMod {
    public static final String MOD_ID = "mymod";
    private static final Logger LOGGER = LogUtils.getLogger();

    public MyMod(FMLJavaModLoadingContext context){
        LOGGER.info("Hello, Minecraft Modding!");
        IEventBus bus = context.getModEventBus();
        ModItems.register(bus); // 物品注册
        ModBlocks.register(bus); //方块注册
        ModEffects.register(bus); // 效果注册
        ModCreativeModeTabs.register(bus); // 创造模式物品栏注册
    }

}
