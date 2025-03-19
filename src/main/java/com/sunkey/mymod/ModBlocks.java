package com.sunkey.mymod;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * @ClassName ModBlocks
 * @Description 模组新方块
 * @Author Haoran_Jiang
 * @Date 2025/3/18 14:55
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MyMod.MOD_ID);


    //注册方块

    // super_block 超级方块
    public static final RegistryObject<Block> SUPER_BLOCK = BLOCKS.register("super_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(3.0F) // 硬度3.0
                    .sound(SoundType.STONE) //声音 石头
                    ));

    // vending 自动贩卖机
    public static final RegistryObject<Block> VENDING = BLOCKS.register("vending",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(5.0F) // 硬度5.0
                    .sound(SoundType.ANVIL) //声音 铁砧
            ));


    public static void register(IEventBus bus){
        BLOCKS.register(bus);
    }

}
