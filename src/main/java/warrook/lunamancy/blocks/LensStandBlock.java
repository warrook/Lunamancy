package warrook.lunamancy.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.apache.logging.log4j.Level;
import warrook.lunamancy.Lunamancy;
import warrook.lunamancy.ModManifest;
import warrook.lunamancy.items.LensItem;
import warrook.lunamancy.utils.enums.LensType;

public class LensStandBlock extends BaseBlock {
    public static final VoxelShape BOX = VoxelShapes.union(createCuboidShape(4d, 0d, 4d, 12d, 9d, 12d));

    public static final EnumProperty<LensType> LENS_TYPE;

    public LensStandBlock() {
        super("lens_stand", FabricBlockSettings
                .of(Material.METAL)
                .nonOpaque()
        );
        this.setDefaultState(this.getDefaultState().with(LENS_TYPE, LensType.NONE));
    }
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient()) {
            if (hand == Hand.MAIN_HAND) {
                if (state.get(LENS_TYPE) == LensType.NONE) { //no lens in block
                    ItemStack stack = player.getStackInHand(hand);
                    if (LensType.getValue(stack.getItem()) != LensType.NONE) { //holding lens
                        BlockState newState = state.with(LENS_TYPE, LensType.getValue(stack.getItem()));
                        world.setBlockState(pos, newState, 3);
                        stack.decrement(1);
                        return ActionResult.SUCCESS;
                    } else {
                        return ActionResult.PASS;
                    }
                } else { //there is a lens
                    ItemStack stack = player.getStackInHand(hand);
                    if (stack.isEmpty()) { //has empty hand
                        BlockState newState = state.with(LENS_TYPE, LensType.NONE);
                        world.setBlockState(pos, newState, 3);
                        player.setStackInHand(hand, new ItemStack(state.get(LENS_TYPE).getItem()));
                        return ActionResult.SUCCESS;
                    }
                }
            }
        }

        return ActionResult.PASS;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(LENS_TYPE);
        super.appendProperties(builder);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return BOX;
    }

    static {
        LENS_TYPE = EnumProperty.of("lens", LensType.class);
    }
}
