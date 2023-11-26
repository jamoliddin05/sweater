<#import "/parts/common.ftlh" as c>
<#import "/parts/login_file.ftlh" as l>

<@c.page>
    <h1>Страница для входа</h1>
<@l.login "/login", "Войти" />
<a href="/registration">Зарегистрироваться</a>
</@c.page>