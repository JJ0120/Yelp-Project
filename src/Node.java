import business.Business;

public class Node {


        public String key;
        public Business value;
        public Node next;

        // Constructor
        public Node(String k, Business v, Node n) {
            this.key = k;
            this.value = v;
            this.next = n;
        }
    }

