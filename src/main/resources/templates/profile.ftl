<#import "parts/common.ftl" as c>

<@c.page>
<h4>Username: ${username}</h4>
<h5>Set you new password or email and press "Save" button</h5>
${message?ifExists}
<form method="post">

    <div class="form-group row">
        <label for="inputPassword" class="col-sm-2 col-form-label"> Password: </label>
        <div class="col-sm-6">
            <input type="password" name="password" class="form-control" id="inputPassword" placeholder="Password"/>
        </div>
    </div>

    <div class="form-group row">
        <label for="inputEmail" class="col-sm-2 col-form-label"> Email: </label>
        <div class="col-sm-6">
            <input type="email" name="email" class="form-control" id="inputEmail" placeholder="Email"/>
        </div>
    </div>

    <div class="form-group row">
        <div class="col-sm-10">
            <button type="submit" class="btn btn-primary">Save</button>
        </div>
    </div>

    <input type="hidden" name="_csrf" value="${_csrf.token}" >
</form>
</@c.page>