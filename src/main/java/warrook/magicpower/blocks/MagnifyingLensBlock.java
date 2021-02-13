package warrook.magicpower.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import warrook.magicpower.blocks.entities.MagnifyingLensBlockEntity;
import warrook.magicpower.utils.MoonUtils;

public class MagnifyingLensBlock extends Block implements BlockEntityProvider {
    protected static final VoxelShape BOUNDS = Block.createCuboidShape(4.0D,0.0D,4.0D,12.0D,10.0D,12.0D);

    public static final BooleanProperty SKY_VISIBLE = BooleanProperty.of("sky_visible");

    public MagnifyingLensBlock() {
        this(FabricBlockSettings.of(Material.METAL).breakInstantly().nonOpaque());
    }

    public MagnifyingLensBlock(AbstractBlock.Settings settings) {
        super(settings);
        setDefaultState(getStateManager().getDefaultState().with(SKY_VISIBLE, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(SKY_VISIBLE);
        super.appendProperties(builder);
    }

    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new MagnifyingLensBlockEntity();
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return BOUNDS;
    }

    public static void updateState(BlockState state, World world, BlockPos pos) {
        world.setBlockState(pos, state.with(SKY_VISIBLE, MoonUtils.canSeeSky(world, pos)));
    }
}
