package warrook.magicpower.items;

import net.fabricmc.fabric.api.item.v1.CustomDamageHandler;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
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

//TODO: Model, Dust Line models
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
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient)
        {

        }
        return super.use(world, user, hand);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        ItemStack stack = context.getStack();

        if (context.getWorld() != null && !context.getWorld().isClient) {
            if (stack.getDamage() < this.getMaxDamage()) {
                if (canRunOnTop(context)) {
                    BlockPos placeAt = context.getBlockPos().add(context.getSide().getVector());
                    stack.damage(1, context.getPlayer(), (playerEntity -> {
                    }));
                    context.getWorld().setBlockState(placeAt, DUST_LINE.getDefaultState()
                            .with(DustLineBlock.DUST_MATERIAL, DustLineMaterial.getValue(dustItem)),
                            32 & -33
                    );
                    return ActionResult.SUCCESS;
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
