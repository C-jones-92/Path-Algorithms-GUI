//TODO:
//  (1) Implement the hash table!
//  (2) JavaDocs

import java.util.Map;
import java.util.Set;

import java.util.Collection; //for returning in the values() function only

//If you uncomment the import to ArrayList below, the grader will manually
//look to make sure you are not using it anywhere else... if you use it
//somewhere else you will get 0pts on the entire project (not a joke).

//uncomment the line below ONLY if you are implementing values().
//import java.util.ArrayList; //for returning in the values() function only
/**
 * Map to be used in graph.
 * 
 * @author Corey
 *
 * @param <K> Key
 * @param <V> Value
 */
class ThreeTenMap<K,V> implements Map<K,V> {
	//********************************************************************************
	//   DO NOT EDIT ANYTHING IN THIS SECTION (except to add the JavaDocs)
	//********************************************************************************

	//you must use this storage for the hash table
	//and you may not alter this variable's name, type, etc.
	/**
	 * Main storage for map.
	 */
	private Pair<K,V>[] storage;

	//you must use to track the current number of elements
	//and you may not alter this variable's name, type, etc.
	/**
	 * Number of elements.
	 */
	private int numElements = 0;

	//provided class, do not edit!
	/**
	 * Little class to set up project.
	 * @author Not Me
	 *
	 * @param <K> Key
	 * @param <V> Value
	 */
	public class Pair<K,V> {
		/**
		 * Key.
		 */
		K key;
		/**
		 * Value.
		 */
		V value;
		/**
		 * Constructor.
		 * @param key key
		 * @param value value
		 */
		Pair(K key, V value) { this.key = key; this.value = value; }
		/**
		 * To string.
		 * @return string
		 */
		public String toString() { return "<" + key + "," + value + ">"; }
	}

	/**
	 * Max load of map.
	 */
	private double maxLoad;
	/**
	 * Size.
	 */
	private int size;
	/**
	 * Power.
	 */
	private int power;

	//Remember... if you want an array of ClassWithGeneric<V>, the following format ___SHOULD NOT___ be used:
	//		 ClassWithGeneric<V>[] items = (ClassWithGeneric<V>[]) new Object[10];
	//instead, use this format:
	//		 ClassWithGeneric<V>[] items = (ClassWithGeneric<V>[]) new ClassWithGeneric[10];

	//If the hash of something is Integer.MIN_VALUE, you need to manually change it
	//to Integer.MAX_VALUE. Math.abs() won't work on Integer.MIN_VALUE!

	//This table does not accept null keys. Why is that important? Think happy little
	//bunnies... (or think tombstones if you don't know about the much cooler bunny thing).

	/**
	 * Creates map of certain size.
	 * @param size wanted size for map.
	 */
	@SuppressWarnings("unchecked")
	public ThreeTenMap(int size) {
		//Create a hash table where the size of the storage is
		//the smallest power of two larger than (or equal to)
		//the requested size. storage size = # slots in the hash table
		//You may assume the requested size is >= 1

		size = (int) Math.ceil(Math.log(size)/Math.log(2));
		size = (int) Math.pow(2, size);
		this.size = size;
		this.storage = (Pair<K,V>[]) new Pair[size];

		this.maxLoad = 0.5;
		//This will use the default maxLoad of 0.5.
	}

	/**
	 * Creates map with size and max load.
	 * @param size size
	 * @param maxLoad maximum Load OVER 9000!!!
	 */
	@SuppressWarnings("unchecked")
	public ThreeTenMap(int size, double maxLoad) {
		//Create a hash table where the size of the storage is
		//the smallest power of two larger than (or equal to)
		//the requested size. storage size = # slots in the hash table
		//You may assume the requested size is >= 1

		//The max load should also be set by this function. You may
		//assume that this will be > 0 and <= 1

		size = (int) Math.ceil(Math.log(size)/Math.log(2));
		size = (int) Math.pow(2, size);
		this.size = size;
		this.storage = (Pair<K,V>[]) new Pair[size];
		this.maxLoad = maxLoad;
	}

	/**
	 * Clears storage.
	 */
	@SuppressWarnings("unchecked")
	public void clear() {
		this.storage = (Pair<K,V>[]) new Pair[this.size];
	}

	/**
	 * Checks if empty.
	 * @return return true if empty
	 */
	public boolean isEmpty() {

		if(this.numElements == 0) 
			return true;
		else
			return false;
	}

	/**
	 * Returns capacity.
	 * @return capacity
	 */
	public int capacity() {

		return this.size;

	}

	/**
	 * Returns size.
	 * @return size
	 */
	public int size() {

		return this.numElements;
	}

	/**
	 * Checks if slow contains specified value.
	 * @param index of slot you wish to check
	 * @return returns true if true.
	 */
	private boolean slotContainsValue(int index) {
		//Private helper method for toString()
		//O(1)

		//Returns true if a "real" value is at the index given
		//Tombstones (and bunnies) are not "real" values in the map.

		if(this.storage[index] != null)
			return true;
		else
			return false;
	}

	/**
	 * Getter from map.
	 * @param key object you want to get.
	 * @return return value.
	 */
	public V get(Object key) { 
		//Worst case: O(n), Average case: O(1)

		for(int i = 0; i < this.size; i++) {
			if(this.storage[i] != null && this.storage[i].key == key){
				return this.storage[i].value;
			}
		}

		return null;
	}

	/**
	 * Returns keySet of map.
	 * @return set of keys.
	 */
	public Set<K> keySet() {
		//a ThreeTenSet is a Set, so return one of those
		//max of O(m) where m = number of slots in the table

		ThreeTenSet<K> set = new ThreeTenSet<K>();

		for(int i = 0; i < this.size; i++) {
			if(this.storage[i] != null)
				set.add(this.storage[i].key);
		}


		return set;
	}

	/**
	 * Removes key.
	 * @param key key
	 * @return value of removed key
	 */
	public V remove(Object key) {
		//Worst case: O(n), Average case: O(1)
		for(int i = 0; i < this.size; i++) {
			if(this.storage[i] != null && this.storage[i].key == key){
				V temp = this.storage[i].value;
				this.storage[i] = null;
				this.numElements--;
				return  temp;

			}
		}

		return null;
	}

	/**
	 * Puts value and location k.
	 * @param key key
	 * @param value value
	 * @return return value of old value.
	 */
	public V put(K key, V value) {
		//Place value v at the location of key k.
		//This table does not accept null keys and you
		//should throw a NullPointerException if k
		//is null (it _is_ ok for the value to be null!).

		if(key == null)
			throw new NullPointerException();

		//If the key already exists in the table
		//replace the current value with v.

		for(int i = 0; i < this.size; i ++) {
			if(this.storage[i] != null && this.storage[i].key == key) {
				V temp = this.storage[i].value;
				this.storage[i].value = value;
				return temp;
			}
		}

		//If _after_ adding, the load on the table is 
		//greater than the max load, expand the table 
		//to twice the table size and rehash. Note ">" != ">=".
		if(this.numElements == this.size) {
			rehash(this.size*2);
		}
		int count = 0;
		boolean okay = true;
		int index = (Math.abs(key.hashCode())%this.size);
		while(okay){
			if(this.storage[index] == null) {
				this.storage[index] = new Pair<K,V>(key, value);
				this.numElements++;
				okay = false;
			}
			else
			{
				count++;
				index += (int)((.5 *count)+.5*Math.pow(count, 2));
				if(index >= this.size)
					index %= this.size;
			}
		}

		if((double) this.numElements/this.size > this.maxLoad) {
			rehash(this.size*2);
		}

		return null;
		//If there is _absolutely no space_ in the table
		//_before_ adding (this can happen with max loads
		//close to 1), then rehash to twice the table size
		//before adding.

		//Worst case: O(n), Average case: O(1) if no rehashing
		//O(m) if rehashing is needed.
	}

	/**
	 * Changes size.
	 * @param size size you want to change it to
	 * @return returns true if rehashed
	 */
	@SuppressWarnings("unchecked")
	public boolean rehash(int size) {
		//Increase or decrease the size of the storage,
		//to the smallest power of two larger than (or equal
		//to) the requested size (and rehashing all values).
		//storage size = # slots in the hash table
		if(size < this.numElements) {
			return false;
		}
		size = (int) Math.ceil(Math.log(size)/Math.log(2));
		size = (int) Math.pow(2, size);
		if(this.numElements/size > this.maxLoad) {
			return false;
		}
		Pair<K,V>[] temp = (Pair<K,V>[]) new Pair[size];

		for(int i = 0; i < this.size; i++) {

			if(this.storage[i] != null)	{		
				int count = 0;
				boolean okay = true;
				int index = Math.abs(this.storage[i].key.hashCode())%temp.length;
				while(okay){
					if(temp[index] == null) {
						temp[index] = new Pair<K,V>(this.storage[i].key, this.storage[i].value);
						okay = false;
					}
					else
					{
						count++;
						index += (int)((.5 *count)+.5*Math.pow(count, 2));
						if(index >= this.size)
							count %= this.size;
					}
				}
			}
		}
		if(this.numElements/size > this.maxLoad) {
			rehash(size*2);
		}
		this.storage = temp;
		this.size = size;
		return true;

		//Note: you should start at the beginning of the
		//old table and go through each index in order
		//to move items into the new table. 

		//If you go backwards, etc. your elements will end up 
		//out of order compared to the expected order.

		//If the new size would result in a load greater
		//than the max load, return false. Note ">" != ">=".
		//Return true if you were able to rehash.
	}

	//********************************************************************************
	//   YOU MAY, BUT DON'T NEED TO EDIT THINGS IN THIS SECTION
	// These are some methods we didn't write for you, but you could write,
	// if you need/want them for building your graph. We will not test
	// (or grade) these methods.
	//********************************************************************************

	/**
	 * {@inheritDoc}
	 */
	public Collection<V> values() {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void	putAll(Map<? extends K,? extends V> m) {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * {@inheritDoc}
	 */
	public boolean containsValue(Object value) {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * {@inheritDoc}
	 */
	public Set<Map.Entry<K,V>> entrySet() {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * {@inheritDoc}
	 */
	public boolean equals(Object o) {
		throw new UnsupportedOperationException();
	}
	/**
	 * {@inheritDoc}
	 */
	public int hashCode() {
		throw new UnsupportedOperationException();
	}
	/**
	 * {@inheritDoc}
	 */
	public boolean containsKey(Object key) {
		throw new UnsupportedOperationException();
	}

	//********************************************************************************
	//   DO NOT EDIT ANYTHING BELOW THIS LINE (except to add the JavaDocs)
	// We will check these things to make sure they still work, so don't break them.
	//********************************************************************************

	/**
	 * To string method.
	 * @return returns string.
	 */
	public String toString() {
		//THIS METHOD IS PROVIDED, DO NOT CHANGE IT
		return toString(false);
	}

	/**
	 * To string method.
	 * @param showEmpty if empty.
	 * @return returns if empty.
	 */
	public String toString(boolean showEmpty) {
		//THIS METHOD IS PROVIDED, DO NOT CHANGE IT
		StringBuilder s = new StringBuilder();
		for(int i = 0; i < storage.length; i++) {
			if(showEmpty || slotContainsValue(i))  {
				s.append("[");
				s.append(i);
				s.append("]: ");
				s.append(storage[i]);
				s.append("\n");
			}
		}
		s.deleteCharAt(s.length()-1);
		return s.toString();
	}

	/**
	 * To array.
	 * @return returns array.
	 */
	@SuppressWarnings("unchecked")
	public Object[] toArray() {
		//THIS METHOD IS PROVIDED, DO NOT CHANGE IT
		Pair<K,V>[] ret = (Pair<K,V>[]) new Pair[numElements];
		for(int i = 0, j = 0; i < storage.length; i++) {
			if(slotContainsValue(i)) {
				ret[j++] = new Pair<>(storage[i].key, storage[i].value);
			}
		}
		return (Object[]) ret;
	}
}