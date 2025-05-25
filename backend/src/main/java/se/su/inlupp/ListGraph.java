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
  public void setConnectionWeight(T node1, T node2, int weight) throws NoSuchElementException {
    Edge<T> edgeTo2 = getEdgeBetween(node1, node2);
    edgeTo2.setWeight(weight);
    Edge<T> edgeTo1 = getEdgeBetween(node2, node1);
    edgeTo1.setWeight(weight);
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
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder("Graf");
    stringBuilder.append("\n");
    for (Map.Entry<T, Set<Edge<T>>> mapEntry : nodes.entrySet()) {
      stringBuilder.append(mapEntry.getKey()).append(": ").append(mapEntry.getValue()).append("\n");
    }
    return stringBuilder.toString();
  }

@Override
  public boolean pathExists(T from, T to) {
    // finns angivna noder i grafen?
    if (nodes.containsKey(from) && nodes.containsKey(to)) {
      Set<T> visited = new HashSet<>(); // vill inte besöka redan besökta noder
      return isAPath(from, to, visited);
      // return !(getPath(from, to) == null);
    }
    return false;
  }

  private boolean isAPath(T from, T to, Set<T> visited) {
    visited.add(from); // markera noden som besökt
    if (from.equals(to)) { // Är noden den vi söker?
      return true;
    }
    // för varje granne som är ansluten via en kant till noden
    for (Edge<T> edge : getEdgesFrom(from)) {
      // Har vi inte redan besökt grannen tidigare?
      if (!visited.contains(edge.getDestination())) {
        // rekursivt anrop, besök granne och se om vägen via denna leder till sökta
        // noden
        if (isAPath(edge.getDestination(), to, visited)) {
          return true;
        }
      }
      // vägen ledde inte till den sökta noden, hitta nästa kant till en obesökt
      // granne
    }
    // ingen av vägar via den här nodens grannar leder till sökta noden
    return false;
  }

  @Override
  public List<Edge<T>> getPath(T from, T to) {
    throw new UnsupportedOperationException("Unimplemented method 'getPath'");
  }
}
