package com.sunkey.mymod.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

/**
 * @ClassName SmallMetalDoor
 * @Description 一格金属小门
 * @Author Haoran_Jiang
 * @Date 2025/3/25 11:29
 */
public class SmallMetalDoor extends HorizontalDirectionalBlock {

    public static final BooleanProperty OPEN = BlockStateProperties.OPEN;

    // 模型

    // X_SHAPE 默认东西方向
    protected static final VoxelShape X_SHAPE = Block.box(7.0D, 0.0D, 0.0D, 9.0D, 16.0D, 16.0D);
    // Z_SHAPE 南北方向
    protected static final VoxelShape Z_SHAPE = Block.box(0.0D, 0.0D, 7.0D, 16.0D, 16.0D, 9.0D);

    // 碰撞体积
    protected static final VoxelShape X_COLLISION_SHAPE = Block.box(7.0D, 0.0D, 0.0D, 9.0D, 24.0D, 16.0D);
    protected static final VoxelShape Z_COLLISION_SHAPE = Block.box(0.0D, 0.0D, 7.0D, 16.0D, 24.0D, 9.0D);

    // 打开状态模型，四个方向
    protected static final VoxelShape EAST_SHAPE_OPEN = Shapes.or(Block.box(8.0D, 0.0D, 0.0D, 16.0D, 16.0D, 2.0D), Block.box(8.0D, 0.0D, 14.0D, 16.0D, 16.0D, 16.0D));
    protected static final VoxelShape SOUTH_SHAPE_OPEN = Shapes.or(Block.box(0.0D, 0.0D, 8.0D, 2.0D, 16.0D, 16.0D), Block.box(14.0D, 0.0D, 8.0D, 16.0D, 16.0D, 16.0D));
    protected static final VoxelShape WEST_SHAPE_OPEN = Shapes.or(Block.box(0.0D, 0.0D, 0.0D, 8.0D, 16.0D, 2.0D), Block.box(0.0D, 0.0D, 14.0D, 8.0D, 16.0D, 16.0D));
    protected static final VoxelShape NORTH_SHAPE_OPEN = Shapes.or(Block.box(0.0D, 0.0D, 0.0D, 2.0D, 16.0D, 8.0D), Block.box(14.0D, 0.0D, 0.0D, 16.0D, 16.0D, 8.0D));

    // 打开状态模型，碰撞体积
    protected static final VoxelShape EAST_COLLISION_SHAPE_OPEN = Shapes.or(Block.box(8.0D, 0.0D, 0.0D, 16.0D, 16.0D, 2.0D), Block.box(8.0D, 0.0D, 14.0D, 16.0D, 16.0D, 16.0D));
    protected static final VoxelShape SOUTH_COLLISION_SHAPE_OPEN = Shapes.or(Block.box(0.0D, 0.0D, 8.0D, 2.0D, 16.0D, 16.0D), Block.box(14.0D, 0.0D, 8.0D, 16.0D, 16.0D, 16.0D));
    protected static final VoxelShape WEST_COLLISION_SHAPE_OPEN = Shapes.or(Block.box(0.0D, 0.0D, 0.0D, 8.0D, 16.0D, 2.0D), Block.box(0.0D, 0.0D, 14.0D, 8.0D, 16.0D, 16.0D));
    protected static final VoxelShape NORTH_COLLISION_SHAPE_OPEN = Shapes.or(Block.box(0.0D, 0.0D, 0.0D, 2.0D, 16.0D, 8.0D), Block.box(14.0D, 0.0D, 0.0D, 16.0D, 16.0D, 8.0D));


    public SmallMetalDoor(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(OPEN, Boolean.FALSE).setValue(FACING, Direction.EAST));
    }

    // 获取模型
    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        // 模型的关键参数：面向方向和打开状态
        var facing = pState.getValue(FACING);
        var isOpen = pState.getValue(OPEN);

        if (isOpen) {
            return switch (facing){
                case SOUTH -> SOUTH_SHAPE_OPEN;
                case WEST -> WEST_SHAPE_OPEN;
                case NORTH -> NORTH_SHAPE_OPEN;
                default -> EAST_SHAPE_OPEN;
            };
        }else {
           return facing.getAxis() == Direction.Axis.Z ? Z_SHAPE : X_SHAPE;
        }
    }

    // 获取碰撞模型
    @Override
    public VoxelShape getCollisionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        // 模型的关键参数：面向方向和打开状态
        var facing = pState.getValue(FACING);
        var isOpen = pState.getValue(OPEN);

        if (isOpen) {
            return switch (facing){
                case SOUTH -> SOUTH_COLLISION_SHAPE_OPEN;
                case WEST -> WEST_COLLISION_SHAPE_OPEN;
                case NORTH -> NORTH_COLLISION_SHAPE_OPEN;
                default -> EAST_COLLISION_SHAPE_OPEN;
            };
        }else {
            return facing.getAxis() == Direction.Axis.Z ? Z_COLLISION_SHAPE : X_COLLISION_SHAPE;
        }
    }


    // 生物是否可以进行路径获取
    @Override
    public boolean isPathfindable(BlockState pState, BlockGetter pLevel, BlockPos pPos, PathComputationType pType) {
       return pState.getValue(OPEN);
    }


    // 使用门
    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        // 判断是否打开
        if (pState.getValue(OPEN)) {
            pState = pState.setValue(OPEN, Boolean.valueOf(false));
            pLevel.setBlock(pPos, pState, 10);
        } else { // 没有打开，看玩家面向，面向门才能开
            Direction direction = pPlayer.getDirection();
            if (pState.getValue(FACING) == direction.getOpposite()) {
                pState = pState.setValue(FACING, direction);
            }

            pState = pState.setValue(OPEN, Boolean.valueOf(true));
            pLevel.setBlock(pPos, pState, 10);
        }

        boolean flag = pState.getValue(OPEN);
        pLevel.gameEvent(pPlayer, flag ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pPos);
        return InteractionResult.sidedSuccess(pLevel.isClientSide);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, OPEN);
    }

    // 放置方块时候决定一些方块的参数
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        Direction direction = pContext.getHorizontalDirection();
        return this.defaultBlockState().setValue(FACING, direction).setValue(OPEN, Boolean.FALSE);
    }


}
