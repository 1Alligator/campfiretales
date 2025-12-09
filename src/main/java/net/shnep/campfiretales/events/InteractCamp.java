package net.shnep.campfiretales.events;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResult;
import net.shnep.campfiretales.CampfireTales;
import net.shnep.campfiretales.guis.CampGUI;

import javax.swing.*;
@Environment(EnvType.CLIENT)
public class InteractCamp {

    public static void initialize() {

        // GETS THE BLOCK THE PLAYER IS LOOKING AT DURING A RIGHT CLICK
        UseBlockCallback.EVENT.register((player, level, interactionHand, blockHitResult) -> {

            if (level.getBlockState(blockHitResult.getBlockPos()).toString().contains(CampfireTales.CONFIG.levelup_block()) && !(player.getMainHandItem().getComponents().toString().contains("consumable"))) {

                player.playSound(SoundEvents.AMETHYST_CLUSTER_PLACE, 0.4f, 0.4f);
                Minecraft.getInstance().execute(() -> {
                    CampGUI display = new CampGUI();
                    display.camp_user = player;
                    Minecraft.getInstance().setScreen(display);
                });
                return InteractionResult.SUCCESS;

            }
            else {
                return InteractionResult.PASS;
            }

        });

    }



}
