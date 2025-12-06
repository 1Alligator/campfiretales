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
    public List<String> trade_names = new ArrayList<>(List.of("+1 Max Health", "Speed Upgrade", "3", "4", "5"));
    public List<String> trade_icons = new ArrayList<>(List.of("bell", "2", "3", "4", "5"));
    public List<String> trade_condition = new ArrayList<>(List.of("level:5", "item:dirt:5", "3", "4", "5"));
    public List<String> trade_result = new ArrayList<>(List.of("item:dirt:5", "attribute:max_health:add:5", "3", "4", "5"));

}