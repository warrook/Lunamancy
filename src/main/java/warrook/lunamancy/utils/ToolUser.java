package warrook.lunamancy.utils;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface ToolUser {
    void onToolUse(World world, BlockPos pos, PlayerEntity player);
}
