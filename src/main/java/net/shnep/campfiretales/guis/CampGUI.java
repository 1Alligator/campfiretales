package net.shnep.campfiretales.guis;

import io.wispforest.owo.ui.base.BaseOwoScreen;
import io.wispforest.owo.ui.component.Components;
import io.wispforest.owo.ui.container.Containers;
import io.wispforest.owo.ui.container.FlowLayout;
import io.wispforest.owo.ui.core.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureContents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.shnep.campfiretales.CampfireTales;
import org.jetbrains.annotations.NotNull;

import java.awt.event.TextEvent;

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
                                .surface(Surface.DARK_PANEL)
                                .verticalAlignment(VerticalAlignment.CENTER)
                                .horizontalAlignment(HorizontalAlignment.CENTER)
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
        main_container.child(
                Components.texture(ResourceLocation.fromNamespaceAndPath("campfire-tales", "camp_icons/heal_icon.png"), -32, -32, 32, 32, 32, 32)
                        .positioning(Positioning.relative( 50, 50))
        );
    }

    //

    protected void update_menu() {

        Minecraft.getInstance().setScreen(new CampGUI());
        System.out.println(String.valueOf(CampfireTales.camp_index));

    }

}
