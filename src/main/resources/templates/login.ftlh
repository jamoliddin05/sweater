<#import "/parts/common.ftlh" as c>
<#import "/parts/login_file.ftlh" as l>

<@c.page>
    <h1>Страница для входа</h1>
    <#if Session?? && Session.SPRING_SECURITY_LAST_EXCEPTION??>
        <div class="alert alert-danger" role="alert">
            ${SPRING_SECURITY_LAST_EXCEPTION.message}
        </div>
    </#if>
<@l.login "/login", "Войти", false />
</@c.page>