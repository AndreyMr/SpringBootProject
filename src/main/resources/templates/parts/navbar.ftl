<#include "security.ftl">
<#import "login.ftl" as l>

<nav class="navbar navbar-expand-lg navbar-light" style="background-color: #5bb8f2;">
    <a class="navbar-brand" href="/">Spring-boot Prj</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item ">
                <a class="nav-link" href="/">Home</a>
            </li>
            <li class="nav-item ">
                <a class="nav-link" href="/main">Messages</a>
            </li>
            <#if isAdmin>
            <li class="nav-item ">
                <a class="nav-link" href="/user">User List</a>
            </li>
            </#if>
            <!--user?? переменная user описывается только в том случае если пользователь авторизован в Spring Security
            см. шаблон security.ftl-->
             <#if user??>
              <li class="nav-item ">
              <a class="nav-link" href="/user/profile">Profile</a>
             </li>
    </#if>
    </ul>
    </div>
    <div class="navbar-text mr-2">${name}</div>
    <#if user??>
    <@l.logout />
</#if>
</nav>