package org.example;

public class Main {
    public static void main(String[] args) {
        MyHashMap<Integer, String> map = new MyHashMap<>();
        map.put(1,"A");
        map.put(2,"B");
        map.put(3,"C");
        map.put(4,"D");
        map.forEach(entry ->{
            if(entry.key%2==0){
                map.remove(entry.key);
            }
            else{
                System.out.println("Key: " + entry.key + ", Value: " + entry.value);
            }
        });
    }
}