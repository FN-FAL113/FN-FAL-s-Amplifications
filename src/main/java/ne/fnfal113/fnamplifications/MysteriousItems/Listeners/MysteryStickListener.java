package ne.fnfal113.fnamplifications.MysteriousItems.Listeners;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;

import ne.fnfal113.fnamplifications.MysteriousItems.*;
import org.bukkit.ChatColor;
import org.bukkit.Effect;

import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import org.bukkit.event.player.PlayerInteractEvent;

import org.bukkit.event.player.PlayerLevelChangeEvent;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.concurrent.ThreadLocalRandom;

public class MysteryStickListener implements Listener {

    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        SlimefunItem stick = SlimefunItem.getByItem(p.getInventory().getItemInMainHand());

        if (stick == null){
            return;
        }

        if(stick instanceof MysteryStick && (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR)){
            if(p.getLevel() >= 5 && checkStick(stick)) {
                ((MysteryStick) stick).interact(e);
            } else {
                blindPlayer(p);
            }
        }

        if(stick instanceof MysteryStick2 && (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR)){
            if(p.getLevel() >= 5 && checkStick(stick)) {
                ((MysteryStick2) stick).interact(e);
            } else {
                blindPlayer(p);
            }
        }

        if(stick instanceof MysteryStick3 && (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR)){
            if(p.getLevel() >= 5 && checkStick(stick)) {
                ((MysteryStick3) stick).interact(e);
            } else {
                blindPlayer(p);
            }
        }

        if(stick instanceof MysteryStick4 && (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR)){
            if(p.getLevel() >= 5 && checkStick(stick)) {
                ((MysteryStick4) stick).interact(e);
            } else {
                blindPlayer(p);
            }
        }

        if(stick instanceof MysteryStick5 && (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR)){
            if(p.getLevel() >= 5 && checkStick(stick)) {
                ((MysteryStick5) stick).interact(e);
            } else {
                blindPlayer(p);
            }
        }

        if(stick instanceof MysteryStick6 && (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR)){
            if(p.getLevel() >= 5 && checkStick(stick)) {
                ((MysteryStick6) stick).interact(e);
            } else {
                blindPlayer(p);
            }
        }


    }

    public boolean checkStick(SlimefunItem stick){
        return !(stick.getItem().getType() == Material.DIAMOND_SWORD)
                && !(stick.getItem().getType() == Material.DIAMOND_AXE) && !(stick.getItem().getType() == Material.BOW);
    }

    public void blindPlayer(Player p){
        p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 180, 1, false, false));
        p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD  + "[FNAmpli" + ChatColor.AQUA + "" + ChatColor.BOLD + "fications] > " + ChatColor.YELLOW + "You're too weak, make sure your exp level is higher than 5");
        p.sendTitle(ChatColor.DARK_RED + " Your vision darkens!", ChatColor.RED + "The stick is unpredictable", 15, 40, 45);
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onClick(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Arrow) {
            Arrow arrow = (Arrow) e.getDamager();

            if (!(arrow.getShooter() instanceof Player)) {
                return;
            }
            Player player = ((Player) arrow.getShooter());
            SlimefunItem stickBow = SlimefunItem.getByItem(player.getInventory().getItemInMainHand());
            if(stickBow instanceof MysteryStick3) {
                if (player.getLevel() >= 5) {
                    if (ThreadLocalRandom.current().nextInt(100) < 35) {
                        player.setLevel(player.getLevel() - 3);
                    }
                    ((MysteryStick3) stickBow).onSwing(e);
                    player.getWorld().playEffect(e.getEntity().getLocation(), Effect.MOBSPAWNER_FLAMES, 1);
                } else {
                    blindPlayer(player);
                }
            } else if (stickBow instanceof MysteryStick6) {
                if (player.getLevel() >= 5) {
                    if (ThreadLocalRandom.current().nextInt(100) < 50) {
                        player.setLevel(player.getLevel() - 5);
                    }
                    ((MysteryStick6) stickBow).onSwing(e);
                    player.getWorld().playEffect(e.getEntity().getLocation(), Effect.MOBSPAWNER_FLAMES, 1);
                } else {
                    blindPlayer(player);
                }
            } else{
                return;
            }
        }

        if(!(e.getDamager() instanceof Player)) {
            return;
        }

        Player p = (Player) e.getDamager();
        SlimefunItem stick = SlimefunItem.getByItem(p.getInventory().getItemInMainHand());

        if (stick == null) {
            return;
        }

        if (stick instanceof MysteryStick) {
            ((MysteryStick) stick).onSwing(e);
        } else if (stick instanceof MysteryStick2) {
            ((MysteryStick2) stick).onSwing(e);
        } else if (stick instanceof MysteryStick4) {
            ((MysteryStick4) stick).onSwing(e);
        } else if (stick instanceof MysteryStick5) {
            ((MysteryStick5) stick).onSwing(e);
        } else {
            return;
        }

    }

    @EventHandler
    public void onExpConsume(PlayerLevelChangeEvent event){
        Player p = event.getPlayer();
        SlimefunItem stick = SlimefunItem.getByItem(p.getInventory().getItemInMainHand());

        if (stick == null){
            return;
        }

        if (stick instanceof MysteryStick) {
            ((MysteryStick) stick).LevelChange(event);
        } else if (stick instanceof MysteryStick2) {
            ((MysteryStick2) stick).LevelChange(event);
        } else if (stick instanceof MysteryStick3) {
            ((MysteryStick3) stick).LevelChange(event);
        } else if (stick instanceof MysteryStick4) {
            ((MysteryStick4) stick).LevelChange(event);
        } else if (stick instanceof MysteryStick5) {
            ((MysteryStick5) stick).LevelChange(event);
        } else if (stick instanceof MysteryStick6) {
            ((MysteryStick6) stick).LevelChange(event);
        } else {
            return;
        }

    }


}
