package net.shnep.campfiretales.events;


import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionResult;
import net.shnep.campfiretales.CampfireTales;
import net.shnep.campfiretales.guis.CampGUI;

public class InteractCamp {



    public static void initialize() {

        // GETS THE BLOCK THE PLAYER IS LOOKING AT DURING A RIGHT CLICK
        UseBlockCallback.EVENT.register((player, level, interactionHand, blockHitResult) -> {

            System.out.println(level.getBlockState(blockHitResult.getBlockPos()).toString());
            if (level.getBlockState(blockHitResult.getBlockPos()).toString().contains(CampfireTales.CONFIG.levelup_block())) {

                Minecraft.getInstance().setScreen(new CampGUI());
                return InteractionResult.SUCCESS;
            }
            else {
                return InteractionResult.PASS;
            }

        });
    }
}
