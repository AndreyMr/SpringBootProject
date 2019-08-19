<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>
<@c.page>
<div>
    <@l.logout />
    <div>
        <form method="post" action="add">
            <input type="text" name="text" placeholder="Введите сообщение"/>
            <input type="hidden" name="_csrf" value="${_csrf.token}" >
            <input type="text" name="tag" placeholder="Тег"/>
            <button type="submit">Добавить</button>
        </form>
    </div>
    <div> Список сообщений </div>
    <form method="get" action="/main">
        <input type="text" name="filter" placeholder="Введите тег" value="${filter?ifExists}">
        <button type="submit">Найти</button>
    </form>
    <#list messages as message>
    <div>
        <b>${message.id}</b>
        <span>${message.messageText}</span>
        <i>${message.tag}</i>
        <strong>${message.authorName}</strong>
    </div>
        <#else>
        No messages
    </#list>
</div>
</@c.page>