package warrook.lunamancy.utils.enums;

import net.minecraft.item.Item;
import net.minecraft.util.StringIdentifiable;
import warrook.lunamancy.ModManifest;

public enum LensType implements StringIdentifiable {
    FOCUSING("focusing"),
    DIFFUSING("diffusing"),
    NONE("none");

    private final String name;
    private LensType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.asString();
    }

    @Override
    public String asString() {
        return this.name;
    }

    public Item getItem() {
        if (name.equals("focusing"))
            return ModManifest.ModItems.LENS_FOCUSING;
        if (name.equals("diffusing"))
            return ModManifest.ModItems.LENS_DIFFUSING;
        return null;
    }

    public static LensType getValue(Item item) {
        if (item == ModManifest.ModItems.LENS_FOCUSING)
            return FOCUSING;
        if (item == ModManifest.ModItems.LENS_DIFFUSING)
            return DIFFUSING;
        return NONE;
    }
}
