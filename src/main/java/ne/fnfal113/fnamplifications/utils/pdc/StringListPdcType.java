package ne.fnfal113.fnamplifications.utils.pdc;

import lombok.RequiredArgsConstructor;
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

@RequiredArgsConstructor
public class StringListPdcType<T, Z> implements PersistentDataType<byte[], List<String>> {

    private final Class<T> primitive;
    private final Class<Z> complex;

    public static final PersistentDataType<byte[], List<String>> STRING_LIST_PDC = new StringListPdcType<>(byte[].class, List.class);

    @SuppressWarnings("unchecked")
    @Nonnull
    @Override
    public Class<byte[]> getPrimitiveType() {
        return (Class<byte[]>) primitive;
    }

    @SuppressWarnings("unchecked")
    @Nonnull
    @Override
    public Class<List<String>> getComplexType() {
        return (Class<List<String>>) complex;
    }

    @Override
    @Nonnull
    public byte[] toPrimitive(@Nonnull List<String> complex, @Nonnull PersistentDataAdapterContext context) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        try(ObjectOutputStream inputStream = new ObjectOutputStream(new BufferedOutputStream(bytes))){
            inputStream.writeObject(complex);
        } catch (IOException | SecurityException | NullPointerException e){
            e.printStackTrace();
        }
        return bytes.toByteArray();
    }

    @SneakyThrows
    @Override
    @Nonnull
    public List<String> fromPrimitive(@Nonnull byte[] primitive, @Nonnull PersistentDataAdapterContext context) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(primitive);
        try(ObjectInputStream bytes = new ObjectInputStream(new BufferedInputStream(inputStream))){
            return (List<String>) bytes.readObject();
        } catch (IOException | ClassCastException e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}