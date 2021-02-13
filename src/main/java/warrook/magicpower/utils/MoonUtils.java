package warrook.magicpower.utils;

import net.fabricmc.fabric.api.renderer.v1.model.SpriteFinder;
import net.fabricmc.fabric.impl.client.indigo.renderer.helper.TextureHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.model.SpriteAtlasManager;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.data.client.model.Texture;
import net.minecraft.data.client.model.TextureKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import warrook.magicpower.MagicPower;

public class MoonUtils {

    public static boolean canSeeSky(World world, BlockPos pos) {
        return world.getDimension().hasSkyLight() && world.isSkyVisible(pos);
    }

    public static int getMoonPhase(World world) {
        return world != null ? world.getMoonPhase() : 0;
    }
    public static float getMoonPhaseNormalized(World world) {
        return getMoonPhase(world) / 7f;
    }

    public static Identifier getMoonClockSprite(World world) {
        String path = "textures/item/moon_clock_0" + getMoonPhase(world) + ".png";
        return new Identifier(MagicPower.MOD_ID, path);
    }

    public static Pair<Float, Float> getMoonlight(World world) {
        float b, d;
        int phase = getMoonPhase(world);

        switch (phase) {
            case 0:
                b = 1f;
                break;
            case 1:
            case 7:
                b = 0.75f;
                break;
            case 2:
            case 6:
                b = 0.5f;
                break;
            case 3:
            case 5:
                b = 0.25f;
                break;
            case 4:
            default:
                b = 0f;
        }
        d = 1f - b;

        return new Pair(b, d);
    }
}
