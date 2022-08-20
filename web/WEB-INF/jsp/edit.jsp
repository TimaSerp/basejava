<%@ page import="com.basejava.webapp.model.ContactType" %>
<%@ page import="com.basejava.webapp.model.SectionType" %>
<%@ page import="com.basejava.webapp.model.BulletedListSection" %>
<%@ page import="com.basejava.webapp.model.Experience" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="com.basejava.webapp.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя:</dt>
            <dd><input type="text" name="fullName" size=50 value="${resume.fullName}"></dd>
        </dl>
        <h3>Контакты:</h3>
        <c:forEach var="contactType" items="<%=ContactType.values()%>">
            <dl>
                <dt>${contactType.title}</dt>
                <dd><input type="text" name="${contactType.name()}" size=30 value="${resume.getContact(contactType)}">
                </dd>
            </dl>
        </c:forEach>
        <h3>Секции:</h3>
        <c:forEach var="type" items="<%=SectionType.values()%>">
            <P style="text-align: center"><b>${type.title}</b>:</P>
            <c:set var="value" value="${resume.getSection(type)}" />
            <jsp:useBean id="value" type="com.basejava.webapp.model.AbstractSection"/>
            <c:if test="${type=='PERSONAL' || type=='OBJECTIVE'}">
                <P style="text-align: center"><textarea name="${type.name()}" cols=165 rows=3>${value}</textarea></P>
            </c:if>
            <c:if test="${type=='ACHIEVEMENT' || type=='QUALIFICATIONS'}">
                <P style="text-align: center"><textarea name="${type.name()}" cols=165
                                                        rows=3><%=String.join("\n", ((BulletedListSection) value).getItems())%></textarea>
                </P>
            </c:if>
            <c:if test="${type=='EXPERIENCE' || type=='EDUCATION'}">
                <c:forEach var="org" items="<%=((Experience) value).getOrganizations()%>">
                    <c:if test="${empty org.homePage.url}">
                        ${org.homePage.name}<br/>
                    </c:if>
                    <c:if test="${!empty org.homePage.url}">
                        <a href="${org.homePage.url}">${org.homePage.name}</a><br/>
                    </c:if>
                    <ul>
                        <c:forEach var="pos" items="${org.positions}">
                            <li>${pos.dateStart} - ${pos.dateFinish}<br/>
                                    ${pos.post}<br/>
                                    ${pos.definition}</li>
                            <br/>
                        </c:forEach>
                    </ul>
                </c:forEach>
            </c:if>
        </c:forEach>
        <hr>
        <button type="submit">Сохранить</button>
        <button type="reset" onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
