package it.unibo.generics.graph.impl;

import java.util.List;
import java.util.Set;
import java.util.Map;
import java.util.Queue;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import it.unibo.generics.graph.api.Graph;

public class SimpleGraph<N> implements Graph<N> {

    private final Map<N, List<N>> graph = new HashMap<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public void addNode(final N node) {
        if(!graph.containsKey(node)) {
            graph.put(node, new ArrayList<>());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addEdge(final N source, final N target) {
        if( source != null 
            && target != null
            && graph.containsKey(source)  
            && graph.containsKey(target)) {
                graph.get(source).add(target);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<N> nodeSet() {
        return new HashSet<>(graph.keySet());
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Set<N> linkedNodes(N node) {
        return new HashSet<>(graph.get(node));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<N> getPath(N source, N target) {
        if(!graph.containsKey(source) || !graph.containsKey(target) ){
            return new ArrayList<>();
        }
        final Set<List<N>> frontier = new HashSet<>();
        final List<N> initList = new ArrayList<>(); 
        initList.add(source);                   // Adding source path
        frontier.add(initList);
        N currentNode = source;                 
        while(!frontier.isEmpty()) {            // Moving the frontier until it's empty
            final Iterator<List<N>> pathIterator = frontier.iterator();
            final Set<List<N>> newPaths = new HashSet<>();
            while(pathIterator.hasNext()) {     // Creating paths until target is found or all precedent paths were examinated 
                final List<N> path = pathIterator.next();
                currentNode = path.getLast();
                for (N node : graph.get(currentNode)) {
                    if(!path.contains(node)) {
                        final List<N> newPath = new ArrayList<>(path);
                        newPath.addLast(node);
                        if(node.equals(target))  {
                            return newPath;
                        }
                        newPaths.add(newPath);
                    }
                }
                pathIterator.remove();
            }
            frontier.addAll(newPaths);
        }
        return new ArrayList<>();
    }
}
