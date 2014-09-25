package se.rosenbaum.iblt.exception;

public class WrongCellIndexException extends RuntimeException {
    public WrongCellIndexException(int index, int cellCount) {
        super("Cell index " + index + " could not be found. Must be < " + (cellCount-1));
    }
}
