<#import "/parts/common.ftlh" as c>

<@c.page>
    <div class="form-row">
        <div class="form-group col-md-4">
            <form method="get" action="/messages" class="form-inline">
                <input type="text" name="tag" class="form-control" value="${filter!}" placeholder="Поиск по тегу">
                <button type="submit" class="btn btn-primary ml-2">Найти</button>
            </form>
        </div>
    </div>

    <a class="btn btn-primary" data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false"
       aria-controls="collapseExample">
        Добавить новое сообщение
    </a>
    <div class="collapse mt-2 mb-2" id="collapseExample">
        <div class="form-group">
            <form method="post" action="/messages" enctype="multipart/form-data">
                <input type="text" name="text" class="form-control mb-2" placeholder="Введите сообщение"/>
                <input type="text" name="tag" class="form-control mb-2" placeholder="Тэг"/>
                <div class="custom-file mb-2">
                    <input type="file" name="file" id="customFile"/>
                    <label class="custom-file-label" for="customFile">Выбрать файл</label>
                </div>
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <button type="submit" class="btn btn-primary">Добавить</button>
            </form>
        </div>
    </div>

    <div>Список сообщений</div>
    <div class="card-columns">
        <#list messages as message>
            <div class="card my-2">
                <#if message.filename??>
                    <img class="card-img-top" src="/img/${message.filename}" alt="something"/>
                </#if>
                <div class="my-2">
                    <span>${message.text}</span>
                    <i>${message.tag}</i>
                </div>
                <div class="card-footer text-muted">
                    <strong>${message.author.username}</strong>
                </div>
            </div>
        </#list>
    </div>
</@c.page>