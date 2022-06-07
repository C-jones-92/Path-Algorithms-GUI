//TODO: JavaDocs

//Don't forget, use inheritDoc to save yourself
//a lot of time for inherited methods from Set!!!

import java.util.Collection;
import java.util.Set;
import java.util.Iterator;
/**
 * Three Ten set class.
 * @author Corey
 *
 * @param <E> generic value.
 */
class ThreeTenSet<E> implements Set<E> {
	//********************************************************************************
	//   YOU MAY, BUT DON'T NEED TO EDIT THINGS IN THIS SECTION
	// These are some methods we didn't write for you, but you could write.
	// if you need/want them for building your graph. We will not test
	// (or grade) these methods.
	//********************************************************************************
	
	/**
	 * {@inheritDoc}
	 */
	public boolean removeAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * {@inheritDoc}
	 */
	public boolean retainAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * {@inheritDoc}
	 */
	public <T> T[] toArray(T[] a) {
		throw new UnsupportedOperationException();
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean containsAll(Collection<?> c) {
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
	
	
	//********************************************************************************
	//   DO NOT EDIT ANYTHING BELOW THIS LINE (except to add the JavaDocs)
	// We will grade these methods to make sure they still work, so don't break them.
	//********************************************************************************
	
	/**
	 * Storage in map.
	 */
	private ThreeTenMap<E,E> storage = new ThreeTenMap<>(7);
	
	/**
	 * adds value to map.
	 * @param e is value
	 * @return true if true
	 */
	public boolean add(E e) {
		//THIS METHOD IS PROVIDED, DO NOT CHANGE IT
		if(e == null) throw new NullPointerException();
		return storage.put(e, e) == null;
	}
	
	/**
	 * Add all to map.
	 * @param c collection of values to add
	 * @return return true if true
	 */
	public boolean addAll(Collection<? extends E> c) {
		//THIS METHOD IS PROVIDED, DO NOT CHANGE IT
		boolean changedSomething = false;
		
		for(E e : c) {
			if(e != null) {
				changedSomething = add(e) || changedSomething;
			}
		}
		
		return changedSomething;
	}
	
	/**
	 * clears map.
	 */
	public void clear() {
		//THIS METHOD IS PROVIDED, DO NOT CHANGE IT
		storage = new ThreeTenMap<>(7);
	}
	
	/**
	 * Checks if contains object.
	 * @param o object
	 * @return true if true
	 */
	public boolean contains(Object o) {
		//THIS METHOD IS PROVIDED, DO NOT CHANGE IT
		return storage.get(o) != null;
	}
	
	/**
	 * Check if empty.
	 * @return true if true.
	 */
	public boolean isEmpty() {
		//THIS METHOD IS PROVIDED, DO NOT CHANGE IT
		return size() == 0;
	}
	
	/**
	 * Remove item.
	 * @param o object
	 * @return returns item.
	 */
	public boolean remove(Object o) {
		//THIS METHOD IS PROVIDED, DO NOT CHANGE IT
		return storage.remove(o) != null;
	}
	
	/**
	 * Size of map.
	 * @return size
	 */
	public int size() {
		//THIS METHOD IS PROVIDED, DO NOT CHANGE IT
		return storage.size();
	}
	
	/**
	 * To array.
	 * @return array
	 */
	@SuppressWarnings("unchecked")
	public Object[] toArray() {
		//THIS METHOD IS PROVIDED, DO NOT CHANGE IT
		ThreeTenMap<E,E>.Pair<E,E>[] arr = (ThreeTenMap<E,E>.Pair<E,E>[]) storage.toArray();
		
		Object[] ret = new Object[arr.length];
		for(int i = 0; i < arr.length; i++) {
			ret[i] = arr[i].key;
		}
		
		return ret;
	}
	
	/**
	 * To string method.
	 * @return returns string
	 */
	public String toString(){
		//THIS METHOD IS PROVIDED, DO NOT CHANGE IT
		return storage.toString();
	}
	
	/**
	 * To string method.
	 * @param showEmpty is show empty
	 * @return return true if empty
	 */
	public String toString(boolean showEmpty) {
		//THIS METHOD IS PROVIDED, DO NOT CHANGE IT
		return storage.toString(showEmpty);
	}
	
	/**
	 * Iterator for set.
	 * @return iterator
	 */
	public Iterator<E> iterator() {
		//THIS METHOD IS PROVIDED, DO NOT CHANGE IT
		return new Iterator<E>() {
			/**
			 * Object array.
			 */
			private Object[] values = toArray();
			/**
			 * Location.
			 */
			private int currentLoc = 0;
			
			/**
			 * Grabs next item.
			 * @returns next value.
			 */
			@SuppressWarnings("unchecked")
			public E next() {
				return (E) values[currentLoc++];
			}
			
			/**
			 * Checks for next.
			 * @returns true if has next.
			 */
			public boolean hasNext() {
				return currentLoc != values.length;
			}
		};
	}
}