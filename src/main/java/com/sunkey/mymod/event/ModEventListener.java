package com.sunkey.mymod.event;

import com.sunkey.mymod.MyMod;
import com.sunkey.mymod.capability.farmxp.PlayerFarmXpProvider;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * @ClassName ModEventListener
 * @Description FML(MOD)事件监听类，这类事件指游戏生命周期发生的事，比如方块物品的注册，游戏启动，配置文件加载等
 * @Author Haoran_Jiang
 * @Date 2025/3/19 10:34
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = MyMod.MOD_ID)
public class ModEventListener {


    /**
     * 农业经验能力注册
     * @param event 能力注册相关事件
     */
    @SubscribeEvent
    public static void registerCapability(RegisterCapabilitiesEvent event){
        event.register(PlayerFarmXpProvider.class);
    }
}
