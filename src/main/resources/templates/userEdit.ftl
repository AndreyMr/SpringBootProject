<#import "parts/common.ftl" as c>
<@c.page>
<h5>User edit</h5>
<form action="/user" method="post" >
    <div class="form-group">
        <input type="text" name="username" value="${user.username}" class="form-control"/>
    </div>
    <#list roles as role>
    <div class="form-group">
        <div class="form-check">
            <input class="form-check-input"  type="checkbox" name="${role}" ${user.roles?seq_contains(role)?string("checked","")}/>${role}
        </div>

    </div>
</#list>
<div class="form-group">
    <button type="submit" class="btn btn-primary">Save</button>
</div>
<input type="hidden" name="userId" value="${user.id}"/>
<input type="hidden" name="_csrf" value="${_csrf.token}"/>
</form>
</@c.page>