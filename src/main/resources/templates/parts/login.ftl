<#macro login path>
<form action="${path}" method="post">
    <div><label>Username: <input type="text" name="name"/></label></div>
    <div><label>Password: <input type="password" name="password"/></label></div>
    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    <div><input type="submit" VALUE="Log in"/></div>
</form>
</#macro>
<#macro logout>
    <form action="/logout" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <input type="submit" VALUE="Sign out"/>
    </form>
</#macro>