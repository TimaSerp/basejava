package com.basejava.webapp.util;

import com.basejava.webapp.model.Resume;
import com.basejava.webapp.model.AbstractSection;
import com.basejava.webapp.model.SimpleLineSection;
import org.junit.Assert;
import org.junit.Test;

import static com.basejava.webapp.ResumeTestData.RESUME_1;


public class JsonParserTest {
    @Test
    public void testResume() throws Exception {
        String json = JsonParser.write(RESUME_1);
        System.out.println(json);
        Resume resume = JsonParser.read(json, Resume.class);
        Assert.assertEquals(RESUME_1, resume);
    }

    @Test
    public void write() throws Exception {
        AbstractSection section1 = new SimpleLineSection("Objective1");
        String json = JsonParser.write(section1, AbstractSection.class);
        System.out.println(json);
        AbstractSection section2 = JsonParser.read(json, AbstractSection.class);
        Assert.assertEquals(section1, section2);
    }
}
