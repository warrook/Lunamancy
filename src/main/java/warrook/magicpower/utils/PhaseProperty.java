package warrook.magicpower.utils;

import net.minecraft.client.item.ModelPredicateProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class PhaseProperty implements ModelPredicateProvider {

    public static final Identifier IDENTIFIER = new Identifier("phase");

    @Override
    public float call(ItemStack stack, @Nullable ClientWorld world, @Nullable LivingEntity entity) {
        if (entity == null && !stack.isInFrame())
            return 0.25f;

        Entity currentEntity;
        if (entity != null)
            currentEntity = entity;
        else
            currentEntity = stack.getFrame();
        if (currentEntity == null)
                return 0.25f;

        World currentWorld;
        if (world != null)
            currentWorld = world;
        else
            currentWorld = currentEntity.world;

        if (currentWorld == null)
            return 0.25f;

        return currentWorld.getMoonPhase() / 7f;
    }
}
