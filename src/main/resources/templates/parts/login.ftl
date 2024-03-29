<#macro login path isRegisterForm>
<form action="${path}" method="post">
    <div class="form-group row">
        <label class="col-sm-2 col-form-label" for="inputUsername"> User name :</label>
            <div class="col-sm-6">
            <input class="form-control ${(usernameError??)?string('is-invalid', '')}" type="text" name="username"
                   id="inputUsername" placeholder="User name" value="<#if user??>${user.username}</#if>"/>
                <#if usernameError??>
                <div class="invalid-feedback">
                    ${usernameError}
                </div>
                </#if>
            </div>
    </div>

    <div class="form-group row">
        <label for="inputPassword" class="col-sm-2 col-form-label"> Password: </label>
            <div class="col-sm-6">
            <input type="password" name="password" class="form-control ${(passwordError??)?string('is-invalid', '')}"
                   id="inputPassword" placeholder="Password"/>
                <#if passwordError??>
                <div class="invalid-feedback">
                    ${passwordError}
                </div>
                </#if>
            </div>
    </div>



    <!--отображаем, если это форма регистрации-->
    <#if isRegisterForm>
    <div class="form-group row">
        <label for="inputPassword2" class="col-sm-2 col-form-label">Confirmation password:</label>
        <div class="col-sm-6">
            <input type="password" name="password2" class="form-control ${(password2Error??)?string('is-invalid', '')}"
                   id="inputPassword2" placeholder="Retype password"/>
            <#if password2Error??>
            <div class="invalid-feedback">
                ${password2Error}
            </div>
            </#if>
    </div>

    </div>
    <div class="form-group row">
        <label for="inputEmail" class="col-sm-2 col-form-label"> Email: </label>
        <div class="col-sm-6">
            <!--${email!''} если email не указан, то будет отображаться пустая строчка-->
            <input type="email" name="email" class="form-control ${(emailError??)?string('is-invalid', '')}"
                   id="inputEmail" value="<#if user??>${user.email}</#if>" placeholder="Enter email"/>
            <#if emailError??>
            <div class="invalid-feedback">
                ${emailError}
            </div>
        </#if>
        </div>
    </div>
    <div class="col-sm-6">
        <div class="g-recaptcha" data-sitekey="6LfCE7YUAAAAAPAH5DrQiluhRsaF9QdeS6pNgzpc"></div>
        <#if captchaError??>
        <div class="alert alert-danger" role="alert">
            ${captchaError}
        </div>
    </#if>
    </div>
</#if>
    <!---->
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