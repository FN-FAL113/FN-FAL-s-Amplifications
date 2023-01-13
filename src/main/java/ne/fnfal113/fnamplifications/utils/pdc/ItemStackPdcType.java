package ne.fnfal113.fnamplifications.utils.pdc;

import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import javax.annotation.Nonnull;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@NoArgsConstructor
public class ItemStackPdcType implements PersistentDataType<byte[], ItemStack> {

    public static final PersistentDataType<byte[], ItemStack> ITEM_STACK_PDC = new ItemStackPdcType();

    @Nonnull
    @Override
    public Class<byte[]> getPrimitiveType() {
        return byte[].class;
    }

    @Nonnull
    @Override
    public Class<ItemStack> getComplexType() {
        return ItemStack.class;
    }

    @Nonnull
    @Override
    public byte[] toPrimitive(@Nonnull ItemStack itemStack, @Nonnull PersistentDataAdapterContext context) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        try(BukkitObjectOutputStream inputStream = new BukkitObjectOutputStream(new BufferedOutputStream(bytes))){
            inputStream.writeObject(itemStack);
        } catch (IOException | SecurityException | NullPointerException e){
            e.printStackTrace();
        }
        return bytes.toByteArray();
    }

    @Nonnull
    @SneakyThrows
    @Override
    public ItemStack fromPrimitive(@Nonnull byte[] primitive, @Nonnull PersistentDataAdapterContext context) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(primitive);
        try(BukkitObjectInputStream bytes = new BukkitObjectInputStream(new BufferedInputStream(inputStream))){
            return (ItemStack) bytes.readObject();
        } catch (IOException | ClassCastException e){
            e.printStackTrace();
            return new ItemStack(Material.AIR);
        }
    }
}
