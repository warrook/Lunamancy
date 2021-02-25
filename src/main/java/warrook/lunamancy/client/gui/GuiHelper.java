package warrook.lunamancy.client.gui;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Matrix4f;
import warrook.lunamancy.Lunamancy;
import warrook.lunamancy.blocks.entities.BasinBlockEntity;
import warrook.lunamancy.utils.enums.Moonlight;

@Environment(EnvType.CLIENT)
public class GuiHelper {
    //private static final Identifier BARS_TEXTURE = new Identifier("minecraft","textures/gui/bars.png");
    private static final MinecraftClient client = MinecraftClient.getInstance();

    public static void renderOverlay() {
        MinecraftClient.getInstance().getProfiler().push(Lunamancy.MOD_NAME);



        MinecraftClient.getInstance().getProfiler().pop();
    }

    public static class SymbolReadout extends DrawableHelper {
        private static final Identifier TEXTURE = Lunamancy.defaultID("textures/gui/symbol.png");

        public static final SymbolReadout INSTANCE = new SymbolReadout();

        public static Identifier getTexture() { return TEXTURE; }

        public void renderSymbol(MatrixStack matrices, int centerX, int centerY, Moonlight lightType, float contained, float maximum) {
            int x = centerX - (32 / 2);
            int y = centerY - (14 / 2);

            renderSymbolDirect(matrices, x, y, lightType, contained, maximum);
        }

        public void renderSymbolDirect(MatrixStack matrices, int x, int y, Moonlight lightType, float contained, float maximum) {
            client.getTextureManager().bindTexture(TEXTURE);

            int u = 8;
            int v = 0; //14

            drawTexture(matrices, x, y, u, v, 32, 14, 64, 64);

            //gem
            u = 0;
            switch (lightType) {
                case BLACK:
                    v = 16;
                    break;
                case WHITE:
                    v = 8;
                    break;
                default:
                    v = 24;
            }

            float pct = (contained / maximum);
            int w = (int)(pct * 8f);

            RenderSystem.color4f(1f,1f,1f, pct);
            drawTexture(matrices, x + 12, y + 3, u, v, 8, 8, 64, 64);
            RenderSystem.color4f(1, 1, 1, 1);
        }

        public void renderFromBasin(MatrixStack matrices, int x, int y, BasinBlockEntity basin) {
            renderSymbol(matrices, x, y, basin.lightType, basin.getAmount(), basin.getCapacity());
        }
    }
}
