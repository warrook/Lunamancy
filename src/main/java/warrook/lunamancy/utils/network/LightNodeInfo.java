package warrook.lunamancy.utils.network;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.apache.logging.log4j.Level;
import warrook.lunamancy.Lunamancy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public class LightNodeInfo {
    private BlockPos position;
    private UUID nodeId;
    private UUID netIn;
    private ArrayList<UUID> connections;

    public LightNodeInfo(BlockPos pos, LightNet lightNet) {
        this(pos, MathHelper.randomUuid(), lightNet.getId());
    }

    public LightNodeInfo(BlockPos pos, UUID netId) {
        this(pos, MathHelper.randomUuid(), netId);
    }

    public LightNodeInfo(BlockPos pos, UUID nodeId, UUID netId) {
        this.position = pos;
        this.nodeId = nodeId;
        this.netIn = netId;
    }

    public LightNodeInfo addConnection(LightNodeInfo node) {
        return addConnections(node);
    }

    public LightNodeInfo addConnections(LightNodeInfo... nodes) {
        if (connections == null) {
            connections = new ArrayList<>(nodes.length);
        }
        for (LightNodeInfo node : nodes) {
            if (connections.contains(node.nodeId)) {
                Lunamancy.log(Level.WARN, "Tried to add preexisting connection to node in LightNet with uuid " + node.netIn);
                continue;
            }
            connections.add(node.nodeId);
        }

        return this;
    }

    public CompoundTag toTag() {
        CompoundTag tag = new CompoundTag();
        tag.putUuid("netIn", netIn);
        tag.putLong("position", position.asLong());
        return tag;
    }

    public static LightNodeInfo fromTag(CompoundTag tag) {
        UUID netIn = tag.getUuid("netIn");
        BlockPos position = BlockPos.fromLong(tag.getLong("position"));
        return new LightNodeInfo(
                position,
                netIn
        );
    }

    public BlockPos getPosition() {
        return position;
    }

    public UUID getNetIn() {
        return netIn;
    }

    public ArrayList<UUID> getConnections() {
        return connections;
    }
}
