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
import java.util.UUID;

@NoArgsConstructor
public class UUIDPdcType implements PersistentDataType<byte[], UUID> {

    public static final PersistentDataType<byte[], UUID> UUID_PDC = new UUIDPdcType();

    @Override
    public Class<byte[]> getPrimitiveType() {
        return byte[].class;
    }

    @Override
    public Class<UUID> getComplexType() {
        return UUID.class;
    }

    @Override
    public byte[] toPrimitive(@Nonnull UUID complex, @Nonnull PersistentDataAdapterContext context) {
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
    public UUID fromPrimitive(@Nonnull byte[] primitive, @Nonnull PersistentDataAdapterContext context) {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(primitive);

        try(ObjectInputStream objectInputStream = new ObjectInputStream(new BufferedInputStream(byteArrayInputStream))){
            return (UUID) objectInputStream.readObject();
        } catch (IOException | ClassCastException e){
            e.printStackTrace();
        }

        return null;
    }
}