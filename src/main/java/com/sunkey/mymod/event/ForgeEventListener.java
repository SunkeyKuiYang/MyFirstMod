package com.sunkey.mymod.event;

import com.mojang.logging.LogUtils;
import com.sunkey.mymod.MyMod;
import com.sunkey.mymod.capability.farmxp.PlayerFarmXpProvider;
import com.sunkey.mymod.command.GetFarmXpCommand;
import com.sunkey.mymod.config.CommonConfig;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jline.utils.Log;
import org.slf4j.Logger;

import static net.minecraft.world.level.block.StemBlock.AGE;
import static net.minecraft.world.level.block.StemBlock.MAX_AGE;

/**
 * @ClassName ForgeEventListener
 * @Description Forge事件监听类，这类事件主要指游戏内发生的事，比如进入了一个世界，作物生长等
 * @Author Haoran_Jiang
 * @Date 2025/3/19 10:32
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = MyMod.MOD_ID)
public class ForgeEventListener {

    private static final Logger LOGGER = LogUtils.getLogger();


    /**
     * 对带梗的作物进行处理（西瓜南瓜等）(弃用...)
     * @param event 玩家使用使用骨粉时的相关事件
     */
    //@SubscribeEvent //forge会自动注册该注解标记的方法
    public static void preventStem(BonemealEvent event){
        BlockState blockState = event.getBlock();
        ItemStack itemStack = event.getStack();
        if (blockState.getBlock() instanceof StemBlock) {
            //如果是带梗的作物，自定义事件: 施加骨粉1次age+1 ，age的max值为7, 最大值时施加无效
            event.getLevel().setBlock(event.getPos(), blockState.setValue(AGE, blockState.getValue(AGE) < MAX_AGE ? blockState.getValue(AGE) + 1 : 7), 2);
            itemStack.shrink(1);// 骨粉数量-1
            event.setCanceled(true);//取消默认事件
        }
    }

    /**
     * 注册获取玩家农业经验命令
     * @param event 命令注册相关事件
     */
    @SubscribeEvent
    public static void registerFarmXpCommand(RegisterCommandsEvent event){
        GetFarmXpCommand.register(event.getDispatcher());
    }

    /**
     * 收取配置中的作物增长农业经验
     * @param event 破坏行为相关事件
     */
    @SubscribeEvent
    public static void getFarmXpFromBreakCrop(BlockEvent.BreakEvent event){
        BlockState blockState = event.getState();
        Block block = blockState.getBlock();
        // 如果破坏的是配置中的作物
        if (CommonConfig.blocksGetXp.contains(block)) {
            // 判断骨粉可用的作物是否已经成熟，成熟了才可以增加农业经验
            // 或者判断作物是不是带梗已成熟的作物
            if ((block instanceof BonemealableBlock bonemealableBlock && !bonemealableBlock.isValidBonemealTarget(event.getLevel(), event.getPos(), blockState, true))
            || block instanceof StemGrownBlock) {
                // 增长玩家的农业经验
                event.getPlayer().getCapability(PlayerFarmXpProvider.PLAYER_FARM_XP_CAPABILITY).ifPresent(
                        xp -> xp.increase(CommonConfig.xpIncrease)
                );
            }
        }
    }

    /**
     * 对配置中的作物使用骨粉需要农业经验
     * 对带梗的作物使用骨粉需要农业经验(弃用)
     * @param event 玩家使用骨粉时的相关事件
     */
    @SubscribeEvent
    public static void useFarmXpToPreventCrop(BonemealEvent event){
        BlockState blockState = event.getBlock();
        Block block = blockState.getBlock();
        // 如果使用骨粉的是配置中的作物
        if (CommonConfig.blocksUseXp.contains(block)) {
            // 判断骨粉可用的作物是否已经成熟，成熟了不使用骨粉不扣农业经验
            if (block instanceof BonemealableBlock bonemealableBlock && bonemealableBlock.isValidBonemealTarget(event.getLevel(), event.getPos(), blockState, true)) {
                event.getEntity().getCapability(PlayerFarmXpProvider.PLAYER_FARM_XP_CAPABILITY).ifPresent( (xp) -> {
                    //判断农业经验够不够，不够的话再取消mc默认事件
                    if (!xp.decrease(CommonConfig.xpDecrease)) event.setCanceled(true);
                });
            }
        }
    }


    /**
     * 玩家死亡农业经验不会丢失
     * @param event 玩家死亡克隆事件
     */
    @SubscribeEvent
    public static void clonePlayerFarmXpIfDead(PlayerEvent.Clone event){
        //LOGGER.info("clonePlayerFarmXpIfDead...");
        var original = event.getOriginal();
        // 先“复活”能力，玩家死亡后,原有的能力数据不再活跃不能再进行直接调用了,需要方法使其重新活跃
        original.reviveCaps();
        // 复制农业经验能力数据
        original.getCapability(PlayerFarmXpProvider.PLAYER_FARM_XP_CAPABILITY).ifPresent( old ->
            event.getEntity().getCapability(PlayerFarmXpProvider.PLAYER_FARM_XP_CAPABILITY).ifPresent(xp -> {
                /* LOGGER.info("old xp : {}" , old.getXp());
                LOGGER.info("new xp : {}" , xp.getXp()); */
                xp.setXp(old.getXp());
            })
        );
    }
}
