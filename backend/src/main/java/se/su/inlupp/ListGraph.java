package se.su.inlupp;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

public class ListGraph<T> implements Graph<T> {

  private final Map<T, Set<Edge<T>>> nodes = new HashMap<>();

  @Override
  public void add(T node) {
    nodes.putIfAbsent(node, new HashSet<>());
  }

  @Override
  public void connect(T node1, T node2, String name, int weight) {
    throw new UnsupportedOperationException("Unimplemented method 'connect'");
  }

  @Override
  public void setConnectionWeight(T node1, T node2, int weight) {
    throw new UnsupportedOperationException("Unimplemented method 'setConnectionWeight'");
  }

  @Override
  public Set<T> getNodes() {
    return new HashSet<>(nodes.keySet());
  }

  @Override
  public Collection<Edge<T>> getEdgesFrom(T node) throws NoSuchElementException {
    // Objects.requireNonNull(node, "null är inte en giltig nod");
    if (nodes.containsKey(node)) {
      return new HashSet<>(nodes.get(node));
    } else {
      throw new NoSuchElementException();
    }
  }

  @Override
  public Edge<T> getEdgeBetween(T node1, T node2) {
    throw new UnsupportedOperationException("Unimplemented method 'getEdgeBetween'");
  }

  @Override
  public void disconnect(T node1, T node2) {
    throw new UnsupportedOperationException("Unimplemented method 'disconnect'");
  }

  @Override
  public void remove(T node) throws NoSuchElementException {
    Collection<Edge<T>> edges = getEdgesFrom(node);
    for (Edge<T> edge : edges) {
      T destination = edge.getDestination();
      disconnect(node, destination);
    }
    //nodes.remove(node); 
  }

  @Override
  public boolean pathExists(T from, T to) {
    throw new UnsupportedOperationException("Unimplemented method 'pathExists'");
  }

  @Override
  public List<Edge<T>> getPath(T from, T to) {
    throw new UnsupportedOperationException("Unimplemented method 'getPath'");
  }
}
