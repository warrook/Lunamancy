package warrook.lunamancy;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
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
import warrook.lunamancy.blocks.*;
import warrook.lunamancy.blocks.entities.*;
import warrook.lunamancy.items.*;
import warrook.lunamancy.entities.LunaMothEntity;
import warrook.lunamancy.items.MoonClockItem;
import warrook.lunamancy.utils.enums.LensType;
import warrook.lunamancy.world.OreFeatures;

public class ModManifest
{
    public static void registerAll()
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
        public static final Item COAL_DUST = new Item(new FabricItemSettings().group(ItemGroup.MISC));
        public static final Item BONE_DUST = new Item(new FabricItemSettings().group(ItemGroup.MISC));
        public static final Item DUST_POUCH = new Item(new FabricItemSettings().group(ItemGroup.TOOLS));
        public static final Item DUST_POUCH_BONE = new DustPouchBone();
        public static final Item DUST_POUCH_COAL = new DustPouchCoal();

        public static final Item LENS_FOCUSING = new LensItem(LensType.FOCUSING);
        public static final Item LENS_DIFFUSING = new LensItem(LensType.DIFFUSING);

        //Tools
        @Deprecated
        public static final Item MOON_CLOCK = new MoonClockItem(new FabricItemSettings().group(ItemGroup.MISC));
        public static final Item ATHAME = new AthameItem();
        public static final Item WAND = new WandItem();
        public static final Item SYMBOL = new SymbolItem();

        //TODO: Silver tools

        static void registerItems()
        {
            //register("fabric_item", FABRIC_ITEM);
            register("moonstone", MOONSTONE);
            register("silver_ingot", SILVER_INGOT);
            register("silver_nugget", SILVER_NUGGET);
            register("bone_dust", BONE_DUST);
            register("coal_dust", COAL_DUST);
            register("dust_pouch", DUST_POUCH);
            register("dust_pouch_bone", DUST_POUCH_BONE);
            register("dust_pouch_coal", DUST_POUCH_COAL);

            register("focusing_lens", LENS_FOCUSING);
            register("diffusing_lens", LENS_DIFFUSING);

            register("moon_clock", MOON_CLOCK);
            register("athame", ATHAME);
            register("wand", WAND);
            register("symbol", SYMBOL);

            Lunamancy.log(Level.INFO, "Items registered");
        }

        static void register(String itemName, Item item)
        {
            Registry.register(Registry.ITEM, Lunamancy.defaultID(itemName), item);
        }
    }

    public static class ModBlocks {
        //TODO: Block stats
        //TODO: Runes, or maybe bone dice? Yew/rowan wood? All sorts of ireland woods? Oghma?
        public static final BaseBlock MOONSTONE_ORE = new BaseBlock("moonstone_ore", FabricBlockSettings.of(Material.STONE).strength(4.0f).breakByTool(FabricToolTags.PICKAXES, 1).requiresTool());
        public static final BaseBlock MOONSTONE_BLOCK = new BaseBlock("moonstone_block", FabricBlockSettings.of(Material.GLASS));
        public static final BaseBlock SILVER_ORE = new BaseBlock("silver_ore", FabricBlockSettings.of(Material.STONE).breakByTool(FabricToolTags.PICKAXES, 2).requiresTool());
        public static final BaseBlock SILVER_BLOCK = new BaseBlock("silver_block", FabricBlockSettings.of(Material.METAL));
        public static final DustLineBlock DUST_LINE = new DustLineBlock();
        public static final LensStandBlock LENS_STAND = new LensStandBlock();
        public static final ChannelerBlock MOONLIGHT_CHANNELER = new ChannelerBlock();

        public static final BowlBlock BOWL_BLOCK = new BowlBlock();
        public static final BlockEntityType<BowlBlockEntity> BOWL_BLOCK_ENTITY = BlockEntityType.Builder.create(BowlBlockEntity::new, BOWL_BLOCK).build(null);
        public static final PylonBlock PYLON_BLOCK = new PylonBlock();
        public static final BlockEntityType<PylonBlockEntity> PYLON_BLOCK_ENTITY = BlockEntityType.Builder.create(PylonBlockEntity::new, PYLON_BLOCK).build(null);
        public static final BasinBlock MOONLIGHT_BASIN = new BasinBlock();
        public static final BlockEntityType<BasinBlockEntity> BASIN_BLOCK_ENTITY = BlockEntityType.Builder.create(BasinBlockEntity::new, MOONLIGHT_BASIN).build(null);
        public static final PrismBlock PRISM = new PrismBlock();
        public static final BlockEntityType<PrismBlockEntity> PRISM_BLOCK_ENTITY = BlockEntityType.Builder.create(PrismBlockEntity::new, PRISM).build(null);

        static void registerBlocks() {
            //TODO: ItemGroup
            register(MOONSTONE_ORE, ItemGroup.BUILDING_BLOCKS);
            register(MOONSTONE_BLOCK, ItemGroup.BUILDING_BLOCKS);
            register(SILVER_ORE, ItemGroup.BUILDING_BLOCKS);
            register(SILVER_BLOCK, ItemGroup.BUILDING_BLOCKS);
            registerBlockOnly(DUST_LINE);
            register(LENS_STAND);
            register(MOONLIGHT_CHANNELER);

            //Block Entities
            register(BOWL_BLOCK, BOWL_BLOCK_ENTITY);
            register(PYLON_BLOCK, PYLON_BLOCK_ENTITY);
            register(MOONLIGHT_BASIN, BASIN_BLOCK_ENTITY);
            register(PRISM, PRISM_BLOCK_ENTITY);

            //Network

            Lunamancy.log(Level.INFO, "Blocks registered");
        }

        static void register(BaseBlock block) {
            register(block, ItemGroup.MISC);
        }

        static void register(BaseBlock block, ItemGroup itemGroup) {
            registerBlockOnly(block);
            registerBlockItem(block, itemGroup);
        }

        static void register(BaseBlock block, BlockEntityType blockEntityType) {
            register(block, blockEntityType, ItemGroup.MISC);
        }

        static void register(BaseBlock block, BlockEntityType blockEntityType, ItemGroup itemGroup) {
            register(block, itemGroup);
            registerBlockEntity(block, blockEntityType);
        }

        private static void registerBlockOnly(BaseBlock block) {
            Registry.register(Registry.BLOCK, Lunamancy.defaultID(block.registryName), block);
        }

        private static void registerBlockItem(BaseBlock block, ItemGroup itemGroup) {
            Registry.register(Registry.ITEM, Lunamancy.defaultID(block.registryName), new BlockItem(block, new FabricItemSettings().group(itemGroup)));
        }

        private static void registerBlockEntity(BaseBlock block, BlockEntityType blockEntityType) {
            Registry.register(Registry.BLOCK_ENTITY_TYPE, Lunamancy.defaultID(block.registryName), blockEntityType);
        }

        public static Item getBlockItem(BaseBlock block) {
            return Registry.ITEM.get(Lunamancy.defaultID(block.registryName));
        }
    }

    public static class ModEntities {

        public static final EntityType<LunaMothEntity> LUNA_MOTH = FabricEntityTypeBuilder
                .create(SpawnGroup.AMBIENT, LunaMothEntity::new)
                .dimensions(EntityDimensions.fixed(1f,1f))
                .build();

        static void registerEntities() {
            register("luna_moth", LUNA_MOTH, LunaMothEntity.createMobAttributes());

            Lunamancy.log(Level.INFO, "Entities registered");
        }

        static void register(String entityName, EntityType entityType, DefaultAttributeContainer.Builder attributes)
        {
            Registry.register(Registry.ENTITY_TYPE, Lunamancy.defaultID(entityName), entityType);
            FabricDefaultAttributeRegistry.register(entityType, attributes);
        }
    }
}
