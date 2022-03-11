package ne.fnfal113.fnamplifications.staffs;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import ne.fnfal113.fnamplifications.FNAmplifications;
import ne.fnfal113.fnamplifications.staffs.abstracts.AbstractStaff;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import java.util.*;

public class StaffOfAwareness extends AbstractStaff {

    private final NamespacedKey defaultUsageKey;

    private final MainStaff mainStaff;

    public StaffOfAwareness(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);

        this.defaultUsageKey = new NamespacedKey(FNAmplifications.getInstance(), "awarestaff");
        this.mainStaff = new MainStaff(lore(), 10, getStorageKey(), this.getItem(), this.getId());
    }

    protected @Nonnull
    NamespacedKey getStorageKey() {
        return defaultUsageKey;
    }

    @Override
    public List<String> lore(){
        List<String> lore = new ArrayList<>();
        lore.add(0, "");
        lore.add(1, ChatColor.LIGHT_PURPLE + "Right click to receive information");
        lore.add(2, ChatColor.LIGHT_PURPLE + "regarding the nearest players around");
        lore.add(3, ChatColor.LIGHT_PURPLE + "50 block radius");

        return lore;
    }

    @Override
    public void onRightClick(PlayerInteractEvent event){
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        Map<Entity, String> PLAYERS = new HashMap<>();
        List<String> players = new ArrayList<>();
        List<String> firstPage = new ArrayList<>();
        int amount = 0;

        ItemMeta meta = item.getItemMeta();

        ItemStack writtenBook = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta bookMeta = (BookMeta) writtenBook.getItemMeta();
        if(bookMeta == null){
            return;
        }
        bookMeta.setTitle("Players around 50 block radius");
        bookMeta.setAuthor("FN_FAL113");

        for (Entity entity: player.getWorld().getNearbyEntities(player.getLocation().clone(), 50, 50, 50)) {
            if(entity instanceof Player && !entity.getName().equals(player.getName())){
                PLAYERS.put(entity, entity.getName());
                amount = amount + 1;
            }
        }

        if(amount != 0) {
            PLAYERS.forEach((key1, value1) -> players.add(ChatColor.DARK_GREEN + value1));

            firstPage.add(ChatColor.GOLD + "  Staff of Awareness\n\n " + ChatColor.GRAY +
                    "The power of staff yields the needed information around your 50 block radius vicinity in which upon players are nearby in your own knowing only");
            bookMeta.addPage(firstPageBook(firstPage));
            for (int i = 0; i < players.size(); i = i + 5) {
                    bookMeta.addPage(players.subList(i, Math.min(i + 5, players.size())).toString()
                            .replace("[", "")
                            .replace("]", "")
                            .replace(":", ChatColor.GRAY + " =")
                            .replace(", ", "\n\n")
                            .replace("_", " "));

            }
        } else {
            firstPage.add(ChatColor.GOLD + "  Staff of Awareness\n\n " + ChatColor.GRAY +
                    "No players around your vicinity");
            bookMeta.addPage(firstPageBook(firstPage));
        }

        mainStaff.updateMeta(item, meta, player);

        writtenBook.setItemMeta(bookMeta);
        player.openBook(writtenBook);

        Objects.requireNonNull(player.getLocation().getWorld()).playSound(player.getLocation(), Sound.ENTITY_ILLUSIONER_CAST_SPELL, 1, 1);

    }

    public String firstPageBook(List<String> firstPage){
        return firstPage.toString().replace("[", "").replace("]", "");
    }
}