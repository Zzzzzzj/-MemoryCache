import com.pittt.app.MemoryCache;
import org.junit.Before;
import org.junit.Test;

public class MemeryCacheTest {

    @Before
    public void beforeTest() {
        MemoryCache.INSTANCE.set("test", "aaaaa");
        MemoryCache.INSTANCE.set("test2", "aaaaa2");
        MemoryCache.INSTANCE.set("test3", "aaaaa3");
        MemoryCache.INSTANCE.set("test4", "aaaaa4", 1000L);
    }

    @Test
    public void funTest() throws InterruptedException {
        System.out.println("qian:" + MemoryCache.INSTANCE.get("test4"));
        Thread.sleep(1001);
        System.out.println("hou:" + MemoryCache.INSTANCE.get("test4"));
        MemoryCache.INSTANCE.set("test4", "ccc");
        System.out.println(MemoryCache.INSTANCE.get("test4"));
        MemoryCache.INSTANCE.set("test4", "ccc", 100000L);
        System.out.println(MemoryCache.INSTANCE.get("test4"));
        Thread.sleep(500);
//        MemoryCache.INSTANCE.add("test4","cccddd");
//        Thread.sleep(1000);
        System.out.println(MemoryCache.INSTANCE.get("test4"));
        System.out.println(MemoryCache.INSTANCE.getCachedCount());
        System.out.println(MemoryCache.INSTANCE.getTimeOut("test4"));
    }

}
