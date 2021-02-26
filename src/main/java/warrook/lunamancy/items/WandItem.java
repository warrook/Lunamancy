package warrook.lunamancy.items;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import warrook.lunamancy.utils.ToolUser;
import warrook.lunamancy.utils.network.LightTransmitter;

public class WandItem extends Item {
    private BlockPos lastUsedOn;

    public WandItem() {
        super(new FabricItemSettings()
                .maxCount(1)
                .group(ItemGroup.TOOLS)
        );
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if (context.getWorld() != null && context.getPlayer() != null) {
            World world = context.getWorld();
            if (!world.isClient) {
                BlockPos pos = context.getBlockPos();
                Block blockAt = world.getBlockState(pos).getBlock();
                if (blockAt instanceof LightTransmitter) {
                    if (lastUsedOn == null) {
                        lastUsedOn = pos;
                    } else {

                    }
                }
                if (blockAt instanceof ToolUser) {
                    ((ToolUser) blockAt).onToolUse(world, pos, context.getPlayer());
                }
            }
        }

        return super.useOnBlock(context); //pass
    }
}
