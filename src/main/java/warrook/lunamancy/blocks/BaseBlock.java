package warrook.lunamancy.blocks;

import net.minecraft.block.Block;

public class BaseBlock extends Block {
    public String registryName;

    public BaseBlock(String name, Settings settings) {
        super(settings);
        registryName = name;
    }

}
