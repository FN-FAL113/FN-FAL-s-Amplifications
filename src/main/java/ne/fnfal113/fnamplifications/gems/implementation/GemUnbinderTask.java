package ne.fnfal113.fnamplifications.gems.implementation;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import lombok.Getter;
import ne.fnfal113.fnamplifications.gems.RetaliateGem;
import ne.fnfal113.fnamplifications.gems.abstracts.AbstractGem;
import ne.fnfal113.fnamplifications.utils.Keys;
import ne.fnfal113.fnamplifications.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Predicate;

public class GemUnbinderTask {

    @Getter
    private final Player player;
    @Getter
    private final ItemStack itemInOffhand;

    public GemUnbinderTask(Player player, ItemStack itemInOffhand) {
        this.player = player;
        this.itemInOffhand = itemInOffhand;
    }

    /**
     * Retrieve the gems from the item in the offhand and display it in a inventory gui
     */
    @SuppressWarnings("ConstantConditions")
    public void getGemsFromItem(){
        Inventory inventory = Bukkit.createInventory(null, 9, Utils.colorTranslator("&cSelect a gem to unbind"));
        PersistentDataContainer pdc = getItemInOffhand().getItemMeta().getPersistentDataContainer();

        if(pdc.isEmpty()){
            return;
        }

        List<ItemStack> gemArray = new ArrayList<>();
        for(NamespacedKey key : GemKeysEnum.GEM_KEYS_ENUM.getGEM_KEYS()){
            if(pdc.has(key, PersistentDataType.STRING)) { // get all the pdc value based from the gem key enum
                SlimefunItem gem = SlimefunItem.getById(pdc.get(key, PersistentDataType.STRING));
                if(gem instanceof AbstractGem) {
                    gemArray.add(gem.getItem().clone());
                }
            }
        }

        if(gemArray.isEmpty()){
            getPlayer().sendMessage(Utils.colorTranslator("&eItem doesn't have any gems bound to it"));
            return;
        }

        for (ItemStack gems: gemArray) { // add all gems inside the inventory ui
            inventory.addItem(gems);
        }

        getPlayer().openInventory(inventory);
        getPlayer().playSound(player.getLocation(), Sound.ENTITY_ILLUSIONER_PREPARE_MIRROR, 1.0F, 1.0F);
    }

    /**
     * this is where the selected gem is unbound from the item
     * @param gem the gem that will be removed from the item
     * @param chance the chance to remove the gem from the item
     */
    @SuppressWarnings("ConstantConditions")
    public void unBindGem(SlimefunItem gem, int chance){
        getPlayer().getInventory().getItemInMainHand().setAmount(0);

        if(ThreadLocalRandom.current().nextInt(100) <= chance) {
            ItemMeta meta = getItemInOffhand().getItemMeta();
            PersistentDataContainer pdc = meta.getPersistentDataContainer();

            NamespacedKey socketAmountKey = Keys.createKey(getItemInOffhand().getType().toString().toLowerCase() + "_socket_amount");
            NamespacedKey gemKey = Keys.createKey(gem.getId().toLowerCase());

            List<String> lore = meta.getLore();

            removeOtherPdc(pdc, gem);

            pdc.remove(gemKey);
            pdc.set(socketAmountKey, PersistentDataType.INTEGER, pdc.get(socketAmountKey, PersistentDataType.INTEGER) - 1);

            if (pdc.get(socketAmountKey, PersistentDataType.INTEGER) == 0) { // remove excess space above the gem lore
                for (int i = 0; i < lore.indexOf(Utils.colorTranslator("&6◤◤◤◤◤◤| &d&lGems &c|◥◥◥◥◥◥")) + 1; i++) {
                    if (lore.get(i).contains(Utils.colorTranslator("&6◤◤◤◤◤◤| &d&lGems &c|◥◥◥◥◥◥"))) {
                        lore.remove(i - 1);
                    }
                }

                Predicate<String> condition =  line ->
                        line.contains(Utils.colorTranslator(gem.getItemName())) ||
                                line.contains(Utils.colorTranslator("&6◤◤◤◤◤◤| &d&lGems &c|◥◥◥◥◥◥")) ||
                                line.contains(Utils.colorTranslator("&6◤◤◤◤◤◤◤◤◤◤◤&c◥◥◥◥◥◥◥◥◥◥◥"));
                lore.removeIf(condition);
                pdc.remove(socketAmountKey);
            } else {
                lore.removeIf(line -> line.contains(Utils.colorTranslator(gem.getItemName())));
            }

            meta.setLore(lore);
            getItemInOffhand().setItemMeta(meta);
            getPlayer().sendMessage(Utils.colorTranslator("&aSuccessfully removed selected gem!"));
            getPlayer().playSound(getPlayer().getLocation(), Sound.ENTITY_VILLAGER_WORK_WEAPONSMITH, 1.0F, 1.0F);
        } else {
            getPlayer().sendMessage(Utils.colorTranslator("&cFailed to unbind the gem from the item!"));
            getPlayer().playSound(getPlayer().getLocation(), Sound.ENTITY_ZOMBIE_INFECT, 1.0F, 1.0F);
        }
    }

    /**
     * Extra pdc instance are removed to avoid problems with new bound gems
     * @param pdc the persistent data container of the item meta
     * @param gem the gem that will be removed from the item
     */
    public void removeOtherPdc(PersistentDataContainer pdc, SlimefunItem gem){
        NamespacedKey gemTierKey = Keys.createKey(gem.getId().toLowerCase() + "_gem_tier");

        if(pdc.has(gemTierKey, PersistentDataType.INTEGER)){
            pdc.remove(gemTierKey);
        } // remove gem tier pdc instance if exist

        if(gem instanceof RetaliateGem){
            pdc.remove(Keys.RETURN_WEAPON_KEY);
        } // is retaliate gem
    }

}