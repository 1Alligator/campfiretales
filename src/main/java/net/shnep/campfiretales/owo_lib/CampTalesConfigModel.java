package net.shnep.campfiretales.owo_lib;

import io.wispforest.owo.config.Option;
import io.wispforest.owo.config.annotation.Config;
import io.wispforest.owo.config.annotation.Modmenu;
import io.wispforest.owo.config.annotation.SectionHeader;
import io.wispforest.owo.config.annotation.Sync;
import net.shnep.campfiretales.CampfireTales;
import java.util.ArrayList;
import java.util.List;

@Sync(Option.SyncMode.OVERRIDE_CLIENT)
@Modmenu(modId = CampfireTales.MOD_ID)
@Config(name = "campconfig", wrapperName = "CampTalesConfig")
public class CampTalesConfigModel {

    // The block that is used
    @SectionHeader("General")
    public String levelup_block = "minecraft:campfire";
    public boolean heal_on_trade = true;

    @SectionHeader("Customize Trades")
    // Some options
    public List<String> trade_names = new ArrayList<>(List.of("+1 Max Health", "Speed Upgrade", "3", "4", "5"));
    public List<String> trade_icons = new ArrayList<>(List.of("bell", "2", "3", "4", "5"));
    public List<String> trade_condition = new ArrayList<>(List.of("level:5", "item:dirt:5", "3", "4", "5"));
    public List<String> trade_result = new ArrayList<>(List.of("item:dirt:5", "attribute:max_health:add:2", "attribute:max_health:add:2", "4", "5"));


}