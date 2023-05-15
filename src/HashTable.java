import business.Business;

public class HashTable {

    //Capacity
    public final int capacity = 10000;

    // bucketArray is used to store array of chains
    Node[] bucketArray = new Node[capacity];

    //Size
    public int count = 0;

    // Constructor
    public HashTable() {
        for (int i = 0; i < capacity; i++) {
            bucketArray[i] = null;
        }
    }

    public int size() {
        return count;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    private Object getIndex(String key)
    {
        if(key == null){
            return null;
        }

        int index = key.hashCode() & (bucketArray.length - 1);
        return index;
    }

    // Returns value for a key
    public Business get(String key) {
        Object index = getIndex(key);

        if(bucketArray[(int)index] == null){
            return null;
        }
        else if(bucketArray[(int) index] != null){
            Node temp = bucketArray[(int) index];

            while(!temp.key.equals(key) && temp.next !=null){
                temp = temp.next;
            }
            return temp.value;
        }
        return null;
    }


    // Adds a key value pair to hash
    public void put(String key, Business value) {
        int h = key.hashCode();
        int i = h & (bucketArray.length - 1);
        Node newNode = new Node(key, value, null);

        if(bucketArray[i] == null){
            bucketArray[i] = newNode;
        }
        for (Node e = bucketArray[i]; e != null; e = e.next) {
            if (key.equals(e.key)) {
                e.value = value;
                return;
            }
        }
        bucketArray[i] = new Node(key, value, bucketArray[i]);
        ++count;
        if(((double) count / (double) bucketArray.length) > 0.75){
            resize();
        }





    }

    private void resize() {
        Node[] oldTable = bucketArray;
        Node[] newTable = new Node[oldTable.length * 2];

        //for every slot, for every element of the slot
        for(int x = 0; x < bucketArray.length; x++){
            Node next;
            for(Node e = oldTable[x]; e != null; e = next){
                next = e.next;
                int h = e.key.hashCode();
                int i = h & (newTable.length - 1);
                e.next = newTable[i];
                newTable[i] = e;
            }

        }
        bucketArray = newTable;
    }


    public Node getBusTest(int index){
        return bucketArray[index];
    }



}
