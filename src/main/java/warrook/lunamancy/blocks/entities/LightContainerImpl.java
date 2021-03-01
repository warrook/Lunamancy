package warrook.lunamancy.blocks.entities;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.nbt.CompoundTag;
import warrook.lunamancy.client.gui.GuiHelper;
import warrook.lunamancy.client.gui.ToolHud;
import warrook.lunamancy.utils.enums.Moonlight;
import warrook.lunamancy.utils.network.LightContainer;
import warrook.lunamancy.utils.network.LightTransmitter;

public abstract class LightContainerImpl extends LightTransmitterImpl implements LightContainer, LightTransmitter, ToolHud, BlockEntityClientSerializable {
    protected Moonlight lightType;
    protected float lightAmount;
    protected float lightCapacity;

    public LightContainerImpl(BlockEntityType<?> type) {
        super(type);
        this.initLight();
    }

    protected void initLight() {
        initLight(Moonlight.WHITE, 1000f);
    }

    protected void initLight(Moonlight lightType, float capacity) {
        this.lightType = lightType;
        this.lightCapacity = capacity;
        this.lightAmount = 0f;
    }

    @Override
    public Moonlight getLightType() {
        return lightType;
    }

    @Override
    public float getAmount() {
        return lightAmount;
    }

    @Override
    public float getCapacity() {
        return lightCapacity;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void renderHud(MatrixStack matrices) {
        GuiHelper.renderSymbolReadout(matrices, this);
    }

    @Override
    public CompoundTag toClientTag(CompoundTag tag) {
        tag.putFloat("amount", this.lightAmount);
        tag.putFloat("capacity", this.lightCapacity);
        tag.putString("light_type", this.lightType.asString());
        return tag;
    }

    @Override
    public void fromClientTag(CompoundTag tag) {
        this.lightType = Moonlight.fromString(tag.getString("light_type"));
        this.lightAmount = tag.getFloat("amount");
        this.lightCapacity = tag.getFloat("capacity");
    }
}