package warrook.magicpower.collector;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import warrook.magicpower.MagicPower;

public class CollectorBlockEntityRenderer extends BlockEntityRenderer<MagnifyingLensBlockEntity> {
    //public static final Identifier TEXTURE = new net.minecraft.util.Identifier(MagicPower.MOD_ID, "textures/")
    static final Identifier MOONDIAL = new Identifier(MagicPower.MOD_ID + "textures/gui/moondial_phases.png");
    TextureManager textureManager = MinecraftClient.getInstance().getTextureManager();

    public CollectorBlockEntityRenderer(BlockEntityRenderDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void render(MagnifyingLensBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {

        matrices.push();


        //MinecraftClient.getInstance().//.getItemRenderer().
    }
}
