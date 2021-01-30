package warrook.magicpower;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.registry.Registry;

public class ModManifest
{
    public static void RegisterAll()
    {
        ModItems.registerItems();
        ModBlocks.registerBlocks();
    }

    public static class ModItems {
        public static final Item FABRIC_ITEM = new FabricItem(new FabricItemSettings().group(ItemGroup.MISC));
        public static final Item MOONSTONE = new Item(new FabricItemSettings().group(ItemGroup.MISC));
        public static final Item SILVER_INGOT = new Item(new FabricItemSettings().group(ItemGroup.MATERIALS));
        public static final Item SILVER_NUGGET = new Item(new FabricItemSettings().group(ItemGroup.MATERIALS));

        //TODO: Silver tools

        static void registerItems()
        {
            register("fabric_item", FABRIC_ITEM);
            register("moonstone", MOONSTONE);
            register("silver_ingot", SILVER_INGOT);
            register("silver_nugget", SILVER_NUGGET);
        }

        static void register(String itemName, Item item)
        {
            Registry.register(Registry.ITEM, MagicPower.defaultID(itemName), item);
        }
    }

    public static class ModBlocks {
        public static final Block MOONSTONE_ORE = new Block(FabricBlockSettings.of(Material.STONE).strength(4.0f).breakByTool(FabricToolTags.PICKAXES, 1).requiresTool());
        public static final Block SILVER_ORE = new Block(FabricBlockSettings.of(Material.STONE).breakByTool(FabricToolTags.PICKAXES, 2).requiresTool());


        static void registerBlocks() {
            register("moonstone_ore", MOONSTONE_ORE, ItemGroup.BUILDING_BLOCKS);
            register("silver_ore", SILVER_ORE, ItemGroup.BUILDING_BLOCKS);
        }

        static void register(String blockName, Block block)
        {
            Registry.register(Registry.BLOCK, MagicPower.defaultID(blockName), block);
            Registry.register(Registry.ITEM, MagicPower.defaultID(blockName), new BlockItem(block, new FabricItemSettings().group(ItemGroup.MATERIALS)));
        }

        static void register(String blockName, Block block, ItemGroup itemGroup)
        {
            Registry.register(Registry.BLOCK, MagicPower.defaultID(blockName), block);
            Registry.register(Registry.ITEM, MagicPower.defaultID(blockName), new BlockItem(block, new FabricItemSettings().group(itemGroup)));
        }
    }
}