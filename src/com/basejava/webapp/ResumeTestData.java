package com.basejava.webapp;

import com.basejava.webapp.model.*;

import java.time.LocalDate;
import java.util.ArrayList;

import static com.basejava.webapp.model.ContactType.*;
import static com.basejava.webapp.model.SectionType.*;

public class ResumeTestData {
    public static void main(String[] args) {
        fillResume("11223", "Григорий Кислин");
    }

    public static Resume fillResume(String uuid, String fullName) {
        Resume newResume = new Resume(uuid, fullName);

        newResume.addContact(PHONE_NUMBER, "+7(921) 855-0482");
        newResume.addContact(SKYPE, "grigory.newResume");
        newResume.addContact(EMAIL, "gnewResume@yandex.ru");
        newResume.addContact(LINKEDIN, "https://www.linkedin.com/in/gnewResume");
        newResume.addContact(GITHUB, "https://github.com/gnewResume");
        newResume.addContact(STACKOVERFLOW, "https://stackoverflow.com/users/548473");
        newResume.addContact(HOME_PAGE, "http://gnewResume.ru/");

        SimpleLineSection personal = new SimpleLineSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.");

        SimpleLineSection objective = new SimpleLineSection("Ведущий стажировок и корпоративного обучения по Java" +
                " Web и Enterprise технологиям");

        ArrayList<String> achievementList = new ArrayList<>();
        achievementList.add("Организация команды и успешная реализация Java проектов для сторонних заказчиков: приложения " +
                "автопарк на стеке Spring Cloud/микросервисы, система мониторинга показателей спортсменов на Spring " +
                "Boot, участие в проекте МЭШ на Play-2, многомодульный Spring Boot + Vaadin проект для комплексных DIY смет");
        achievementList.add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", " +
                "\"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное " +
                "взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 3500 выпускников.");
        achievementList.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. " +
                "Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        achievementList.add("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция " +
                "с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. " +
                "Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.");
        achievementList.add("Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, " +
                "Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.");
        achievementList.add("Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов " +
                "(SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии " +
                "через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга системы " +
                "по JMX (Jython/ Django).");
        achievementList.add("Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, " +
                "Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.");
        BulletedListSection achievement = new BulletedListSection(achievementList);

        ArrayList<String> qualificationsList = new ArrayList<>();
        qualificationsList.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        qualificationsList.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        qualificationsList.add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle, MySQL, SQLite, MS SQL, HSQLDB");
        qualificationsList.add("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy");
        qualificationsList.add("XML/XSD/XSLT, SQL, C/C++, Unix shell scripts");
        qualificationsList.add("Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring (MVC, " +
                "Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements).");
        qualificationsList.add("Python: Django.");
        qualificationsList.add("JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js");
        qualificationsList.add("Scala: SBT, Play2, Specs2, Anorm, Spray, Akka");
        qualificationsList.add("Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, StAX, SAX, DOM, " +
                "XSLT, MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, BPMN2, LDAP, OAuth1, OAuth2, JWT.");
        qualificationsList.add("Инструменты: Maven + plugin development, Gradle, настройка Ngnix");
        qualificationsList.add("администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, Flyway, Nagios, iReport, OpenCmis, Bonita, pgBouncer");
        qualificationsList.add("Отличное знание и опыт применения концепций ООП, SOA, шаблонов проектрирования, архитектурных шаблонов, UML, функционального программирования");
        qualificationsList.add("Родной русский, английский \"upper intermediate\"");
        BulletedListSection qualifications = new BulletedListSection(qualificationsList);

        ArrayList<Organization> experienceList = new ArrayList<>();
        experienceList.add(new Organization(LocalDate.of(2013, 05, 01), LocalDate.now(), "http://javaops.ru/", "Java Online Projects", "Автор проекта.",
                "Создание, организация и проведение Java онлайн проектов и стажировок."));
        experienceList.add(new Organization(LocalDate.of(2013, 05, 01), LocalDate.now(), "http://javaops.ru/", "Java Online Projects", "Автор проекта.",
                "Создание, организация и проведение Java онлайн проектов и стажировок."));
        experienceList.add(new Organization(LocalDate.of(2014, 10, 01), LocalDate.of(2016, 01, 01), "https://www.wrike.com/", "Wrike", "Старший разработчик (backend)",
                "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, " +
                        "MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO."));
        experienceList.add(new Organization(LocalDate.of(2012, 04, 01), LocalDate.of(2014, 10, 01), " ", "RIT Center", "Java архитектор",
                "Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. " +
                        "Архитектура БД и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование из " +
                        "браузера документов MS Office. Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python"));
        experienceList.add(new Organization(LocalDate.of(2010, 12, 01), LocalDate.of(2012, 04, 01), "http://www.luxoft.ru/", "Luxoft(Deutsche Bank)", "Ведущий программист",
                "Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC, SmartGWT, GWT, Jasper, " +
                        "Oracle). Реализация клиентской и серверной части CRM. Реализация RIA-приложения для администрирования, мониторинга " +
                        "и анализа результатов в области алгоритмического трейдинга. JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock, Commet, HTML5."));
        Experience experience = new Experience(experienceList);

        ArrayList<Organization> educationList = new ArrayList<>();
        educationList.add(new Organization(LocalDate.of(2013, 03, 01), LocalDate.of(2013, 05, 01), "https://www.coursera.org/course/progfun", "Coursera", "'Functional Programming Principles in Scala' by Martin Odersky", ""));
        educationList.add(new Organization(LocalDate.of(2011, 03, 01), LocalDate.of(2011, 04, 01), "http://www.luxoft-training.ru/training/catalog/course.html?ID=22366", "Luxoft", "Курс 'Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.'", ""));
        educationList.add(new Organization(LocalDate.of(2005, 01, 01), LocalDate.of(2005, 04, 01), "http://www.siemens.ru/", "Siemens AG", "3 месяца обучения мобильным IN сетям (Берлин)", ""));
        Experience education = new Experience(educationList);

        newResume.addSection(PERSONAL, personal);
        newResume.addSection(OBJECTIVE, objective);
        newResume.addSection(ACHIEVEMENT, achievement);
        newResume.addSection(QUALIFICATIONS, qualifications);
        newResume.addSection(EXPERIENCE, experience);
        newResume.addSection(EDUCATION, education);
        System.out.println(newResume.toString());
        return newResume;
    }
}
