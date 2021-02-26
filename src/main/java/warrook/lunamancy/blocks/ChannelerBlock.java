package warrook.lunamancy.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Material;
import warrook.lunamancy.utils.network.LightTransmitter;

//TODO: Probably a block entity but I also have to figure out how the whole thing works
public class ChannelerBlock extends LightTransmitterBlockImpl implements LightTransmitter {
    public ChannelerBlock() {
        super("moonlight_channeler", FabricBlockSettings.of(Material.METAL).nonOpaque());
    }


}
