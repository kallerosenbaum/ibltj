package se.rosenbaum.iblt.bitcoin;

import com.google.bitcoin.core.StoredBlock;
import com.google.bitcoin.core.Transaction;
import com.google.bitcoin.kits.WalletAppKit;
import com.google.bitcoin.params.TestNet3Params;
import com.google.bitcoin.store.BlockStoreException;
import com.google.common.util.concurrent.Service;
import org.junit.Test;
import se.rosenbaum.iblt.Cell;
import se.rosenbaum.iblt.IBLT;
import se.rosenbaum.iblt.data.IntegerData;
import se.rosenbaum.iblt.data.LongData;
import se.rosenbaum.iblt.hash.LongDataHashFunction;
import se.rosenbaum.iblt.hash.LongDataSubtablesHashFunctions;

import java.io.File;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlockTest {
    LongDataHashFunction cellHashFunction = new LongDataHashFunction();

    private LongData data(long value) {
        return new LongData(value);
    }

    private Cell<LongData, LongData> createCell() {
        Cell<LongData, LongData> cell = new Cell<LongData, LongData>(data(0), data(0), new IntegerData(0),
                cellHashFunction);
        return cell;
    }

    private Cell<LongData, LongData>[] createCells(int cellCount) {
        Cell<LongData, LongData>[] cells = new Cell[cellCount];
        for (int i = 0; i < cellCount; i++) {
            cells[i] = createCell();
        }
        return cells;
    }

    private IBLT<LongData, LongData> createIBLT() {
        return new IBLT<LongData, LongData>(createCells(10), new LongDataSubtablesHashFunctions(10, 2));
    }

    private Map<LongData, LongData> encodeTransaction(Transaction transaction) {
        Map<LongData, LongData> map = new HashMap<LongData, LongData>();
        byte[] bytes = transaction.bitcoinSerialize();
        byte[] keyBytes = Arrays.copyOfRange(transaction.getHash().getBytes(), 0, 7); // 64 bit
        keyBytes[6] = 0; // Set index bytes to 0
        keyBytes[7] = 0;

        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        while (buffer.hasRemaining()) {
            LongData key = new LongData(ByteBuffer.wrap(keyBytes).getLong(0));

        }
    }

    @Test
    public void testSingleTransaction() throws BlockStoreException {
        WalletAppKit walletAppKit = new WalletAppKit(new TestNet3Params(), new File("/tmp"), "iblt");
        Service.State state = walletAppKit.startAndWait();
        StoredBlock chainHead = walletAppKit.chain().getBlockStore().getChainHead();
        List<Transaction> transactions = chainHead.getHeader().getTransactions();


    }
}
