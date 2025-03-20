package com.sunkey.mymod.capability.farmxp;

import net.minecraft.nbt.CompoundTag;

/**
 * @ClassName PlayerFarmXp
 * @Description 玩家农业经验类，使用Capability自定义能力来实现。
 * 设定对带梗的作物(如西瓜、南瓜等)使用骨粉催熟需要使用农业经验值，收获其他作物可以获得农业经验值
 * @Author Haoran_Jiang
 * @Date 2025/3/19 15:44
 */
public class PlayerFarmXp {

    private int xp;

    public PlayerFarmXp() {
        this.xp = 0;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    //增加和减少xp的方法
    public void increase(int i){
        xp += i;
    }
    public void increase(){
        this.increase(1);
    }
    public boolean decrease(int d){
        if (xp >= d) {
            xp -= d;
            return true;
        }else {
            return false;
        }
    }
    public boolean decrease(){
        return this.decrease(1);
    }
    /**
     * mc存储和加载数据都是靠NBT组件
     * @param compoundTag
     */
    public void saveNBTData(CompoundTag compoundTag){
        compoundTag.putInt("farm_xp", xp);
    }

    //加载NBT数据
    public void loadNBTData(CompoundTag compoundTag){
        xp = compoundTag.getInt("farm_xp");
    }
}
