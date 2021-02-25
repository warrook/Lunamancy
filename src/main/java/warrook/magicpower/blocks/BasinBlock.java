package warrook.magicpower.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.apache.logging.log4j.Level;
import warrook.magicpower.MagicPower;
import warrook.magicpower.ModManifest;
import warrook.magicpower.blocks.entities.BasinBlockEntity;
import warrook.magicpower.client.gui.ToolHud;
import warrook.magicpower.utils.enums.LensType;

public class BasinBlock extends BaseBlock implements BlockEntityProvider {
    public BasinBlock() {
        super("moonlight_basin", FabricBlockSettings
                .of(Material.METAL)
        );
    }

    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new BasinBlockEntity(300f, 1f);
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        if (fromPos.equals(pos.up())) {
            BlockState upState = world.getBlockState(pos.up());
            BasinBlockEntity entity = ((BasinBlockEntity)world.getBlockEntity(pos));
            if (entity != null) {
                entity.checkLens();
            }
        }
        super.neighborUpdate(state, world, pos, block, fromPos, notify);
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        if (world.getBlockEntity(pos) != null) {
            BasinBlockEntity entity = (BasinBlockEntity) world.getBlockEntity(pos);
            if (entity != null)
                entity.checkLens();
        }
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            BasinBlockEntity entity = (BasinBlockEntity) world.getBlockEntity(pos);
            if (entity != null) {
                if (player.isHolding(ModManifest.ModItems.WAND)) {
                    entity.lightType = entity.lightType.next();
                } else if (player.isHolding(ModManifest.ModItems.ATHAME)) {
                    //Activate a ritual or something
                }
            }
        }

        return super.onUse(state, world, pos, player, hand, hit);
    }


}
