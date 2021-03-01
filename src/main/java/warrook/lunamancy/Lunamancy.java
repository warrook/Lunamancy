package warrook.lunamancy;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import warrook.lunamancy.utils.network.LightScheduler;

import java.lang.ref.WeakReference;

public class Lunamancy implements ModInitializer {

    private static WeakReference<MinecraftServer> SERVER;
    private static final Logger LOGGER = LogManager.getLogger();

    public static final String MOD_ID = "lunamancy";
    public static final String MOD_NAME = "Lunamancy";

    @Override
    public void onInitialize() {
        log(Level.INFO, "Initializing");
        ModManifest.registerAll();
        ServerLifecycleEvents.SERVER_STARTING.register((server -> {
            SERVER = new WeakReference<>(server);
            log(Level.INFO, "hello from starting");
        }));

        ServerLifecycleEvents.SERVER_STARTED.register((server -> {
            LightScheduler.fromTags(); //it should be safe now
            log(Level.INFO, "hello from started");
        }));
    }

    public static Identifier defaultID(String objectName)
    {
        return new Identifier(MOD_ID, objectName);
    }

    public static void log(Level level, String message){
        LOGGER.log(level, "["+MOD_NAME+"] " + message);
    }

    public static MinecraftServer getServer() {
        return SERVER.get();
    }
}

