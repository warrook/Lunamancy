package warrook.magicpower;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.Level;
import warrook.magicpower.blocks.BowlBlock;
import warrook.magicpower.blocks.MagnifyingLensBlock;
import warrook.magicpower.blocks.entities.BowlBlockEntity;
import warrook.magicpower.blocks.entities.MagnifyingLensBlockEntity;
import warrook.magicpower.entities.LunaMothEntity;
import warrook.magicpower.items.MoonClockItem;
import warrook.magicpower.world.OreFeatures;

public class ModManifest
{
    public static void RegisterAll()
    {
        ModItems.registerItems();
        ModBlocks.registerBlocks();
        ModEntities.registerEntities();

        OreFeatures.registerAll();
    }

    public static class ModItems {
        public static final Item FABRIC_ITEM = new FabricItem(new FabricItemSettings().group(ItemGroup.MISC));
        public static final Item MOONSTONE = new Item(new FabricItemSettings().group(ItemGroup.MISC));
        public static final Item SILVER_INGOT = new Item(new FabricItemSettings().group(ItemGroup.MATERIALS));
        public static final Item SILVER_NUGGET = new Item(new FabricItemSettings().group(ItemGroup.MATERIALS));
        public static final Item MOON_CLOCK = new MoonClockItem(new FabricItemSettings().group(ItemGroup.MISC));

        //TODO: Silver tools

        static void registerItems()
        {
            register("fabric_item", FABRIC_ITEM);
            register("moonstone", MOONSTONE);
            register("silver_ingot", SILVER_INGOT);
            register("silver_nugget", SILVER_NUGGET);
            register("moon_clock", MOON_CLOCK);

            MagicPower.log(Level.INFO, "Items registered");
        }

        static void register(String itemName, Item item)
        {
            Registry.register(Registry.ITEM, MagicPower.defaultID(itemName), item);
        }
    }

    public static class ModBlocks {
        public static final Block MOONSTONE_ORE = new Block(FabricBlockSettings.of(Material.STONE).strength(4.0f).breakByTool(FabricToolTags.PICKAXES, 1).requiresTool());
        public static final Block SILVER_ORE = new Block(FabricBlockSettings.of(Material.STONE).breakByTool(FabricToolTags.PICKAXES, 2).requiresTool());
        public static final Block MOONSTONE_BLOCK = new Block(FabricBlockSettings.of(Material.GLASS)); //TODO: Actual real settings

        public static final Block MAGNIFYING_LENS_BLOCK = new MagnifyingLensBlock();
        public static final BlockEntityType<MagnifyingLensBlockEntity> MAGNIFYING_LENS_BLOCK_ENTITY = BlockEntityType.Builder.create(MagnifyingLensBlockEntity::new, MAGNIFYING_LENS_BLOCK).build(null);
        public static final Block BOWL_BLOCK = new BowlBlock();
        public static final BlockEntityType<BowlBlockEntity> BOWL_BLOCK_ENTITY = BlockEntityType.Builder.create(BowlBlockEntity::new, BOWL_BLOCK).build(null);

        static void registerBlocks() {
            register("moonstone_ore", MOONSTONE_ORE, ItemGroup.BUILDING_BLOCKS);
            register("silver_ore", SILVER_ORE, ItemGroup.BUILDING_BLOCKS);
            register("moonstone_block", MOONSTONE_BLOCK);

            //Block Entities
            register("magnifying_lens", MAGNIFYING_LENS_BLOCK, MAGNIFYING_LENS_BLOCK_ENTITY);
            register("bowl", BOWL_BLOCK, BOWL_BLOCK_ENTITY);

            //Registry.register(BuiltinRegistries.CONFIGURED_FEATURE,)

            MagicPower.log(Level.INFO, "Blocks registered");
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

        static void register(String blockName, BlockEntityType blockEntityType) {
            Registry.register(Registry.BLOCK_ENTITY_TYPE, MagicPower.defaultID(blockName), blockEntityType);
        }

        static void register(String blockName, Block block, BlockEntityType blockEntityType) {
            register(blockName, block);
            register(blockName, blockEntityType);
        }
    }

    public static class ModEntities {

        public static final EntityType<LunaMothEntity> LUNA_MOTH = FabricEntityTypeBuilder
                .create(SpawnGroup.AMBIENT, LunaMothEntity::new)
                .dimensions(EntityDimensions.fixed(1f,1f))
                .build();

        static void registerEntities() {
            register("luna_moth", LUNA_MOTH, LunaMothEntity.createMobAttributes());

            MagicPower.log(Level.INFO, "Entities registered");
        }

        static void register(String entityName, EntityType entityType, DefaultAttributeContainer.Builder attributes)
        {
            Registry.register(Registry.ENTITY_TYPE, MagicPower.defaultID(entityName), entityType);
            FabricDefaultAttributeRegistry.register(entityType, attributes);
        }
    }
}
