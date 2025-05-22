package se.su.inlupp;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;

public class ListGraph<T> implements Graph<T> {

  private final Map<T, Set<Edge<T>>> nodes = new HashMap<>();

  // private boolean containsNodePair(T node1, T node2){
  //   return nodes.containsKey(node1) && nodes.containsKey(node2);
  // }

  @Override
  public void add(T node) {
    Objects.requireNonNull(node, "Null är ingen nod!");
    nodes.putIfAbsent(node, new HashSet<>());
  }

  @Override
  public void connect(T node1, T node2, String name, int weight) throws NoSuchElementException {
    if(nodes.containsKey(node1) && nodes.containsKey(node2)){
      // TODO: testa att ersätta sökning efter existerande kant med en överskuggad equals och hashcode i ListEdge
      // OBS: kontrollerar om kant finns från node1 till node2, bör räcka så länge det bara går att lägga till oriktade förbindelser
      Collection<Edge<T>> edges1 = getEdgesFrom(node1);
      for (Edge<T> edge : edges1) {
        if(edge.getDestination().equals(node2)){
          throw new IllegalStateException();
        } 
      }  
      nodes.get(node1).add(new ListEdge<>(node2, name, weight));
      nodes.get(node2).add(new ListEdge<>(node1, name, weight));
    } else {
      throw new NoSuchElementException();
    }
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
    if (nodes.containsKey(node)) {
      return new HashSet<>(nodes.get(node));
    } else {
      throw new NoSuchElementException(); // checked exception
    }
  }

  @Override
  public Edge<T> getEdgeBetween(T node1, T node2) throws NoSuchElementException {
    if (nodes.containsKey(node2)) {
      Collection<Edge<T>> edges1 = getEdgesFrom(node1);
      for (Edge<T> edge : edges1) {
        if (edge.getDestination().equals(node2)) {
          return edge;
        }
      }
    } else {
      throw new NoSuchElementException(); // saknas
    }
    return null;
  }

  @Override
  public void disconnect(T node1, T node2) {
    Edge<T> edgeTo2 = getEdgeBetween(node1, node2);
    Edge<T> edgeTo1 = getEdgeBetween(node2, node1);
    if (edgeTo2 == null || edgeTo1 == null) {
      throw new IllegalStateException(); // kant saknas mellan noder
    } else {
      Set<Edge<T>> edges1 = nodes.get(node1);
      edges1.remove(edgeTo2);
      Set<Edge<T>> edges2 = nodes.get(node2);
      edges2.remove(edgeTo1);
    }
  }

  @Override
  public void remove(T node) throws NoSuchElementException {
    Collection<Edge<T>> edges = getEdgesFrom(node);
    for (Edge<T> edge : edges) {
      T destination = edge.getDestination();
      disconnect(node, destination);
    }
    nodes.remove(node);
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
