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
<!---->
<a class="btn btn-primary" data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false" aria-controls="collapseExample">
    Add new message
</a>
<div class="collapse" id="collapseExample">
    <div class="form-group mt-3">
        <form method="post" action="add" enctype="multipart/form-data">
            <div class="form-group">
                <input type="text" name="text" class="form-control" placeholder="Введите сообщение"/>
            </div>
            <div class="form-group">
                <input type="text" name="tag" class="form-control" placeholder="Тег"/>
            </div>
            <div class="form-group">
                <div class="custom-file">
                    <input type="file" class="form-control" name="file" class="custom-file-input" id="customFile"/>
                    <label class="custom-file-label" for="customFile">Choose file</label>
                </div>
            </div>
            <div class="form-group">
                <input type="hidden" name="_csrf" value="${_csrf.token}">
                <button type="submit" class="btn btn-primary">Add</button>
            </div>
        </form>
    </div>
</div>
<div class="card-columns">
<#list messages as message>
    <div class="card my-3">
        <div class="card-body">
            <div class="m-2">
            <h5 class="card-title">${message.tag}</h5>
            <p class="card-text">${message.messageText}</p>
            </div>
            <div class="card-footer text-muted">
                ${message.authorName}
            </div>
        </div>
        <#if message.filename??>
        <img src="/img/${message.filename}" class="card-img-top">
        </#if>
    </div>
<#else>
No messages
</#list>
</div>

</@c.page>