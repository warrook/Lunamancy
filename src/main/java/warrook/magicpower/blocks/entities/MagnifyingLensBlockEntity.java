package warrook.magicpower.blocks.entities;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Pair;
import net.minecraft.util.Tickable;
import org.apache.logging.log4j.Level;
import warrook.magicpower.MagicPower;
import warrook.magicpower.ModManifest;
import warrook.magicpower.blocks.MagnifyingLensBlock;
import warrook.magicpower.utils.MoonUtils;

public class MagnifyingLensBlockEntity extends BlockEntity implements Tickable {

    private int MoonPhase;
    private float BrightRate;
    private float DarkRate;
    private boolean isBlocked;

    public MagnifyingLensBlockEntity() {
        this(ModManifest.ModBlocks.MAGNIFYING_LENS_BLOCK_ENTITY);
    }

    public MagnifyingLensBlockEntity(BlockEntityType type) {
        super(type);
        if (world != null) {
            MoonPhase = world.getMoonPhase();
            setRatio(MoonUtils.getMoonlight(world));
        } else { //Hopefully signal that bad things are happening
            MoonPhase = -1;
            BrightRate = -1f;
            DarkRate = -1f;
        }

    }

    @Override
    public void tick() {
        if (world != null) {

            if (!this.world.isClient) {
                //Slow update block state
                if (world.getTime() % 200L == 0L) {
                    BlockState blockState = this.getCachedState();
                    Block block = blockState.getBlock();
                    if (block instanceof MagnifyingLensBlock) {
                        MagnifyingLensBlock.updateState(blockState, this.world, this.pos);
                        isBlocked = !blockState.get(MagnifyingLensBlock.SKY_VISIBLE);
                    }
                }

                if (world.getMoonPhase() != MoonPhase) {
                    MoonPhase = world.getMoonPhase();
                    Pair<Float,Float> pair = MoonUtils.getMoonlight(world);
                    BrightRate = pair.getLeft();
                    DarkRate = pair.getRight();

                    MagicPower.log(Level.INFO, "Moon phase: " + MoonPhase + " - ratio: " + BrightRate + " : " + DarkRate);
                }
            }
        }
    }

    private void setRatio(Pair<Float, Float> pair) {
        BrightRate = pair.getLeft();
        DarkRate = pair.getRight();
    }
}
