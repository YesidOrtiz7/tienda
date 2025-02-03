INSERT INTO tbl_roles(id,nombre) VALUES (1,"ADMIN"),(2,"USUARIO");

INSERT INTO tbl_usuarios(id_usuario, documento, nombres, apellidos, contrasena, totp_secret, bloqueado,  correo, eliminado, telefono)
VALUES (1, '202411', 'admin', 'admin', '$2y$10$g/cIAoYgYM67F0tmaEkVW.j0XFyUEDrfSLzSQQQmJdq/uXJzEpwAS', 'UEC3MJBRFYDG3YCR', false, 'admin@adminTienda.com', false, "+57 9662278411");

INSERT INTO tbl_usuario_rol(rol_id, usuario_id) VALUES (1,1);