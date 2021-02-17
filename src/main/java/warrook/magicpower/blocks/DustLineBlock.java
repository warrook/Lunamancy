package warrook.magicpower.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldView;
import warrook.magicpower.utils.DustLineMaterial;

public class DustLineBlock extends Block {

    public static final EnumProperty<DustLineMaterial> DUST_MATERIAL = EnumProperty.of("material", DustLineMaterial.class);

    public DustLineBlock() {
        super(FabricBlockSettings.of(Material.AIR)
                .nonOpaque()
                .breakInstantly()
        );

        this.setDefaultState(this.getDefaultState()
                .with(DUST_MATERIAL, DustLineMaterial.NONE)
        );
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(DUST_MATERIAL);
        super.appendProperties(builder);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockPos down = pos.down();
        BlockState downState = world.getBlockState(down);
        return downState.isSideSolidFullSquare(world, down, Direction.UP);
    }
}
