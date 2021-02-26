package warrook.lunamancy.utils.network;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface LightTransmitter {

    default BlockPos getPos() {
        if (this instanceof BlockEntity) {
            return ((BlockEntity) this).getPos();
        }

        return null;
    }



    LightNet getNet();
    ActionResult pushLight(LightTransmitter to);
    void requestAmount(float amount);




    //default ActionResult pullLight(World world)
}
