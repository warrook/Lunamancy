package warrook.magicpower.client.models;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.data.client.model.Texture;
import net.minecraft.util.Identifier;
import warrook.magicpower.MagicPower;
import warrook.magicpower.blocks.entities.BasinBlockEntity;

public class BasinBlockEntityRenderer extends BlockEntityRenderer<BasinBlockEntity> {
    private static final Identifier BARS_TEXTURE = new Identifier("minecraft", "textures/gui/bars.png");

    MinecraftClient client = MinecraftClient.getInstance();

    public BasinBlockEntityRenderer(BlockEntityRenderDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void render(BasinBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.push();

        RenderSystem.enableTexture();
        RenderSystem.bindTexture(BARS_TEXTURE.hashCode());
        //RenderSystem.activeTexture(BARS_TEXTURE.hashCode());
        VertexConsumer buffer = vertexConsumers.getBuffer(RenderLayer.getCutout()); //POSITION_COLOR_TEXTURE_LIGHT_NORMAL

        //buffer.vertex()

        RenderSystem.disableTexture();



        matrices.pop();
    }
}
