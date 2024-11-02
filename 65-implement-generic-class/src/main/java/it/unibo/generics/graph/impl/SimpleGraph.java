package it.unibo.generics.graph.impl;

import java.util.List;
import java.util.Set;
import java.util.Map;
import java.util.ArrayList;
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
    public Set<N> linkedNodes(final N node) {
        return new HashSet<>(graph.get(node));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<N> getPath(final N source, final N target) {
        if(!graph.containsKey(source) || !graph.containsKey(target) ){
            return new ArrayList<>();
        }
        final Set<List<N>> frontier = initFrontier(source);
        //Moving the frontier until it's empty
        while(!frontier.isEmpty()) {            
            final Iterator<List<N>> pathIterator = frontier.iterator();
            Set<List<N>> newPaths = new HashSet<>();
            // Creating new paths until all precedent paths were examinated (and removed)
            while(pathIterator.hasNext()) {    
                moveFrontier(newPaths, pathIterator.next());
                pathIterator.remove();
            }
            // Checking if the target was found during frontier expansion
            for(final List<N> path : newPaths) {
                if(path.getLast().equals(target)) {
                    return path;
                }
            }
            frontier.addAll(newPaths);
        }
        return new ArrayList<>();
    }

    /**
     * Given a path, adds in newPaths the new paths found 
     * @param newPaths set of new paths 
     * @param path source path
     */
    private void moveFrontier(final Set<List<N>> newPaths, final List<N> path) {
        final N currentNode = path.getLast();
        for (final N node : graph.get(currentNode)) {
            if(!path.contains(node)) {
                final List<N> newPath = new ArrayList<>(path);
                newPath.addLast(node);
                newPaths.add(newPath);
            }
        }
    }

    /**
     * Initializes frontier
     * @param source Source node to add to the frontier
     * @return initialized set
     */
    private Set<List<N>> initFrontier(final N source) {
        final Set<List<N>> frontier = new HashSet<>();
        final List<N> initList = new ArrayList<>(); 
        initList.add(source);                   // Adding source path
        frontier.add(initList);
        return frontier;
    }
}
