package ne.fnfal113.fnamplifications.utils.pdc;

import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nonnull;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class StringListPdcType implements PersistentDataType<byte[], List<String>> {

    public static final PersistentDataType<byte[], List<String>> STRING_LIST_PDC = new StringListPdcType();

    @Override
    public Class<byte[]> getPrimitiveType() {
        return byte[].class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Class<List<String>> getComplexType() {
        return (Class<List<String>>) (Class) new ArrayList<>().getClass();
    }

    @Override
    public byte[] toPrimitive(@Nonnull List<String> complex, @Nonnull PersistentDataAdapterContext context) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try(ObjectOutputStream objectOutputStream = new ObjectOutputStream(new BufferedOutputStream(byteArrayOutputStream))){
            objectOutputStream.writeObject(complex);
        } catch (IOException | SecurityException | NullPointerException e){
            e.printStackTrace();
        }

        return byteArrayOutputStream.toByteArray();
    }

    @SneakyThrows
    @Override
    public List<String> fromPrimitive(@Nonnull byte[] primitive, @Nonnull PersistentDataAdapterContext context) {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(primitive);

        try(ObjectInputStream objectInputStream = new ObjectInputStream(new BufferedInputStream(byteArrayInputStream))){
            return (List<String>) objectInputStream.readObject();
        } catch (IOException | ClassCastException e){
            e.printStackTrace();
        }

        return null;
    }
}