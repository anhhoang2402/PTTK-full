<%@ include file="common/init.jspf" %>
<c:set var="pageTitle" value="Payment slip"/>
<%@ include file="common/header.jspf" %>

<ol class="steps no-print">
    <li class="done">Search reader card</li>
    <li class="done">Select returned documents</li>
    <li class="done">Confirm invoice</li>
    <li class="current">Payment slip</li>
</ol>

<div class="alert alert-success no-print">Return successfully. Give the payment slip to the reader.</div>

<section class="slip">
    <header class="slip-head">
        <div>
            <strong>LIBMAN06 · UNIVERSITY LIBRARY</strong>
            <small>Return documents desk</small>
        </div>
        <div class="slip-no">PAYMENT SLIP<br><span>No. ${invoice.id}</span></div>
    </header>

    <dl class="info-grid">
        <dt>Reader</dt><dd>${invoice.readerCard.reader.fullName}</dd>
        <dt>Student code</dt><dd>${invoice.readerCard.reader.studentCode}</dd>
        <dt>Card code</dt><dd>${invoice.readerCard.cardCode}</dd>
        <dt>Return date</dt><dd>${invoice.returnDate.format(df)}</dd>
    </dl>

    <table class="data">
        <thead>
        <tr><th>#</th><th>Returned document</th><th class="num">Late fine</th><th class="num">Damage fine</th></tr>
        </thead>
        <tbody>
        <c:forEach var="d" items="${invoice.listLoanDetail}" varStatus="st">
            <tr>
                <td>${st.index + 1}</td>
                <td>${d.document.title}</td>
                <td class="num"><fmt:formatNumber value="${d.lateFine}" pattern="#,##0"/></td>
                <td class="num"><fmt:formatNumber value="${d.damageFine}" pattern="#,##0"/></td>
            </tr>
        </c:forEach>
        </tbody>
        <tfoot>
        <tr>
            <td colspan="3" class="num">TOTAL TO PAY</td>
            <td class="num total"><fmt:formatNumber value="${invoice.totalFine}" pattern="#,##0"/> VND</td>
        </tr>
        </tfoot>
    </table>

    <div class="signatures">
        <div>Reader<br><small>(sign)</small></div>
        <div>Staff<br><small>${invoice.staff.fullName}</small></div>
    </div>
</section>

<div class="actions no-print">
    <a class="btn btn-ghost" href="${ctx}/staff/home">Back to staff home</a>
    <button class="btn btn-primary" type="button" onclick="window.print()">Print payment slip</button>
</div>
<script>window.addEventListener('load', () => window.print());</script>

<%@ include file="common/footer.jspf" %>
