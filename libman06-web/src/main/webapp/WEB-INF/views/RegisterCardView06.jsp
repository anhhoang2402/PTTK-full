<%@ include file="common/init.jspf" %>
<c:set var="pageTitle" value="Register reader card"/>
<%@ include file="common/header.jspf" %>

<c:choose>
    <c:when test="${not empty card}">
        <section class="card success-card">
            <div class="success-icon">&#x2713;</div>
            <h1>Register successfully</h1>
            <p class="lead">Your reader card code is <span class="code-badge">${card.cardCode}</span></p>
            <dl class="info-grid">
                <dt>Full name</dt><dd>${card.reader.fullName}</dd>
                <dt>Student code</dt><dd>${card.reader.studentCode}</dd>
                <dt>Class</dt><dd>${card.reader.className}</dd>
                <dt>Card type</dt><dd>${card.cardType}</dd>
                <dt>Issue date</dt><dd>${card.issueDate.format(df)}</dd>
                <dt>Expiry date</dt><dd>${card.expiryDate.format(df)}</dd>
            </dl>
            <a class="btn btn-primary" href="${ctx}/">Back to home</a>
        </section>
    </c:when>
    <c:otherwise>
        <section class="card">
            <h1>Register reader card</h1>
            <p class="muted">Fields marked * are required.</p>
            <form method="post" action="${ctx}/register-card" class="form-grid">
                <h2 class="form-section">Reader information</h2>
                <label>Full name *
                    <input name="fullName" value="${fn:escapeXml(param.fullName)}" placeholder="Nguyen Van A">
                </label>
                <label>Date of birth *
                    <input type="date" name="dateOfBirth" value="${fn:escapeXml(param.dateOfBirth)}">
                </label>
                <label>Gender *
                    <select name="gender">
                        <option value="Male" ${param.gender == 'Male' ? 'selected' : ''}>Male</option>
                        <option value="Female" ${param.gender == 'Female' ? 'selected' : ''}>Female</option>
                    </select>
                </label>
                <label>Student code *
                    <input name="studentCode" value="${fn:escapeXml(param.studentCode)}" placeholder="B21DCCN007">
                </label>
                <label>Class *
                    <input name="className" value="${fn:escapeXml(param.className)}" placeholder="D21CNPM06">
                </label>
                <label>Phone *
                    <input name="phone" value="${fn:escapeXml(param.phone)}" placeholder="0912345607">
                </label>
                <label class="span-2">Email *
                    <input name="email" value="${fn:escapeXml(param.email)}" placeholder="name@stu.edu.vn">
                </label>
                <label class="span-2">Address
                    <input name="address" value="${fn:escapeXml(param.address)}" placeholder="Ha Dong, Ha Noi">
                </label>

                <h2 class="form-section">Card information</h2>
                <label>Card type *
                    <select name="cardType">
                        <option value="Student" ${param.cardType == 'Student' ? 'selected' : ''}>Student</option>
                        <option value="Postgraduate" ${param.cardType == 'Postgraduate' ? 'selected' : ''}>Postgraduate</option>
                    </select>
                </label>
                <p class="hint">The card is issued today and is valid for 4 years.</p>

                <div class="actions span-2">
                    <a class="btn btn-ghost" href="${ctx}/">Cancel</a>
                    <button class="btn btn-primary" type="submit">Save</button>
                </div>
            </form>
        </section>
    </c:otherwise>
</c:choose>

<%@ include file="common/footer.jspf" %>
