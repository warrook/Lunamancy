package warrook.magicpower.collector;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Tickable;
import org.apache.logging.log4j.Level;
import warrook.magicpower.MagicPower;
import warrook.magicpower.ModManifest;

public class CollectorBlockEntity extends BlockEntity implements Tickable {

    private int MoonPhase;

    public CollectorBlockEntity() {
        this(ModManifest.ModBlocks.COLLECTOR_BLOCK_ENTITY);
    }

    public CollectorBlockEntity(BlockEntityType type) {
        super(type);
    }

    @Override
    public void tick() {
        MoonPhase = world.getMoonPhase();

        if (world.getTime() % 200 == 0)
        {
            MagicPower.log(Level.INFO, "Current phase: " + MoonPhase);
        }
    }
}
