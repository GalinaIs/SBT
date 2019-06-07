<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>

<@c.page>
    <h3>Введите данные для регистрации</h3>

    <#if message??>
        <div class="warning">Ошибка при регистрации - пользователь с такими данными уже существует</div>
    </#if>

    <@l.login "/registration" "Зарегистироваться">
        <div class="div_flex"><label> Email: </label><input type="email" name="email" required/> </div>
    </@l.login>

</@c.page>
