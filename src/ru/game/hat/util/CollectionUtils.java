package ru.game.hat.util;

import java.util.ArrayList;
import java.util.Collection;

public class CollectionUtils {

	public static <T,K> Collection<K> transform(Collection<T> collection, Function<? super T, K> func) {
		final Collection<K> result = new ArrayList<K>();
		for (T elem : collection) {
			result.add(func.apply(elem));
		}
		return result;
	}
	
	public static interface Function<A,B> {
		B apply(A input);
	}
}
