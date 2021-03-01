package warrook.lunamancy.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import warrook.lunamancy.utils.network.LightTransmitter;

//TODO: Probably a block entity but I also have to figure out how the whole thing works
public class ChannelerBlock extends LightTransmitterBlockImpl implements LightTransmitter {
    public static final VoxelShape BOX;

    public ChannelerBlock() {
        super("moonlight_channeler", FabricBlockSettings.of(Material.METAL).nonOpaque());
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return BOX;
    }

    static {
        BOX = createCuboidShape(5d, 0d, 5d, 11d, 16d, 11d);
    }
}
