package warrook.lunamancy;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;

public class LunamancyClient implements ClientModInitializer {

    private static final MinecraftClient CLIENT = MinecraftClient.getInstance();

    @Override
    public void onInitializeClient() {
        ModManifestClient.registerAll();

    }


}
