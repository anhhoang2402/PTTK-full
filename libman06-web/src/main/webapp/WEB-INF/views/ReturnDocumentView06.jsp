<%@ include file="common/init.jspf" %>
<c:set var="pageTitle" value="Select returned documents"/>
<%@ include file="common/header.jspf" %>
<c:set var="card" value="${sessionScope.card}"/>

<ol class="steps">
    <li class="done">Search reader card</li>
    <li class="current">Select returned documents</li>
    <li>Confirm invoice</li>
    <li>Payment slip</li>
</ol>

<section class="card">
    <div class="reader-head">
        <div>
            <h1>${card.reader.fullName}</h1>
            <p class="muted">${card.reader.studentCode} · ${card.reader.className} · ${card.reader.phone}</p>
        </div>
        <div class="reader-card-box">
            <span class="code-badge">${card.cardCode}</span>
            <small>${card.cardType} · valid until ${card.expiryDate.format(df)}</small>
        </div>
    </div>

    <h2>Borrowed documents</h2>
    <p class="muted">Tick the documents the reader brings back. Late fine is 2,000 VND per day after the due date.
        Enter a damage fine if a document is damaged.</p>

    <form method="post" action="${ctx}/staff/return-document" id="returnForm">
        <div class="table-wrap">
            <table class="data">
                <thead>
                <tr>
                    <th class="center">Return</th>
                    <th>#</th>
                    <th>Code</th>
                    <th>Title</th>
                    <th>Borrow date</th>
                    <th>Due date</th>
                    <th class="num">Days late</th>
                    <th class="num">Late fine</th>
                    <th class="num">Damage fine</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="d" items="${sessionScope.listLoanDetail}" varStatus="st">
                    <c:set var="picked" value="${sessionScope.selection[d.id]}"/>
                    <tr class="${d.daysLate > 0 ? 'late' : ''}">
                        <td class="center">
                            <input type="checkbox" name="selectedId" value="${d.id}" data-late="${d.lateFine}"
                                   ${not empty picked ? 'checked' : ''}>
                        </td>
                        <td>${st.index + 1}</td>
                        <td>${d.document.code}</td>
                        <td><strong>${d.document.title}</strong><br><small class="muted">${d.document.author}</small></td>
                        <td>${d.loanSlip.borrowDate.format(df)}</td>
                        <td>${d.dueDate.format(df)}</td>
                        <td class="num">${d.daysLate}</td>
                        <td class="num"><fmt:formatNumber value="${d.lateFine}" pattern="#,##0"/></td>
                        <td class="num">
                            <input class="money" type="number" min="0" step="1000" name="damageFine_${d.id}"
                                   value="<c:if test="${not empty picked and picked > 0}"><fmt:formatNumber value="${picked}" pattern="0"/></c:if>" placeholder="0">
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>

        <div class="summary-bar">
            <span>Selected: <strong id="selCount">0</strong> document(s)</span>
            <span>Estimated total fine: <strong id="selTotal">0</strong> VND</span>
            <div class="actions">
                <a class="btn btn-ghost" href="${ctx}/staff/search-card">Back</a>
                <button class="btn btn-primary" type="submit">Return</button>
            </div>
        </div>
    </form>
</section>

<script>
    (function () {
        const form = document.getElementById('returnForm');
        const fmt = n => n.toLocaleString('en-US');
        function update() {
            let count = 0, total = 0;
            form.querySelectorAll('input[name=selectedId]').forEach(cb => {
                if (!cb.checked) return;
                count++;
                const damage = form.querySelector('input[name=damageFine_' + cb.value + ']').value;
                total += parseFloat(cb.dataset.late) + (parseFloat(damage) || 0);
            });
            document.getElementById('selCount').textContent = count;
            document.getElementById('selTotal').textContent = fmt(total);
        }
        form.addEventListener('input', update);
        update();
    })();
</script>

<%@ include file="common/footer.jspf" %>
