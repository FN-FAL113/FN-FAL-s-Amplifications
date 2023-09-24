package ne.fnfal113.fnamplifications.utils.pdc;

import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
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

    @Override
    public Class<byte[]> getPrimitiveType() {
        return byte[].class;
    }

    @Override
    public Class<ItemStack> getComplexType() {
        return ItemStack.class;
    }

    @Override
    public byte[] toPrimitive(@Nonnull ItemStack itemStack, @Nonnull PersistentDataAdapterContext context) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        
        try(BukkitObjectOutputStream objectOutputStream = new BukkitObjectOutputStream(new BufferedOutputStream(byteArrayOutputStream))){
            objectOutputStream.writeObject(itemStack);
        } catch (IOException | SecurityException | NullPointerException e){
            e.printStackTrace();
        }
        
        return byteArrayOutputStream.toByteArray();
    }

    @SneakyThrows
    @Override
    public ItemStack fromPrimitive(@Nonnull byte[] primitive, @Nonnull PersistentDataAdapterContext context) {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(primitive);

        try(BukkitObjectInputStream objectInputStream = new BukkitObjectInputStream(new BufferedInputStream(byteArrayInputStream))){
            return (ItemStack) objectInputStream.readObject();
        } catch (IOException | ClassCastException e){
            e.printStackTrace();
        }

        return null;
    }
}
