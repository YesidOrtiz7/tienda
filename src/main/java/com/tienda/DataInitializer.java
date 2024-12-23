package com.tienda;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import java.sql.Connection;

import org.springframework.stereotype.Component;

@Component
public class DataInitializer {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DataInitializer(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    public void initializeDatabase() {
        try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
            // Verificar si la tabla tbl_administradores tiene registros
            String checkRowCountQuery = "SELECT COUNT(*) FROM tbl_administradores";
            Integer rowCount = jdbcTemplate.queryForObject(checkRowCountQuery, Integer.class);

            if (rowCount == 0) {
                // Si la tabla está vacía, ejecutar el script para inicializar datos
                Resource dataResource = new ClassPathResource("data.sql");
                ScriptUtils.executeSqlScript(connection, dataResource);
                System.out.println("Datos inicializados desde data.sql porque la tabla estaba vacía.");
            } else {
                System.out.println("La tabla tbl_administradores ya contiene datos. No se inicializan datos.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
