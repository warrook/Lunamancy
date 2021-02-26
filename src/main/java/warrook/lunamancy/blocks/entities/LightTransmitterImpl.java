package warrook.lunamancy.blocks.entities;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.BlockPos;
import warrook.lunamancy.utils.network.LightNet;
import warrook.lunamancy.utils.network.LightTransmitter;

public abstract class LightTransmitterImpl extends BlockEntity implements LightTransmitter {
    protected LightNet lightNet;

    public LightTransmitterImpl(BlockEntityType<?> type) {
        super(type);
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        super.fromTag(state, tag);
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        return super.toTag(tag);
    }
}
