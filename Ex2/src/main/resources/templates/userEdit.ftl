<#import "parts/common.ftl" as c>

<@c.page>
<h3>Редактирование пользователя</h3>
<form action="/user" method="post">
    <div class="flex_button">
        <input type="text" value="${user.username}" name="username" class="wide_input">
    </div>
    <#list roles as role>
        <div>
            <input type="checkbox" name="${role}" ${user.roles?seq_contains(role)?string("checked", "")}><label>${role}</label>
        </div>
    </#list>
    <input type="hidden" value="${user.id}" name="userId">
    <input type="hidden" name="_csrf" value="${_csrf.token}" />
    <div class="flex_button">
        <button type="submit">Сохранить</button>
    </div>
</form>
</@c.page>