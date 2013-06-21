package edu.kit.checkstyle;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;


public final class CollectionUtils {

  @SafeVarargs
  public static <A> Set<A> mkSet(final A... as) {
    return Sets.newHashSet(as);
  }

  @SafeVarargs
  public static <A> List<A> mkList(final A... as) {
    return Lists.newArrayList(as);
  }

  public static <A, B> Map<A, B> mkMap() {
    return Maps.newHashMap();
  }
}
