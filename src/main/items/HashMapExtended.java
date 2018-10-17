package main.items;

import java.util.HashMap;

/**
 * This class will be used later on to make sure that the HashMap we used for
 * numeric patterns return a value for unknown words (which do not exist in the
 * dictionary of words)
 * 
 * @author Mondher Bouazizi
 *
 * @param <K> The Key
 * @param <V> The value
 */
public class HashMapExtended<K, V> extends HashMap<K, V> {

	private static final long serialVersionUID = -2801324067751577230L;

	protected V defaultValue;

	public HashMapExtended(V defaultValue) {
		this.defaultValue = defaultValue;
	}

	@Override
	public V get(Object k) {
		return containsKey(k) ? super.get(k) : defaultValue;
	}
}
