package warrook.lunamancy.client.gui;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.util.math.MatrixStack;

public interface ToolHud {
    @Environment(EnvType.CLIENT)
    void renderHud(MatrixStack matrices);
}
