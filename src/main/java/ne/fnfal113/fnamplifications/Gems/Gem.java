package ne.fnfal113.fnamplifications.Gems;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import ne.fnfal113.fnamplifications.FNAmplifications;
import ne.fnfal113.fnamplifications.Utils.Utils;
import org.apache.commons.lang.Validate;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Gem {

    private final SlimefunItem slimefunItem;
    private final ItemStack itemStack;
    private final String sfItemName;
    private final String gemID;
    private final Player player;
    private final NamespacedKey key1;
    private final NamespacedKey key2;

    @ParametersAreNonnullByDefault
    public Gem(SlimefunItem sfItem, ItemStack itemToSocket, Player p){
        this.slimefunItem = sfItem;
        this.itemStack = itemToSocket;
        this.sfItemName = sfItem.getItemName();
        this.gemID = sfItem.getId();
        this.player = p;
        this.key1 = new NamespacedKey(FNAmplifications.getInstance(), sfItem.getId());
        this.key2 = new NamespacedKey(FNAmplifications.getInstance(), itemToSocket.getType().toString().toLowerCase() + "_socket_amount");

    }

    public String getSfItemName(){
        return sfItemName;
    }

    public SlimefunItem getSlimefunItem() {
        return slimefunItem;
    }

    public ItemStack getSocketedItemStack() {
        return itemStack;
    }

    public String getGemID() {
        return gemID;
    }

    public Player getPlayer() {
        return player;
    }

    protected @Nonnull
    NamespacedKey getStorageKey1() {
        return key1;
    }

    protected @Nonnull
    NamespacedKey getStorageKey2() {
        return key2;
    }

    public void socketItem(){
        String name = getSfItemName();
        ItemStack itemStack = getSocketedItemStack();
        ItemMeta meta = itemStack.getItemMeta();
        Validate.notNull(meta, "Meta must not be null!");

        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        int amountOfGems = pdc.getOrDefault(getStorageKey2(), PersistentDataType.INTEGER, 0);

        if (amountOfGems == 0) {
            List<String> lore = new ArrayList<>();
            lore.add("");
            lore.add(Utils.colorTranslator("&6◤◤◤◤◤◤| &d&lGems &c|◥◥◥◥◥◥"));
            lore.add(ChatColor.RED + "◬ " + name);
            lore.add(Utils.colorTranslator("&6◤◤◤◤◤◤◤◤◤◤◤&c◥◥◥◥◥◥◥◥◥◥◥"));

            meta.setLore(lore);
        }else {
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

        pdc.set(getStorageKey1(), PersistentDataType.STRING, getGemID());
        pdc.set(getStorageKey2(), PersistentDataType.INTEGER, amountOfGems + 1);
        itemStack.setItemMeta(meta);

        getPlayer().sendMessage(Utils.colorTranslator("&eSuccessfully bound " + name + " &eto " +
                itemStack.getType().name().replace("_", " ").toLowerCase(Locale.ROOT)
                        .replace(itemStack.getType().name().substring(0, 0).toLowerCase(Locale.ROOT), itemStack.getType().name().substring(0, 0).toUpperCase(Locale.ROOT))));
        getPlayer().playSound(getPlayer().getLocation(), Sound.ENTITY_BLAZE_SHOOT, 1.0F, 1.0F);
    }

    public boolean isSameGem(ItemStack itemStack){
        ItemMeta meta = itemStack.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();

        if(container.isEmpty()) {
           return false;
        }

        return container.has(getStorageKey1(), PersistentDataType.STRING);
    }

}
