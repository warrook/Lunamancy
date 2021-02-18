package warrook.magicpower.items;

import net.fabricmc.fabric.api.item.v1.CustomDamageHandler;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import warrook.magicpower.ModManifest;
import warrook.magicpower.blocks.DustLineBlock;
import warrook.magicpower.utils.DustLineMaterial;

public class DustPouch extends Item {
    protected Item dustItem;
    protected static final Block DUST_LINE = ModManifest.ModBlocks.DUST_LINE;

    public DustPouch(Item dustItem) {
        super(new FabricItemSettings()
                .group(ItemGroup.TOOLS)
                .maxCount(1)
                .maxDamage(256)
        );
        this.isDamageable();
        this.dustItem = dustItem;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        ItemStack stack = context.getStack();

        if (context.getWorld() != null && !context.getWorld().isClient) {
            World world = context.getWorld();
            if (stack.getDamage() < this.getMaxDamage()) {
                if (canRunOnTop(context)) {
                    BlockPos placeAt = context.getBlockPos().add(context.getSide().getVector());
                    if (!world.getBlockState(placeAt).isOf(DUST_LINE)) {
                        stack.damage(1, context.getPlayer(), (playerEntity -> {//TODO: BreakCallback
                        }));

                        BlockState state = DUST_LINE.getDefaultState().with(DustLineBlock.DUST_MATERIAL, DustLineMaterial.getValue(dustItem));

                        world.setBlockState(placeAt, ((DustLineBlock) DUST_LINE).getConnectedState(world, placeAt, state), 0b0000011);

                        return ActionResult.SUCCESS;
                    }
                }
            }
        }

        return ActionResult.FAIL;
    }

    public boolean canRunOnTop(ItemUsageContext context) {
        return DUST_LINE.canPlaceAt(
                DUST_LINE.getDefaultState(),
                context.getWorld(),
                context.getBlockPos().add(context.getSide().getVector())
        );
    }
}
