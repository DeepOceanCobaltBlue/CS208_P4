/* Josue Florian - Wrote an array based hashmap implementation to keep
   track of a runners location and status within the game
   The hashcode is used to calculate the index in which to place the key's respective value.
   During rehashing the hashcode is again used to calculate a new index.
 */
public class GameMap<K,V> {
    /**
     * A bucket which contains a key and its respective value
     * @param <K> key
     * @param <V> value
     */
    public static class Bucket<K,V> {
        private K key;
        private V value;

        public Bucket(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return this.key.toString();
        }
    }

    private Bucket[] buckets = new Bucket[10];
    private double numKeys;

    /**
     * Constructor
     */
    public GameMap() {
        for(int i = 0; i < buckets.length; i++) {
            buckets[i] = new Bucket(null,null);
        }
    }

    /**
     * Places a bucket into the hashmap
     * @param key - assigned key to
     * @param value - associate with this value
     * @return true if the insertion was successful, false otherwise
     */
    public boolean put(K key, V value) {
        int hashCode = key.hashCode();
        //calculates index from hashcode
        int index = hashCode % buckets.length;

        double loadFactor = 0;
        int bucketsProbed = 0;

        //collision resolution via linear probing
        while(bucketsProbed < buckets.length) {
            //if the index is empty a new bucket is created at that index
            if(buckets[index].key == null) {
                buckets[index] = new Bucket<K, V>(key, value);
                numKeys++;

                //calculates the load factor
                loadFactor = numKeys / buckets.length;
                //once the load factor exceeds 0.75 rehashing occurs
                if(loadFactor > 0.75) {
                    rehash();
                }
                return true;
            }

            //if the key is found the value at that key index is replaced
            if(buckets[index] != null && buckets[index].key.equals(key)) {
                buckets[index].value = value;
                return true;
            }

            //increments the bucket index, wrapping around once it reaches the length of the array
            index = (index + 1) % buckets.length;
            bucketsProbed++;
        }
        return false;
    }

    /**
     * Doubles the size of the array when the loadFactor exceeds 0.75
     */
    public void rehash() {
        //saves the current array
        Bucket[] oldBuckets = this.buckets;
        //doubles the size of the array
        this.buckets = new Bucket[2 * oldBuckets.length + 1];
        this.numKeys = 0;

        //initializes the array
        for(int i = 0; i < buckets.length; i++) {
            buckets[i] = new Bucket(null,null);
        }

        //calculates a new index and places the key and value into the new array
        for(int i = 0; i < oldBuckets.length; i++) {
            if(oldBuckets[i].key != null) {
                put((K) oldBuckets[i].key, (V) oldBuckets[i].value);
            }
        }
    }

    /**
     * Looks for a key and returns its respective value
     * @param key - key value to find associated value
     * @return the value found a certain key, otherwise null
     */
    public V get(K key) {
        int hashCode = key.hashCode();
        int index = hashCode % buckets.length;

        //returns the value if the key is found
        if(buckets[index].key.equals(key)) {
            return (V) buckets[index].value;
        }

        //searches for the key
        while(!buckets[index].key.equals(key)) {
            if(buckets[index].key.equals(key)) {
                return (V) buckets[index].value;
            }
            index = (index + 1) % buckets.length;
        }
        return null;
    }

    /**
     * Prints out all the buckets
     */
    public void print() {
        for(int i = 0; i < buckets.length; i++) {
            System.out.println(buckets[i].toString());
        }
    }

    /**
     * Displays players currently in this room
     * @param room - which room to display
     * @return a string of players in a certain room
     */
    public String getRoom(int room) {
        int count = 0;
        String out = "";
        for(int i = 0; i < buckets.length; i++) {
            if(buckets[i].value != null) {
                if((Integer) buckets[i].value == room) {
                    count++;
                    out = out + buckets[i].toString() + " ";
                }
            }
        }
        return out;
    }

    /**
     * Displays number of players currently in this room
     * @param room - which room to display
     * @return an int of the number of players in a certain room
     */
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
