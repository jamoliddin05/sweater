<#import "/parts/common.ftlh" as c>

<@c.page>
    <h1>Страница для редактирования данных ${username}</h1>
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
                    <input type="email" name="email" class="form-control" placeholder="${email!''}"/>
                </div>
            </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <div>
            <button class="btn btn-primary" type="submit">Изменить</button>
        </div>
    </form>
    ${message!}
</@c.page>