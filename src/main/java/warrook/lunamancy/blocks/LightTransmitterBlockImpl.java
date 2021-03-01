package warrook.lunamancy.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.WorldChunk;
import warrook.lunamancy.utils.network.LightNet;
import warrook.lunamancy.utils.network.LightNetManager;
import warrook.lunamancy.utils.network.LightTransmitter;

public abstract class LightTransmitterBlockImpl extends BaseBlock implements LightTransmitter {

    public LightTransmitterBlockImpl(String name, Settings settings) {
        super(name, settings);
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        LightNetManager.of((ServerWorld) world).removeFromAnyNetWithNodePos(pos);
        super.onStateReplaced(state, world, pos, newState, moved);
    }


}
