package io.github.taills.webb.core.DbHelper;

import org.junit.Test;

public class MainDatabaseTest {

    @Test
    public void testTag() {
        for (int i = 0; i < 10; i++) {
            String tagName = String.format("tag-%d", i);
            int r = MainDatabase.getKeyValueDatabase().put("TAG", tagName, tagName);
            assert r == 1;
        }
        assert MainDatabase.getKeyValueDatabase().list("TAG").size() >= 10;
        for (int i = 0; i < 10; i++) {
            String tagName = String.format("tag-%d", i);
            int r = MainDatabase.getKeyValueDatabase().destroy("TAG", tagName);
            assert r == 1;
        }
    }
}