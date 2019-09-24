<#import "parts/common.ftl" as c>
<@c.page>
<div class="form-group row">
    <div class="form-group col-md-6">
        <form method="get" action="/main" class="form-inline">
            <input type="text" name="filter" placeholder="Enter tag" value="${filter?ifExists}" class="form-control">
            <button type="submit" class="btn btn-primary ml-3">Search</button>
        </form>
    </div>
</div>
<!--вставляем форму редактирования-->
<#include "parts/messageEdit.ftl">
<!--вставляем сообщения-->
<#include "parts/messageList.ftl">
</@c.page>