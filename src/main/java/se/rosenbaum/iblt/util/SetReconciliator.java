package se.rosenbaum.iblt.util;

import se.rosenbaum.iblt.IBLT;
import se.rosenbaum.iblt.data.Data;

import java.util.HashMap;
import java.util.Map;


public class SetReconciliator<K extends Data, V extends Data> {
    private IBLT<K, V> incomingIBLT;

    public SetReconciliator(IBLT<K, V> incomingIBLT) {
        this.incomingIBLT = incomingIBLT;
    }

    /**
     * Tries to reconcile the iblt with the entries in myData.
     * @param myData The data to subtract from the IBLT. This is the "guess" on what
     *               data the IBLT contains.
     * @return A map containing all the entries in the iblt. If reconciliation fails, null is returned
     */
    public Map<K, V> reconcile(Map<K, V> myData) {
        for (Map.Entry<K, V> entry : myData.entrySet()) {
            incomingIBLT.delete(entry.getKey(), entry.getValue());
        }

        ResidualData<K, V> residualData = incomingIBLT.listEntries();
        if (residualData == null) {
            // Reconciliation not possible, since we couldn't list entries.
            return null;
        }

        Map<K, V> result = new HashMap<K, V>(myData);
        for (K key : residualData.getAbsentEntries().keySet()) {
            result.remove(key);
        }

        result.putAll(residualData.getExtraEntries());

        return result;
    }
}