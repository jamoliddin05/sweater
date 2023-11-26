<#import "/parts/common.ftlh" as c>
<#import "/parts/login_file.ftlh" as l>

<@c.page>
    <h1>Страница для регистрации</h1>
    ${message}
<@l.login "/registration", "Добавить" />
</@c.page>