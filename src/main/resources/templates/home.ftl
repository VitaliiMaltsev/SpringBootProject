<#import "parts/common.ftl" as c>
<@c.page>
    <div>
        <h1>Hello, User</h1>

        <p>This is an unsecured page, but you can access the secured pages after authenticating.</p>
        <ul>
            <li>Go to the <a href="/user" >secured pages</a></li>
            <li>Please click <a href="/hello">here</a> to see a message</li>
            <li>Please click <a href="/main">here</a> to see a main page</li>
            <li>Please click <a href="/maif" >here</a> to see a main test page</li>
            <li>Please click <a href="/topics/java/courses" >here</a> to see all JAVA courses </li>
        </ul>
    </div>
</@c.page>


