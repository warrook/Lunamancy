package warrook.lunamancy;

import net.fabricmc.api.ModInitializer;

import net.minecraft.util.Identifier;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Lunamancy implements ModInitializer {

    public static Logger LOGGER = LogManager.getLogger();

    public static final String MOD_ID = "lunamancy";
    public static final String MOD_NAME = "Lunamancy";

    @Override
    public void onInitialize() {
        log(Level.INFO, "Initializing");
        ModManifest.registerAll();

    }

    public static Identifier defaultID(String objectName)
    {
        return new Identifier(MOD_ID, objectName);
    }

    public static void log(Level level, String message){
        LOGGER.log(level, "["+MOD_NAME+"] " + message);
    }

}

