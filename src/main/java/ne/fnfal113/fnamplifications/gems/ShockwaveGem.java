package ne.fnfal113.fnamplifications.gems;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import lombok.Getter;
import ne.fnfal113.fnamplifications.FNAmplifications;
import ne.fnfal113.fnamplifications.gems.abstracts.AbstractGem;
import ne.fnfal113.fnamplifications.gems.handlers.GemUpgrade;
import ne.fnfal113.fnamplifications.gems.handlers.OnDamageHandler;
import ne.fnfal113.fnamplifications.utils.Utils;
import ne.fnfal113.fnamplifications.utils.WeaponArmorEnum;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class ShockwaveGem extends AbstractGem implements OnDamageHandler, GemUpgrade {

    @Getter
    private final List<Double> cosine = new ArrayList<>();
    @Getter
    private final List<Double> sine = new ArrayList<>();

    @Getter
    private final Map<UUID, Long> playerCooldownMap = new HashMap<>();

    public ShockwaveGem(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe, 15);

        // cache angle values
        for (int x = 0; x <= 360; x++) {
            cosine.add(x, Math.cos(x));
            sine.add(x, Math.sin(x));
        }
    }

    @Override
    public void onDrag(Player player, SlimefunItem slimefunGemItem, ItemStack gemItem, ItemStack itemStackToSocket){
        if (WeaponArmorEnum.HELMET.isTagged(itemStackToSocket.getType()) || WeaponArmorEnum.CHESTPLATE.isTagged(itemStackToSocket.getType()) ||
                WeaponArmorEnum.LEGGINGS.isTagged(itemStackToSocket.getType()) || WeaponArmorEnum.BOOTS.isTagged(itemStackToSocket.getType())) {
            if(isUpgradeGem(gemItem, this.getId())) {
                upgradeGem(slimefunGemItem, itemStackToSocket, gemItem, player);
            } else {
                bindGem(slimefunGemItem, itemStackToSocket, player);
            }
        } else {
            player.sendMessage(Utils.colorTranslator("&eInvalid item to socket! Gem works on armors only"));
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

        if(!(event.getDamager() instanceof LivingEntity)) {
            return;
        }

        Player player = (Player) event.getEntity();
        LivingEntity livingEntity = (LivingEntity) event.getDamager();

        int tier = getTier(itemStack, this.getId());
        double amount = 3.0D * (tier == 4 ? 1 : Math.abs(tier - 5)); // damage multiplier per tier

        if(ThreadLocalRandom.current().nextInt(100) < getChance() / tier){
             // cooldown check
            if(Utils.cooldownHelper(getPlayerCooldownMap().getOrDefault(player.getUniqueId(), 15L)) < 5) {
                Long cd = Utils.cooldownHelper(getPlayerCooldownMap().get(player.getUniqueId()));

                player.sendMessage(Utils.colorTranslator("&dShockwave gem in cooldown for " + (5 - cd) + " seconds!"));
                
                return;
            } else {
                getPlayerCooldownMap().put(player.getUniqueId(), System.currentTimeMillis());
            }

            sendGemMessage(player, this.getItemName());
            
            player.getWorld().playSound(player.getLocation(), Sound.BLOCK_END_GATEWAY_SPAWN, 1.0F, 1.0F);
            
            livingEntity.damage(amount, player);
            livingEntity.setVelocity(new Vector(0, 0.8, 0));

            for (Entity entity: livingEntity.getNearbyEntities(8, 8, 8)) {
                if(entity instanceof LivingEntity && !entity.getUniqueId().equals(player.getUniqueId())) {
                    ((Damageable) entity).damage(amount, player);
                    
                    entity.setVelocity(new Vector(0, 0.75, 0)); // bounce off the entity from the ground
                }
            }
    
            Set<Block> blocks = new HashSet<>();
            double height = 0.1;
           
            for (int i = 0; i < 8; i++) { // radius
                for (int c = 0; c <= 360; c++) { // loop the radius angles
                    double x = i * getCosine().get(c);
                    double z = i * getSine().get(c);
                    Block block = player.getLocation().getBlock().getRelative((int) x, -1, (int) z); // get block from player ground level
    
                    // check block below if applicable to be a falling block
                    if(!blocks.contains(block) && block.getType() != Material.AIR && block.getRelative(BlockFace.UP).getType() == Material.AIR) {
                        blocks.add(block);
                        
                        spawnJumpingBlock(block, height);
                        
                        height += 0.003475;
                    }
                }
            }
        }
    }

    public void spawnJumpingBlock(Block blockOnGround, double height){
        Location loc = blockOnGround.getRelative(BlockFace.UP).getLocation();
        FallingBlock block = blockOnGround.getWorld().spawnFallingBlock(loc, blockOnGround.getBlockData());
        
        block.setDropItem(false);
        block.setVelocity(new Vector(0, height, 0));
        // this metadata gets check on entity block change event, since falling blocks are converted to placed blocks on land
        block.setMetadata("shockwave_gem", new FixedMetadataValue(FNAmplifications.getInstance(), "ghost_block"));
    }

}