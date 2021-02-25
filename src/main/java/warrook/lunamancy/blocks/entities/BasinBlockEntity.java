package warrook.lunamancy.blocks.entities;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.Tickable;
import org.apache.logging.log4j.Level;
import org.jetbrains.annotations.Nullable;
import warrook.lunamancy.Lunamancy;
import warrook.lunamancy.ModManifest;
import warrook.lunamancy.blocks.LensStandBlock;
import warrook.lunamancy.client.gui.GuiHelper;
import warrook.lunamancy.client.gui.ToolHud;
import warrook.lunamancy.utils.LightContainer;
import warrook.lunamancy.utils.MoonUtils;
import warrook.lunamancy.utils.enums.LensType;
import warrook.lunamancy.utils.enums.Moonlight;

import java.util.Map;

public class BasinBlockEntity extends BlockEntity implements Tickable, ToolHud, LightContainer, BlockEntityClientSerializable {
    private static final Map<LensType, Float> LENS_MAP;

    private boolean isBlocked = true;
    private float amount = 0;
    private float capacity;
    private float baseRate;
    private float lensModifier;
    private LensType lensOver;

    public Moonlight lightType;

    public BasinBlockEntity() {
        this(10000f, 1f);
    }

    public BasinBlockEntity(float capacity, float baseRate) {
        super(ModManifest.ModBlocks.BASIN_BLOCK_ENTITY);
        this.amount = 0f;
        this.capacity = capacity;
        this.baseRate = baseRate;
        this.lightType = Moonlight.BOTH;

        setLensOver(LensType.NONE);
        checkLens();
    }

    public void checkLens() {
        // Allow for lenses to filter light
        if (world != null && !world.isClient()) {
            BlockState state = world.getBlockState(this.getPos().up());
            if (state.isOf(ModManifest.ModBlocks.LENS_STAND)) {
                LensType lens = state.get(LensStandBlock.LENS_TYPE);
                if (lens != this.lensOver) {
                    setLensOver(lens);
                }
            } else {
                setLensOver(LensType.NONE);
            }
        }
    }

    private void setLensOver(LensType lensType) {
        lensOver = lensType;
        lensModifier = LENS_MAP.get(lensType);
    }

    @Override
    public void tick() {
        if (world != null && !world.isClient()) {
            if (world.getTime() % 120 == 0) {
                isBlocked = MoonUtils.canSeeSky(world, pos);
                Lunamancy.log(Level.INFO, amount + " / " + capacity + " " + lightType.asString());
            }
            if (!isBlocked && MoonUtils.worldTimeIsBetween(world, 13000L, 23000L)) {
                if (amount < capacity) {
                    float adjustment = lightType == Moonlight.BOTH ? 0.5f : 1 + MoonUtils.getSidedLightRate(world, lightType);
                    float toAdd = baseRate * lensModifier * adjustment;
                    amount = Math.min(amount + toAdd, capacity);
                }
                this.sync();
            }
        }
    }



    @Environment(EnvType.CLIENT)
    public void renderHud(MatrixStack matrices) {
        int x = MinecraftClient.getInstance().getWindow().getScaledWidth() / 2;
        int y = MinecraftClient.getInstance().getWindow().getScaledHeight() / 2 + 30;

        GuiHelper.SymbolReadout.INSTANCE.renderFromBasin(matrices, x, y, this);
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        super.toTag(tag);

        tag.putFloat("amount", amount);
        tag.putFloat("capacity", capacity);
        tag.putFloat("base_rate", baseRate);
        tag.putString("light_type", lightType.asString());

        return tag;
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        super.fromTag(state, tag);

        amount = tag.getFloat("amount");
        capacity = tag.getFloat("capacity");
        baseRate = tag.getFloat("base_rate");
        lightType = Moonlight.fromString(tag.getString("light_type"));
    }

    @Override
    public CompoundTag toClientTag(CompoundTag tag) {
        tag.putFloat("amount", amount);
        tag.putFloat("capacity", capacity);
        return tag;
    }

    @Override
    public void fromClientTag(CompoundTag tag) {
        amount = tag.getFloat("amount");
        capacity = tag.getFloat("capacity");
    }

    @Override
    public float getAmount() {
        return this.amount;
    }

    @Override
    public float getCapacity() {
        return this.capacity;
    }

    @Override
    public Moonlight getLightType() {
        return lightType;
    }

    static {
        LENS_MAP = Maps.newEnumMap(ImmutableMap.of(LensType.NONE, 1f, LensType.FOCUSING, 2f, LensType.DIFFUSING, 0.5f));
    }
}
