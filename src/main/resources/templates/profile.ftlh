<#import "/parts/common.ftlh" as c>

<@c.page>
    <h5>Страница для редактирования данных ${username}</h5>
    ${message!}
    <form method="post">
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Пароль: </label>
            <div class="col-sm-6">
                <input type="password" name="password" class="form-control" placeholder="Пароль"/>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Email: </label>
            <div class="col-sm-6">
                <input type="email" name="email" class="form-control" value="${email!''}"/>
            </div>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <div>
            <button class="btn btn-primary" type="submit">Сохранить</button>
        </div>
    </form>
</@c.page>