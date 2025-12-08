package net.shnep.campfiretales;

import net.fabricmc.api.ModInitializer;
import net.shnep.campfiretales.owo_lib.CampTalesConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CampfireTales implements ModInitializer {

	public static final String MOD_ID = "campfire-tales";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static final CampTalesConfig CONFIG = CampTalesConfig.createAndLoad();

    // Used in the upgrade menu to tell the current page.
    public static Integer camp_index = 0;

	@Override
	public void onInitialize() {

	}

}