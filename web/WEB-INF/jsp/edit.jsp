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
            <c:if test="${type=='PERSONAL' || type=='OBJECTIVE'}">
                <P style="text-align: center"><textarea name="${type.name()}" cols=165 rows=3>${value}</textarea></P>
            </c:if>
            <c:if test="${type=='ACHIEVEMENT' || type=='QUALIFICATIONS'}">
                <P style="text-align: center"><textarea name="${type.name()}" cols=165
                rows=3>
                <c:forEach var="item" items="${value.items}">
                    ${item} <%="\n"%>
                </c:forEach>
                </textarea>
                </P>
            </c:if>
            <c:if test="${type=='EXPERIENCE' || type=='EDUCATION'}">
                <c:forEach var="org" items="${value.items}" varStatus="counter">
                    <P style="text-align: center">Название организации:
                        <textarea name="${type}" rows=1 cols=165>${org.homePage.name}</textarea></P>
                    <P style="text-align: center">Ссылка:
                        <textarea name="${type}url" rows=1 cols=165>${org.homePage.url}</textarea></P>
                    <c:if test="${org.positions.size() <= 1}">
                        <P style="text-align: center">Должность: </P><br/>
                    </c:if>
                    <c:if test="${org.positions.size() > 1}">
                        <P style="text-align: center">Должности: </P><br/>
                    </c:if>
                    <c:forEach var="pos" items="${org.positions}">
                        <jsp:useBean id="pos" type="com.basejava.webapp.model.Organization.Position"/>
                        От:
                        <input type="text" name="${type}dateStart${counter.index}" value="${pos.dateStart}"
                               size=8/><br/>
                        До:
                        <input type="text" name="${type}dateFinish${counter.index}" value="${pos.dateFinish}"
                               size=8/><br/>
                        Занимаемая должность:
                        <input type="text" name="${type}post${counter.index}" value="${pos.post}" size=50/><br/>
                        Описание:
                        <input type="text" name="${type}definition${counter.index}" value="${pos.definition}"
                               size=100/><br/><br/>
                    </c:forEach>
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
