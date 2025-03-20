package com.sunkey.mymod;

import com.mojang.logging.LogUtils;
import com.sunkey.mymod.capability.farmxp.PlayerFarmXpProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AttachCapabilitiesEvent;
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
        MinecraftForge.EVENT_BUS.addGenericListener(Entity.class, this::attachCapability);// 为玩家附加农业经验能力
    }

    /**
     * 特殊事件GenericEvent的子类事件，特殊处理
     * 农业经验能力附加到玩家身上
     * @param event 附加能力事件
     */
    public void attachCapability(AttachCapabilitiesEvent<Entity> event){
        if (event.getObject() instanceof Player player) { // 判断当前注册能力的是不是玩家
            // 判断当前添加能力的玩家是否拥有农业经验能力，没有再添加
            if (!player.getCapability(PlayerFarmXpProvider.PLAYER_FARM_XP_CAPABILITY).isPresent()) {
                event.addCapability(ResourceLocation.fromNamespaceAndPath(MyMod.MOD_ID, "farm_xp"), new PlayerFarmXpProvider());
            }
        }
    }

}
