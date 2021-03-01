package warrook.lunamancy.blocks.entities;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.world.ServerWorld;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.jmx.Server;
import warrook.lunamancy.Lunamancy;
import warrook.lunamancy.utils.network.*;

import java.util.LinkedList;
import java.util.UUID;

public abstract class LightTransmitterImpl extends BlockEntity implements LightTransmitter {
    protected LightNodeInfo lightNodeInfo = null;

    public LightTransmitterImpl(BlockEntityType<?> type) {
        super(type);
    }

    public void addConnection(LightNodeInfo node) {
        if (lightNodeInfo == null) {
            Lunamancy.log(Level.ERROR, "Tried to add connection out of order");
        }
        lightNodeInfo.addConnection(node);
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        CompoundTag t = (CompoundTag) tag.get("nodeInfo");
        if (t != null) {
            lightNodeInfo = LightNodeInfo.fromTag(t);
        }
        super.fromTag(state, tag);
    }

    public void nodeFromTag(CompoundTag tag) {
        CompoundTag t = (CompoundTag) tag.get("nodeInfo");
        if (t != null) {
            lightNodeInfo = LightNodeInfo.fromTag(t);
        }
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        if (lightNodeInfo != null) {
            tag.put("nodeInfo", lightNodeInfo.toTag());
        }
        return super.toTag(tag);
    }



    public boolean setNodeInfo(LightNodeInfo nodeInfo) {
        this.lightNodeInfo = nodeInfo;
        return true;
    }

    public LightNodeInfo getNodeInfo() {
        return lightNodeInfo;
    }
}