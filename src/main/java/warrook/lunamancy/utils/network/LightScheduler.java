package warrook.lunamancy.utils.network;

import warrook.lunamancy.blocks.entities.LightTransmitterImpl;

import java.util.ArrayList;
import java.util.List;

// Some things need to be delayed so this exists -- needs to happen on server started
public class LightScheduler {
    private static List<LightTransmitterImpl> fromTags = new ArrayList<>();

    public static void scheduleFromTag(LightTransmitterImpl transmitter) {
        fromTags.add(transmitter);
    }

    public static void fromTags() {
        for (LightTransmitterImpl transmitter : fromTags) {
            transmitter.nodeFromTag(transmitter.toTag(transmitter.toInitialChunkDataTag()));
        }
        fromTags.clear();
    }
}
