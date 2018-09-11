<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>
<@c.page>
<div>
    <@l.logout/>
</div>
<div>
<form method="post">
    <input type="text" name="name" placeholder="Введите название новой темы"/>
    <input type="text" name="description" placeholder="Введите описание"/>
    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    <button type="submit" VALUE="Добавить"></button>
</div>
<div>
Список доступных тем<br>
    <form method="post" action="/filter">
        <input type="text" name="filter">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button type="submit" VALUE="Найти"></button>
    </form>
    <#list topics as topic>
        <div>
<b>${topic.id}}</b>
<span>${topic.name}</span>
<i>${topic.description}</i>
        </div>
    <#else >
    No messages
    </#list>
   </@c.page>