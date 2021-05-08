package ru.peytob.mineville.machine.nodes;

/**
 * Child exception class.
 * Thrown when the number of children is incorrect or we try to arrange it.
 */
public class ChildException extends Exception {

    /**
     * Message to be shown when the exception is thrown.
     */
    private String message;

    /**
     * Constructor that sets the message.
     *
     * @param _message specific message
     */
    public ChildException(String _message) {
        message = _message;
    }

    @Override
    public String toString() {
        return message;
    }
}
