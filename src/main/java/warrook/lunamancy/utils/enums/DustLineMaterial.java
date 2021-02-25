package warrook.lunamancy.utils.enums;

import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.StringIdentifiable;
import warrook.lunamancy.ModManifest;

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

    public static int getColor(DustLineMaterial value) {
        if (value == COAL) {
            return Blocks.COAL_BLOCK.getDefaultMaterialColor().color;
        }
        if (value == BONE) {
            return Blocks.BONE_BLOCK.getDefaultMaterialColor().color;
        }
        return -1;
    }
}
