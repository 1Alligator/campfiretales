package net.shnep.campfiretales.guis;

import com.mojang.authlib.minecraft.client.MinecraftClient;
import io.wispforest.owo.mixin.ui.MinecraftClientMixin;
import io.wispforest.owo.serialization.RegistriesAttribute;
import io.wispforest.owo.ui.base.BaseOwoScreen;
import io.wispforest.owo.ui.component.Components;
import io.wispforest.owo.ui.container.Containers;
import io.wispforest.owo.ui.container.FlowLayout;
import io.wispforest.owo.ui.core.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.core.*;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.HolderSetCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.commands.GameRuleCommand;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.datafix.fixes.EntityAttributeBaseValueFix;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.WorldOptions;
import net.minecraft.world.level.storage.ValueOutput;
import net.shnep.campfiretales.CampfireTales;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Objects;


@Environment(EnvType.CLIENT)
public class CampGUI extends BaseOwoScreen<FlowLayout> {

    public Player camp_user = null;

    @Override
    protected @NotNull OwoUIAdapter<FlowLayout> createAdapter() {
        return OwoUIAdapter.create(this, Containers::verticalFlow);
        // TO DO
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    protected void build(FlowLayout rootComponent) {

        rootComponent
                .surface(Surface.VANILLA_TRANSLUCENT)
                .horizontalAlignment(HorizontalAlignment.CENTER)
                .verticalAlignment(VerticalAlignment.CENTER);

        FlowLayout main_container = rootComponent.child(
                        Containers.verticalFlow(Sizing.content(), Sizing.content())
                                .padding(Insets.of(80))
                                .surface(Surface.VANILLA_TRANSLUCENT)
                                .verticalAlignment(VerticalAlignment.CENTER)
                                .horizontalAlignment(HorizontalAlignment.CENTER)
                );

        main_container.child(
                Components.texture(ResourceLocation.fromNamespaceAndPath("campfire-tales", "camp_icons/layout.png"), 0, 0, 320, 180, 320, 180)
                        .positioning(Positioning.relative( 50, 50))
        );

        main_container.child(
                Components.texture(ResourceLocation.fromNamespaceAndPath("campfire-tales", "camp_icons/textbar.png"), 0, 0, 128, 20, 128, 20)
                        .positioning(Positioning.relative( 50, 29))
        );

        main_container.child(
                Components.texture(ResourceLocation.fromNamespaceAndPath("campfire-tales", "camp_icons/banner.png"), 0, 0, 128 * 2, 64 * 2, 128 * 2, 64 * 2)
                        .positioning(Positioning.relative( 50, 50))
        );

        // MOVE PAGE RIGHT
        if ( CampfireTales.camp_index < CampfireTales.CONFIG.trade_names().size() - 1) {
            rootComponent.child(
                    Components.button(Component.literal(">"), button -> {
                                CampfireTales.camp_index += 1;
                                update_menu();
                            }
                    ).positioning(Positioning.relative(64, 50)).verticalSizing(Sizing.fixed(50))
            );
        }
        else {
            rootComponent.child(
                    Components.button(Component.literal(">"), button -> {
                            }
                    ).active(false).positioning(Positioning.relative(64, 50)).verticalSizing(Sizing.fixed(50))
            );
        }

        // MOVE PAGE LEFT
        if (CampfireTales.camp_index > 0) {
            rootComponent.child(
                    Components.button(Component.literal("<"), button -> {
                                    CampfireTales.camp_index -= 1;
                                    update_menu();
                            }
                    ).positioning(Positioning.relative(36, 50)).verticalSizing(Sizing.fixed(50))
            );
        }
        else {
            rootComponent.child(
                    Components.button(Component.literal("<"), button -> {
                            }
                    ).active(false).positioning(Positioning.relative(36, 50)).verticalSizing(Sizing.fixed(50))
            );
        }

        // TRADE NAME
        main_container.child(
                        Components.label(Component.literal(CampfireTales.CONFIG.trade_names().get(CampfireTales.camp_index))
                        ).positioning(Positioning.relative(50, 30))
        );

        // TRADE ICON
        System.out.println(String.valueOf(CampfireTales.CONFIG.trade_icons().get(CampfireTales.camp_index)));
        String trade_icon = "textures/item/" + CampfireTales.CONFIG.trade_icons().get(CampfireTales.camp_index) + ".png";

        main_container.child(
                Components.texture(ResourceLocation.withDefaultNamespace(trade_icon), -32, -32, 32, 32, 32, 32)
                        .positioning(Positioning.relative( 50, 50))
        );

        // Trade condition setup
        String trade_condition = CampfireTales.CONFIG.trade_condition().get(CampfireTales.camp_index);
        String[] checker = trade_condition.split(":", 5);
        assert Minecraft.getInstance().player != null;

        if (Objects.equals(checker[0], "level")) {
            condition_update(rootComponent, Minecraft.getInstance().player.experienceLevel >= Integer.parseInt(checker[1]), checker);
        }
        else if (Objects.equals(checker[0], "item")) {

            for (int i = 0; i < 36; ++i) {
               ItemStack item_check = Minecraft.getInstance().player.getInventory().getItem(i);
               if ((item_check.getItemName().toString().contains(checker[1])) && item_check.getCount() >= Integer.parseInt(checker[2])) {
                   condition_update(rootComponent, true, checker);
                   break;
               }
               else if (i == 35) {
                   condition_update(rootComponent, false, checker);
                   break;
               }
            }

            //condition_update(rootComponent, Minecraft.getInstance().player.inventoryMenu.getItems() >= Integer.parseInt(checker[1]));
        }
        else {
            System.out.println("Condition unrecognized");
        }

    }

    protected void condition_update(FlowLayout root, boolean condition_met, String[] cond_vals) {

        String trade_cond_name = "";

        if (Objects.equals(cond_vals[0], "level")) {
            trade_cond_name = "Offer " + cond_vals[1] + " Levels";
        }
        else if (Objects.equals(cond_vals[0], "item")) {
            trade_cond_name = "Offer " + cond_vals[2] + " " + cond_vals[1];
        }
        else {
            trade_cond_name = "NULL";
        }

        root.child(Components.button(Component.literal(trade_cond_name), button -> {

                    assert Minecraft.getInstance().player != null;

                        // LEVEL OPERATION
                        if (Objects.equals(cond_vals[0], "level")) {
                            int new_val = -Integer.parseInt(cond_vals[1]);
                            if (camp_user != null) {
                                camp_user.giveExperienceLevels(new_val);
                            }
                        }
                        // ITEM OPERATION
                        else if (Objects.equals(cond_vals[0], "item")) {

                            if (camp_user != null) {
                                for (int i = 0; i < 36; ++i) {
                                    if ( camp_user.getInventory().getItem(i).getItemName().toString().contains(cond_vals[1])) {
                                        camp_user.getInventory().getItem(i).setCount(camp_user.getInventory().getItem(i).getCount() - Integer.parseInt(cond_vals[2]));
                                        break;
                                    }
                                }
                            }
                        }

                        // Runs the command_result

                    // Minecraft.getInstance().level.getServer().getGameRules().getRule(GameRules.RULE_SENDCOMMANDFEEDBACK).set(false, Minecraft.getInstance().level.getServer());
                    Minecraft.getInstance().player.connection.sendCommand(CampfireTales.CONFIG.trade_result().get(CampfireTales.camp_index));
                    Minecraft.getInstance().player.closeContainer();
                    Minecraft.getInstance().player.playSound(SoundEvents.ENCHANTMENT_TABLE_USE, 2.0f, 1.0f);
                    camp_user = null;
                    CampfireTales.camp_index = 0;

                    }
            ).active(condition_met).positioning(Positioning.relative(50, 72)).horizontalSizing(Sizing.fixed(90))
        );

    }

    protected void update_menu() {

        CampGUI display = new CampGUI();
        display.camp_user = camp_user;
        Minecraft.getInstance().setScreen(display);
        System.out.println(String.valueOf(CampfireTales.camp_index));

    }

}
