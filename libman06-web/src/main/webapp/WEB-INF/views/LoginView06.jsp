<%@ include file="common/init.jspf" %>
<c:set var="pageTitle" value="Staff login"/>
<%@ include file="common/header.jspf" %>

<section class="card narrow">
    <h1>Library staff login</h1>
    <form method="post" action="${ctx}/login" class="form-stack">
        <label>Username
            <input name="username" value="${fn:escapeXml(param.username)}" autofocus>
        </label>
        <label>Password
            <input type="password" name="password">
        </label>
        <button class="btn btn-primary" type="submit">Log in</button>
    </form>
    <p class="hint">Demo account: <code>hoangva</code> / <code>123456</code></p>
</section>

<%@ include file="common/footer.jspf" %>
