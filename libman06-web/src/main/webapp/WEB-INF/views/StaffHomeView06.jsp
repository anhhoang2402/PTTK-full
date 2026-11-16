<%@ include file="common/init.jspf" %>
<c:set var="pageTitle" value="Staff home"/>
<%@ include file="common/header.jspf" %>

<section class="hero">
    <h1>Hello, ${sessionScope.staff.fullName}</h1>
    <p>${sessionScope.staff.position}</p>
</section>

<section class="tiles">
    <a class="tile tile-main" href="${ctx}/staff/search-card">
        <span class="tile-icon">&#x1F4DA;</span>
        <strong>Receive and return documents</strong>
        <span>Scan the reader card, tick the returned documents and print the payment slip.</span>
    </a>
    <div class="tile tile-disabled" title="Not part of the two modules of this assignment">
        <span class="tile-icon">&#x1F4D6;</span>
        <strong>Lend documents</strong>
        <span>Not in these modules</span>
    </div>
    <div class="tile tile-disabled" title="Not part of the two modules of this assignment">
        <span class="tile-icon">&#x1F5C2;</span>
        <strong>Manage documents, readers, suppliers</strong>
        <span>Not in these modules</span>
    </div>
</section>

<%@ include file="common/footer.jspf" %>
