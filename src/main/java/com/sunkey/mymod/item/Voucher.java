package com.sunkey.mymod.item;

import com.sunkey.mymod.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

/**
 * @ClassName Voucher
 * @Description 兑换券
 * @Author Haoran_Jiang
 * @Date 2025/3/18 17:25
 */
public class Voucher extends Item {
    public Voucher(Properties p_41383_) {
        super(p_41383_);
    }

    // 在物品上使用(右击物品)
    @Override
    public InteractionResult useOn(UseOnContext p_186371_){
        //拿到事件点击的方块
        Level level = p_186371_.getLevel();
        BlockPos blockpos = p_186371_.getClickedPos();
        BlockState blockstate = level.getBlockState(blockpos);
        Block block = blockstate.getBlock();

        // 右击的方块是自动贩卖机
        if (block == ModBlocks.VENDING.get()){
            Player player = p_186371_.getPlayer();
            ItemStack itemStack = new ItemStack(Items.GOLDEN_APPLE);
            player.addItem(itemStack);//给玩家一个金苹果
            ItemStack itemInHand = p_186371_.getItemInHand();
            itemInHand.shrink(1);//玩家手里的物品减少1
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return super.useOn(p_186371_);
    }

}
