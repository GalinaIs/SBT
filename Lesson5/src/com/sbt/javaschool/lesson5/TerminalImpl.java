package com.sbt.javaschool.lesson5;

public class TerminalImpl {
    private final TerminalServer server;
    private final PinValidator pinValidator;
    private final ViewUserI viewUser;

    public TerminalImpl() throws PinValidatorException {
        this(10000, "1234");
    }

    public TerminalImpl(int amountAccount, String pinCode) throws PinValidatorException {
        server = new TerminalServer(amountAccount);
        pinValidator = new PinValidator(pinCode);
        viewUser = new ViewUserConsole();
    }

    /***
     * Основаная функция терминала - пользователь вводит операцию, которую необохидмо провести.
     */
    public void work() {
        boolean quit = false;
        while (!quit) {
            try {
                String messageUser = "Для работы с терминалом выберите одну из операций:\n" +
                        "0 - ввести ПИН-код\n" +
                        "1 - получить баланс\n" +
                        "2 - добавить деньги на счет\n" +
                        "3 - снять деньги со счета\n" +
                        "Для выхода нажмите q";
                viewUser.print(messageUser);
                String response = viewUser.response();
                switch (response) {
                    case "0":
                        inputPinCode();
                        break;
                    case "1":
                        outputBalance();
                        break;
                    case "2":
                        putCash();
                        break;
                    case "3":
                        takeCash();
                        break;
                    case "q":
                        quit = true;
                        messageUser = "Работа с терминалом завершена";
                        viewUser.print(messageUser);
                        break;
                    default:
                        messageUser = "Вы ввели неверную операцию";
                        viewUser.print(messageUser);
                }
            } catch (Exception ex) {
                viewUser.print(ex.getMessage());
            }
        }
    }

    /**
     * Проверка вводимого ПИН-кода
     * @throws PinValidatorException - в случае, если пользователь ввел пароль не 4 цифры
     * @throws AccountIsLockedException - в случае, если аккаунт заблокирован
     */
    private void inputPinCode() throws PinValidatorException, AccountIsLockedException {
        pinValidator.accountAllowed();
        String messageUser = "Введите ПИН-код: ";
        viewUser.print(messageUser);
        String pinCode = viewUser.response();
        messageUser = pinValidator.validatePinCode(pinCode) ? "ПИН-код введен успешно" : "Неверный ПИН-код";
        viewUser.print(messageUser);
    }

    /**
     * Запрос баланса
     * @throws TerminalException - в случае, если операция выполняется без ввода ПИН-кода
     * @throws AccountIsLockedException -  в случае, если аккаунт заблокирован
     * @throws ServerException -  в случае, если в операции отказано сервером - недостаточно средств на счете
     * или на сервере ведут работы
     */
    private void outputBalance() throws TerminalException, AccountIsLockedException, ServerException {
        if (!pinValidator.accountAllowed())
            throw new TerminalException("Ошибка при попытке вывести баланс", "Необходимо ввести ПИН-код");
        String messageUser = "Баланс: " + server.balance() + " руб.";
        viewUser.print(messageUser);
    }

    /**
     * Положить деньги на счет
     * @throws TerminalException - в случае, если операция выполняется без ввода ПИН-кода
     * @throws IncorrectSumTerminal - введена некоректная сумма операции (не кратная 100)
     * @throws AccountIsLockedException -  в случае, если аккаунт заблокирован
     * @throws ServerException -  в случае, если в операции отказано сервером - недостаточно средств на счете
     * или на сервере ведут работы
     */
    private void putCash() throws TerminalException, AccountIsLockedException, IncorrectSumTerminal, ServerException {
        if (!pinValidator.accountAllowed())
            throw new TerminalException("Ошибка при попытке добавления денег на счет", "Необходимо ввести ПИН-код");
        String messageUser = "Введите сумму, которую хотите положить: ";
        viewUser.print(messageUser);
        String response = viewUser.response();
        try {
            int sum = Integer.parseInt(response);
            checkSum(sum);
            server.putMoney(sum);
            messageUser = "Деньги успешно добавлены на счет";
            viewUser.print(messageUser);
        } catch (NumberFormatException ex) {
            throw new TerminalException("Ошибка при попытке добавления денег на счет", "Сумма должна быть целым числом");
        }
    }

    /**
     * Снять деньги со счета
     * @throws TerminalException - в случае, если операция выполняется без ввода ПИН-кода
     * @throws IncorrectSumTerminal - введена некоректная сумма операции (не кратная 100)
     * @throws AccountIsLockedException -  в случае, если аккаунт заблокирован
     * @throws ServerException -  в случае, если в операции отказано сервером - недостаточно средств на счете
     * или на сервере ведут работы
     */
    private void takeCash() throws TerminalException, AccountIsLockedException, IncorrectSumTerminal, ServerException {
        if (!pinValidator.accountAllowed())
            throw new TerminalException("Ошибка при попытке снять деньги", "Необходимо ввести ПИН-код");
        String messageUser = "Введите сумму, которую хотите снять: ";
        viewUser.print(messageUser);
        String response = viewUser.response();
        try {
            int sum = Integer.parseInt(response);
            checkSum(sum);
            server.giveMoney(sum);
            messageUser = "Деньги успешно сняты на счет";
            viewUser.print(messageUser);
        } catch (NumberFormatException ex) {
            throw new TerminalException("Ошибка при попытке снять деньги", "Сумма должна быть целым числом");
        }
    }

    /**
     * Проверка корректности ввода суммы - сумма должна быть кратна 100.
     * @param sum Сумма операции
     * @throws IncorrectSumTerminal Введена некоректная сумма операции (не кратная 100)
     */
    private void checkSum(int sum) throws IncorrectSumTerminal {
        if (sum % 100 != 0)
            throw new IncorrectSumTerminal("Ошибка при вводе суммы", "Сумма должна быть кратной 100");
    }
}
