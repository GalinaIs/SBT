<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>

<@c.page>
    <h3>Введите имя для входа в чат</h3>
    <@l.login "/login" "Войти">
    </@l.login>
    <#if param??>
    ${param.error}
    </#if>
</@c.page>
