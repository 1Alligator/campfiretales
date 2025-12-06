package net.shnep.campfiretales.guis;

import io.wispforest.owo.ui.base.BaseOwoScreen;
import io.wispforest.owo.ui.component.Components;
import io.wispforest.owo.ui.container.Containers;
import io.wispforest.owo.ui.container.FlowLayout;
import io.wispforest.owo.ui.core.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.shnep.campfiretales.CampfireTales;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;


@Environment(EnvType.CLIENT)
public class CampGUI extends BaseOwoScreen<FlowLayout> {
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
                                .surface(Surface.PANEL)
                                .verticalAlignment(VerticalAlignment.CENTER)
                                .horizontalAlignment(HorizontalAlignment.CENTER)
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
        //

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
        //

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
            System.out.println("Condition Item");
            //condition_update(rootComponent, Minecraft.getInstance().player.inventoryMenu.getItems() >= Integer.parseInt(checker[1]));
        }
        else {
            System.out.println("Condition unrecognized");
        }

    }

    protected void condition_update(FlowLayout root, boolean condition_met, String[] cond_vals) {

        root.child(Components.button(Component.literal("Among"), button -> {

                    assert Minecraft.getInstance().player != null;

                        // Insert the success result here.

                        // LEVEL
                        if (Objects.equals(cond_vals[0], "level")) {
                            Minecraft.getInstance().player.experienceLevel -= Integer.parseInt(cond_vals[1]);
                        }


                        Minecraft.getInstance().player.connection.sendCommand("give @s minecraft:dirt");
                        Minecraft.getInstance().player.closeContainer();
                        Minecraft.getInstance().player.playSound(SoundEvents.ENCHANTMENT_TABLE_USE, 2.0f, 1.0f);

                    }
            ).active(condition_met).positioning(Positioning.relative(50, 72)).horizontalSizing(Sizing.fixed(90))
        );

    }

    protected void update_menu() {

        Minecraft.getInstance().setScreen(new CampGUI());
        System.out.println(String.valueOf(CampfireTales.camp_index));

    }

}
