package warrook.magicpower.items;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public class SymbolItem extends Item {
    public SymbolItem() {
        super(new FabricItemSettings()
                .group(ItemGroup.TOOLS)
                .maxCount(1)
        );
    }
}
