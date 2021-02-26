package warrook.lunamancy.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import warrook.lunamancy.blocks.entities.BasinBlockEntity;
import warrook.lunamancy.utils.ToolUser;

public class BasinBlock extends BaseBlock implements BlockEntityProvider, ToolUser {
    public BasinBlock() {
        super("moonlight_basin", FabricBlockSettings
                .of(Material.METAL)
        );
    }

    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new BasinBlockEntity(1f);
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        if (fromPos.equals(pos.up())) {
            BlockState upState = world.getBlockState(pos.up());
            BasinBlockEntity entity = ((BasinBlockEntity)world.getBlockEntity(pos));
            if (entity != null) {
                entity.checkLens();
            }
        }
        super.neighborUpdate(state, world, pos, block, fromPos, notify);
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        if (world.getBlockEntity(pos) != null) {
            BasinBlockEntity entity = (BasinBlockEntity) world.getBlockEntity(pos);
            if (entity != null)
                entity.checkLens();
        }
    }

    @Override
    public void onToolUse(World world, BlockPos pos, PlayerEntity player) {
        ((BasinBlockEntity) world.getBlockEntity(pos)).onToolUse(world, pos, player);
    }
}
