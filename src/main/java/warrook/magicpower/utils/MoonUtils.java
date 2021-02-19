package warrook.magicpower.utils;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import warrook.magicpower.MagicPower;
import warrook.magicpower.utils.enums.Moonlight;

public class MoonUtils {

    public static final long TIME_NIGHT_STARTS = 13000L;
    public static final long TIME_NIGHT_ENDS = 23000L;
    public static final long NIGHT_DURATION = TIME_NIGHT_ENDS - TIME_NIGHT_STARTS;
    public static final long TIME_MIDNIGHT = 18000L;
    public static final long FULL_DAY_DURATION = 24000L;


    public static boolean canSeeSky(World world, BlockPos pos) {
        return world.getDimension().hasSkyLight() && world.isSkyVisible(pos);
    }

    public static int getMoonPhase(World world) {
        return world != null ? world.getMoonPhase() : 0;
    }
    public static float getMoonPhaseNormalized(World world) {
        return getMoonPhase(world) / 7f;
    }

    @Environment(EnvType.CLIENT)
    public static Identifier getMoonClockSprite(World world) {
        String path = "textures/gui/moon_phase_0" + getMoonPhase(world) + ".png";
        return new Identifier(MagicPower.MOD_ID, path);
    }

    public static float getWhite(World world) {
        int phase = getMoonPhase(world);

        switch (phase) {
            case 0:
                return 1f;
            case 1:
            case 7:
                return 0.75f;
            case 2:
            case 6:
                return 0.5f;
            case 3:
            case 5:
                return 0.25f;
            case 4:
            default:
                return 0f;
        }
    }

    public static float getBlack(World world) {
        return 1 - getWhite(world);
    }

    public static float getSidedLight(World world, Moonlight light) {
        switch (light) {
            case WHITE:
                return getWhite(world);
            case BLACK:
                return getBlack(world);
            case BOTH:
                return 1f;
            case NEITHER:
            default:
                return 0f;
        }
    }

    public static boolean worldTimeIsBetween(World world, long min, long max) {
        long time = world.getTimeOfDay() % FULL_DAY_DURATION;
        return time >= min && time < max;
    }

    public static float getFlatLightRate(World world) {
        long time = world.getTimeOfDay() % FULL_DAY_DURATION;
        long night = time - TIME_NIGHT_STARTS;

        return MathHelper.abs(night - NIGHT_DURATION / 2f) / 5000f;
    }

    public static float getSidedLightRate(World world, Moonlight light) {
        return getFlatLightRate(world) * getSidedLight(world, light);
    }
}
