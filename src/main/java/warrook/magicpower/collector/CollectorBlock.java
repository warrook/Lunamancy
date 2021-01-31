package warrook.magicpower.collector;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

public class CollectorBlock extends Block implements BlockEntityProvider {

    public CollectorBlock() {
        this(FabricBlockSettings.of(Material.STONE).breakInstantly());
    }

    public CollectorBlock(AbstractBlock.Settings settings) {
        super(settings);
    }

    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new CollectorBlockEntity();
    }
}
