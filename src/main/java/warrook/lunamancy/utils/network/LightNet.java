package warrook.lunamancy.utils.network;

import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.Level;
import warrook.lunamancy.Lunamancy;
import warrook.lunamancy.ModManifest;
import warrook.lunamancy.blocks.entities.LightTransmitterImpl;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class LightNet {

    //private Map<BlockPos, LightNodeInfo> connectedNodes;
    private LinkedList<LightNodeInfo> connectedNodes = new LinkedList<>();
    private UUID id;

    public LightNet(UUID id) {
        this.id = id;
    }

    public LightNet addNode(LightNodeInfo node) {
        if (connectedNodes.contains(node)) {
            Lunamancy.log(Level.WARN, "Tried to add node to LightNet that already contains it");
            return null;
        }
        connectedNodes.add(node);
        return this;
    }

    public LightNet addNodes(LightNodeInfo... nodes) {
        for (LightNodeInfo node : nodes) {
            this.addNode(node);
        }
        return this;
    }

    public LightNet merge(LightNet... lightNets) {
        LinkedList<LightNodeInfo> list = new LinkedList<>(this.connectedNodes);

        for (LightNet net : lightNets) {
            if (net == this) {
                Lunamancy.log(Level.WARN, "Tried to merge identical LightNets");
                continue;
            }

            list.addAll(net.connectedNodes);
        }

        connectedNodes = list;
        return this;
    }



    /*public CompoundTag toTag() {
        CompoundTag tag = new CompoundTag();
        tag.putUuid("id", id);
        ListTag listTag = new ListTag();
        for (Map.Entry<BlockPos, LightNodeInfo> entry : this.connectedNodes.entrySet()) {
            CompoundTag entryTag = new CompoundTag();

            long pos = entry.getKey().asLong();
            entryTag.putLong("pos", pos);

            LightNodeInfo nodeInfo = entry.getValue();
            if (nodeInfo instanceof BlockEntity) {
                entryTag.put("node", ((BlockEntity) nodeInfo).toTag(entryTag));
            } else {
                entryTag.put("node", null);
            }

            listTag.add(entryTag);
        }

        tag.put("nodes", listTag);

        return tag;
    }

    public void fromTag(CompoundTag tag) {
        this.id = tag.getUuid("id");

        ListTag listTag = tag.getList("nodes", NbtType.COMPOUND);
        for (Tag t : listTag) {
            LongTag posTag = (LongTag) t;
            BlockPos p = BlockPos.fromLong(posTag.getLong());

            connectedNodes.put(p, )
        }

    }*/

    public UUID getId() {
        return id;
    }
}
