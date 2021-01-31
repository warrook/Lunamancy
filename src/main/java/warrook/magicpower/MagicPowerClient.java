package warrook.magicpower;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.mixin.object.builder.ModelPredicateProviderRegistryAccessor;
import net.fabricmc.fabric.mixin.object.builder.ModelPredicateProviderRegistrySpecificAccessor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.util.Identifier;
import org.lwjgl.system.CallbackI;
import warrook.magicpower.collector.CollectorBlockEntityRenderer;
import warrook.magicpower.utils.PhaseProperty;

public class MagicPowerClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        //TODO: Move to ModManifest, probably.
        //BlockEntityRendererRegistry.INSTANCE.register(ModManifest.ModBlocks.COLLECTOR_BLOCK_ENTITY, CollectorBlockEntityRenderer::new);
        ModelPredicateProviderRegistryAccessor.callRegister(PhaseProperty.IDENTIFIER, new PhaseProperty());
    }
}
