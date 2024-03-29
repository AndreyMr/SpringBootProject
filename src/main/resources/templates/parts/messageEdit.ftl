<a class="btn btn-primary" data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false" aria-controls="collapseExample">
    Message Edit
</a>
<div class="collapse <#if message??>show</#if>" id="collapseExample">
    <div class="form-group mt-3">
        <form method="post" enctype="multipart/form-data">
            <div class="form-group">
                <!--приводим поле textError к булевому значению, если textError существует то добавляется
                класс is-invalid, если нет то пустое значение -->
                <input type="text" class="form-control ${(messageTextError??)?string('is-invalid', '')}"
                       value="<#if message??>${message.messageText}</#if>" name="messageText" placeholder="Введите сообщение" />
                <#if messageTextError??>
                    <div class="invalid-feedback">
                         ${messageTextError}
                    </div>
                </#if>
            </div>

            <div class="form-group">
              <input type="text" name="tag" class="form-control ${(messageTag??)?string('is-invalid', '')}"
               value="<#if message??>${message.tag}</#if>" placeholder="Тег"/>
               <#if tagError??>
                  <div class="invalid-feedback">
                  ${tagError}
                  </div>
               </#if>
            </div>

            <div class="form-group">
              <div class="custom-file">
                <input type="file" class="form-control" name="file" class="custom-file-input" id="customFile"/>
                <label class="custom-file-label" for="customFile">Choose file</label>
              </div>
            </div>

            <div class="form-group">
                <button type="submit" class="btn btn-primary">Save Message</button>
            </div>

            <input type="hidden" name="_csrf" value="${_csrf.token}">
            <input type="hidden" name="id" value="<#if message??>${message.id}</#if>">
        </form>
    </div>
</div>