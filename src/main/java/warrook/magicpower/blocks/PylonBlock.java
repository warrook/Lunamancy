package warrook.magicpower.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;
import warrook.magicpower.blocks.entities.PylonBlockEntity;

public class PylonBlock extends BaseBlock implements BlockEntityProvider {

    protected static final VoxelShape OUTLINE;

    public PylonBlock() {
        super("pylon", FabricBlockSettings
                .of(Material.STONE)
                .nonOpaque()
        );
    }

    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new PylonBlockEntity();
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return OUTLINE;
    }



    static {
        OUTLINE = VoxelShapes.union(createCuboidShape(4.0D, 8.0D, 4.0D, 12.0D, 12.0D, 12.0D), createCuboidShape(6.0d, 0.0d, 6.0d, 10d, 8d, 10d));
    }
}
