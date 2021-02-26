package warrook.lunamancy.utils.network;

import net.minecraft.util.math.BlockPos;

import java.util.UUID;

public class LightNodeInfo {
    private BlockPos position;
    private UUID inLightNet; //use it to get the net from manager

    public LightNodeInfo(BlockPos pos, LightNet lightNet) {
        this.position = pos;
        this.inLightNet = lightNet.getId();
    }
}
