package warrook.magicpower;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.impl.client.texture.SpriteRegistryCallbackHolder;
import net.fabricmc.fabric.impl.renderer.SpriteFinderImpl;
import net.fabricmc.fabric.mixin.object.builder.ModelPredicateProviderRegistryAccessor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.model.SpriteAtlasManager;
import net.minecraft.client.texture.SpriteAtlasTexture;
import warrook.magicpower.entities.LunaMothEntityRenderer;
import warrook.magicpower.models.BowlBlockEntityRenderer;
import warrook.magicpower.utils.PhaseProperty;

public class MagicPowerClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        //TODO: Move to ModManifest, probably.
        BlockEntityRendererRegistry.INSTANCE.register(ModManifest.ModBlocks.BOWL_BLOCK_ENTITY, BowlBlockEntityRenderer::new);


        //Moon phase model property
        ModelPredicateProviderRegistryAccessor.callRegister(PhaseProperty.IDENTIFIER, new PhaseProperty());

        BlockRenderLayerMap.INSTANCE.putBlock(ModManifest.ModBlocks.MAGNIFYING_LENS_BLOCK, RenderLayer.getTranslucent());
        EntityRendererRegistry.INSTANCE.register(ModManifest.ModEntities.LUNA_MOTH, (dispatcher, context) -> new LunaMothEntityRenderer(dispatcher));
    }
}
