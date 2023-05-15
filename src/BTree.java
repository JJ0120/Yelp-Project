import java.io.Serializable;


   class Btree implements Serializable {

        public int deg; //Sets minimum degree of node
        public BtreeNode root; //Initialize root node


        //Constructor for Btree
        public Btree(int deg) {
            root = null;
            this.deg = deg;
        }

        //Btree insertion method
        public void insert(String s) {
            //If root is empty, set root to a new node with inserted value, and increase the number of keys
            if (root == null) {
                root = new BtreeNode(deg);
                root.keys[0] = s;
                root.numKeys++;
            } else if (root.isFull()) {
                //If root is full, initialize a new node, set first child of new node to old root, and split new node's child
                BtreeNode newNode = new BtreeNode(deg);
                newNode.children[0] = root;
                newNode.splitChild(0, root); //Splits child to maintain balance

                int i = 0; //Index to compare to inserting string
                if (newNode.keys[0].compareTo(s) < 0) {
                    i++;
                }
                newNode.children[i].notFullInsert(s); //Run notFullInsert since node is no longer full

                root = newNode; //change root to new node
            } else {
                root.notFullInsert(s); //Node isn't full, so run nonFullInsert
            }
        }

        public void traverse() {
            if (this.root != null) {
                this.root.traverse();
            }
            System.out.println();
        }
    }

    //BtreeNode class
    class BtreeNode implements Serializable {
        //Initialize degree, keys in node, children of the node, and the number of keys in node
        int deg;
        String[] keys;
        BtreeNode[] children;
        int numKeys;

        //Constructor
        public BtreeNode(int deg) {
            this.deg = deg;
            this.keys = new String[2 * deg - 1];
            this.children = new BtreeNode[2 * deg];
            this.numKeys = 0;
        }

        //Method to check if the node is a leaf by checking if there are no elements in children[]
        public boolean isLeaf(BtreeNode node) {
            for (BtreeNode n : node.children) {
                if (n != null) {
                    return false;
                }
            }
            return true;
        }

        //Method to tell if the node is full by checking if there are any null spots in the node
        public boolean isFull() {
            for (String s : keys) {
                if (s == null) {
                    return false;
                }
            }
            return true;
        }

        public void splitChild(int index, BtreeNode node) {
            BtreeNode newNode = new BtreeNode(node.deg); //Initialize a new node
            newNode.numKeys = deg - 1; //Set keys to one less than degree

            //If degree is greater or equal to 1,
            if (deg - 1 >= 0) {
                System.arraycopy(node.keys, deg, newNode.keys, 0, deg - 1); //Copy last deg - 1 elements from node to newNode
            }

            //If the node is not a leaf,
            if (!isLeaf(node)) {
                //and degree is >= 0,
                if (deg >= 0) {
                    System.arraycopy(node.children, deg, newNode.children, 0, deg); //Copy last deg elements from node to newNode
                }
            }

            node.numKeys = deg - 1; //Sets number of keys to deg - 1

            if (numKeys + 1 - (index + 1) >= 0) {
                System.arraycopy(children, index + 1, children, index + 1 + 1, numKeys + 1 - (index + 1));
            }

            children[index + 1] = newNode;

            if (numKeys - index >= 0) {
                System.arraycopy(keys, index, keys, index + 1, numKeys - index);
            }

            keys[index] = node.keys[deg - 1];

            numKeys++;
        }

        //Method to insert into a non-full node
        public void notFullInsert(String s) {
            int index = numKeys - 1; //Initialize index as the number of keys - 1

            //If the node is a leaf, loop through each key until it is less than inserting string and insert string
            if (isLeaf(this)) {
                while (index >= 0 && keys[index].compareTo(s) > 0) {
                    keys[index + 1] = keys[index];
                    index--;
                }

                keys[index + 1] = s;
                numKeys++;
            } else {
                //else, find index where inserting string is greater than the key at the index
                while (index >= 0 && keys[index].compareTo(s) > 0) {
                    index--;
                }

                //If children at index is full, split it, and check which of the splits gets the new key
                if (children[index + 1].numKeys == 2 * deg - 1) {
                    splitChild(index + 1, children[index + 1]);

                    if (keys[index + 1].compareTo(s) < 0) {
                        index++;
                    }
                }
                children[index + 1].notFullInsert(s);
            }
        }

        public void traverse() {
            int i;
            for (i = 0; i < this.numKeys; i++) {
                if (!isLeaf(this)) {
                    children[i].traverse();
                }
                System.out.print(keys[i] + " ");
            }

            if (!isLeaf(this)) {
                children[i].traverse();
            }
        }
    }


