<#include "security.ftlh">

<div>Список сообщений</div>
<div class="card-columns" id="message-list">
    <#list messages as message>
        <div class="card my-2">
            <#if message.filename??>
                <img class="card-img-top" src="/img/${message.filename}" alt="something"/>
            </#if>
            <div class="my-2">
                <span>${message.text}</span><br/>
                <i>#${message.tag}</i>
            </div>
            <div class="card-footer text-muted">
                <a href="/user-messages/${message.author.id}">${message.author.username}</a>
                <#if message.author.id == currentUserId>
                    <a class="btn btn-primary" href="/user-messages/${message.author.id}?message=${message.id}">
                        Изменить сообщение
                    </a>
                </#if>
            </div>
        </div>
    </#list>
</div>