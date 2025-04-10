package com.sunkey.mymod;


import com.mojang.blaze3d.shaders.Effect;
import com.sunkey.mymod.item.Voucher;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * @ClassName ModItems
 * @Description 模组新物品
 * @Author Haoran_Jiang
 * @Date 2025/3/12 15:52
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MyMod.MOD_ID);

    // 注册物品

    //super_diamond 超级钻石
    public static final RegistryObject<Item> SUPER_DIAMOND = ITEMS.register("super_diamond",
            () -> new Item(new Item.Properties().stacksTo(64)));

    //super_weapon 超级钻石剑
    public static final RegistryObject<Item> SUPER_WEAPON = ITEMS.register("super_weapon",
            () -> new SwordItem(Tiers.DIAMOND, // 剑的材质为钻石
                    6, // 额外伤害值，基础伤害加上这个值时最终伤害值
                    1.5F, // 剑的攻击速度，负值表示攻击速度较慢。
                    new Item.Properties()));

    //super_food 超级食物
    public static final RegistryObject<Item> SUPER_FOOD = ITEMS.register("super_food",
            () -> new Item(new Item.Properties()
                    .food(new FoodProperties.Builder()
                            .nutrition(6) //食物提供的饱腹度为6
                            .saturationMod(1.2F) //食物提供的饱和度为1.2F
                            .build())));

    //super_potion 超级药水
    public static final RegistryObject<Item> SUPER_POTION = ITEMS.register("super_potion",
            () -> new Item(new Item.Properties()
                    .stacksTo(2) // 可堆叠
                    .craftRemainder(Items.GLASS_BOTTLE) // 使用后返回玻璃瓶
                    .food(new FoodProperties.Builder()
                            .alwaysEat() // 允许随时使用，指任何饱腹度
                            .effect( () -> new MobEffectInstance(ModEffects.SUPER_EFFECT.get(), 600, 0), 1.0F)// 添加药水效果
                            .build())));

    //super_block 超级方块（物品）
    public static final RegistryObject<Item> SUPER_BLOCK_ITEM = ITEMS.register("super_block",
            () -> new BlockItem(ModBlocks.SUPER_BLOCK.get(), new Item.Properties()));

    //vending 自动贩卖机（物品）
    public static final RegistryObject<Item> VENDING_ITEM = ITEMS.register("vending",
            () -> new BlockItem(ModBlocks.VENDING.get(), new Item.Properties()));

    // voucher 兑换券
    public static final RegistryObject<Item> VOUCHER = ITEMS.register("voucher",
            () -> new Voucher(new Item.Properties()));

    // small_metal_door 小金属门（物品）
    public static final RegistryObject<Item> SMALL_METAL_DOOR_ITEM = ITEMS.register("small_metal_door",
            () -> new BlockItem(ModBlocks.SMALL_METAL_DOOR.get(), new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
