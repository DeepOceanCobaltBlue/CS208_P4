public class GameMap<K,V> {
    public static class Bucket<K,V> {
        private K key;
        private V value;

        public Bucket(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return "Runner:" + this.key + " ";
        }
    }

    private Bucket[] buckets = new Bucket[10];
    private double numKeys;

    public GameMap() {
        for(int i = 0; i < buckets.length; i++) {
            buckets[i] = new Bucket(null,null);
        }
    }

    public boolean put(K key, V value) {
        int hashCode = key.hashCode();
        int index = hashCode % buckets.length;                          //calculates index

        double loadFactor = 0;
        int bucketsProbed = 0;

        while(bucketsProbed < buckets.length) {                         //collision resolution(linear probing)
            if(buckets[index].key == null) {                            //checks if the bucket is empty
                buckets[index] = new Bucket<K, V>(key, value);
                numKeys++;

                loadFactor = numKeys / buckets.length;                  //calculates load factor
                if(loadFactor > 0.75) {
                    rehash();
                }

                return true;                                            //successful insertion
            }

            if(buckets[index] != null && buckets[index].key == key) {   //checks if key is found
                buckets[index].value = value;                           //replaces value at key
                return true;
            }

            index = (index + 1) % buckets.length;                       //increments bucket index
            bucketsProbed++;
        }
        return false;
    }

    public void rehash() {
        Bucket[] oldBuckets = this.buckets;
        this.buckets = new Bucket[2 * oldBuckets.length + 1];           //doubles the size of the array
        this.numKeys = 0;

        for(int i = 0; i < buckets.length; i++) {
            buckets[i] = new Bucket(null,null);
        }

        for(int i = 0; i < oldBuckets.length; i++) {                    //calculates new index
            if(oldBuckets[i].key != null) {                             //puts at new index
                put((K) oldBuckets[i].key, (V) oldBuckets[i].value);
            }
        }
    }

    public V get(K key) {
        int hashCode = key.hashCode();
        int index = hashCode % buckets.length;

        if(buckets[index].key == key) {
            System.out.println(buckets[index].toString());
            return (V) buckets[index].value;
        }

        while(buckets[index].key != key) {
            if(buckets[index].key == key) {
                return (V) buckets[index].value;
            }
            index = (index + 1) % buckets.length;
        }
        return null;
    }

    public void print() {
        for(int i = 0; i < buckets.length; i++) {
            System.out.println(buckets[i].toString());
        }
    }

    /**
     * Displays players currently in this room
     */
    public String getRoom(int room) {
        int count = 0;
        String out = "";
        for(int i = 0; i < buckets.length; i++) {
            if(buckets[i].value != null) {
                if((Integer) buckets[i].value == room) {
                    count++;
                    out = out + buckets[i].toString();
                }
            }
        }
        return out;
    }

    public int getRoomCount(int room) {
        int count = 0;
        for(int i = 0; i < buckets.length; i++) {
            if(buckets[i].value != null) {
                if((Integer) buckets[i].value == room) {
                    count++;
                }
            }
        }
        return count;
    }
}
