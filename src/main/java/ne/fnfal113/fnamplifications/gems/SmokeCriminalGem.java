package ne.fnfal113.fnamplifications.gems;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import ne.fnfal113.fnamplifications.FNAmplifications;
import ne.fnfal113.fnamplifications.gems.abstracts.AbstractGem;
import ne.fnfal113.fnamplifications.gems.handlers.GemUpgrade;
import ne.fnfal113.fnamplifications.gems.handlers.OnDamageHandler;
import ne.fnfal113.fnamplifications.utils.Utils;
import ne.fnfal113.fnamplifications.utils.WeaponArmorEnum;
import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class SmokeCriminalGem extends AbstractGem implements OnDamageHandler, GemUpgrade {

    public SmokeCriminalGem(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe, 14);
    }

    @Override
    public void onDrag(Player player, SlimefunItem slimefunGemItem, ItemStack gemItem, ItemStack itemStackToSocket){
        if (WeaponArmorEnum.BOOTS.isTagged(itemStackToSocket.getType())) {
            if(isUpgradeGem(gemItem, this.getId())) {
                upgradeGem(slimefunGemItem, itemStackToSocket, gemItem, player);
            } else {
                bindGem(slimefunGemItem, itemStackToSocket, player);
            }
        } else {
            player.sendMessage(Utils.colorTranslator("&eInvalid item to socket! Gem works on boots only"));
        }
    }

    @Override
    public void onDamage(EntityDamageByEntityEvent event, ItemStack itemStack) {
        if(event.isCancelled()) {
            return;
        }

        if(!(event.getEntity() instanceof Player)) {
            return;
        }

        Player victim = (Player) event.getEntity();

        if(ThreadLocalRandom.current().nextInt(100) < getChance() / getTier(itemStack, this.getId())) {
            int victimMaxHealth = (int) Objects.requireNonNull(victim.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getValue();

            Bukkit.getScheduler().runTaskLater(FNAmplifications.getInstance(), () -> { // delay getting the actual hp of the victim
                if(victim.getHealth() <= victimMaxHealth * 0.30){
                    sendGemMessage(victim, this.getItemName());
                    victim.setNoDamageTicks(100);

                    AtomicInteger i = new AtomicInteger(0);

                    Bukkit.getScheduler().runTaskTimer(FNAmplifications.getInstance(), task -> {
                        for(double a = 0; a < 50; a += 0.15) {
                            double x = Math.cos(a) * 0.65;
                            double z = Math.sin(a) * 0.65;
                            double y = a / 25;
                            
                            victim.getWorld().spawnParticle(Particle.SMOKE_NORMAL, victim.getLocation().clone().add(x, y, z), 0);
                        }

                        if(i.get() == 10 || victim.isDead() || !victim.isValid()) {
                            task.cancel();
                        }

                        i.getAndIncrement();
                    }, 0L, 10L); // repeating task
                } // check health
            }, 3L); // delayed task
        }

    }

}