package warrook.magicpower.utils;

import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MoonUtils {

    public static boolean canSeeSky(World world, BlockPos pos) {
        return world.getDimension().hasSkyLight() && world.isSkyVisible(pos);
    }

    public static int getMoonPhase(World world) {
        return world != null ? world.getMoonPhase() : 0;
    }

    public static Pair<Float, Float> getMoonlight(World world)
    {
        float b, d;
        int phase = getMoonPhase(world);

        switch (phase)
        {
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
