import java.util.HashMap;
import java.util.Map;

public class PiDatabase {
    private final Map<Integer, Double> cache = new HashMap<>();

    public synchronized Double get(int n) {
        return cache.get(n);
    }

    public synchronized void put(int n, Double pi) {
        cache.put(n, pi);
    }

    public synchronized boolean contains(int n) {
        return cache.containsKey(n);
    }
}
