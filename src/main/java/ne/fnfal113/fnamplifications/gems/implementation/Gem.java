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

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@SuppressWarnings("ConstantConditions")
public class Gem {

    @Getter
    private final SlimefunItem slimefunGemItem;
    @Getter
    private final String SlimefunGemItemName;
    @Getter
    private final ItemStack itemStackToSocket;
    @Getter
    private final String SlimefunGemItemID;
    @Getter
    private final Player player;
    @Getter
    private final NamespacedKey SlimefunGemItemIDKey;
    @Getter
    private final NamespacedKey socketAmountKey;

    @ParametersAreNonnullByDefault
    public Gem(SlimefunItem slimefunGemItem, ItemStack itemStackToSocket, Player player){
        this.slimefunGemItem = slimefunGemItem;
        this.itemStackToSocket = itemStackToSocket;
        this.player = player;
        this.SlimefunGemItemName = slimefunGemItem.getItemName();
        this.SlimefunGemItemID = slimefunGemItem.getId();
        this.SlimefunGemItemIDKey = Keys.createKey(slimefunGemItem.getId().toLowerCase());
        this.socketAmountKey = Keys.createKey(itemStackToSocket.getType().toString().toLowerCase() + "_socket_amount");
    }

    public void startSocket(){
        ItemMeta meta = getItemStackToSocket().getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        int itemGemAmount = checkGemAmount(pdc, getItemStackToSocket());

        if(itemGemAmount < 5) { // gem amount must be below 5
            if(!isSameGem(getItemStackToSocket())){ // check if the gem being added already exist
                getPlayer().setItemOnCursor(new ItemStack(Material.AIR));
                socketGemToItemStack(meta, pdc, itemGemAmount);
            } else {
                getPlayer().sendMessage(Utils.colorTranslator("&6Your item has " + getSlimefunGemItemName() + " &6socketed already!"));
                getPlayer().playSound(getPlayer().getLocation(), Sound.ENTITY_BAT_TAKEOFF, 1.0F, 1.0F);
            }

            return;
        } 

        getPlayer().sendMessage(Utils.colorTranslator("&eOnly 5 gems per item are allowed!"));
        getPlayer().playSound(getPlayer().getLocation(), Sound.ENTITY_BLAZE_HURT, 1.0F, 1.0F);
    }

    public void socketGemToItemStack(ItemMeta meta, PersistentDataContainer pdc, int itemGemAmount){
        String gemSlimefunItemname = getSlimefunGemItemName();
        List<String> lore = meta.hasLore() ? lore = meta.getLore() : new ArrayList<>();
        
        if (itemGemAmount == 0) { // add the lore when adding a gem for the first time
            lore.add("");
            lore.add(Utils.colorTranslator("&6◤◤◤◤◤◤| &d&lGems &c|◥◥◥◥◥◥"));
            lore.add(ChatColor.RED + "◬ " + gemSlimefunItemname);
            lore.add(Utils.colorTranslator("&6◤◤◤◤◤◤◤◤◤◤◤&c◥◥◥◥◥◥◥◥◥◥◥"));

            meta.setLore(lore);
        } else { // append the new added gem to existing lore
            for (int i = 0; i < lore.size(); i++) {
                if(lore.get(i).startsWith(Utils.colorTranslator("&6◤◤◤◤◤◤| &d&lGems &c|◥◥◥◥◥◥"))){
                    lore.add(i + 1, ChatColor.RED + "◬ " + gemSlimefunItemname);
                }
            }

            meta.setLore(lore);
        }

        pdc.set(getSlimefunGemItemIDKey(), PersistentDataType.STRING, getSlimefunGemItemID());
        pdc.set(getSocketAmountKey(), PersistentDataType.INTEGER, itemGemAmount + 1);
        
        getItemStackToSocket().setItemMeta(meta);

        getPlayer().sendMessage(Utils.colorTranslator("&eSuccessfully bound " + gemSlimefunItemname + " &eto " +
            getItemStackToSocket().getType().name().replace("_", " ").toLowerCase(Locale.ROOT)));
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
        return pdc.getOrDefault(Keys.createKey(itemStack.getType().toString().toLowerCase() + "_socket_amount"),
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

        return container.has(getSlimefunGemItemIDKey(), PersistentDataType.STRING);
    }

}