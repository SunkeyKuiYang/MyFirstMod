package com.sunkey.mymod.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

/**
 * @ClassName SuperEffect
 * @Description 超级药水效果类 demo
 * @Author Haoran_Jiang
 * @Date 2025/3/17 16:43
 */
public class SuperEffect extends MobEffect {

    public SuperEffect(){
        //第一个参数表示效果的类别，这里是有益效果。第二个参数表示效果的颜色，这里是绿色。
        super(MobEffectCategory.BENEFICIAL, 0x00FF00);
    }
}
