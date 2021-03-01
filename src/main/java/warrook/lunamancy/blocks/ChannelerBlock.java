package warrook.lunamancy.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import warrook.lunamancy.utils.network.LightTransmitter;

public class ChannelerBlock extends LightTransmitterBlockImpl {
    public static final VoxelShape BOX;

    public ChannelerBlock() {
        super("moonlight_channeler", FabricBlockSettings.of(Material.METAL).nonOpaque());
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return BOX;
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        super.onStateReplaced(state, world, pos, newState, moved);
    }

    static {
        BOX = VoxelShapes.union(
                /*base*/ createCuboidShape(6d, 0d, 6d, 10d, 3d, 10d),
                /*rod*/ createCuboidShape(7d, 3d, 7d, 9d, 12d, 9d),
                /*focus*/ createCuboidShape(6d, 12d, 6d, 10d, 16d, 10d)
        );
    }
}
