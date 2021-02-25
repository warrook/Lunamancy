package warrook.lunamancy.items;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.apache.logging.log4j.Level;
import warrook.lunamancy.Lunamancy;
import warrook.lunamancy.ModManifest;
import warrook.lunamancy.blocks.entities.BasinBlockEntity;

public class AthameItem extends Item {

    public AthameItem() {
        super(new FabricItemSettings()
                .group(ItemGroup.TOOLS)
                .maxCount(1)
        );
    }
}
