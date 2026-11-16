<%@ include file="common/init.jspf" %>
<c:set var="pageTitle" value="Search reader card"/>
<%@ include file="common/header.jspf" %>

<ol class="steps">
    <li class="current">Search reader card</li>
    <li>Select returned documents</li>
    <li>Confirm invoice</li>
    <li>Payment slip</li>
</ol>

<section class="card narrow">
    <h1>Receive and return documents</h1>
    <p class="muted">Scan the reader card with the barcode scanner, or type the card code.</p>
    <form method="post" action="${ctx}/staff/search-card" class="search-row">
        <input name="cardCode" value="${fn:escapeXml(param.cardCode)}" placeholder="RC000006" autofocus autocomplete="off">
        <button class="btn btn-primary" type="submit">Search</button>
    </form>
</section>

<%@ include file="common/footer.jspf" %>
