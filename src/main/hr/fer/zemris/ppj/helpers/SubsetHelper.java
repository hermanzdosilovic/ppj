package hr.fer.zemris.ppj.helpers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class SubsetHelper {
  public static <T> Set<Set<T>> getAllSubsets(final Collection<T> collection) {
    List<T> set = new ArrayList<>(collection);
    Set<Set<T>> subsets = new HashSet<>();
    
    long subsetsSize = (long) (1 << set.size());
    for (long i = 0; i < subsetsSize; i++) {
      long n = i;
      int position = 0;
      Set<T> subset = new HashSet<>();
      while (n != 0) {
        if (n%2 == 1) {
          subset.add(set.get(position));
        }
        n /= 2;
        position++;
      }
      subsets.add(subset);
    }
    
    return subsets;
  }
}
