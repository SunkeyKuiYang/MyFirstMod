package com.sunkey.mymod;

import com.sunkey.mymod.effect.SuperEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * @ClassName ModEffects
 * @Description 模组新效果
 * @Author Haoran_Jiang
 * @Date 2025/3/17 16:37
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEffects {
    public static final DeferredRegister<MobEffect> EFFECTS =
            DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, MyMod.MOD_ID);

    // 注册药水效果
    public static final RegistryObject<MobEffect> SUPER_EFFECT = EFFECTS.register("super_effect",
            SuperEffect::new);



    public static void register(IEventBus bus){
        EFFECTS.register(bus);
    }

}
