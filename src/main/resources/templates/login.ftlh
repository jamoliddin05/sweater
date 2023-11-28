<#import "/parts/common.ftlh" as c>
<#import "/parts/login_file.ftlh" as l>

<@c.page>
    <h1>Страница для входа</h1>
    ${message!}
<@l.login "/login", "Войти", false />
</@c.page>