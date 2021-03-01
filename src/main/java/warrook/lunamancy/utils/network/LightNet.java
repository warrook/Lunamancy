package warrook.lunamancy.utils.network;

import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.apache.logging.log4j.Level;
import org.jetbrains.annotations.Nullable;
import warrook.lunamancy.Lunamancy;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.UUID;

public class LightNet {
    private UUID id;
    //private LinkedList<LightNodeInfo> connectedNodes = new LinkedList<>();
    private HashMap<BlockPos, LightNodeInfo> connectedNodes = new HashMap<>();

    public LightNet(UUID id) {
        this.id = id;
    }

    public LightNet addNode(LightNodeInfo node) {
        connectedNodes.put(node.getPosition().toImmutable(), node);
        return this;
    }

    public LightNet addNodes(LightNodeInfo... nodes) {
        for (LightNodeInfo node : nodes) {
            this.addNode(node);
        }
        return this;
    }

    @Nullable
    public LightNet addNodeOrNull(LightNodeInfo node) {
        if (connectedNodes.containsKey(node.getPosition())) {
            Lunamancy.log(Level.WARN, "Tried to add node to LightNet that already contains it");
            return null;
        }
        addNode(node);
        return this;
    }

    public boolean hasNodeAt(BlockPos pos) {
            return connectedNodes.containsKey(pos.toImmutable());
    }

    public LightNet merge(LightNet... lightNets) {
        HashMap<BlockPos, LightNodeInfo> map = new HashMap<>(connectedNodes);
        for (LightNet net : lightNets) {
            if (net.equals(this)) {
                Lunamancy.log(Level.WARN, "Tried to merge identical LightNets");
                continue;
            }
            map.putAll(net.connectedNodes);
        }
        connectedNodes = map;
        return this;
    }

    public CompoundTag toTag() {
        CompoundTag tag = new CompoundTag();
        tag.putUuid("Id", id);
        ListTag listTag = new ListTag();
        for (Map.Entry<BlockPos, LightNodeInfo> entry : connectedNodes.entrySet()) {
            listTag.add(entry.getValue().toTag());
        }
        tag.put("ConnectedNodes", listTag);
        return tag;
    }

    public static LightNet fromTag(CompoundTag tag) {
        UUID id = tag.getUuid("Id");
        LightNet net = new LightNet(id);
        for (Tag t : tag.getList("ConnectedNodes", NbtType.COMPOUND)) {
            CompoundTag node = (CompoundTag) t;
            LightNodeInfo info = LightNodeInfo.fromTag(node);
            net.connectedNodes.put(info.getPosition(), info);
        }
        return net;
    }

    public UUID getId() {
        return id;
    }
}