package net.shnep.campfiretales.guis;

import io.wispforest.owo.ui.base.BaseOwoScreen;
import io.wispforest.owo.ui.component.Components;
import io.wispforest.owo.ui.container.Containers;
import io.wispforest.owo.ui.container.FlowLayout;
import io.wispforest.owo.ui.core.*;
import net.minecraft.network.chat.Component;
import net.shnep.campfiretales.CampfireTales;
import net.shnep.campfiretales.owo_lib.CampTalesConfig;
import org.jetbrains.annotations.NotNull;

public class CampGUI extends BaseOwoScreen<FlowLayout> {
    @Override
    protected @NotNull OwoUIAdapter<FlowLayout> createAdapter() {
        return OwoUIAdapter.create(this, Containers::verticalFlow);
        // TO DO
    }

    String mystring = "Button Test";

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

        main_container.child(
                        Components.button(Component.literal("Offer XP"), button -> {
                                    System.out.println("click");
                                }
                        ).positioning(Positioning.relative(50, 70))
                );

        main_container.child(
                        Components.label(Component.literal("This costs 10 XP Levels")
                        ).positioning(Positioning.relative(50, 30))
                );

        main_container.child(
                Components.button(Component.literal("Offer XP"), button -> {
                            System.out.println("click");
                        }
                ).positioning(Positioning.relative(50, 70))
        );

    }
}
