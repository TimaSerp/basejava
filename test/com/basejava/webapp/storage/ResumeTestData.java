package com.basejava.webapp.storage;

import com.basejava.webapp.model.BulletedListSection;
import com.basejava.webapp.model.Resume;
import com.basejava.webapp.model.SimpleLineSection;

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
//
//        ArrayList<Organization> experienceList = new ArrayList<>();
//        experienceList.add(new Organization(LocalDate.of(2013, 05, 01), LocalDate.now(), "http://javaops.ru/", "Java Online Projects", "Автор проекта.",
//                "Создание, организация и проведение Java онлайн проектов и стажировок."));
//        experienceList.add(new Organization(LocalDate.of(2013, 05, 01), LocalDate.now(), "http://javaops.ru/", "Java Online Projects", "Работник проекта.",
//                "Создание, организация и проведение Java онлайн проектов и стажировок."));
//        experienceList.add(new Organization(LocalDate.of(2014, 10, 01), LocalDate.of(2016, 01, 01), null, "Wrike", "Старший разработчик (backend)",
//                "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, " +
//                        "MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO."));
//        Experience experience = new Experience(experienceList);
//
//        ArrayList<Organization> educationList = new ArrayList<>();
//        educationList.add(new Organization(LocalDate.of(2013, 03, 01), LocalDate.of(2013, 05, 01), "https://www.coursera.org/course/progfun", "Coursera", "'Functional Programming Principles in Scala' by Martin Odersky", ""));
//        educationList.add(new Organization(LocalDate.of(2011, 03, 01), LocalDate.of(2011, 04, 01), "http://www.luxoft-training.ru/training/catalog/course.html?ID=22366", "Luxoft", "Курс 'Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.'", ""));
//        Experience education = new Experience(educationList);
//
        newResume.addSection(PERSONAL, personal);
        newResume.addSection(OBJECTIVE, objective);
        newResume.addSection(ACHIEVEMENT, achievement);
        newResume.addSection(QUALIFICATIONS, qualifications);
//        newResume.addSection(EXPERIENCE, experience);
//        newResume.addSection(EDUCATION, education);
        return newResume;
    }
}
