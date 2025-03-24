package com.sunkey.mymod.config;

import com.sunkey.mymod.MyMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @ClassName CommonConfig
 * @Description 通用forge配置类
 * @Author Haoran_Jiang
 * @Date 2025/3/20 16:57
 */
@Mod.EventBusSubscriber(modid = MyMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonConfig {

    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    // 对农业经验farm_xp的增减数量做出配置
    private static final ForgeConfigSpec.IntValue XP_INCREASE = BUILDER.defineInRange("xp_increase", 1, 0, Integer.MAX_VALUE);
    private static final ForgeConfigSpec.IntValue XP_DECREASE = BUILDER.defineInRange("xp_decrease", 1, 0, Integer.MAX_VALUE);

    // 对农业经验能力作用的方块做出配置列表
    private static ForgeConfigSpec.ConfigValue<List<? extends String>> BLOCKS_USE_XP = BUILDER.defineListAllowEmpty("blocks_use_xp", List.of("minecraft:carrots"), CommonConfig::allow);
    private static ForgeConfigSpec.ConfigValue<List<? extends String>> BLOCKS_GET_XP = BUILDER.defineListAllowEmpty("blocks_get_xp", List.of("minecraft:potatoes", "minecraft:melon"), CommonConfig::allow);

    // 判断这个方块是不是包含在mc原版Blocks里面？
    private static boolean allow(Object object){
        return ForgeRegistries.BLOCKS.containsKey(ResourceLocation.parse((String) object));
    }

    // 配置类构建
    public static ForgeConfigSpec COMMON_CONFIG_SPEC = BUILDER.build();


    // 提供对外的参数，以及参数处理，对农业经验作用的方块列表需要转换成blockState，并且存在set集合中防止重复
    public static int xpIncrease, xpDecrease;
    public static Set<Block> blocksUseXp, blocksGetXp;

    private static void getConfig(){
        xpIncrease = XP_INCREASE.get();
        xpDecrease = XP_DECREASE.get();
        blocksUseXp = BLOCKS_USE_XP.get().stream().map( (blockName) -> ForgeRegistries.BLOCKS.getValue(ResourceLocation.parse(blockName))).collect(Collectors.toSet());
        blocksGetXp = BLOCKS_GET_XP.get().stream().map( (blockName) -> ForgeRegistries.BLOCKS.getValue(ResourceLocation.parse(blockName))).collect(Collectors.toSet());
    }


    /**
     * 执行getConfig()的时间点
     * @param event 客户端完全加载完毕后的事件
     */
    @SubscribeEvent
    public static void onSetup (FMLCommonSetupEvent event){
        getConfig();
    }

    /**
     * 执行getConfig()的时间点
     * @param event 模组配置加载，重载后的事件
     */
    @SubscribeEvent
    public static void onload(ModConfigEvent.Reloading event){
        getConfig();
    }
}
