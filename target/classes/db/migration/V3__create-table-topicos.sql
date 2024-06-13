
create table topicos(
    id bigint not null auto_increment,
    titulo varchar(200) not null,
    mensaje varchar(500) not null unique,
    fechaCreacion datetime not null,
    estado varchar(100) not null,
    autor_id bigint not null,
    respuesta_id bigint not null,

    primary key(id),

    constraint fk_topicos_respuesta_id foreign key(respuesta_id) references respuestas(id),
    constraint fk_topicos_autor_id foreign key(autor_id) references usuarios(id)
);