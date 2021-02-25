package warrook.lunamancy.items;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public class WandItem extends Item {
    public WandItem() {
        super(new FabricItemSettings()
                .maxCount(1)
                .group(ItemGroup.TOOLS)
        );
    }
}
