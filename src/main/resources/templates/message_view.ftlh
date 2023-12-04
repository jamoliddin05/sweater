<#import "/parts/common.ftlh" as c>

<@c.page>
    <div class="form-row">
        <div class="form-group col-md-4">
            <form method="get" action="/messages" class="form-inline">
                <input type="text" name="tag" class="form-control" value="${filter!}" placeholder="Поиск по тегу">
                <button type="submit" class="btn btn-primary ml-2">Найти</button>
            </form>
        </div>
    </div>

    <#include "parts/messageEdit.ftlh" />

    <#include "parts/messageList.ftlh" />
</@c.page>