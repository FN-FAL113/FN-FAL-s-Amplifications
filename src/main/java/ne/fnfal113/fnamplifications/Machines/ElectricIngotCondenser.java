package ne.fnfal113.fnamplifications.Machines;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.libraries.dough.skins.PlayerHead;
import io.github.thebusybiscuit.slimefun4.libraries.dough.skins.PlayerSkin;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.AContainer;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import ne.fnfal113.fnamplifications.FNAmplifications;
import ne.fnfal113.fnamplifications.Items.FNAmpItems;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ElectricIngotCondenser extends AContainer implements RecipeDisplayItem {

    private static final SlimefunAddon plugin = FNAmplifications.getInstance();

    public ElectricIngotCondenser(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    public List<ItemStack> getDisplayRecipes() {
        List<ItemStack> displayRecipes = new ArrayList<>(recipes.size() * 4);

        for (MachineRecipe recipe : recipes) {
            displayRecipes.add(recipe.getInput()[0]);
            displayRecipes.add(recipe.getInput()[1]);
            displayRecipes.add(new ItemStack(Material.AIR));
            displayRecipes.add(PlayerHead.getItemStack(PlayerSkin.fromHashCode(
                    "682ad1b9cb4dd21259c0d75aa315ff389c3cef752be3949338164bac84a96e")));
            displayRecipes.add(new ItemStack(Material.AIR));
            displayRecipes.add(recipe.getOutput()[0]);
            displayRecipes.add(new ItemStack(Material.AIR));
            displayRecipes.add(new ItemStack(Material.AIR));
            displayRecipes.add(new ItemStack(Material.AIR));
            displayRecipes.add(new ItemStack(Material.AIR));
        }

        return displayRecipes;
    }

    @Override
    protected void registerDefaultRecipes() {
        registerRecipe(12, new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.BLISTERING_INGOT, 3),
                        new SlimefunItemStack(SlimefunItems.CARBONADO,3)},
                new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.BLISTERING_INGOT_3, 1)});
        registerRecipe(12, new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.GOLD_10K, 1),
                        new SlimefunItemStack(SlimefunItems.GOLD_14K,1)},
                new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.GOLD_24K, 1)});
        registerRecipe(12, new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.DAMASCUS_STEEL_INGOT, 6),
                        new SlimefunItemStack(SlimefunItems.COMPRESSED_CARBON,8)},
                new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.HARDENED_METAL_INGOT, 1)});
        registerRecipe(12, new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.STEEL_INGOT, 4),
                        new SlimefunItemStack(SlimefunItems.CARBON,10)},
                new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.DAMASCUS_STEEL_INGOT, 2)});
        registerRecipe(12, new ItemStack[]{new SlimefunItemStack(SlimefunItems.BRONZE_INGOT, 4),
                        new ItemStack(Material.COPPER_INGOT, 2)},
                new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.CORINTHIAN_BRONZE_INGOT, 1)});
        registerRecipe(12, new ItemStack[]{new ItemStack(Material.IRON_INGOT, 1),
                        new SlimefunItemStack(SlimefunItems.COPPER_INGOT, 1)},
                new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.NICKEL_INGOT, 4)});
        registerRecipe(12, new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.NICKEL_INGOT, 1),
                        new SlimefunItemStack(SlimefunItems.COPPER_INGOT, 1)},
                new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.COBALT_INGOT, 1)});
        registerRecipe(12, new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.ALUMINUM_INGOT, 1),
                        new SlimefunItemStack(SlimefunItems.COPPER_INGOT, 1)},
                new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.DURALUMIN_INGOT, 1)});
        registerRecipe(12, new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.ALUMINUM_INGOT, 1),
                        new SlimefunItemStack(SlimefunItems.BRONZE_INGOT, 1)},
                new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.ALUMINUM_BRONZE_INGOT, 1)});
        registerRecipe(12, new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.ALUMINUM_INGOT, 1),
                        new SlimefunItemStack(SlimefunItems.BRASS_INGOT, 1)},
                new SlimefunItemStack[]{new SlimefunItemStack(SlimefunItems.ALUMINUM_BRASS_INGOT, 1)});


    }

    public static void setup(){
        new ElectricIngotCondenser(FNAmpItems.MACHINES, FNAmpItems.FN_FAL_CONDENSER_1, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                FNAmpItems.MOTOR_SWITCH, FNAmpItems.THREAD_PART, FNAmpItems.FUNNEL_PART,
                FNAmpItems.GEAR_PART, FNAmpItems.BASIC_MACHINE_BLOCK, FNAmpItems.POWER_COMPONENT,
                FNAmpItems.COMPRESSOR_PART, FNAmpItems.GOLD_PLATING, FNAmpItems.COMPRESSOR_PART
        }).setCapacity(1536).setEnergyConsumption(128).setProcessingSpeed(1).register(plugin);

        new ElectricIngotCondenser(FNAmpItems.MACHINES, FNAmpItems.FN_FAL_CONDENSER_2, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                new SlimefunItemStack(FNAmpItems.MOTOR_SWITCH, 4), new SlimefunItemStack(FNAmpItems.THREAD_PART, 5), new SlimefunItemStack(FNAmpItems.FUNNEL_PART, 4),
                new SlimefunItemStack(FNAmpItems.GEAR_PART, 2), FNAmpItems.BASIC_MACHINE_BLOCK, new SlimefunItemStack(FNAmpItems.POWER_COMPONENT, 2),
                new SlimefunItemStack(FNAmpItems.COMPONENT_PART, 3), new SlimefunItemStack(FNAmpItems.DIAMOND_PLATING, 6), new SlimefunItemStack(FNAmpItems.COMPONENT_PART, 3)
        }).setCapacity(1536).setEnergyConsumption(192).setProcessingSpeed(2).register(plugin);

        new ElectricIngotCondenser(FNAmpItems.MACHINES, FNAmpItems.FN_FAL_CONDENSER_3, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                new SlimefunItemStack(FNAmpItems.FUNNEL_PART, 8), new SlimefunItemStack(FNAmpItems.THREAD_PART, 10), new SlimefunItemStack(FNAmpItems.FUNNEL_PART, 8),
                new SlimefunItemStack(FNAmpItems.GEAR_PART, 16), FNAmpItems.HIGHTECH_MACHINE_BLOCK, new SlimefunItemStack(FNAmpItems.POWER_COMPONENT, 16),
                new SlimefunItemStack(FNAmpItems.COMPONENT_PART, 10), new SlimefunItemStack(FNAmpItems.REINFORCED_CASING, 12), new SlimefunItemStack(FNAmpItems.COMPONENT_PART, 10)
        }).setCapacity(1536).setEnergyConsumption(384).setProcessingSpeed(4).register(plugin);



    }


    @Override
    public String getMachineIdentifier() {
        return "ELECTRIC_INGOT_CONDENSER";
    }

    @Override
    public ItemStack getProgressBar() {
        return new SlimefunItemStack(FNAmpItems.CONDENSER_PART, 1);
    }

}
