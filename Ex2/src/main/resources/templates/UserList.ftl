<#import "parts/common.ftl" as c>

<@c.page>
<h3>Список пользователей</h3>
<table class="table_info">
    <tr>
        <th>Имя</th>
        <th>Роль</th>
        <th>Действие</th>
    </tr>
<#list users as user>
    <tr>
        <td>${user.username}</td>
        <td>
            <#list user.roles as role>
                ${role} <#sep>,
            </#list>
        </td>
        <td>
            <a href="/user/${user.id}">Редактировать</a>
        </td>
    </tr>
</#list>
</table>
</@c.page>