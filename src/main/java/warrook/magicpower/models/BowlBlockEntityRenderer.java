package warrook.magicpower.models;

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

        matrices.push();

        Sprite spr = client.getBlockRenderManager().getModel(Fluids.FLOWING_WATER.getDefaultState().getBlockState()).getSprite();
        float min = 3/16f;
        float max = 13/16f;
        float y = 4f/16f;

        int biomeColor = entity.getWorld().getBiome(entity.getPos()).getWaterColor();
        int r = (biomeColor & 0xFF0000) >> 16;
        int g = (biomeColor & 0x00FF00) >> 8;
        int b = (biomeColor & 0x0000FF);
        int a = 230;

        //check RenderLayer to see what settings a vertex needs given what buffer
        VertexConsumer buffer = vertexConsumers.getBuffer(RenderLayer.getTranslucent());
        buffer.vertex(model, min, y, min).color(r,g,b,a).texture(spr.getMinU(), spr.getMinV()).light(light).normal(normal, 0f,-1f,0f).next();
        buffer.vertex(model, min, y, max).color(r,g,b,a).texture(spr.getMinU(), spr.getMaxV()).light(light).normal(normal, 0f, -1f, 0f).next();
        buffer.vertex(model, max, y, max).color(r,g,b,a).texture(spr.getMaxU(), spr.getMaxV()).light(light).normal(normal, 0f, -1f, 0f).next();
        buffer.vertex(model, max, y, min).color(r,g,b,a).texture(spr.getMaxU(), spr.getMinV()).light(light).normal(normal, 0f, -1f, 0f).next();

        matrices.pop();

        if (entity.showMoon()) {
            y += 0.01f;
            r = 255;
            g = 255;
            b = 255;
            a = 180;
            Identifier id = MoonUtils.getMoonClockSprite(entity.getWorld());

            //light = 0xf000f0; //full brightness
            light = 0x78078; //half brightness

            //moon layer -- 15728640, 15728880
            matrices.push();
            buffer = vertexConsumers.getBuffer(RenderHelper.getRenderLayerUnlit(id));
            buffer.vertex(model, min, y, min).color(r, g, b, a).texture(0f, 0f).light(light).normal(normal, 0f, -1f, 0f).next();
            buffer.vertex(model, min, y, max).color(r, g, b, a).texture(0f, 1f).light(light).normal(normal, 0f, -1f, 0f).next();
            buffer.vertex(model, max, y, max).color(r, g, b, a).texture(1f, 1f).light(light).normal(normal, 0f, -1f, 0f).next();
            buffer.vertex(model, max, y, min).color(r, g, b, a).texture(1f, 0f).light(light).normal(normal, 0f, -1f, 0f).next();
            matrices.pop();
            
        }


        /*
        loafToday at 10:00 PM
        how would I render a texture that's not part of a block or item in a block entity renderer? i'm pretty sure a sprite atlas is involved but i have no idea how to manually add a texture to one (if i'm right and i DO need to do that)
        FoundationGamesToday at 10:01 PM
        you draw a quad
        and make sure to bind the texture beforehand
        loafToday at 10:01 PM
        ah ok so it is that easy
        rad
        FoundationGamesToday at 10:01 PM
        if it's a block or item texture, you will need to deal with the sprite atlas
        otherwise, youre good to go
        loafToday at 10:01 PM
        i just use an identifier that leads to the file, right
        FoundationGamesToday at 10:02 PM
        yes
        and you do MinecraftClient.getInstance().getTextureManager().bindTexture(MY_IDENTIFIER)
        also to draw the quad
        you'll want to RenderSystem.enableTexture() I believe
        and then youll need an entity vertex consumer (You can get one in your block entity renderer with vertexConsumers.getBuffer(RenderLayer.<whichever layer>))
        then you'll have to consumer.vertex(...).color(...).texture(...).overlay(...).light(...).normal(...).next(); four times
        oh and make sure to push and pop your matrix stack before all of this
        and you should have a quad
        loafToday at 10:04 PM
        sick
        thanks bruv
         */
    }
}
