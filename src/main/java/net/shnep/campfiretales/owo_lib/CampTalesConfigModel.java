package net.shnep.campfiretales.owo_lib;

import com.google.gson.annotations.SerializedName;
import com.terraformersmc.modmenu.util.mod.Mod;
import io.wispforest.endec.annotations.Comment;
import io.wispforest.owo.config.ConfigWrapper;
import io.wispforest.owo.config.Option;
import io.wispforest.owo.config.OwoConfigCommand;
import io.wispforest.owo.config.annotation.*;
import io.wispforest.owo.registration.annotations.AssignedName;
import jdk.jfr.Name;
import net.shnep.campfiretales.CampfireTales;
import java.util.ArrayList;
import java.util.List;
import io.wispforest.owo.config.Option.Key;
import net.shnep.campfiretales.owo_lib.CampTalesConfig.Keys;

@Sync(Option.SyncMode.OVERRIDE_CLIENT)
@Modmenu(modId = CampfireTales.MOD_ID)
@Config(name = "campconfig", wrapperName = "CampTalesConfig")
public class CampTalesConfigModel {

    // The block that is used


    @SectionHeader("General")
    public String levelup_block = "minecraft:campfire";
    public boolean heal_on_trade = true;
    public boolean do_trade_firework = true;

    @SectionHeader("Customize Trades")
    // Some options
    public List<String> trade_names = new ArrayList<>(List.of("+1 Heart", "+5% Speed Upgrade", "+1 Armor Point"));
    public List<String> trade_icons = new ArrayList<>(List.of("apple", "sugar", "iron_ingot"));
    public List<String> trade_condition = new ArrayList<>(List.of("level:10", "level:5", "item:iron_block:2"));
    public List<String> trade_result = new ArrayList<>(List.of("attribute:max_health:add:2", "attribute:movement_speed:add:2", "attribute:armor:add:1"));


}