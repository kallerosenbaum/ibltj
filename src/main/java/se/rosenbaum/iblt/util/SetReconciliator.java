package se.rosenbaum.iblt.util;

import se.rosenbaum.iblt.IBLT;
import se.rosenbaum.iblt.data.Data;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class SetReconciliator<K extends Data, V extends Data> {
    private IBLT<K, V> incomingIBLT;

    public SetReconciliator(IBLT<K, V> incomingIBLT) {
        this.incomingIBLT = incomingIBLT;
    }

    public Map<K, V> reconcile(Map<K, V> myData) {
        for (Map.Entry<K, V> entry : myData.entrySet()) {
            incomingIBLT.delete(entry.getKey(), entry.getValue());
        }

        ResidualData<K, V> residualData = incomingIBLT.listEntries();
        if (residualData == null) {
            // Reconciliation not possible, since we couldn't list entries.
            return null;
        }

        Map<K, V> result = new HashMap(myData);
        for (K key : residualData.getAbsentEntries().keySet()) {
            result.remove(key);
        }

        result.putAll(residualData.getExtraEntries());

        return result;
    }
}