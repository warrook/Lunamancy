package warrook.magicpower;

import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.mixin.object.builder.ModelPredicateProviderRegistryAccessor;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.render.RenderLayer;
import warrook.magicpower.blocks.DustLineBlock;
import warrook.magicpower.client.models.LunaMothEntityRenderer;
import warrook.magicpower.client.models.BowlBlockEntityRenderer;
import warrook.magicpower.utils.enums.DustLineMaterial;
import warrook.magicpower.utils.PhaseProperty;

import static warrook.magicpower.ModManifest.*;

public class ModManifestClient {
    public static void registerAll() {
        registerBlocks();
        registerEntities();
        registerMisc();
    }

    private static void registerBlocks() {
        //blocks
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getTranslucent(), ModBlocks.LENS_STAND);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.DUST_LINE, RenderLayer.getCutout());
        ColorProviderRegistry.BLOCK.register(((state, world, pos, tintIndex) -> world != null && pos != null ? BiomeColors.getWaterColor(world, pos) : -1),
                ModBlocks.PYLON_BLOCK,
                ModBlocks.MOONLIGHT_BASIN
        );
        ColorProviderRegistry.ITEM.register(((stack, tintIndex) -> 0x3F76E4),
                ModBlocks.getBlockItem(ModBlocks.PYLON_BLOCK),
                ModBlocks.getBlockItem(ModBlocks.MOONLIGHT_BASIN)
        );
        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> DustLineMaterial.getColor(state.get(DustLineBlock.DUST_MATERIAL)), ModBlocks.DUST_LINE);

        //block entities
        BlockEntityRendererRegistry.INSTANCE.register(ModBlocks.BOWL_BLOCK_ENTITY, BowlBlockEntityRenderer::new);

    }

    private static void registerEntities() {
        EntityRendererRegistry.INSTANCE.register(ModEntities.LUNA_MOTH, (dispatcher, context) -> new LunaMothEntityRenderer(dispatcher));
    }

    private static void registerMisc() {
        //Moon phase model property
        ModelPredicateProviderRegistryAccessor.callRegister(PhaseProperty.IDENTIFIER, new PhaseProperty());
    }
}
