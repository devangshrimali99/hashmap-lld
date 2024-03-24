package org.example;

import java.util.function.Consumer;

public class MyHashMap<K,V> {

    private static final int INITIAL_SIZE =  1<<4;
    private static final int MAXIMUM_SIZE = 1<<30;
    private static final double LOAD_FACTOR = 0.75;
    public static int CURRENT_SIZE=0;

    private Entry[] hashTable;

    class Entry<K,V> {
        public K key;
        public V value;
        public Entry next;

        Entry(K k,V v){
            key=k;
            value=v;
        }
    }

    public MyHashMap(){
        hashTable = new Entry[INITIAL_SIZE];
    }

    public MyHashMap(int capacity){
        int tableSize = tableSizeFor(capacity);
        hashTable = new Entry[tableSize];
    }

    final int tableSizeFor(int cap){
        int n = cap-1;
        n=n>>>1;
        n=n>>>2;
        n=n>>>4;
        n=n>>>8;
        n=n>>>16;
        return (n<0) ? 1 : (n>=MAXIMUM_SIZE) ? MAXIMUM_SIZE : n+1;
    }

    private int getHash(K key){
        return Math.abs(key.hashCode()%hashTable.length);
    }

    public void put(K key, V value){
        if(CURRENT_SIZE+1>hashTable.length*LOAD_FACTOR){
            resize();
        }
        int hashCode = getHash(key);
        Entry node = hashTable[hashCode];
        if (node == null) {
            Entry newNode = new Entry(key,value);
            hashTable[hashCode] = newNode;
        } else {
            Entry previousNode = node;
            while(node!=null){
                if(node.key==key){
                    node.value=value;
                    return;
                }
                previousNode=node;
                node=node.next;
            }
            Entry newNode = new Entry(key,value);
            previousNode.next=newNode;
        }
        CURRENT_SIZE++;
    }

    public V get(K key){
        int hashCode = getHash(key);
        Entry node = hashTable[hashCode];
        while(node!=null){
            if(node.key==key){
                return (V) node.value;
            }
            node=node.next;
        }
        throw new IllegalArgumentException("Key does not exist in the HashMap : " + key);
    }

    public void remove(K key){
        int hashCode = getHash(key);
        Entry node = hashTable[hashCode];
        Entry prevNode = null;
        while(node!=null){
            if(node.key==key){
                if(prevNode==null){
                    hashTable[hashCode]=null;
                }
                else{
                    prevNode.next=node.next;
                }
                CURRENT_SIZE--;
                return;
            }
        }
        throw new IllegalArgumentException("Cannot Remove Key from HashMap as Key : " + key + "not found");
    }

    public void forEach(Consumer<Entry<K, V>> action) {
        for (Entry<K, V> node : hashTable) {
            if (node != null) {
                action.accept(node);
            }
        }
    }

    private void resize(){
        int newCapacity = hashTable.length*2;
        if(newCapacity>MAXIMUM_SIZE){
            throw new IllegalStateException("Cannot add more elements, maximum size reached");
        }
        Entry<K,V>[] newHashTable = new Entry[newCapacity];
        for(Entry<K,V> node: hashTable){
            if(node!=null){
                int newHashCode = Math.abs(node.key.hashCode()) % newCapacity;
                newHashTable[newHashCode] = node;
            }
        }
        hashTable=newHashTable;
    }
}
