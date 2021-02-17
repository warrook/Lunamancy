package warrook.magicpower;

import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.mixin.object.builder.ModelPredicateProviderRegistryAccessor;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.render.RenderLayer;
import warrook.magicpower.entities.LunaMothEntityRenderer;
import warrook.magicpower.models.BowlBlockEntityRenderer;
import warrook.magicpower.utils.PhaseProperty;

public class ModManifestClient {
    public static void registerAll() {
        registerBlocks();
        registerEntities();
        registerMisc();
    }

    private static void registerBlocks() {
        //blocks
        BlockRenderLayerMap.INSTANCE.putBlock(ModManifest.ModBlocks.MAGNIFYING_LENS_BLOCK, RenderLayer.getTranslucent());
        ColorProviderRegistry.BLOCK.register(((state, world, pos, tintIndex) -> world != null && pos != null ? BiomeColors.getWaterColor(world, pos) : -1), ModManifest.ModBlocks.PYLON_BLOCK);

        //block entities
        BlockEntityRendererRegistry.INSTANCE.register(ModManifest.ModBlocks.BOWL_BLOCK_ENTITY, BowlBlockEntityRenderer::new);

    }

    private static void registerEntities() {
        EntityRendererRegistry.INSTANCE.register(ModManifest.ModEntities.LUNA_MOTH, (dispatcher, context) -> new LunaMothEntityRenderer(dispatcher));
    }

    private static void registerMisc() {
        //Moon phase model property
        ModelPredicateProviderRegistryAccessor.callRegister(PhaseProperty.IDENTIFIER, new PhaseProperty());
    }
}
