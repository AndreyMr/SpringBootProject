<#assign
<!--проверяем есть ли сессия в контексте Spring Security и приводим значение к булевой переменной-->
know = Session.SPRING_SECURITY_CONTEXT??
>
<!--если сессия существует-->
<#if know>
    <#assign
        user = Session.SPRING_SECURITY_CONTEXT.authentication.principal
        name = user.getUsername()
        isAdmin = user.isAdmin()
        currentUserId = user.getId()
    >
<#else>
    <#assign
        name = "Guest"
        isAdmin = false
        currentUserId = -1
    >
</#if>