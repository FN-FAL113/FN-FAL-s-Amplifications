package ne.fnfal113.fnamplifications.gems;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.utils.tags.SlimefunTag;
import ne.fnfal113.fnamplifications.gems.abstracts.AbstractGem;
import ne.fnfal113.fnamplifications.gems.handlers.GemUpgrade;
import ne.fnfal113.fnamplifications.gems.handlers.OnBlockBreakHandler;
import ne.fnfal113.fnamplifications.utils.Utils;
import ne.fnfal113.fnamplifications.utils.WeaponArmorEnum;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.concurrent.ThreadLocalRandom;

public class HastyGem extends AbstractGem implements OnBlockBreakHandler, GemUpgrade {

    public HastyGem(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe, 16);
    }

    @Override
    public void onDrag(Player player, SlimefunItem slimefunGemItem, ItemStack gemItem, ItemStack itemStackToSocket){
        if (WeaponArmorEnum.AXES.isTagged(itemStackToSocket.getType()) || WeaponArmorEnum.SHOVELS.isTagged(itemStackToSocket.getType())
                || WeaponArmorEnum.PICKAXE.isTagged(itemStackToSocket.getType())) {
            if(isUpgradeGem(gemItem, this.getId())) {
                upgradeGem(slimefunGemItem, itemStackToSocket, gemItem, player);
            } else {
                bindGem(slimefunGemItem, itemStackToSocket, player);
            }
        } else {
            player.sendMessage(Utils.colorTranslator("&eInvalid item to socket! Gem works on shovels, pickaxes and axes only"));
        }
    }

    @Override
    public void onBlockBreak(BlockBreakEvent event, Player player, ItemStack itemStack){
        if(event.isCancelled()) {
            return;
        }
        
        Block block = event.getBlock();

        if(SlimefunTag.ORES.isTagged(block.getType()) || SlimefunTag.STONE_VARIANTS.isTagged(block.getType())) {
            if (ThreadLocalRandom.current().nextInt(100) < (getChance() / getTier(itemStack, this.getId()))) {
                PotionEffect potionEffect = new PotionEffect(PotionEffectType.FAST_DIGGING, 120, 2, true, false, false);
                
                player.addPotionEffect(potionEffect);
                
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR,
                        TextComponent.fromLegacyText(Utils.colorTranslator("&eYou're too hasty now!")));
            }
        }
    }
}