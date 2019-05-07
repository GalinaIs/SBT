<#macro login path nameButton>
<form action="${path}" method="post">
    <div class="div_flex"><label> User Name : </label><input type="text" name="username" required/> </div>
    <div class="div_flex"><label> Password: </label><input type="password" name="password" required/> </div>
    <#nested>
    <!--<input type="hidden" name="_csrf" value="${_csrf.token}" />-->
    <div class="flex_button"><button type="submit">${nameButton}</button></div>
</form>
</#macro>