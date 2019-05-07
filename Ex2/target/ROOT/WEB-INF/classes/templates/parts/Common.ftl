<#macro page>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Shared Resource</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/themes/smoothness/jquery-ui.css">
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>
</head>
<body>
<header class="container">
    <div class="menu">
        <ul >
            <li><a href="/">Главная</a></li>
            <li><a href="/main">График</a></li>

            <#if userAuth??>
                <li><a href="/login?logout">Выйти</a></li>
                <#list userAuth.roles as role >
                    <#if role == 'ADMIN'>
                        <li><a href="/user">Пользователи</a></li>
                    </#if>
                </#list>
            <#else>
                <li><a href="/login">Войти</a></li>
            </#if>

            <li><a href="/registration">Зарегистрироваться</a></li>
        </ul>
    </div>
</header>
<main class="container">
    <#nested>
</main>
<footer class="container"></footer>
</body>
</html>
</#macro>
