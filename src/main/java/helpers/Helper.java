package helpers;

import java.util.HashMap;

final public class Helper {
    private Helper() {
    }

    public static <T> Long nextId(HashMap<Long, T> items) {
        long currentMaxId = items.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }
}
