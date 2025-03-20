package com.sunkey.mymod.capability.farmxp;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @ClassName PlayerFarmXpProvider
 * @Description 玩家农业经验Provider, 使用Capability”能力“去实现玩家农业经验
 * @Author Haoran_Jiang
 * @Date 2025/3/19 16:40
 */
public class PlayerFarmXpProvider implements ICapabilityProvider, INBTSerializable {

    private PlayerFarmXp playerFarmXp;

    public static final Capability<PlayerFarmXp> PLAYER_FARM_XP_CAPABILITY = CapabilityManager.get(new CapabilityToken<>(){});

    private final LazyOptional<PlayerFarmXp> lazyOptional = LazyOptional.of(() -> this.playerFarmXp);

    public PlayerFarmXpProvider(){
        playerFarmXp = new PlayerFarmXp();
    }

    // 序列化和反序列化NBT数据
    @Override
    public Tag serializeNBT() {
        var tag = new CompoundTag();
        playerFarmXp.saveNBTData(tag);
        return tag;
    }
    @Override
    public void deserializeNBT(Tag nbt) {
        playerFarmXp.loadNBTData((CompoundTag) nbt);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return getCapability(cap);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        if (cap == PLAYER_FARM_XP_CAPABILITY) {
            return lazyOptional.cast();
        }else {
            return lazyOptional.empty();
        }
    }


}
