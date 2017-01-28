import org.fostash.atomic.jdbctojson.DBFactoryPool;
import org.fostash.atomic.storer.Storer;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import static org.fostash.atomic.mysql.Factory.select;
import static org.fostash.atomic.mysql.Table.table;

/**
 *
 * Created by Fausto on 23/02/16.
 */
public class TestAtomic {

    @Before
    public void setup() throws SQLException {

        DBFactoryPool.setupDriver(() -> new HashMap<DBFactoryPool.SetupInformation.Keys, String>() {
            { put(DBFactoryPool.SetupInformation.Keys.DRIVER, "com.mysql.jdbc.Driver"); }
            { put(DBFactoryPool.SetupInformation.Keys.URL, "jdbc:mysql://localhost:3306/employees"); }
            { put(DBFactoryPool.SetupInformation.Keys.USER, "root"); }
            { put(DBFactoryPool.SetupInformation.Keys.PASSWORD, "root"); }
            { put(DBFactoryPool.SetupInformation.Keys.POOLNAME, "mysql_test"); }
        });
    }

    @After
    public void tearDown() throws Exception {
        DBFactoryPool.shutdownDriver();
    }

    @Test
    public void selectAll() {
        final List<JSONObject> objects =
                Storer.read(
                        select().from(table("employees"))
                );

        Assert.assertTrue(objects.size() > 100000);
    }
}
