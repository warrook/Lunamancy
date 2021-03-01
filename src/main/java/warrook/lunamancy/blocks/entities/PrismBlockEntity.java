package warrook.lunamancy.blocks.entities;

import warrook.lunamancy.ModManifest;
import warrook.lunamancy.utils.enums.Moonlight;
import warrook.lunamancy.utils.network.LightTransmitter;

public class PrismBlockEntity extends LightContainerImpl {

    public PrismBlockEntity() {
        this(Moonlight.WHITE, 1000f);
    }

    public PrismBlockEntity(Moonlight lightType, float capacity) {
        super(ModManifest.ModBlocks.PRISM_BLOCK_ENTITY);
        this.initLight(lightType, capacity);
    }
}
