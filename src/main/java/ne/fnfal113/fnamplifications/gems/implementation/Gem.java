package ne.fnfal113.fnamplifications.gems.implementation;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import lombok.Getter;
import ne.fnfal113.fnamplifications.FNAmplifications;
import ne.fnfal113.fnamplifications.utils.Keys;
import ne.fnfal113.fnamplifications.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@SuppressWarnings("ConstantConditions")
public class Gem {

    @Getter
    private final SlimefunItem slimefunItem;
    @Getter
    private final ItemStack itemStackToSocket;
    @Getter
    private final String sfItemName;
    @Getter
    private final String gemID;
    @Getter
    private final Player player;
    @Getter
    private final NamespacedKey key1;
    @Getter
    private final NamespacedKey key2;

    @ParametersAreNonnullByDefault
    public Gem(SlimefunItem sfItem, ItemStack itemToSocket, Player p){
        this.slimefunItem = sfItem;
        this.itemStackToSocket = itemToSocket;
        this.sfItemName = sfItem.getItemName();
        this.gemID = sfItem.getId();
        this.player = p;
        this.key1 = new NamespacedKey(FNAmplifications.getInstance(), sfItem.getId().toLowerCase());
        this.key2 = new NamespacedKey(FNAmplifications.getInstance(), itemToSocket.getType().toString().toLowerCase() + "_socket_amount");

    }

    public void onDrag(InventoryClickEvent event, boolean retaliateWeapon){
        ItemMeta meta = getItemStackToSocket().getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();

        if(checkGemAmount(container, getItemStackToSocket()) < 5) { // gem amount must be below 5
            if(!isSameGem(getItemStackToSocket())){ // check if the gem being added already exist
                getPlayer().setItemOnCursor(new ItemStack(Material.AIR));
                socketItem();
                if(retaliateWeapon){ // add return weapon pdc value
                    retaliateWeapon();
                }
            } else{
                getPlayer().sendMessage(Utils.colorTranslator("&6Your item has " + getSfItemName() + " &6socketed already!"));
                getPlayer().playSound(getPlayer().getLocation(), Sound.ENTITY_BAT_TAKEOFF, 1.0F, 1.0F);
            }
        } else {
            getPlayer().sendMessage(Utils.colorTranslator("&eOnly 5 gems per item is allowed!"));
            getPlayer().playSound(getPlayer().getLocation(), Sound.ENTITY_BLAZE_HURT, 1.0F, 1.0F);
        }
        event.setCancelled(true);
    }

    public void socketItem(){
        String name = getSfItemName();
        ItemStack itemStack = getItemStackToSocket();
        ItemMeta meta = itemStack.getItemMeta();

        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        int amountOfGems = pdc.getOrDefault(getKey2(), PersistentDataType.INTEGER, 0);

        if (amountOfGems == 0) { // add the lore when adding a gem for the first time
            List<String> lore;
            if(meta.hasLore()){ // compatibility with other items that has existing lore
                lore = meta.getLore();
            }else{
                lore = new ArrayList<>();
            }
            lore.add("");
            lore.add(Utils.colorTranslator("&6◤◤◤◤◤◤| &d&lGems &c|◥◥◥◥◥◥"));
            lore.add(ChatColor.RED + "◬ " + name);
            lore.add(Utils.colorTranslator("&6◤◤◤◤◤◤◤◤◤◤◤&c◥◥◥◥◥◥◥◥◥◥◥"));

            meta.setLore(lore);
        } else { // append the new added gem to existing lore
            List<String> lore2 = meta.getLore();
            if (lore2 != null) {
                for (int i = 0; i < lore2.size(); i++) {
                    if(lore2.get(i).startsWith(Utils.colorTranslator("&6◤◤◤◤◤◤| &d&lGems &c|◥◥◥◥◥◥"))){
                        lore2.add(i + 1, ChatColor.RED + "◬ " + name);
                    }
                }

                meta.setLore(lore2);
            }
        }

        pdc.set(getKey1(), PersistentDataType.STRING, getGemID());
        pdc.set(getKey2(), PersistentDataType.INTEGER, amountOfGems + 1);
        itemStack.setItemMeta(meta);

        getPlayer().sendMessage(Utils.colorTranslator("&eSuccessfully bound " + name + " &eto " +
                itemStack.getType().name().replace("_", " ").toLowerCase(Locale.ROOT)));
        getPlayer().playSound(getPlayer().getLocation(), Sound.ENTITY_BLAZE_SHOOT, 1.0F, 1.0F);
    }

    /**
     *
     * @param pdc the persistent data that contains the key and amount
     *            value from the itemstack
     * @param itemStack the itemstack that has the needed pdc data
     * @return the amount of gem inside the itemstack if there are any
     */
    public int checkGemAmount(PersistentDataContainer pdc, ItemStack itemStack){
        return pdc.getOrDefault(
                new NamespacedKey(FNAmplifications.getInstance(), itemStack.getType().toString().toLowerCase() + "_socket_amount"),
                PersistentDataType.INTEGER, 0);
    }

    /**
     *
     * @param itemStack the item to check whether it has already the gem
     * @return if it has the same existing gem
     */
    public boolean isSameGem(ItemStack itemStack){
        ItemMeta meta = itemStack.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();

        if(container.isEmpty()) {
           return false;
        }

        return container.has(getKey1(), PersistentDataType.STRING);
    }

    /**
     * This makes throwable weapons return to the owner by
     * adding the needed pdc to check whether it has retaliate gem
     */
    public void retaliateWeapon(){
        ItemMeta meta = getItemStackToSocket().getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        pdc.set(Keys.RETURN_WEAPON_KEY, PersistentDataType.STRING, "true");
        getItemStackToSocket().setItemMeta(meta);
    }

}