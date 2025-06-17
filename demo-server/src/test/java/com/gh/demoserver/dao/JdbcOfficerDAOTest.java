package com.gh.demoserver.dao;

import com.gh.demoserver.entities.Officer;
import com.gh.demoserver.entities.Rank;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class JdbcOfficerDAOTest {
    @Autowired
    JdbcOfficerDAO dao;

    @Autowired
    private JdbcTemplate template;

    // Retrieves the current list of IDs from the officers table
    private List<Integer> ids() {
        return template.query("SELECT id FROM officers",
        (rs, num) -> rs.getInt("id"));
    }

    @Test
    void save() {
        Officer officer = new Officer(null, Rank.ENSIGN, "Wesley", "Crusher");
        officer = dao.save(officer);
        assertNotNull(officer.getId());
    }

    @Test
    void findByIdThatExits() {
        ids().forEach(id -> {
            Optional<Officer> officer = dao.findById(id);
            assertTrue(officer.isPresent());
            assertEquals(officer.get().getId(), id);
        });
    }

    @Test
    void findByIdThatDoesNotExits() {
        assertThat(ids()).doesNotContain(999);
        Optional<Officer> officer = dao.findById(999);
        assertFalse(officer.isPresent());
    }

    @Test
    void count() {
        assertEquals(ids().size(), dao.count());
    }

    @Test
    void findAll() {
        List<String> officerLastNames = dao.findAll()
                .stream()
                .map(Officer::getLastName)
                .toList();
        assertThat(officerLastNames).containsAll(List.of(
                "Archer", "Burnham", "Freeman", "Janeway",
                "Kirk", "Picard", "Pike", "Sisko"));
    }


    @Test
    void delete() {
        ids().forEach(id -> {
            Optional<Officer> officer = dao.findById(id);
            assertTrue(officer.isPresent());
            dao.delete(officer.get());
        });
        assertEquals(0, dao.count());
    }

    @Test
    void existsById() {
        ids().forEach(id -> {assertTrue(dao.existsById(id));});
    }
}