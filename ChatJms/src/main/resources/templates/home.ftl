<#import "parts/common.ftl" as c>

<@c.page>

<#if userAuth??>
    <p>Добро пожаловать в чат</p>
<#else>
    <p>Добро пожаловать</p>
    <p>Для того, чтобы присоединиться к чату, необходимо войти в систему</p>
</#if>

</@c.page>