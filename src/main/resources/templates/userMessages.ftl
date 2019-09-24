<#import "parts/common.ftl" as c>
<@c.page>

<#if isCurrentUser>
    <!--вставляем форму редактирования-->
    <#include "parts/messageEdit.ftl">
</#if>

<!--вставляем сообщения-->
<#include "parts/messageList.ftl">

</@c.page>