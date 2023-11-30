<#import "/parts/common.ftlh" as c>

<@c.page>
    <h1>Список пользователей</h1>
    <table border="5">
        <thead>
        <tr>
            <th>Имя</th>
            <th>Роль</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <#list users as user>
            <tr>
                <td>${user.username}</td>
                <td><#list user.roles as role>${role}<#sep>, </#list></td>
                <td><a href="/user/${user.id}">изменить</a></td>
                <td><form method="post" action="/user/delete/${user.id}">
                        <input type="hidden" name="_csrf" value="${_csrf.token}">
                        <button type="submit">Удалить</button>
                    </form></td>
            </tr>
        </#list>
        </tbody>
    </table>
</@c.page>