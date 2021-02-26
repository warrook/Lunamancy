package warrook.lunamancy.blocks;

import net.minecraft.block.Block;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import warrook.lunamancy.utils.network.LightTransmitter;

public abstract class LightTransmitterBlockImpl extends BaseBlock implements LightTransmitter {


    public LightTransmitterBlockImpl(String name, Settings settings) {
        super(name, settings);
    }

    @Override
    public BlockPos getPos() {
        return null;
    }

    @Override
    public ActionResult pushLight(LightTransmitter to) {
        return null;
    }

    @Override
    public void requestAmount(float amount) {

    }
}
