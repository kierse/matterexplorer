package gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pissiphany.matterexplorer.gson.AutoParcelTypeAdapterFactory;
import com.pissiphany.matterexplorer.model.Matter;

import junit.framework.TestCase;

/**
 * Created by kierse on 15-09-08.
 */
public class AutoParcelTypeAdapterFactoryTest extends TestCase {
    private Gson gson;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        gson = new GsonBuilder()
                .registerTypeAdapterFactory(new AutoParcelTypeAdapterFactory())
                .create();
    }

    public void testParsing() throws Exception {
        final Matter matter = Matter.builder()
                .setId(1L)
                .setDisplayNumber("Foo")
                .build();
        final String json = gson.toJson(matter);
        final Matter matterFromJson = gson.fromJson(json, Matter.class);

        assertEquals(matter, matterFromJson);
    }
}
