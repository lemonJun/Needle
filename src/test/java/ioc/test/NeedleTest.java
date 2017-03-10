package ioc.test;

import org.junit.Test;

import lemon.needle.Needle;

public class NeedleTest {

    static {
        Needle.init();
    }

    @Test
    public void instance() {
        A a = Needle.getInstance(A.class);
    }

    @Test
    public void same() {
        C c1 = Needle.getInstance(C.class);
        C c2 = Needle.getInstance(C.class);
        System.out.println(c1 == c2);
    }

    @Test
    public void provid() {
        try {
            D c2 = Needle.getInstance(D.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
