package warrook.magicpower.client.gui;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import warrook.magicpower.MagicPower;

@Environment(EnvType.CLIENT)
public class GuiHelper {
    private static final Identifier BARS_TEXTURE = new Identifier("minecraft","textures/gui/bars.png");
    private static final MinecraftClient client = MinecraftClient.getInstance();

    public static void renderOverlay() {
        MinecraftClient.getInstance().getProfiler().push(MagicPower.MOD_NAME);



        MinecraftClient.getInstance().getProfiler().pop();
    }

    public static class SymbolReadout extends DrawableHelper {
        //TODO: this.
    }

    public static class BarReadout extends DrawableHelper {

        private int amount;
        private int maximum;

        public BarReadout(float amount, float maximum) {
            this.amount = (int)amount;
            this.maximum = (int) maximum;
        }

        public void render(MatrixStack matrices) {
            int screenWidth = client.getWindow().getScaledWidth();
            int posX = screenWidth / 2;
            int posY = 30;

            client.getTextureManager().bindTexture(BARS_TEXTURE);
            this.renderBar(matrices, posX, posY);
        }

        private void renderBar(MatrixStack matrices, int x, int y) {
            //BG
            this.drawTexture(matrices, x, y, 0, 60, 182, 5); //bar
            this.drawTexture(matrices, x, y, 0, 80, 182, 5); //overlay
            //FG
            int prog = (int)(amount / maximum * 183.0F);
            if (prog > 0) {
                this.drawTexture(matrices, x, y, 0, 60, prog, 5); //bar
                this.drawTexture(matrices, x, y, 0, 80, prog, 5); //overlay
            }

            Text text = new TranslatableText("gui.%s.basin", MagicPower.MOD_ID);
            client.textRenderer.drawWithShadow(matrices, text, 0, 0, 0xFFFFFF);
        }
    }
}
