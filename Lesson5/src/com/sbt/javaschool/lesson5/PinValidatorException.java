package com.sbt.javaschool.lesson5;

class TerminalException extends Exception{
    private String causeMessage;

    /***
     * @param message Ошибка
     * @param causeMessage Причина ошибки
     */
    public TerminalException(String message, String causeMessage){
        super(message);
        this.causeMessage = causeMessage;
    }

    @Override
    public String getMessage() {
        return super.getMessage() + "\nПричина: " + causeMessage;
    }
}

public class PinValidatorException extends Exception{
    private String causeMessage;

    public PinValidatorException(String message, String causeMessage){
        super(message);
        this.causeMessage = causeMessage;
    }

    @Override
    public String getMessage() {
        return super.getMessage() + "\nПричина: " + causeMessage;
    }
}

class IncorrectSumTerminal extends Exception{
    private String causeMessage;

    public IncorrectSumTerminal(String message, String causeMessage){
        super(message);
        this.causeMessage = causeMessage;
    }

    @Override
    public String getMessage() {
        return super.getMessage() + "\nПричина: " + causeMessage;
    }
}

class AccountIsLockedException extends Exception {
    AccountIsLockedException(long time) {
        super(String.format("Терминал временно заблокирован. До разблокировки: %d сек.", time / 1000));
    }
}

class ServerException extends Exception{
    private String causeMessage;

    public ServerException(String message, String causeMessage){
        super(message);
        this.causeMessage = causeMessage;
    }

    @Override
    public String getMessage() {
        return super.getMessage() + "\nПричина: " + causeMessage;
    }
}
