package warrook.magicpower.items;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.apache.logging.log4j.Level;
import warrook.magicpower.MagicPower;
import warrook.magicpower.ModManifest;

public class AthameItem extends Item {

    public AthameItem() {
        super(new FabricItemSettings()
                .group(ItemGroup.TOOLS)
                .maxCount(1)
        );
    }

    /**
     * Called when an item is used by a player.
     * The use action, by default, is bound to the right mouse button.
     *
     * <p>This method is called on both the logical client and logical server, so take caution when overriding this method.
     * The logical side can be checked using {@link World#isClient() world.isClient()}.
     *
     * @param world the world the item was used in
     * @param user  the player who used the item
     * @param hand  the hand used
     * @return a typed action result that specifies whether using the item was successful.
     * The action result contains the new item stack that the player's hand will be set to.
     */
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient) {
            //MagicPower.log(Level.INFO, user.getMainHandStack().getTranslationKey() + " " + user.getOffHandStack().getTranslationKey());
            /*if (user.inventory.getEmptySlot() > 0 || user.inventory.contains(new ItemStack(ModManifest.ModBlocks.MAGNIFYING_LENS_BLOCK))) {
                user.giveItemStack(new ItemStack(ModManifest.ModBlocks.MAGNIFYING_LENS_BLOCK));
            }*/
        }

        return super.use(world, user, hand);
    }
}
