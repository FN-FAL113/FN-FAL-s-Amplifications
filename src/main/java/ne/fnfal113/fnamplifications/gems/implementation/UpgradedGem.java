package ne.fnfal113.fnamplifications.gems.implementation;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import lombok.Getter;
import ne.fnfal113.fnamplifications.utils.Keys;
import ne.fnfal113.fnamplifications.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

public class UpgradedGem extends Gem {

    @Getter
    private final NamespacedKey key;

    public UpgradedGem(SlimefunItem slimefunGemItem, ItemStack itemStackToSocket, Player p){
        super(slimefunGemItem, itemStackToSocket, p);

        this.key = Keys.createKey(slimefunGemItem.getId().toLowerCase() + "_gem_tier");
    }

    public void upgradeExistingGem(ItemStack slimefunGemItem, int gemTier){
        ItemMeta meta = getItemStackToSocket().getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();
        int itemGemTier = container.getOrDefault(getKey(), PersistentDataType.INTEGER, 4);
        List<String> lore = meta.getLore();

        if(isSameGem(getItemStackToSocket())){
            // gem pdc value must be equal to the succeeding item pdc value for tier order upgrades
            // pdc value is reversed, tier 1 = 4, tier 2 = 3, tier 3 = 2, tier 4 = 1
            // pdc value is then divided to the chance of the gem based from the config value or instance value
            if(itemGemTier - 1 == gemTier){ // item gem tier II = 3 - 1 == 2, gem to bind tier 3 = 2.
                getPlayer().setItemOnCursor(new ItemStack(Material.AIR));
                if (lore != null) { // update item gem lore
                    for (int i = 0; i < lore.size(); i++) {
                        if(lore.get(i).contains(Utils.colorTranslator(getSlimefunGemItemName().substring(0, getSlimefunGemItemName().lastIndexOf(" ") + 2)))){
                            lore.set(i, ChatColor.RED + "◬ " + slimefunGemItem.getItemMeta().getDisplayName());
                        }
                    }

                    meta.setLore(lore);
                    meta.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, gemTier);
                    getItemStackToSocket().setItemMeta(meta);

                    getPlayer().playSound(getPlayer().getLocation(), Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, 1.0F, 1.0F);
                }
            } else {
                getPlayer().playSound(getPlayer().getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 1.0F, 1.0F);
                getPlayer().sendMessage(Utils.colorTranslator("&eGem tier not compatible! upgrades must be in order (1-2-3-4)!"));
            }
        } else {
            getPlayer().sendMessage(Utils.colorTranslator("&eYou do not have a similar gem that can be upgraded!"));
        }
    }

}
