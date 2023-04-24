package ne.fnfal113.fnamplifications.gems;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import ne.fnfal113.fnamplifications.FNAmplifications;
import ne.fnfal113.fnamplifications.gems.abstracts.AbstractGem;
import ne.fnfal113.fnamplifications.gems.handlers.GemUpgrade;
import ne.fnfal113.fnamplifications.gems.handlers.OnPlayerDeathHandler;
import ne.fnfal113.fnamplifications.utils.Utils;
import ne.fnfal113.fnamplifications.utils.WeaponArmorEnum;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.ThreadLocalRandom;

public class AwakenGem extends AbstractGem implements OnPlayerDeathHandler, GemUpgrade {

    public AwakenGem(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe, 15);
    }

    @Override
    public void onDrag(Player player, SlimefunItem slimefunGemItem, ItemStack gemItem, ItemStack itemStackToSocket){
        if (WeaponArmorEnum.HELMET.isTagged(itemStackToSocket.getType())) {
            if(isUpgradeGem(gemItem, this.getId())) {
                upgradeGem(slimefunGemItem, itemStackToSocket, gemItem, player);
            } else {
                bindGem(slimefunGemItem, itemStackToSocket, player);
            }
        } else {
            player.sendMessage(Utils.colorTranslator("&eInvalid item to socket! Gem works on helmet only"));
        }
    }

    @Override
    public void onPlayerDeath(PlayerDeathEvent event, ItemStack itemStack) {
        if(ThreadLocalRandom.current().nextInt(100) < getChance() / getTier(itemStack, this.getId())){
            Player player = event.getEntity();
            Location loc = player.getLocation();

            // delayed respawn task
            Bukkit.getScheduler().runTaskLater(FNAmplifications.getInstance(), () -> {
                player.spigot().respawn();
                player.teleport(loc.clone());
                player.playSound(loc, Sound.ENTITY_VINDICATOR_CELEBRATE, 1.0F, 1.0F);
                sendGemMessage(player, this.getItemName());
                for (int d = 0; d <= 90; d++) {
                    int r = ThreadLocalRandom.current().nextInt(255);
                    int g = ThreadLocalRandom.current().nextInt(255);
                    int b = ThreadLocalRandom.current().nextInt(255);
                    Location particleLoc = new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ());
                    // calculate the angle of x, z and multiply it by the size
                    particleLoc.setX(loc.getX() + Math.cos(d) * 2);
                    particleLoc.setZ(loc.getZ() + Math.sin(d) * 2);
                    player.getWorld().spawnParticle(Particle.REDSTONE, particleLoc, 2,
                            new Particle.DustOptions(Color.fromRGB(r, g, b), 2));
                }
            }, 2L);

            event.setKeepInventory(true); // keep inventory upon death
            event.getDrops().clear();
        }
    }
}