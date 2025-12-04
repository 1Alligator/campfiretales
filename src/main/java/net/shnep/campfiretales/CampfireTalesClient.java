package net.shnep.campfiretales;

import net.fabricmc.api.ClientModInitializer;
import net.shnep.campfiretales.events.InteractCamp;

public class CampfireTalesClient implements ClientModInitializer {


    @Override
    public void onInitializeClient() {

        InteractCamp.initialize();

    }
}
