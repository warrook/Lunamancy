package warrook.lunamancy.utils.network;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.PersistentState;
import org.apache.logging.log4j.Level;
import warrook.lunamancy.Lunamancy;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class LightNetManager extends PersistentState {
    private static final String KEY = "light_nets";

    private final Map<UUID, LightNet> lightNets = new ConcurrentHashMap<>();
    private final ServerWorld world;

    public LightNetManager(ServerWorld world) {
        super(KEY);
        this.world = world;
    }

    public static LightNetManager of(ServerWorld world) {
        return world.getPersistentStateManager().getOrCreate(() -> new LightNetManager(world), KEY);
    }

    public LightNet register(LightNet net) {
        return register(newId(), net);
    }

    public LightNet register(UUID id, LightNet net) {
        LightNet n = lightNets.putIfAbsent(id, net);
        if (n != null) {
            Lunamancy.log(Level.WARN, "LightNetManager tried to register already registered LightNet ");
        }
        return net;
    }

    public LightNet makeNewNet() {
        UUID id = newId();
        return register(id, new LightNet(id));
    }

    public void remove(UUID id) {
        lightNets.remove(id);
    }

    public LightNet getNet(UUID id) {
        return this.lightNets.get(id);
    }

    @Override
    public void fromTag(CompoundTag tag) {

    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        for (Map.Entry<UUID, LightNet> entry : this.lightNets.entrySet()) {
            tag.put(entry.getKey().toString(), entry.getValue().toTag());
        }

        return null;
    }

    private UUID newId() {
        return MathHelper.randomUuid();
    }
}
