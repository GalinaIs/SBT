package com.sbt.javaschool.lesson5;

public class TerminalServer {
    private int amountAccount;
    private boolean isAllowed;

    public TerminalServer(int amount) {
        this.amountAccount = amount;
        isAllowed = true;
    }

    /**
     * Положить деньги на счет
     * @param sum сумма, которую клиент хочет положить на счет
     * @throws ServerException - если ведутся работы на сервере
     */
    public void putMoney(int sum) throws ServerException {
        if (!isAllowed) {
            throw new ServerException("Ошибка при попытке добавить деньги на счет", "Ведутся работы на сервере");
        }
        amountAccount += sum;
    }

    /**
     * Снять деньги со счета
     * @param sum сумма, которую клиент хочет снять
     * @throws ServerException - если ведутся работы на сервере или недостаточно средств на счете
     */
    public void giveMoney(int sum) throws ServerException {
        if (!isAllowed) {
            throw new ServerException("Ошибка при попытке снять деньги со счета", "Ведутся работы на сервере");
        }
        if (amountAccount - sum < 0)
            throw new ServerException("Ошибка при попытке снять деньги со счета", "Недостаточно средст на счете");

        amountAccount -= sum;
    }

    /**
     * Получить баланс счета
     * @return сумма на счете
     * @throws ServerException - если ведутся работы на сервере
     */
    public int balance() throws ServerException {
        if (!isAllowed) {
            throw new ServerException("Ошибка при попытке вывести баланс", "Ведутся работы на сервере");
        }
        return amountAccount;
    }

    /**
     * Вызывается для изменения состояния сервера с доступного на недоступный и наоборот.
     */
    public void changeAllowedServer() {
        isAllowed = !isAllowed;
    }
}
