package com.sunkey.mymod.command;

import com.mojang.brigadier.CommandDispatcher;
import com.sunkey.mymod.capability.farmxp.PlayerFarmXpProvider;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

/**
 * @ClassName GetFarmXpCommand
 * @Description 自定义命令command类，获取当前玩家拥有的农业经验值
 * @Author Haoran_Jiang
 * @Date 2025/3/20 13:17
 */
public class GetFarmXpCommand {

    //注册命令方法
    public static void register(CommandDispatcher<CommandSourceStack> commandDispatcher) {
        commandDispatcher.register(Commands
                .literal("farmxp")
                .executes( (context) -> {
                    context.getSource().getPlayer().getCapability(PlayerFarmXpProvider.PLAYER_FARM_XP_CAPABILITY).ifPresent((xp)-> {
                        context.getSource().sendSuccess( () ->
                            Component.literal("FarmXP: " + xp.getXp())
                        , false);
                    });
                    return 0;
                }));
    }
}
