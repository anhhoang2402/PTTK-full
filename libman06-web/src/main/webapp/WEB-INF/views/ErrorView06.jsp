<%@ page isErrorPage="true" %>
<%@ include file="common/init.jspf" %>
<c:set var="pageTitle" value="Error"/>
<%@ include file="common/header.jspf" %>

<section class="card narrow">
    <h1>Something went wrong</h1>
    <p>${fn:escapeXml(pageContext.exception.message)}</p>
    <p class="hint">Check that MySQL is running, that database <code>libman06</code> exists
        (run <code>database/libman06.sql</code>) and that <code>db.properties</code> has the right password.</p>
    <a class="btn btn-primary" href="${ctx}/">Back to home</a>
</section>

<%@ include file="common/footer.jspf" %>
