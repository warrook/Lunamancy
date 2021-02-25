package warrook.magicpower.client.gui;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import warrook.magicpower.MagicPower;
import warrook.magicpower.ModManifest.*;
import warrook.magicpower.blocks.entities.BasinBlockEntity;
import warrook.magicpower.utils.LightContainer;

@Environment(EnvType.CLIENT) //irrelevant, pretty sure
public class MoonHud extends DrawableHelper {
    //private final MinecraftClient client = MinecraftClient.getInstance();

    public static void render(MatrixStack matrices, float partialTick) {
        if (!MinecraftClient.isHudEnabled())
            return;

        MinecraftClient.getInstance().getProfiler().push(MagicPower.MOD_ID + " hud");
        MinecraftClient client = MinecraftClient.getInstance();
        int width = client.getWindow().getScaledWidth();
        int height = client.getWindow().getScaledHeight();
        assert client.player != null;
        ClientPlayerEntity player = client.player;

        matrices.push();

        //Hover over
        if (client.crosshairTarget != null && client.crosshairTarget.getType() == HitResult.Type.BLOCK) {
            assert client.world != null;
            BlockHitResult lookingAt = (BlockHitResult) client.crosshairTarget;
            Block block = client.world.getBlockState(lookingAt.getBlockPos()).getBlock();
            BlockEntity entity = client.world.getBlockEntity(lookingAt.getBlockPos());

            if (entity instanceof ToolHud) {
                if (entity instanceof LightContainer && player.isHolding(ModItems.SYMBOL)) {
                    ((ToolHud) entity).renderHud(matrices);
                }
            }
        }

        matrices.pop();
        MinecraftClient.getInstance().getProfiler().pop();
    }
}
