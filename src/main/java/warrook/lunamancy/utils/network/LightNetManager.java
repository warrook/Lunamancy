package warrook.lunamancy.utils.network;

import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.PersistentState;
import org.apache.logging.log4j.Level;
import warrook.lunamancy.Lunamancy;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class LightNetManager extends PersistentState {
    private static final String KEY = "light_nets";

    private final Map<UUID, LightNet> managedLightNets = new ConcurrentHashMap<>();
    private final ServerWorld world;

    public LightNetManager(ServerWorld world) {
        super(KEY);
        this.world = world;
        markDirty();
    }

    public static LightNetManager of(ServerWorld world) {
        return world.getPersistentStateManager().getOrCreate(() -> new LightNetManager(world), KEY);
    }

    public LightNet register(LightNet net) {
        if (net.getId() == null)
            return register(newId(), net);
        else
            return register(net.getId(), net);
    }

    public LightNet register(UUID id, LightNet net) {
        LightNet n = managedLightNets.putIfAbsent(id, net);
        if (n != null) {
            Lunamancy.log(Level.WARN, "LightNetManager tried to register already registered LightNet ");
        }
        markDirty();
        return net;
    }

    public LightNet makeAndRegisterNewNet() {
        UUID id = newId();
        return register(id, new LightNet(id));
    }

    public void removeNet(UUID id) {
        managedLightNets.remove(id);
        markDirty();
    }

    public void merge(LightNet priority, LightNet... toMerge) {
        priority.merge(toMerge);
        for (LightNet net : toMerge) {
            removeNet(net.getId());
        }
        markDirty();
    }

    public LightNet getNetWithUuid(UUID id) {
        return this.managedLightNets.get(id);
    }

    public LightNet getNetWithNodePos(BlockPos pos) {
        for (Map.Entry<UUID, LightNet> entry : managedLightNets.entrySet()) {
            LightNet net = entry.getValue();
            if (net.hasNodeAt(pos.toImmutable())) {
                return entry.getValue();
            }
        }
        return null;
    }

    public void removeFromAnyNetWithNodePos(BlockPos pos) {
        LightNet n = getNetWithNodePos(pos);
        if (n != null) {
            removeNodePosInNet(n.getId(), pos);
        }
    }

    public void removeNodePosInNet(UUID netId, BlockPos pos) {
        LightNet n = getNetWithUuid(netId);
        n.removeNode(pos);
        markDirty();
    }

    public ServerWorld getWorld() {
        return world;
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        ListTag list = new ListTag();
        for (Map.Entry<UUID, LightNet> entry : managedLightNets.entrySet()) {
            list.add(entry.getValue().toTag());
        }
        tag.put("ManagedLightNets", list);
        return tag;
    }

    @Override
    public void fromTag(CompoundTag tag) {
        ListTag list = tag.getList("ManagedLightNets", NbtType.COMPOUND);
        for (Tag netTag : list) {
            LightNet net = LightNet.fromTag((CompoundTag) netTag);
            managedLightNets.putIfAbsent(net.getId(), net);
        }
    }

    private UUID newId() {
        return MathHelper.randomUuid();
    }
}