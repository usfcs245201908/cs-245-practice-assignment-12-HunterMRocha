import java.util.ArrayList;
import java.lang.Math;
public class Hashtable{

	public class HashNode{
		String key;
		String value;
		HashNode next;
		public HashNode(String key, String value){
			this.key = key;
			this.value = value;
			next = null;
		}
	}

	private ArrayList<HashNode> bucketArray;

	// Current capacity of array list
	private int numBuckets;

	// Current size of array list
	private int size;

	private float load_threshold = (float)0.5;


   //this function creates the Hashtable 
	public Hashtable(){
		bucketArray = new ArrayList<>();
		numBuckets = 2027; //initialize the amount of buckets to 2027
		size = 0;

		for(int i=0; i<numBuckets; i++)
			bucketArray.add(null);
	}

   //returns the size
	public int getSize(){
		return size;
	}

	public boolean isEmpty(){
		if(size==0)
			return true;
		return false;
	}

   //creates a unique hash code
	public int getHash(String key){
		return Math.abs(key.hashCode());
	}

	public int getIndex(String key){
		return getHash(key) % numBuckets;
	}

   //goes through the structure and checks if the key is somewhere in it
	public boolean containsKey(String key){
		HashNode head = bucketArray.get(getIndex(key));

		while(head!=null){
			if(head.key.equals(key))
				return true;
			else
				head = head.next;
		}
		return false;
	}

	public String get(String key){
		HashNode head = bucketArray.get(getIndex(key));

		while(head!=null){
			if(head.key.equals(key))
				return head.value;
			else
				head = head.next;
		}

		return null;
	}



	public void put(String key, String value){
		int index = getIndex(key);
		HashNode head = bucketArray.get(index);

		//This changes the value of the HashNode if the key exists
		while(head != null){
			if(head.key.equals(key)){
				head.value = value;
				return;
			}
			head = head.next;
		}

		head = bucketArray.get(index);
		HashNode newNode = new HashNode(key, value);

		//Checks if there are no nodes in the index
		if(head == null){
			bucketArray.set(index, newNode);
		}

		else{
			newNode.next = head;
			bucketArray.set(index, newNode);
		}

		size++;

		if((1.0*size)/numBuckets >= load_threshold){
			ArrayList<HashNode> temp = bucketArray;
			bucketArray = new ArrayList<>();
			numBuckets *= 2;
			size=0;

			for(int i=0; i<numBuckets; i++)
				bucketArray.add(null);

			for(int i=0; i<temp.size(); i++){
				HashNode oldNode = temp.get(i);
				while(oldNode != null){
					put(oldNode.key, oldNode.value);
					oldNode = oldNode.next;
				}
			}
		}

	}

	public String remove(String key){
		int index = getIndex(key);
		HashNode head = bucketArray.get(index);

		HashNode prev=null;
		while(head!=null){
			if(head.key.equals(key))
				break;

			prev = head;
			head = head.next;
		}

		if(head==null)
			return null;

		size--;

		if(prev!=null)
			prev.next = head.next;
		else
			bucketArray.set(index, head.next);
		return head.value;
	}
	
}