
create table usuarios(
    id bigint not null auto_increment,
    nombre varchar(200) not null,
    correo varchar(200) not null,
    contraseña varchar(300) not null,

    primary key(id)
);