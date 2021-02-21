package warrook.magicpower.blocks.entities;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Tickable;
import warrook.magicpower.ModManifest;
import warrook.magicpower.blocks.LensStandBlock;
import warrook.magicpower.client.gui.ToolHud;
import warrook.magicpower.utils.MoonUtils;
import warrook.magicpower.utils.enums.LensType;
import warrook.magicpower.utils.enums.Moonlight;

import java.util.Map;

public class BasinBlockEntity extends BlockEntity implements Tickable, ToolHud {
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
        if (!world.isClient()) {
            if (world.getTime() % 120 == 0) {
                isBlocked = MoonUtils.canSeeSky(world, pos);
                //MagicPower.log(Level.INFO, amount + " / " + capacity + " " + lightType.asString());
            }
            if (!isBlocked && MoonUtils.worldTimeIsBetween(world, 13000L, 23000L)) {
                if (amount < capacity) {
                    float adjustment = lightType == Moonlight.BOTH ? 0.5f : 1 + MoonUtils.getSidedLightRate(world, lightType);
                    float toAdd = baseRate * lensModifier * adjustment;
                    amount = Math.min(amount + toAdd, capacity);
                }
            }
        }
        if (world.isClient()) {
            PlayerEntity player = MinecraftClient.getInstance().player;
            if (player != null && player.isHolding(ModManifest.ModItems.WAND)) {

            }
        }
    }

    @Environment(EnvType.CLIENT)
    public void renderHud(MatrixStack matrices) {

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

    static {
        LENS_MAP = Maps.newEnumMap(ImmutableMap.of(LensType.NONE, 1f, LensType.FOCUSING, 2f, LensType.DIFFUSING, 0.5f));
    }
}
