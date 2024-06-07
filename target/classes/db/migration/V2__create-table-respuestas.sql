
create table respuestas(
    id bigint not null auto_increment,
    mensaje varchar(500) not null unique,
    fechaCreacion datetime not null,
    estado varchar(100) not null,
    autor_id bigint not null,
    respuesta varchar(100) not null,

    primary key(id),
    constraint fk_respuestas_autor_id foreign key(autor_id) references usuarios(id)
);