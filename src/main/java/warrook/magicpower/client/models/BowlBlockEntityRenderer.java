package warrook.magicpower.client.models;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Matrix3f;
import net.minecraft.util.math.Matrix4f;
import warrook.magicpower.blocks.entities.BowlBlockEntity;
import warrook.magicpower.utils.MoonUtils;
import warrook.magicpower.utils.RenderHelper;


public class BowlBlockEntityRenderer extends BlockEntityRenderer<BowlBlockEntity> {

    MinecraftClient client = MinecraftClient.getInstance();

    public BowlBlockEntityRenderer(BlockEntityRenderDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void render(BowlBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        Matrix4f model = matrices.peek().getModel();
        Matrix3f normal = matrices.peek().getNormal();

        //TODO: Add blockstates to BowlBlock to eliminate this quad (translucency doesn't really matter)
        matrices.push();

        Sprite spr = client.getBlockRenderManager().getModel(Fluids.FLOWING_WATER.getDefaultState().getBlockState()).getSprite();
        float min = 3/16f;
        float max = 13/16f;
        float y = 4f/16f;

        int biomeColor = entity.getWorld().getBiome(entity.getPos()).getWaterColor();
        int r = (biomeColor & 0xFF0000) >> 16;
        int g = (biomeColor & 0x00FF00) >> 8;
        int b = (biomeColor & 0x0000FF);
        int a = 255;

        //check RenderLayer to see what settings a vertex needs given what buffer
        VertexConsumer buffer = vertexConsumers.getBuffer(RenderLayer.getTranslucent());
        buffer.vertex(model, min, y, min).color(r,g,b,a).texture(spr.getMinU(), spr.getMinV()).light(light).normal(normal, 0f,-1f,0f).next();
        buffer.vertex(model, min, y, max).color(r,g,b,a).texture(spr.getMinU(), spr.getMaxV()).light(light).normal(normal, 0f, -1f, 0f).next();
        buffer.vertex(model, max, y, max).color(r,g,b,a).texture(spr.getMaxU(), spr.getMaxV()).light(light).normal(normal, 0f, -1f, 0f).next();
        buffer.vertex(model, max, y, min).color(r,g,b,a).texture(spr.getMaxU(), spr.getMinV()).light(light).normal(normal, 0f, -1f, 0f).next();

        matrices.pop();

        Identifier id = MoonUtils.getMoonClockSprite(entity.getWorld());

        r = 255;
        g = 255;
        b = 255;
        a = 255;

        //light = 0xf000f0; //full brightness
        //light = 0x78078; //half brightness

        matrices.push();
        buffer = vertexConsumers.getBuffer(RenderHelper.getRenderLayerUnlit(id));
        buffer.vertex(model, min, y, min).color(r, g, b, a).texture(0f, 0f).light(light).normal(normal, 0f, -1f, 0f).next();
        buffer.vertex(model, min, y, max).color(r, g, b, a).texture(0f, 1f).light(light).normal(normal, 0f, -1f, 0f).next();
        buffer.vertex(model, max, y, max).color(r, g, b, a).texture(1f, 1f).light(light).normal(normal, 0f, -1f, 0f).next();
        buffer.vertex(model, max, y, min).color(r, g, b, a).texture(1f, 0f).light(light).normal(normal, 0f, -1f, 0f).next();
        matrices.pop();
    }
}
