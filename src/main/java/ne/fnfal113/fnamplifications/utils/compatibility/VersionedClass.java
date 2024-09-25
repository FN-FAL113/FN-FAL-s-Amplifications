package ne.fnfal113.fnamplifications.utils.compatibility;

import java.lang.reflect.Method;

import javax.annotation.Nullable;


/*
 * https://www.spigotmc.org/threads/inventoryview-changed-to-interface-backwards-compatibility.651754/#post-4747875
 * Classes that are converted to interface, accessing methods through reflection
 */
public class VersionedClass {

    @Nullable
    public static <T> Object invoke(T instance, String methodName) {
        try {
            Method method = instance.getClass().getMethod(methodName);

            method.setAccessible(true);

            return (Object) method.invoke(instance);
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

}
