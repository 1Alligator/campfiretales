package net.shnep.campfiretales.events;

import com.mojang.authlib.minecraft.client.MinecraftClient;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.gameevent.GameEvent;
import net.shnep.campfiretales.CampfireTales;
import net.shnep.campfiretales.guis.CampGUI;

import javax.swing.*;
@Environment(EnvType.CLIENT)
public class InteractCamp {

    public static void initialize() {

        // GETS THE BLOCK THE PLAYER IS LOOKING AT DURING A RIGHT CLICK
        UseBlockCallback.EVENT.register((player, level, interactionHand, blockHitResult) -> {

            // Crashes due to not being run on the render thread.
            System.out.println(player.getMainHandItem().getComponents().toString());

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
