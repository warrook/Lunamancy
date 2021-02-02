package warrook.magicpower.entities;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.BatEntityRenderer;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import warrook.magicpower.MagicPower;
import warrook.magicpower.models.LunaMothEntityModel;

@Environment(EnvType.CLIENT)
public class LunaMothEntityRenderer extends MobEntityRenderer<LunaMothEntity, LunaMothEntityModel> {

    public LunaMothEntityRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher, new LunaMothEntityModel(), 0.5f);
    }

    @Override
    public Identifier getTexture(LunaMothEntity entity) {
        return MagicPower.defaultID("textures/entity/luna_moth.png");
    }
}
