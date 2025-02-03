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
            // Verificar si la tabla tbl_usuarios tiene registros
            String checkRowCountQuery = "SELECT COUNT(*) FROM tbl_usuarios";
            Integer rowCountTbl_usuarios = jdbcTemplate.queryForObject(checkRowCountQuery, Integer.class);

            Integer rowCountTbl_roles = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM tbl_roles", Integer.class);

            if (rowCountTbl_usuarios == 0 && rowCountTbl_roles==0) {
                // Si la tabla está vacía, ejecutar el script para inicializar datos
                Resource dataResource = new ClassPathResource("data.sql");
                ScriptUtils.executeSqlScript(connection, dataResource);
                System.out.println("Inicializando datos de usuario por defecto.");
            } else {
                System.out.println("Las tablas tbl_usuarios y tbl_roles ya contiene datos. No se inicializan datos.");
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
