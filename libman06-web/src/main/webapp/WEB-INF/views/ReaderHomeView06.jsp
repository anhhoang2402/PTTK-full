<%@ include file="common/init.jspf" %>
<c:set var="pageTitle" value="Home"/>
<%@ include file="common/header.jspf" %>

<section class="hero">
    <h1>Welcome to the University Library</h1>
    <p>Register your reader card online, then borrow books, textbooks and journals at the library desk.</p>
</section>

<section class="tiles">
    <a class="tile tile-main" href="${ctx}/register-card">
        <span class="tile-icon">&#x1F4C7;</span>
        <strong>Register reader card</strong>
        <span>Fill in your student information and get your card code at once.</span>
    </a>
    <a class="tile" href="${ctx}/login">
        <span class="tile-icon">&#x1F511;</span>
        <strong>Library staff</strong>
        <span>Log in to receive returned documents from readers.</span>
    </a>
</section>

<%@ include file="common/footer.jspf" %>
