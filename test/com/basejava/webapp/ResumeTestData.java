package com.basejava.webapp;

import com.basejava.webapp.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

import static com.basejava.webapp.model.ContactType.*;
import static com.basejava.webapp.model.SectionType.*;

public class ResumeTestData {
    public static final String UUID_1 = String.valueOf(UUID.randomUUID());
    public static final String FULLNAME_1 = "name1";
    public static final Resume RESUME_1 = fillResume(UUID_1, FULLNAME_1);
//    private static final Resume RESUME_1 = new Resume(UUID_1, FULLNAME_1);

    public static final String UUID_2 = String.valueOf(UUID.randomUUID());
    public static final String FULLNAME_2 = "name2";
    public static final Resume RESUME_2 = fillResume(UUID_2, FULLNAME_2);
//    private static final Resume RESUME_2 = new Resume(UUID_2, FULLNAME_2);

    public static final String UUID_3 = String.valueOf(UUID.randomUUID());
    public static final String FULLNAME_3 = "name3";
    public static final Resume RESUME_3 = fillResume(UUID_3, FULLNAME_3);
//    private static final Resume RESUME_3 = new Resume(UUID_3, FULLNAME_3);

    public static final String UUID_4 = String.valueOf(UUID.randomUUID());
    public static final String FULLNAME_4 = "name4";
    public static final Resume RESUME_4 = fillResume(UUID_4, FULLNAME_4);
    //    private static final Resume RESUME_4 = new Resume(UUID_4, FULLNAME_4);

    public static void main(String[] args) {
        fillResume("11223", "Григорий Кислин");
    }

    public static Resume fillResume(String uuid, String fullName) {
        Resume newResume = new Resume(uuid, fullName);

        newResume.addContact(PHONE_NUMBER, "+7(921) 855-0482");
        newResume.addContact(SKYPE, "grigory.kislin");
        newResume.addContact(EMAIL, "gkislin@yandex.ru");
        newResume.addContact(LINKEDIN, "https://www.linkedin.com/in/gkislin");
        newResume.addContact(GITHUB, "https://github.com/gkislin");
        newResume.addContact(STACKOVERFLOW, "https://stackoverflow.com/users/548473");
        newResume.addContact(HOME_PAGE, "http://gkislin.ru/");

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
        BulletedListSection achievement = new BulletedListSection(achievementList);

        ArrayList<String> qualificationsList = new ArrayList<>();
        qualificationsList.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        qualificationsList.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        BulletedListSection qualifications = new BulletedListSection(qualificationsList);

        ArrayList<Organization> experienceList = new ArrayList<>();
        experienceList.add(new Organization(LocalDate.of(2013, 05, 01), LocalDate.now(), "http://javaops.ru/", "Java Online Projects", "Автор проекта.",
                "Создание, организация и проведение Java онлайн проектов и стажировок."));
        experienceList.add(new Organization(LocalDate.of(2013, 05, 01), LocalDate.now(), "http://javaops.ru/", "Java Online Projects", "Работник проекта.",
                "Создание, организация и проведение Java онлайн проектов и стажировок."));
        experienceList.add(new Organization(LocalDate.of(2014, 10, 01), LocalDate.of(2016, 01, 01), null, "Wrike", "Старший разработчик (backend)",
                "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, " +
                        "MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO."));
        Experience experience = new Experience(experienceList);

        ArrayList<Organization> educationList = new ArrayList<>();
        educationList.add(new Organization(LocalDate.of(2013, 03, 01), LocalDate.of(2013, 05, 01), "https://www.coursera.org/course/progfun", "Coursera", "'Functional Programming Principles in Scala' by Martin Odersky", ""));
        educationList.add(new Organization(LocalDate.of(2011, 03, 01), LocalDate.of(2011, 04, 01), "http://www.luxoft-training.ru/training/catalog/course.html?ID=22366", "Luxoft", "Курс 'Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.'", ""));
        Experience education = new Experience(educationList);

        newResume.putSection(PERSONAL, personal);
        newResume.putSection(OBJECTIVE, objective);
        newResume.putSection(ACHIEVEMENT, achievement);
        newResume.putSection(QUALIFICATIONS, qualifications);
        newResume.putSection(EXPERIENCE, experience);
        newResume.putSection(EDUCATION, education);
        return newResume;
    }
}
