<#macro login path nameButton>
<form action="${path}" method="post">
    <div class="div_flex"><label> Имя пользователя : </label><input type="text" name="username" required/> </div>
    <input type="hidden" name="password" value="password"/>
    <#nested>
    <input type="hidden" name="_csrf" value="${_csrf.token}" />
    <div class="flex_button"><button type="submit">${nameButton}</button></div>
</form>
</#macro>