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
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class ItemStackListPdcType implements PersistentDataType<byte[], List<ItemStack>> {

    public static final PersistentDataType<byte[], List<ItemStack>> ITEM_STACK_LIST_PDC = new ItemStackListPdcType();

    @Override
    public Class<byte[]> getPrimitiveType() {
        return byte[].class;
    }

    @Override
    public Class<List<ItemStack>> getComplexType() {
        return (Class<List<ItemStack>>) (Class) new ArrayList<>().getClass();
    }

    @Override
    public byte[] toPrimitive(@Nonnull List<ItemStack> complex, @Nonnull PersistentDataAdapterContext context) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        
        try(BukkitObjectOutputStream objectOutputStream = new BukkitObjectOutputStream(new BufferedOutputStream(byteArrayOutputStream))){
            objectOutputStream.writeObject(complex);
        } catch (IOException | SecurityException | NullPointerException e){
            e.printStackTrace();
        }

        return byteArrayOutputStream.toByteArray();
    }

    @SneakyThrows
    @Override
    public List<ItemStack> fromPrimitive(@Nonnull byte[] primitive, @Nonnull PersistentDataAdapterContext context) {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(primitive);

        try(BukkitObjectInputStream objectInputStream = new BukkitObjectInputStream(new BufferedInputStream(byteArrayInputStream))){
            return (List<ItemStack>) objectInputStream.readObject();
        } catch (IOException | ClassCastException e){
            e.printStackTrace();
        }

        return null;
    }
}
