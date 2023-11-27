<#macro login path, button, isRegisterForm>
    <form method="post" action="${path}">
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Имя пользователя: </label>
            <div class="col-sm-6">
                <input type="text" name="username" class="form-control" placeholder="Имя"/>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Пароль: </label>
            <div class="col-sm-6">
                <input type="password" name="password" class="form-control" placeholder="Пароль"/>
            </div>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <#if !isRegisterForm><a href="/registration">Зарегистрироваться</a></#if>
        <div><button class="btn btn-primary" type="submit">${button}</button></div>
    </form>
</#macro>

<#macro logout>
    <form method="post" action="/logout">
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <button class="btn btn-primary" type="submit">Выйти</button>
    </form>
</#macro>