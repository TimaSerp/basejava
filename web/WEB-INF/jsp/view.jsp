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
    <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"></a></h2>
    <p>
        <c:forEach var="contactEntry" items="${resume.contacts}">
            <jsp:useBean id="contactEntry"
                         type="java.util.Map.Entry<com.basejava.webapp.model.ContactType, java.lang.String>"/>
                <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
        </c:forEach>
        <c:forEach var="sectionEntry" items="${resume.sections}">
            <jsp:useBean id="sectionEntry"
                         type="java.util.Map.Entry<com.basejava.webapp.model.SectionType, com.basejava.webapp.model.AbstractSection>"/>
            <c:set var="type" value="<%=sectionEntry.getKey()%>"/>
            <c:set var="value" value="<%=sectionEntry.getValue()%>"/>
            <jsp:useBean id="value" type="com.basejava.webapp.model.AbstractSection"/>
        <c:if test="<%=value != null && value.toString().trim().length() > 0%>">
            <c:if test="${type=='PERSONAL' || type=='OBJECTIVE'}">
                <P style="text-align: center"><b>${type.title}</b>:</P>
                ${value}<br/>
            </c:if>
            <c:if test="${type=='ACHIEVEMENT' || type=='QUALIFICATIONS'}">
                <P style="text-align: center"><b>${type.title}</b>:</P>
                <ul>
                    <c:forEach var="item" items="${value.items}">
                        <li>${item}<br/></li>
                    </c:forEach>
                </ul>
            </c:if>
            <c:if test="${type=='EXPERIENCE' || type=='EDUCATION'}">
                <P style="text-align: center"><b>${type.title}</b>:</P>
                <c:forEach var="org" items="${value.items}">
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
        </c:if>
    </c:forEach>
    <p>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
