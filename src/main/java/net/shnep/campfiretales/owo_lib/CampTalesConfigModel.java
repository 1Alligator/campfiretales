package net.shnep.campfiretales.owo_lib;

import io.wispforest.owo.config.Option;
import io.wispforest.owo.config.annotation.*;
import net.shnep.campfiretales.CampfireTales;
import java.util.ArrayList;
import java.util.List;

@Sync(Option.SyncMode.OVERRIDE_CLIENT)
@Modmenu(modId = CampfireTales.MOD_ID)
@Config(name = "campconfig", wrapperName = "CampTalesConfig")
public class CampTalesConfigModel {

    @SectionHeader("general")
    public String levelup_block = "minecraft:campfire";
    public boolean heal_on_trade = true;
    public boolean do_trade_firework = true;

    @SectionHeader("customize_trades")
    public List<String> trade_names = new ArrayList<>(List.of("+1 Heart", "+1 Armor Point", "Cobble for Ingot"));
    public List<String> trade_icons = new ArrayList<>(List.of("apple", "iron_chestplate", "iron_ingot"));
    public List<String> trade_condition = new ArrayList<>(List.of("level:10", "item:minecraft:iron_block:4", "item:minecraft:cobblestone:32"));
    public List<String> trade_result = new ArrayList<>(List.of("attribute:minecraft:max_health:add:2", "attribute:minecraft:armor:add:1", "item:minecraft:iron_ingot:1"));

}