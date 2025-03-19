package com.sunkey.mymod.event;

import com.sunkey.mymod.MyMod;
import net.minecraftforge.fml.common.Mod;

/**
 * @ClassName ModEventListener
 * @Description FML(MOD)事件监听类，这类事件指游戏生命周期发生的事，比如方块物品的注册，游戏启动，配置文件加载等
 * @Author Haoran_Jiang
 * @Date 2025/3/19 10:34
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = MyMod.MOD_ID)
public class ModEventListener {
}
