package warrook.magicpower;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;

public class MagicPowerClient implements ClientModInitializer {

    private static final MinecraftClient CLIENT = MinecraftClient.getInstance();

    @Override
    public void onInitializeClient() {
        ModManifestClient.registerAll();

    }


}
