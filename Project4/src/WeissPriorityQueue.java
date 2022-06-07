//TODO:
//  (1) Update this code to meet the style and JavaDoc requirements.
//			Why? So that you get experience with the code for a heap!
//			Also, this happens a lot in industry (updating old code
//			to meet your new standards). We've done this for you in
//			WeissCollection and WeissAbstractCollection.
//  (2) Implement update() method -- see project description

import java.util.Iterator;
import java.util.Comparator;
import java.util.NoSuchElementException;

/**
 * PriorityQueue class implemented via the binary heap.
 * From your textbook (Weiss)
 * @author Corey & Weiss
 * @param <E> generic value
 */
public class WeissPriorityQueue<E> extends WeissAbstractCollection<E>
{
	/**
	 * Updates the queue.
	 * @param x Any item.
	 * @return returns true if true.
	 */
	public boolean update(E x) {
		int hole = 0;
		int temp = 0;
		E one = null;
		for(int i = 1; i <= this.currentSize; i++ ) {
			if(this.array[i].hashCode() == x.hashCode()) {
				one = this.array[i];
				hole = i;
			}
		}
		temp = hole;
		array[ 0 ] = x;

		for( ; compare( x, array[ hole / 2 ] ) < 0; hole /= 2 ) 
			array[ hole ] = array[ hole / 2 ];
		array[ hole ] = x;
		
		hole = temp;

		this.percolateDown(hole);


		return true;
	}

	/**
	 * Construct an empty PriorityQueue.
	 */
	@SuppressWarnings("unchecked")
	public WeissPriorityQueue( )
	{
		currentSize = 0;
		cmp = null;
		array = (E[]) new Object[ DEFAULT_CAPACITY + 1 ];
	}

	/**
	 * Construct an empty PriorityQueue with a specified comparator.
	 * @param c comparator
	 */
	@SuppressWarnings("unchecked")
	public WeissPriorityQueue( Comparator<? super E> c )
	{
		currentSize = 0;
		cmp = c;
		array = (E[]) new Object[ DEFAULT_CAPACITY + 1 ];
	}


	/**
	 * Construct a PriorityQueue from another Collection.
	 * @param coll collection
	 */
	@SuppressWarnings("unchecked")
	public WeissPriorityQueue( WeissCollection<? extends E> coll )
	{
		cmp = null;
		currentSize = coll.size( );
		array = (E[]) new Object[ ( currentSize + 2 ) * 11 / 10 ];

		int i = 1;
		for( E item : coll )
			array[ i++ ] = item;
		buildHeap( );
	}

	/**
	 * Compares lhs and rhs using comparator if
	 * provided by cmp, or the default comparator.
	 * @param lhs left hand side
	 * @param rhs right hand side
	 * @return values based on compartor
	 */
	@SuppressWarnings("unchecked")
	private int compare( E lhs, E rhs )
	{
		if( cmp == null )
			return ((Comparable)lhs).compareTo( rhs );
		else
			return cmp.compare( lhs, rhs );	
	}

	/**
	 * Adds an item to this PriorityQueue.
	 * @param x any object.
	 * @return true.
	 */
	public boolean add( E x )
	{
		if( currentSize + 1 == array.length )
			doubleArray( );

		// Percolate up
		int hole = ++currentSize;
		array[ 0 ] = x;

		for( ; compare( x, array[ hole / 2 ] ) < 0; hole /= 2 )
			array[ hole ] = array[ hole / 2 ];
		array[ hole ] = x;

		return true;
	}

	/**
	 * Returns the number of items in this PriorityQueue.
	 * @return the number of items in this PriorityQueue.
	 */
	public int size( )
	{
		return currentSize;
	}

	/**
	 * Make this PriorityQueue empty.
	 */
	public void clear( )
	{
		currentSize = 0;
	}

	/**
	 * Returns an iterator over the elements in this PriorityQueue.
	 * The iterator does not view the elements in any particular order.
	 * @return returns iterator
	 */
	public Iterator<E> iterator( )
	{
		return new Iterator<E>( )
		{
			int current = 0;

			public boolean hasNext( )
			{
				return current != size( );
			}

			@SuppressWarnings("unchecked")
			public E next( )
			{
				if( hasNext( ) )
					return array[ ++current ];
				else
					throw new NoSuchElementException( );
			}

			public void remove( )
			{
				throw new UnsupportedOperationException( );
			}
		};
	}

	/**
	 * Returns the smallest item in the priority queue.
	 * @return the smallest item.
	 * @throws NoSuchElementException if empty.
	 */
	public E element( )
	{
		if( isEmpty( ) )
			throw new NoSuchElementException( );
		return array[ 1 ];
	}

	/**
	 * Removes the smallest item in the priority queue.
	 * @return the smallest item.
	 * @throws NoSuchElementException if empty.
	 */
	public E remove( )
	{
		E minItem = element( );
		array[ 1 ] = array[ currentSize-- ];
		percolateDown( 1 );

		return minItem;
	}


	/**
	 * Establish heap order property from an arbitrary
	 * arrangement of items. Runs in linear time.
	 */
	private void buildHeap( )
	{
		for( int i = currentSize / 2; i > 0; i-- )
			percolateDown( i );
	}
	
	/**
	 * Default capacity.
	 */
	private static final int DEFAULT_CAPACITY = 100;
	
	/**
	 * Number of elements in heap.
	 */
	private int currentSize;   // Number of elements in heap
	/**
	 * The heap array.
	 */
	private E [ ] array; // The heap array
	
	/**
	 * Comparataor.
	 */
	private Comparator<? super E> cmp;

	/**
	 * Internal method to percolate down in the heap.
	 * @param hole the index at which the percolate begins.
	 */
	private void percolateDown( int hole )
	{
		int child;
		E tmp = array[ hole ];

		for( ; hole * 2 <= currentSize; hole = child )
		{
			child = hole * 2;
			if( child != currentSize &&
					compare( array[ child + 1 ], array[ child ] ) < 0 )
				child++;
			if( compare( array[ child ], tmp ) < 0 )
				array[ hole ] = array[ child ];
			else
				break;
		}
		array[ hole ] = tmp;
	}

	/**
	 * Internal method to extend array.
	 */
	@SuppressWarnings("unchecked")
	private void doubleArray( )
	{
		E [ ] newArray;

		newArray = (E []) new Object[ array.length * 2 ];
		for( int i = 0; i < array.length; i++ )
			newArray[ i ] = array[ i ];
		array = newArray;
	}
}
