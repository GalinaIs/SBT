<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>

<@c.page>
    <h3>Введите логин и пароль для входа в систему</h3>
    <@l.login "/login" "Войти">
    </@l.login>
</@c.page>
