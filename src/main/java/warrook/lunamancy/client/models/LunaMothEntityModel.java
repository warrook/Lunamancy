package warrook.lunamancy.client.models;// Made with Blockbench 3.7.5

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import warrook.lunamancy.entities.LunaMothEntity;

public class LunaMothEntityModel extends EntityModel<LunaMothEntity> {
    private final ModelPart right_wing;
    private final ModelPart left_wing;
    private final ModelPart body;
    private final ModelPart antennae;

    public LunaMothEntityModel() {
        textureWidth = 32;
        textureHeight = 32;

        right_wing = new ModelPart(this);
        right_wing.setPivot(-1.0F, 22.0F, -1.0F);
        right_wing.setTextureOffset(0, 15).addCuboid(-10.0F, 0.0F, -5.0F, 10.0F, 0.0F, 17.0F, 0.0F, false);

        left_wing = new ModelPart(this);
        left_wing.setPivot(1.0F, 22.0F, -1.0F);
        left_wing.setTextureOffset(0, 15).addCuboid(0.0F, 0.0F, -5.0F, 10.0F, 0.0F, 17.0F, 0.0F, true);

        body = new ModelPart(this);
        body.setPivot(0.0F, 23.0F, 0.0F);
        body.setTextureOffset(16, 7).addCuboid(-1.0F, -1.0F, -3.0F, 2.0F, 2.0F, 6.0F, 0.0F, false);
        body.setTextureOffset(16, 0).addCuboid(-1.0F, 1.0F, -3.0F, 2.0F, 1.0F, 6.0F, 0.0F, false);

        antennae = new ModelPart(this);
        antennae.setPivot(0.0F, -1.0F, -3.0F);
        body.addChild(antennae);
        antennae.pitch = -0.3491F;
        //setRotationAngle(antennae, -0.3491F, 0.0F, 0.0F);
        antennae.setTextureOffset(7, 3).addCuboid(-3.0F, 0.0F, -3.0F, 6.0F, 0.0F, 3.0F, 0.0F, false);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        matrices.translate(0, -0.0625d, 0);
        right_wing.render(matrices, vertices, light, overlay);
        left_wing.render(matrices, vertices, light, overlay);
        body.render(matrices, vertices, light, overlay);


    }

    @Override
    public void setAngles(LunaMothEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

    }
}