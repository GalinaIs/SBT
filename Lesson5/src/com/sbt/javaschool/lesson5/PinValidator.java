package com.sbt.javaschool.lesson5;

public class PinValidator {
    private final String pinCode;
    private StatePinValidate statePin;
    private int countFailedAttempts;
    private long periodForLocked;
    private long timeLocked;

    PinValidator(String pinCode) throws PinValidatorException{
        if (pinIsNumber(pinCode))
            this.pinCode = pinCode;
        else
            throw new PinValidatorException("Ошибка при попытке установить пин-код", "ПИН должен состоять из 4 цифр");

        statePin = StatePinValidate.ACCESS_DENIED;
        countFailedAttempts = 0;
        periodForLocked = 5000;
    }

    /**
     * Проверка - ПИН состоит из 4 цифр
     * @param pinCode ПИН код
     * @return состоит ли ПИН код из 4 цифр
     */
    private boolean pinIsNumber(String pinCode) {
        return pinCode.matches("\\d{4}");
    }

    /**
     * Функция для определения возможности проведения операции
     * @return true - операцию провести возможно, иначе false.
     * @throws AccountIsLockedException - в случае если аккаунт заблокирован.
     */
    public boolean accountAllowed() throws AccountIsLockedException {
        switch (statePin) {
            case ACCESS_LOCKED:
                checkLocked();
                statePin = StatePinValidate.ACCESS_DENIED;
            case ACCESS_DENIED:
                return false;
        }
        return true;
    }

    /**
     * Проверяет заблокирован ли аккаунт
     * @throws AccountIsLockedException - в случае если аккаунт заблокирован.
     */
    private void checkLocked() throws AccountIsLockedException {
        long time = timeLocked - System.currentTimeMillis();
        if (time > 0) {
            throw new AccountIsLockedException(time);
        }
        countFailedAttempts = 0;
    }

    /*
     * Функция для проверки ПИН кода. Увеличивает количество неудачных попыток ввода ПИНа, если ПИН код введен неверно.
     * Если кол-во неверных вводов равно 3, то аккаунт блокируется и устанавливаеся время блокировки.
     * @param pin Проверяемый ПИН код.
     * @return true, если ПИН код введен верно, иначе false.
     * @throws PinValidatorException - в случае, если ПИН код состоит не из 4 цифр
     * @throws AccountIsLockedException - в случае если аккаунт заблокирован.
     */
    public boolean validatePinCode(String pin) throws PinValidatorException, AccountIsLockedException {
        if (statePin == StatePinValidate.ACCESS_LOCKED)
            checkLocked();

        if (pinCode.equals(pin)) {
            statePin = StatePinValidate.ACCESS_ALLOWED;
            return true;
        }

        if (++countFailedAttempts == 3) {
            statePin = StatePinValidate.ACCESS_LOCKED;
            timeLocked = System.currentTimeMillis() + periodForLocked;
        } else {
            statePin = StatePinValidate.ACCESS_DENIED;
        }

        if (!pinIsNumber(pin)) {
            throw new PinValidatorException("Ошибка при проверке пин-кода", "ПИН должен состоять из 4 цифр");
        }

        return false;
    }

    /**
     * Для описание доступа к аккаунту - разрешен, запрещен или заблокирован.
     */
    enum StatePinValidate {
        ACCESS_DENIED,
        ACCESS_ALLOWED,
        ACCESS_LOCKED
    }
}
