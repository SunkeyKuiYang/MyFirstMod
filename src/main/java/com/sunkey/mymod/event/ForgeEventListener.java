package com.sunkey.mymod.event;

import com.sunkey.mymod.MyMod;
import com.sunkey.mymod.capability.farmxp.PlayerFarmXp;
import com.sunkey.mymod.capability.farmxp.PlayerFarmXpProvider;
import com.sunkey.mymod.command.GetFarmXpCommand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.StemBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

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


    /**
     * 对带梗的作物进行处理（西瓜南瓜等）
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
     * 收取作物增长农业经验
     * @param event 破坏行为相关事件
     */
    @SubscribeEvent
    public static void breakCropToIncreaseFarmXp(BlockEvent.BreakEvent event){
        // 如果破坏的是作物
        if (event.getState().getBlock() instanceof CropBlock) {
            // 增长玩家的农业经验
            event.getPlayer().getCapability(PlayerFarmXpProvider.PLAYER_FARM_XP_CAPABILITY).ifPresent(PlayerFarmXp::increase);
        }
    }

    /**
     * 对带梗的作物使用骨粉需要农业经验
     * @param event 玩家使用骨粉时的相关事件
     */
    @SubscribeEvent
    public static void decreaseFarmXpToPreventStem(BonemealEvent event){
        BlockState blockState = event.getBlock();
        if (blockState.getBlock() instanceof StemBlock) {
           event.getEntity().getCapability(PlayerFarmXpProvider.PLAYER_FARM_XP_CAPABILITY).ifPresent( (xp) -> {
               //判断农业经验够不够，不够的话再取消mc默认事件
               if (!xp.decrease()) event.setCanceled(true);
           });
        }
    }
}
