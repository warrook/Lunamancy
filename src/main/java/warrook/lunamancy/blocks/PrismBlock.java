package warrook.lunamancy.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import warrook.lunamancy.blocks.entities.PrismBlockEntity;
import warrook.lunamancy.utils.ToolUser;

//Store & spread light
public class PrismBlock extends BaseBlock implements BlockEntityProvider, ToolUser {
    public PrismBlock() {
        super("prism", FabricBlockSettings.of(Material.METAL).nonOpaque());
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new PrismBlockEntity();
    }

    @Override
    public void onToolUse(World world, BlockPos pos, PlayerEntity player) {

    }
}
