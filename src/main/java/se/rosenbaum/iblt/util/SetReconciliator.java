package se.rosenbaum.iblt.util;

import se.rosenbaum.iblt.IBLT;
import se.rosenbaum.iblt.data.Data;

import java.util.Collections;
import java.util.Iterator;


public class SetReconciliator<K extends Data, V extends Data> {
    private IBLT<K, V> incomingIBLT;

    public SetReconciliator(IBLT<K, V> incomingIBLT) {
        this.incomingIBLT = incomingIBLT;
    }

    public Iterator<Tuple<K, V>> reconcile(Iterator<Tuple<K, V>> myData) {
        return Collections.emptyIterator();
    }


}