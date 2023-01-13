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

    @Nonnull
    @Override
    public Class<byte[]> getPrimitiveType() {
        return byte[].class;
    }

    @Nonnull
    @Override
    public Class<UUID> getComplexType() {
        return UUID.class;
    }

    @Override
    @Nonnull
    public byte[] toPrimitive(@Nonnull UUID complex, @Nonnull PersistentDataAdapterContext context) {
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
    public UUID fromPrimitive(@Nonnull byte[] primitive, @Nonnull PersistentDataAdapterContext context) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(primitive);

        try(ObjectInputStream bytes = new ObjectInputStream(new BufferedInputStream(inputStream))){
            return (UUID) bytes.readObject();
        } catch (IOException | ClassCastException e){
            e.printStackTrace();
            return new UUID(0,0);
        }
    }
}