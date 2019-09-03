<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>

<@c.page>
<div class="mb-1">
    <h4>Add new user</h4>
</div>
<#if message??>
<div class="alert alert-danger" role="alert">
    ${message}
</div>
</#if>
<@l.login "/registration"  true/>
</@c.page>