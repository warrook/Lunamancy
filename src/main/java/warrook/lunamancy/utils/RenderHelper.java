package warrook.lunamancy.utils;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;
import warrook.lunamancy.Lunamancy;

@Environment(EnvType.CLIENT)
public class RenderHelper {

    public static RenderLayer getRenderLayerUnlit(Identifier texture) {
        RenderLayer.MultiPhaseParameters params = RenderLayer.MultiPhaseParameters.builder()
                .texture(new RenderPhase.Texture(texture, false, false))
                .transparency(RenderPhases.TEST_TRANSPARENCY)
                .writeMaskState(new RenderPhase.WriteMaskState(true,false))
                .fog(RenderPhases.FOG) //maybe not necessary
                .lightmap(new RenderPhase.Lightmap(true))
                .build(false);

        return RenderLayer.of(label("unlit"), VertexFormats.POSITION_COLOR_TEXTURE_LIGHT_NORMAL, 7, 256, false, true, params);
    }

    public static class RenderPhases {
        public static final RenderPhase.Transparency TRANSLUCENT_TRANSPARENCY = new RenderPhase.Transparency(
                label("translucent_transparency"),
                () -> {
                    RenderSystem.enableBlend();
                    RenderSystem.blendFuncSeparate(
                            GlStateManager.SrcFactor.SRC_ALPHA,
                            GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA,
                            GlStateManager.SrcFactor.ONE,
                            GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA);
                },
                () -> {
                    RenderSystem.disableBlend();
                    RenderSystem.defaultBlendFunc();
                });

        public static final RenderPhase.Transparency ADDITIVE_TRANSPARENCY = new RenderPhase.Transparency(
                label("additive_transparency"),
                () -> {
                    RenderSystem.enableBlend();
                    RenderSystem.blendFuncSeparate(
                            GlStateManager.SrcFactor.SRC_ALPHA,
                            GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA,
                            GlStateManager.SrcFactor.ONE_MINUS_SRC_ALPHA,
                            GlStateManager.DstFactor.ONE
                    );
                },
                () -> {
                   RenderSystem.disableBlend();
                   RenderSystem.defaultBlendFunc();
                });

        public static final RenderPhase.Transparency TEST_TRANSPARENCY = new RenderPhase.Transparency(
                label("test_transparency"),
                () -> {
                    RenderSystem.enableBlend();
                    RenderSystem.blendFuncSeparate(
                            GlStateManager.SrcFactor.DST_COLOR,
                            GlStateManager.DstFactor.DST_COLOR,
                            GlStateManager.SrcFactor.SRC_ALPHA,
                            GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA
                    );
                },
                () -> {
                    RenderSystem.disableBlend();
                    RenderSystem.defaultBlendFunc();
                });

        public static final RenderPhase.Fog FOG = new RenderPhase.Fog(
                label("fog"), //BLACK_FOG
                () -> {
                    RenderSystem.fog(2918, 0.0F, 0.0F, 0.0F, 1.0F);
                    RenderSystem.enableFog();
                },
                () -> {
                    BackgroundRenderer.setFogBlack();
                    RenderSystem.disableFog();
                });
    }

    private static String label(String name) {
        return Lunamancy.defaultID(name).toString();
    }
}
