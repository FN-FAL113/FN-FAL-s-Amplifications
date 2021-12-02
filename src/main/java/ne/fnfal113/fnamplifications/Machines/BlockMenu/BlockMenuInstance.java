package ne.fnfal113.fnamplifications.Machines.BlockMenu;

import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;

import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;

import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;

import ne.fnfal113.fnamplifications.Machines.ElectricBlockBreaker;

import org.bukkit.block.Block;

import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

public class BlockMenuInstance extends BlockMenuPreset {

    private final ElectricBlockBreaker blockBreaker;

    public BlockMenuInstance(@Nonnull String id, @Nonnull String title, @Nonnull ElectricBlockBreaker blockBreaker) {
            super(id, title);
            this.blockBreaker = blockBreaker;
        }

        public void init() {
            for (int i = 0; i < 9; i++) {
                this.addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
            }
            this.addItem(4, ElectricBlockBreaker.noPower);
        }

        public int[] getSlotsAccessedByItemTransport(ItemTransportFlow flow) {
            return new int[0];
        }

        public boolean canOpen(@Nonnull Block block, @Nonnull Player player) {
            return true;
        }

        public void newInstance(@Nonnull BlockMenu menu, @Nonnull Block block) {
            this.blockBreaker.onNewInstance(menu, block);
            this.blockBreaker.onNewInstanceToggle(menu, block);
        }
}

