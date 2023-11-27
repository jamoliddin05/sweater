<#import "/parts/common.ftlh" as c>
<#import "/parts/login_file.ftlh" as l>

<@c.page>
    <div>
        <h3>Ваш юзернейм: ${user.username}</h3>
        <@l.logout />
        <span><a href="/user">Список пользователей</a></span>
    </div>
    <div>
        <form method="post" action="/messages">
            <input type="text" name="text" placeholder="Введите сообщение" />
            <input type="text" name="tag" placeholder="Тэг">
            <input type="hidden" name="_csrf" value="${_csrf.token}" />
            <button type="submit">Добавить</button>
        </form>
    </div>

    <form method="get" action="/messages">
        <input type="text" name="tag" value="${filter!}">
        <button type="submit">Найти</button>
    </form>

    <div>Список сообщений</div>
    <#list messages as message>
        <div>
            <b>${message.id}</b>
            <span>${message.text}</span>
            <i>${message.tag}</i>
            <strong>${message.author.username}</strong>
        </div>
    </#list>
</@c.page>