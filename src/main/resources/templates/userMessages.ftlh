<#import "/parts/common.ftlh" as c>
<#include "/parts/security.ftlh">

<@c.page>
    <h3>${userChannel.username}</h3>
    <#if !isCurrentUser>
        <#if isSubscriber>
            <form method="post" action="/user/unsubscribe/${userChannel.id}">
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <button class="btn btn-info" type="submit">Отменить подписку</button>
            </form>
        <#else>
            <form method="post" action="/user/subscribe/${userChannel.id}">
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <button class="btn btn-info" type="submit">Подписаться</button>
            </form>
        </#if>
    </#if>
    <div class="my-3">
        <div class="row">
            <div class="col">
                <div class="card">
                    <div class="card-title">Подписки</div>
                    <h3 class="card-text">
                        <a href="/user/subscriptions/${userChannel.id}">${subscriptionsCount}</a>
                    </h3>
                </div>
            </div>
            <div class="col">
                <div class="card">
                    <div class="card-title">Подписчики</div>
                    <h3 class="card-text">
                        <a href="/user/subscribers/${userChannel.id}">${subscribersCount}</a>
                    </h3>
                </div>
            </div>
        </div>
    </div>
    <#if isCurrentUser>
        <#include "parts/messageEdit.ftlh" />
    </#if>

    <#include "parts/messageList.ftlh" />
</@c.page>