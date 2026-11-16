<%@ include file="common/init.jspf" %>
<c:set var="pageTitle" value="Return invoice"/>
<%@ include file="common/header.jspf" %>
<c:set var="invoice" value="${sessionScope.invoice}"/>

<ol class="steps">
    <li class="done">Search reader card</li>
    <li class="done">Select returned documents</li>
    <li class="current">Confirm invoice</li>
    <li>Payment slip</li>
</ol>

<section class="card">
    <h1>Return invoice</h1>
    <dl class="info-grid">
        <dt>Reader</dt><dd>${invoice.readerCard.reader.fullName} (${invoice.readerCard.reader.studentCode})</dd>
        <dt>Card code</dt><dd>${invoice.readerCard.cardCode}</dd>
        <dt>Staff</dt><dd>${invoice.staff.fullName}</dd>
        <dt>Return date</dt><dd>${invoice.returnDate.format(df)}</dd>
    </dl>

    <div class="table-wrap">
        <table class="data">
            <thead>
            <tr>
                <th>#</th>
                <th>Title</th>
                <th>Due date</th>
                <th class="num">Days late</th>
                <th class="num">Late fine</th>
                <th class="num">Damage fine</th>
                <th class="num">Fine</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="d" items="${invoice.listLoanDetail}" varStatus="st">
                <tr>
                    <td>${st.index + 1}</td>
                    <td>${d.document.title}</td>
                    <td>${d.dueDate.format(df)}</td>
                    <td class="num">${d.daysLate}</td>
                    <td class="num"><fmt:formatNumber value="${d.lateFine}" pattern="#,##0"/></td>
                    <td class="num"><fmt:formatNumber value="${d.damageFine}" pattern="#,##0"/></td>
                    <td class="num"><fmt:formatNumber value="${d.lateFine + d.damageFine}" pattern="#,##0"/></td>
                </tr>
            </c:forEach>
            </tbody>
            <tfoot>
            <tr>
                <td colspan="6" class="num">Total fine</td>
                <td class="num total"><fmt:formatNumber value="${invoice.totalFine}" pattern="#,##0"/> VND</td>
            </tr>
            </tfoot>
        </table>
    </div>

    <form method="post" action="${ctx}/staff/return-invoice" class="actions">
        <button class="btn btn-ghost" type="submit" name="action" value="cancel">Cancel</button>
        <button class="btn btn-primary" type="submit" name="action" value="confirm">Confirm</button>
    </form>
</section>

<%@ include file="common/footer.jspf" %>
