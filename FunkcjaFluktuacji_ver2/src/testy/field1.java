package testy;


import java.lang.reflect.*;

public class field1 {

    public static void main(String args[]) {
        try {
            Class cls = Class.forName("ModelWykres2DTest");

            Field fieldlist[] = cls.getDeclaredFields();
            for (int i = 0; i < fieldlist.length; i++) {
                Field fld = fieldlist[i];
                System.out.println("name = " + fld.getName());
                System.out.println("decl class = " + fld.getDeclaringClass());
                System.out.println("type = " + fld.getType());
                int mod = fld.getModifiers();
                System.out.println("modifiers = " + Modifier.toString(mod));
                System.out.println("-----");
            }
        } catch (Throwable e) {
            System.err.println(e);
        }
    }
}
