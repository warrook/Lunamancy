package warrook.lunamancy.blocks.entities;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.apache.logging.log4j.Level;
import warrook.lunamancy.Lunamancy;
import warrook.lunamancy.ModManifest;
import warrook.lunamancy.blocks.LensStandBlock;
import warrook.lunamancy.utils.MoonUtils;
import warrook.lunamancy.utils.ToolUser;
import warrook.lunamancy.utils.enums.LensType;
import warrook.lunamancy.utils.enums.Moonlight;
import warrook.lunamancy.utils.network.LightTransmitter;

import java.util.Map;

//TODO: Decide whether to make a BER to display the moonlight at a distance, by color changing the water, or color changing the moonstone base. Maybe make it give off more light based on contents?
public class BasinBlockEntity extends LightContainerImpl implements Tickable, ToolUser, BlockEntityClientSerializable {
    private static final Map<LensType, Float> LENS_MAP;

    private boolean isBlocked = true;
    private float baseRate;
    private float lensModifier;
    private LensType lensOver;

    public BasinBlockEntity() {
        this(1f);
    }

    public BasinBlockEntity(float baseRate) {
        super(ModManifest.ModBlocks.BASIN_BLOCK_ENTITY);
        this.baseRate = baseRate;

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
                //Lunamancy.log(Level.INFO, amount + " / " + capacity + " " + lightType.asString());
            }
            if (!isBlocked && MoonUtils.worldTimeIsBetween(world, 13000L, 23000L)) {
                if (this.lightAmount < this.lightCapacity) {
                    float adjustment = lightType == Moonlight.BOTH ? 0.5f : 1 + MoonUtils.getSidedLightRate(world, lightType);
                    float toAdd = baseRate * lensModifier * adjustment;
                    this.lightAmount = Math.min(this.getAmount() + toAdd, this.getCapacity());
                }
                this.sync();
            }
        }
    }

    @Override
    public void onToolUse(World world, BlockPos pos, PlayerEntity player) {
        Lunamancy.log(Level.INFO, getLightType().asString());
        if (player.isHolding(ModManifest.ModItems.WAND)) {
            //TODO: Warn player that it'll empty the container, require confirmation
            this.lightType = this.lightType.next();
            markDirty();
        } else if (player.isHolding(ModManifest.ModItems.ATHAME)) {
            //Activate a ritual or something
        }
    }


    @Override
    public CompoundTag toTag(CompoundTag tag) {
        super.toTag(tag);

        tag.putFloat("base_rate", baseRate);
        tag.putFloat("amount", this.lightAmount);
        tag.putFloat("capacity", this.lightCapacity);
        tag.putString("light_type", this.lightType.asString());

        return tag;
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        super.fromTag(state, tag);

        //TODO: unnecessary due to LightContainer i believe
        baseRate = tag.getFloat("base_rate");
        this.lightType = Moonlight.fromString(tag.getString("light_type"));
        this.lightAmount = tag.getFloat("amount");
        this.lightCapacity = tag.getFloat("capacity");
    }

    static {
        LENS_MAP = Maps.newEnumMap(ImmutableMap.of(LensType.NONE, 1f, LensType.FOCUSING, 2f, LensType.DIFFUSING, 0.5f));
    }
}