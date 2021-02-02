package warrook.magicpower.world;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.block.BlockState;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.RangeDecoratorConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import warrook.magicpower.MagicPower;
import warrook.magicpower.ModManifest;

public class OreFeatures {
    private static final ConfiguredFeature<?,?> OVERWORLD_MOONSTONE_ORE = oreFeature(ModManifest.ModBlocks.MOONSTONE_ORE.getDefaultState(), 7, 8, 0, 55, 6);
    private static final ConfiguredFeature<?,?> OVERWORLD_SILVER_ORE = oreFeature(ModManifest.ModBlocks.SILVER_ORE.getDefaultState(), 6, 1, 0, 31, 2);

    public static void registerAll() {
        register("moonstone_ore", OVERWORLD_MOONSTONE_ORE);
        register("silver_ore", OVERWORLD_SILVER_ORE);
    }

    //probably gonna need to be able to change where it spawns but yolo
    private static void register(String oreName, ConfiguredFeature<?,?> configuredFeature) {
        RegistryKey<ConfiguredFeature<?,?>> key = getKey(MagicPower.defaultID("overworld_" + oreName));
        Registry.register(
                BuiltinRegistries.CONFIGURED_FEATURE,
                key.getValue(),
                configuredFeature
        );
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES, key);
    }

    private static RegistryKey<ConfiguredFeature<?,?>> getKey(Identifier identifier) {
        return RegistryKey.of(Registry.CONFIGURED_FEATURE_WORLDGEN, identifier);
    }

    private static ConfiguredFeature<?,?> oreFeature(
                                              BlockState blockState,
                                              int veinSize,
                                              int offset,
                                              int lowestY,
                                              int highestY,
                                              int count) //per chunk
    {
        return Feature.ORE
                .configure(oreConfig(blockState, veinSize))
                .decorate(Decorator.RANGE.configure(oreDecoratorConfig(offset, lowestY, highestY)))
                .spreadHorizontally()
                .repeat(count);
    }

    private static OreFeatureConfig oreConfig(BlockState blockState, int veinSize) {
        return new OreFeatureConfig(OreFeatureConfig.Rules.BASE_STONE_OVERWORLD, blockState, veinSize);
    }

    private static RangeDecoratorConfig oreDecoratorConfig(int lowestY, int highestY, int maximum) {
        return new RangeDecoratorConfig(lowestY, highestY, maximum);
    }
}
