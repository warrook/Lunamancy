package warrook.lunamancy.client.models;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import warrook.lunamancy.Lunamancy;
import warrook.lunamancy.client.models.LunaMothEntityModel;
import warrook.lunamancy.entities.LunaMothEntity;

@Environment(EnvType.CLIENT)
public class LunaMothEntityRenderer extends MobEntityRenderer<LunaMothEntity, LunaMothEntityModel> {

    public LunaMothEntityRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher, new LunaMothEntityModel(), 0.5f);
    }

    @Override
    public Identifier getTexture(LunaMothEntity entity) {
        return Lunamancy.defaultID("textures/entity/luna_moth.png");
    }
}
