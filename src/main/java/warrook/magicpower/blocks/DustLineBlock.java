package warrook.magicpower.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.ShapeContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import warrook.magicpower.utils.DustLineMaterial;

public class DustLineBlock extends Block {

    //TODO: hitbox
    //TODO: Make mixed connections possible (a T where one part is a different dust), by clicking on a block with dust on it already with a different dust?
    protected static final VoxelShape DOT_SHAPE;
    public static final EnumProperty<DustLineMaterial> DUST_MATERIAL;
    public static final BooleanProperty NORTH;
    public static final BooleanProperty EAST;
    public static final BooleanProperty SOUTH;
    public static final BooleanProperty WEST;

    private BlockState STATEPART_NO_CONNECTIONS;
    private BlockState STATEPART_ALL_CONNECTIONS;

    public DustLineBlock() {
        super(FabricBlockSettings.of(Material.CARPET)
                .nonOpaque()
                .breakInstantly()
                .noCollision()
        );

        this.setDefaultState(this.getDefaultState()
                .with(DUST_MATERIAL, DustLineMaterial.NONE)
                .with(NORTH, false)
                .with(EAST, false)
                .with(SOUTH, false)
                .with(WEST, false)
        );
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder
                .add(DUST_MATERIAL)
                .add(NORTH)
                .add(EAST)
                .add(SOUTH)
                .add(WEST);
        super.appendProperties(builder);
    }

    public BlockState getConnectedState(BlockView world, BlockPos pos, BlockState state) {
        boolean n = canConnectTo(world, pos.north(), state);
        boolean e = canConnectTo(world, pos.east(), state);
        boolean s = canConnectTo(world, pos.south(), state);
        boolean w = canConnectTo(world, pos.west(), state);

        return state
                .with(NORTH, n)
                .with(EAST, e)
                .with(SOUTH, s)
                .with(WEST, w);
    }

    private boolean canConnectTo(BlockView world, BlockPos pos, BlockState state) {
        BlockState test = world.getBlockState(pos);
        return test.isOf(this) && test.get(DUST_MATERIAL) == state.get(DUST_MATERIAL);
    }

    //This doesn't actually do the thing
    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        if (!oldState.isOf(state.getBlock()) && !world.isClient) {
            for (Direction dir : Direction.Type.VERTICAL) {
                world.updateNeighborsAlways(pos.offset(dir), this);
            }
            for (Direction dir : Direction.Type.HORIZONTAL) {
                this.updateNeighbors(world, pos.offset(dir));
            }
            for (Direction dir : Direction.Type.HORIZONTAL) {
                BlockPos offpos = pos.offset(dir);
                if (world.getBlockState(offpos).isFullCube(world, offpos)) {
                    this.updateNeighbors(world, offpos.up());
                } else {
                    this.updateNeighbors(world, offpos.down());
                }
            }
        }
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (!moved && !state.isOf(newState.getBlock())) {
            super.onStateReplaced(state, world, pos, newState, moved);
            if (!world.isClient) {
                for (Direction dir : Direction.values()) {
                    world.updateNeighborsAlways(pos.offset(dir), this);
                }
                for (Direction dir : Direction.Type.HORIZONTAL) {
                    this.updateNeighbors(world, pos.offset(dir));
                }
                for (Direction dir : Direction.Type.HORIZONTAL) {
                    BlockPos offpos = pos.offset(dir);
                    if (world.getBlockState(offpos).isFullCube(world, offpos)) {
                        this.updateNeighbors(world, offpos.up());
                    } else {
                        this.updateNeighbors(world, offpos.down());
                    }
                }
            }
        }
    }

    private void updateNeighbors(World world, BlockPos pos) {
        if (world.getBlockState(pos).isOf(this)) {
            world.updateNeighborsAlways(pos, this);

            for (Direction direction : Direction.values()) {
                world.updateNeighborsAlways(pos.offset(direction), this);
            }
        }
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        if (!world.isClient) {
            if (!state.canPlaceAt(world, pos)) {
                world.removeBlock(pos, false);
            }
        }
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
        if (direction == Direction.DOWN) {
            return state;
        } else {
            BlockState st = direction == Direction.UP ? state
                    .with(NORTH, this.canConnectTo(world, pos.north(), state))
                    .with(EAST, this.canConnectTo(world, pos.east(), state))
                    .with(SOUTH, this.canConnectTo(world, pos.south(), state))
                    .with(WEST, this.canConnectTo(world, pos.west(), state))
                    : this.getConnectedState(world, pos, this.getDefaultState().with(DUST_MATERIAL, state.get(DUST_MATERIAL)));
            //MagicPower.log(Level.INFO, pos + " " + st.toString());
            return st;
        }
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockPos down = pos.down();
        BlockState downState = world.getBlockState(down);
        return downState.isSideSolidFullSquare(world, down, Direction.UP);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return DOT_SHAPE;
    }

    static {
        DOT_SHAPE = createCuboidShape(3d,0d,3d,13d,1d,13d);
        DUST_MATERIAL = EnumProperty.of("material", DustLineMaterial.class);
        NORTH = BooleanProperty.of("north");
        EAST = BooleanProperty.of("east");
        SOUTH = BooleanProperty.of("south");
        WEST = BooleanProperty.of("west");
    }

    {
        STATEPART_NO_CONNECTIONS = this.getDefaultState();
        STATEPART_ALL_CONNECTIONS = this.getDefaultState()
                .with(NORTH, true)
                .with(EAST, true)
                .with(SOUTH, true)
                .with(WEST, true);
    }
}
