package warrook.magicpower.blocks.entities;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Tickable;
import warrook.magicpower.ModManifest;

// TODO: Require filling with water
public class BowlBlockEntity extends BlockEntity implements Tickable {


    public BowlBlockEntity() {
        this(ModManifest.ModBlocks.BOWL_BLOCK_ENTITY);
    }

    public BowlBlockEntity(BlockEntityType<?> type) {
        super(type);
    }

    @Override
    public void tick() {
        //
    }

    /*@Environment(EnvType.CLIENT)
    public boolean showMoon() {
        assert this.world != null;
        PlayerEntity playerEntity = this.world.getClosestPlayer(this.pos.getX(), this.pos.getY(), this.pos.getZ(), 4.0d, (entity -> ((PlayerEntity)entity).isHolding(ModManifest.ModItems.MOONSTONE)));
        return playerEntity != null;
    }*/
}
