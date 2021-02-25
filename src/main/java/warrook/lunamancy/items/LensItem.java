package warrook.lunamancy.items;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import warrook.lunamancy.utils.enums.LensType;

public class LensItem extends Item {

    public static LensType LENS_TYPE;

    public LensItem(LensType lensType) {
        super(new FabricItemSettings().group(ItemGroup.MATERIALS));

        LENS_TYPE = lensType;
    }
}
