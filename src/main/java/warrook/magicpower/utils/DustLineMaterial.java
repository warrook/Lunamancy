package warrook.magicpower.utils;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StringIdentifiable;
import warrook.magicpower.ModManifest;

public enum DustLineMaterial implements StringIdentifiable {
    COAL("coal"),
    BONE("bone"),
    NONE("none");

    private final String name;

    private DustLineMaterial(String name) {
        this.name = name;
    }

    public String toString() {
        return this.asString();
    }

    @Override
    public String asString() {
        return this.name;
    }

    public boolean isFilled() {
        return this != NONE;
    }

    public static DustLineMaterial getValue(Item item) {
        if (item == ModManifest.ModItems.COAL_DUST)
            return COAL;
        if (item == ModManifest.ModItems.BONE_DUST)
            return BONE;
        return NONE;
    }
}
