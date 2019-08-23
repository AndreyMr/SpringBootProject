<#macro login path isRegisterForm>
<form action="${path}" method="post">
    <div class="form-group row">
        <label class="col-sm-2 col-form-label" for="inputEmail3"> User name :</label>
            <div class="col-sm-6">
            <input class="form-control" type="text" name="username" id="inputEmail3" placeholder="User name"/>
            </div>
    </div>

    <div class="form-group row">
        <label for="inputPassword3" class="col-sm-2 col-form-label"> Password: </label>
            <div class="col-sm-6">
            <input type="password" name="password" class="form-control" id="inputPassword3" placeholder="Password"/>
            </div>
    </div>

    <input type="hidden" name="_csrf" value="${_csrf.token}" >
    <div class="form-group row">
        <div class="col-sm-10">
            <button type="submit" class="btn btn-primary"><#if isRegisterForm>Create<#else>Sign in</#if></button>
        </div>
    </div>
</form>
<!--Если это не форма регистрации, то отображаем-->
<#if !isRegisterForm> <a href="/registration">Add new user</a> </#if>
</#macro>

<#macro logout>
<form action="/logout" method="post">
    <input type="hidden" name="_csrf" value="${_csrf.token}" >
    <button type="submit" class="btn btn-primary">Sign Out</button>
</form>
</#macro>