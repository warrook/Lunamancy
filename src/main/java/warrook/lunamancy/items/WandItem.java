package warrook.lunamancy.items;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import org.apache.logging.log4j.Level;
import warrook.lunamancy.Lunamancy;
import warrook.lunamancy.utils.LocalizationKeys;
import warrook.lunamancy.utils.ToolUser;
import warrook.lunamancy.utils.network.LightTransmitter;
import warrook.lunamancy.utils.network.LightUtils;

public class WandItem extends Item {
    private BlockPos lastUsedOn = null;
    private RegistryKey<World> lastUsedIn = null;

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
                BlockPos nowUsedOn = context.getBlockPos();
                RegistryKey<World> nowUsedIn = world.getRegistryKey();
                Block blockAt = world.getBlockState(nowUsedOn).getBlock();
                BlockEntity entityAt = world.getBlockEntity(nowUsedOn);

                //LightNet
                if (blockAt instanceof LightTransmitter || entityAt instanceof LightTransmitter) {
                    if (lastUsedOn == null || lastUsedIn == null) {
                        lastUsedOn = nowUsedOn; lastUsedIn = nowUsedIn;
                        Lunamancy.log(Level.INFO, LocalizationKeys.GUI_NET_CONNECTING.translate("start"));
                    } else if (!lastUsedOn.equals(nowUsedOn)) {
                        if (LightUtils.isValidConnection(lastUsedIn, nowUsedIn, lastUsedOn, nowUsedOn)) {
                            LightUtils.connect(world, lastUsedOn, nowUsedOn);
                            lastUsedOn = null; lastUsedIn = null;
                        }
                    }
                }

                //ToolUser
                if (blockAt instanceof ToolUser) {
                    ((ToolUser) blockAt).onToolUse(world, nowUsedOn, context.getPlayer());
                }
            }
        }

        return super.useOnBlock(context); //pass
    }
}
