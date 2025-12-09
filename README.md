# <ins>Campfire Tales</ins>

Campfire Tales is a mod for Minecraft that attempts to add a basic *player upgrade* system to campfires (or any block you desire) in a similar fashion to the systems seen in **Dark Souls** and **The legend of Zelda: Breath of The Wild**

## *- How it works -*
Simply **right-click** on a campfire to open up a menu which provides several different stat/item "trades/upgrades" that cost XP levels or items.

## *- Configuration -*
Campfire Tales is made to be tweaked to fit whatever modpack / gameplay situations you may need. Below is a list of the different config parameters and how they can be tweaked:

### **(General)**   
:string: - Upgrade Block - *The block that displays the upgrade UI when right-clicked.*     
:bool: - Heal On Trade - *Heals the player back to full health when a trade is made (Good for scenarios where you increase the player's max health)*    
:bool: - Play Firework on Trade - *Adds a firework explosion when a trade is made for pizzaz*   

### **(Upgrade Customization)**
:list: - Trade Names - *The names of the upgrades*  
:list: - Trade Icons - *The icon of each upgrade*   
:list: - Trade Conditions - *The condition for each upgrade*    
:list: - Trade Results - *The operation that is run once an upgrade is selected*       

### *(Usage)*
Each upgrade/trade is constructed based off of the list values provided in the "Trade" lists. Let's say I wanted to create a upgrade on *Page 1* of the upgrade menu that costs 5 levels, increases max health by 1, has an apple icon, and is named "my cool upgrade". This can be achieved by structuring the values at **index 1** of each trade lists as so: 

Trade Names: (my cool upgrade)      
Trade Icons: (apple)     
Trade Conditions: (level:5)     
Trade Result: (attribute:minecraft:max_health:add:1)

### **(Operators)**
*Conditions*

level:(amount)  
item:(namespace):(item_name):(amount)

*Results*

item:(namespace):(item_name):(amount)    
attribute:(namespace):(item_name):(amount)   
command:(command)

## **- Known Issues / Lacking Features -**
* Currently the trade/upgrade icon can *only* be a **Vanilla Minecraft** item texture.

___

This mod requires [owo-lib](https://github.com/wisp-forest/owo-lib) to function properly. Additionally, for the modpack makers out there I recommend pairing this mod with [Healing Campfire](https://github.com/Serilum/Healing-Campfire) *By Serilum* to really make campfires feel like a safety/upgrade zone.



# Downloads
WIP
