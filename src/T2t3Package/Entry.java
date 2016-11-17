/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package T2t3Package;

import java.util.Map;

/**
 *
 * @author korenciak.marek
 */
public class Entry<K extends Comparable,V extends Object> implements Map.Entry<K,V>{
    
    private K key;
    private V value;

    public Entry(K key, V value) {
        this.key = key;
        this.value = value;
    }    

    @Override
    public K getKey() {
        return this.key;
    }

    @Override
    public V getValue() {
        return this.value;
    }

    @Override
    public V setValue(V value) {
        this.value = value;
        return value;
    }  
    
    public String toString(){
        return this.key+", "+this.value;
    }
}
