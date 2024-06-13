
create table topicos(
    id bigint not null auto_increment,
    titulo varchar(200) not null,
    mensaje varchar(500) not null,
    fechacreacion datetime not null,
    estado tinyint,
    autor_id bigint not null,
    respuesta varchar(100) not null,

    primary key(id),

    constraint fk_topicos_autor_id foreign key(autor_id) references usuarios(id)
);