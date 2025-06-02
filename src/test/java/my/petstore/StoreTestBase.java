package my.petstore;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public abstract class StoreTestBase {
    protected StoreClient client = new StoreClient();

    static ZoneId zone = ZoneOffset.UTC;
    protected static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ").withZone(zone);
    protected final static String date = OffsetDateTime.now().format(formatter);

    protected static final Random random = new Random();

    protected static int randomPositiveInt() {
        return random.nextInt(100) + 1;
    }
    protected static int randomSmallInt() {
        return random.nextInt(10) + 1; // 1 to 10
    }
    protected static boolean randomBoolean(){
        return new Random().nextBoolean();
    }
}
