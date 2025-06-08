package com.example.project3.util;

import java.util.Iterator;

/**
 * List templating class 
 * @author Mohith Kodavati and Nalita Pillay
 */
public class List<E> implements Iterable<E> {
    private static final int INITIAL_CAPACITY = 4;
    private static final int CAPACITY_INCREMENT = 4;
    private static final int NOT_FOUND = -1;

    private E[] objects;
    private int size;

    /**
     * Constructor to initialize the list with initial capacity of 4
     */
    @SuppressWarnings("unchecked")
    public List() {
        // Create a new array type-casted to E with capacity of 4
        objects = (E[]) new Object[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Find the index of an object in the list
     * @param e the object to find
     * @return the index if found, NOT_FOUND (-1) otherwise
     */
    private int find(E e) {
        if (e == null) return NOT_FOUND;
        for (int i = 0; i < size; i++) {
            if (e.equals(objects[i])) {
                return i;
            }
        }
        return NOT_FOUND;
    }
    
    /**
     * Increase the array capacity by 4
     */
    @SuppressWarnings("unchecked")
    private void grow() {
        E[] newObjects = (E[]) new Object[objects.length + CAPACITY_INCREMENT];
        for (int i = 0; i < size; i++) {
            newObjects[i] = objects[i];
        }
        objects = newObjects;
    }

    /**
     * Check if the list contains an object
     * @param e the object to check
     * @return true if found, false otherwise
     */
    public boolean contains(E e) {
        return find(e) != NOT_FOUND;
    }

    /**
     * Add an object to the end of the list
     * @param e the object to add
     */
    public void add(E e) {
        if (e == null) return;
        if (contains(e)) return;
        // If array is full, grow it
        if (size == objects.length) {
            grow();
        }
        objects[size] = e;
        size++;
    }

    /**
     * Remove an object from the list
     * @param e the object to remove
     */
    public void remove(E e) {
        int index = find(e);
        if (index == NOT_FOUND) return;
        // Replace with the last element (maintains order)
        objects[index] = objects[size - 1];
        objects[size - 1] = null; // Clear the last element
        size--;
    }

    /**
     * Check if the list is empty
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }
    
    /**
     * Get the number of elements in the list
     * @return the size
     */
    public int size() {
        return size;
    }

    /**
     * Return an iterator for the list
     * @return the iterator
     */
    @Override
    public Iterator<E> iterator() {
        return new ListIterator<E>();
    }
    
    /**
     * Get the object at a specific index
     * @param index the index
     * @return the object at the index
     */
    public E get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        return objects[index];
    }
    
    /**
     * Set an object at a specific index
     * @param index the index
     * @param e the object to set
     */
    public void set(int index, E e) {
        if (index < 0 || index >= size || e == null) {
            return;
        }
        objects[index] = e;
    }

    /**
     * Find the index of an object
     * @param e the object to find
     * @return the index if found, NOT_FOUND (-1) otherwise
     */
    public int indexOf(E e) {
        return find(e);
    }

    /**
     * Iterator implementation for the list
     * Using Type T to avoid hiding Type E
     * @param <T> the type of the object
     * @return the iterator
     */
    private class ListIterator<T> implements Iterator<T> {
        int current = 0; //current index when traversing the list (array)

        /**
         * Check if there are more elements
         * @return true if there are more elements, false otherwise
         */
        @Override
        public boolean hasNext() {
            return current < size;
        }

        /**
         * Get the next element
         * @return the next element
         */
        @SuppressWarnings("unchecked")
        @Override
        public T next() {
            if (!hasNext()) {
                return null;
            }
            return (T) objects[current++];
        }
    }
}
