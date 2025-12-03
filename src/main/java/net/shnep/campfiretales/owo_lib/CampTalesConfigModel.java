package net.shnep.campfiretales.owo_lib;

import io.wispforest.owo.config.annotation.Config;
import io.wispforest.owo.config.annotation.Modmenu;
import io.wispforest.owo.registration.annotations.AssignedName;
import net.shnep.campfiretales.CampfireTales;
import java.util.ArrayList;
import java.util.List;

@Modmenu(modId = CampfireTales.MOD_ID)
@Config(name = "campconfig", wrapperName = "CampTalesConfig")
public class CampTalesConfigModel {

    // The block that is used
    @AssignedName("Block to use")
    public String levelup_block = "minecraft:campfire";

    // Some options
    public List<String> someOption = new ArrayList<>(List.of("1", "2", "3", "4", "5"));

}