package net.shnep.campfiretales.guis;


import io.wispforest.owo.particles.systems.ParticleSystem;
import io.wispforest.owo.ui.base.BaseOwoScreen;
import io.wispforest.owo.ui.component.Components;
import io.wispforest.owo.ui.container.Containers;
import io.wispforest.owo.ui.container.FlowLayout;
import io.wispforest.owo.ui.core.*;
import it.unimi.dsi.fastutil.ints.IntList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.item.Firework;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleGroup;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.core.*;
import net.minecraft.core.component.predicates.FireworksPredicate;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.FireworkExplosion;
import net.minecraft.world.item.component.Fireworks;
import net.shnep.campfiretales.CampfireTales;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.logging.Level;
import java.util.random.RandomGenerator;

@Environment(EnvType.CLIENT)
public class CampGUI extends BaseOwoScreen<FlowLayout> {

    public Player camp_user = null;

    @Override
    protected @NotNull OwoUIAdapter<FlowLayout> createAdapter() {
        return OwoUIAdapter.create(this, Containers::verticalFlow);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    protected void build(FlowLayout rootComponent) {

        // Component Setup

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

        // MAIN GUI
        main_container.child(
                Components.texture(ResourceLocation.fromNamespaceAndPath("campfire-tales", "camp_textures/layout.png"), 0, 0, 320, 180, 320, 180)
                        .positioning(Positioning.relative( 50, 50))
        );

        // TRADE NAME
        main_container.child(
                Components.texture(ResourceLocation.fromNamespaceAndPath("campfire-tales", "camp_textures/textbar.png"), 0, 0, 128, 20, 128, 20)
                        .positioning(Positioning.relative( 50, 29))
        );
        // TRADE DISPLAY
        main_container.child(
                Components.texture(ResourceLocation.fromNamespaceAndPath("campfire-tales", "camp_textures/banner.png"), 0, 0, 128 * 2, 64 * 2, 128 * 2, 64 * 2)
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
                Components.texture(ResourceLocation.withDefaultNamespace("apple"), -32, -32, 32, 32, 32, 32)
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
            String item_name = cond_vals[1].toUpperCase().replace("_", " ");
            trade_cond_name = "Offer " + cond_vals[2] + " " + item_name;
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
                                    if (camp_user.getInventory().getItem(i).getItemName().toString().contains(cond_vals[1])) {
                                        camp_user.getInventory().getItem(i).setCount(camp_user.getInventory().getItem(i).getCount() - Integer.parseInt(cond_vals[2]));
                                        break;
                                    }
                                }
                            }
                        }

                    String[] result_checker = CampfireTales.CONFIG.trade_result().get(CampfireTales.camp_index).split(":", 5);
                        System.out.println(result_checker[0]);
                    switch (result_checker[0]) {
                        case "item" -> {
                            // Get item and add to inv
                            ItemStack result_item = new ItemStack(BuiltInRegistries.ITEM.getValue(ResourceLocation.fromNamespaceAndPath(result_checker[1], result_checker[2])), Integer.parseInt(result_checker[3]));
                            camp_user.getInventory().add(result_item);
                        }
                        case "attribute" -> {

                            // Get attribute add apply to player
                            final Holder<Attribute> ATTRI = BuiltInRegistries.ATTRIBUTE.wrapAsHolder(Objects.requireNonNull(BuiltInRegistries.ATTRIBUTE.getValue(ResourceLocation.fromNamespaceAndPath(result_checker[1], result_checker[2]))));
                            if (Objects.equals(result_checker[3], "add")) {
                                Objects.requireNonNull(camp_user.getLivingEntity().getAttribute(ATTRI)).setBaseValue(camp_user.getLivingEntity().getAttributeValue(ATTRI) + Double.parseDouble(result_checker[4]));
                            } else if (Objects.equals(result_checker[3], "set")) {
                                Objects.requireNonNull(camp_user.getLivingEntity().getAttribute(ATTRI)).setBaseValue(Double.parseDouble(result_checker[4]));
                            }
                        }
                        case "command" -> Minecraft.getInstance().player.connection.sendCommand(result_checker[1]);
                        case null, default ->
                                Minecraft.getInstance().player.connection.sendCommand("say <Campfire Tales>: INVALID OPERATION (Try: item, attribute, command)");
                    }


                    Minecraft.getInstance().player.closeContainer();
                    Minecraft.getInstance().player.playSound(SoundEvents.ENCHANTMENT_TABLE_USE, 2.0f, 1.0f);

                    // If do_trade_firework is true do cool firework upgrade effect
                    if (CampfireTales.CONFIG.do_trade_firework()) {

                        FireworkExplosion fire_work_ex = new FireworkExplosion(
                                FireworkExplosion.Shape.BURST,
                                IntList.of(0x0FDEF2, 0x279CF5, 0x63E068, 0x8063E0), // Cyan, Pink, Yellow, White
                                IntList.of(), // No fade colors
                                true, // Flicker
                                true  // Trail
                        );

                        assert Minecraft.getInstance().level != null;
                        Minecraft.getInstance().level.createFireworks(camp_user.position().x, camp_user.position().y, camp_user.position().z, 0.0, 0.1, 0.0, List.of(fire_work_ex));

                    }

                    // Heals the player if heal on trade is currently true
                    if (CampfireTales.CONFIG.heal_on_trade()) {
                        camp_user.heal(999);
                    }

                    // Clean up values
                    camp_user = null;
                    CampfireTales.camp_index = 0;

                    }
            ).active(condition_met).positioning(Positioning.relative(50, 72)).horizontalSizing(Sizing.fixed(100))
        );
    }

    protected void update_menu() {

        CampGUI display = new CampGUI();
        display.camp_user = camp_user;
        Minecraft.getInstance().setScreen(display);
        System.out.println(String.valueOf(CampfireTales.camp_index));

    }
}
