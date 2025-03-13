DROP SCHEMA IF EXISTS sistemamatriculacion;

CREATE SCHEMA IF NOT EXISTS sistemamatriculacion DEFAULT CHARACTER SET utf8 COLLATE utf8_spanish_ci;
USE sistemamatriculacion;

DROP USER sistemamatriculacion@'%';
CREATE USER sistemamatriculacion@'%' IDENTIFIED BY 'sistemamatriculacion-2024';
GRANT ALL PRIVILEGES ON sistemamatriculacion.* TO sistemamatriculacion;

-- tabla alumno
create table alumno(
	nombre varchar(50) not null,
	telefono VARCHAR(9) not null,
	correo VARCHAR(45) NOT NULL,
	dni char(9) not null,
    fechaNacimiento DATE not null,
    primary key(dni)
) engine = Innodb;

-- tabla CicloFormativo
create table cicloFormativo(
	codigo  int unsigned,
	familiaProfesional varchar(20) not null,
	grado enum('gradod','gradoe') not null,
    nombre varchar(30) not null,
	horas  int unsigned not null,
	nombreGrado varchar(30) not null,
	numAniosGrado int unsigned not null,
    modalidad enum ('presencial',"semipresencial"),
    numEdiciones int unsigned,
    primary key (codigo)
)engine = Innodb;

-- tabla Asignatura
CREATE TABLE asignatura (
  codigo char(4) NOT NULL,
  nombre varchar(30) NOT NULL,
  horasAnuales int unsigned NOT NULL,
  curso enum('primero','segundo') NOT NULL,
  horasDesdoble int unsigned NOT NULL,
  especialidadProfesorado enum('informatica','sistemas','fol') NOT NULL,
  codigoCicloFormativo int unsigned NOT NULL,
  PRIMARY KEY (codigo),
  foreign key (codigoCicloFormativo) REFERENCES cicloFormativo (codigo)
        on delete cascade
        on update cascade
) engine = Innodb;

-- Tabla Maricula
create table matricula(
    idMatricula  int unsigned,
    cursoAcademico char(9) not null,
    fechaMatriculacion DATE not null,
    fechaAnulacion DATE,
    dni char(9) not null,
	primary key(idMatricula),
    foreign key (dni) REFERENCES alumno (dni)
        on delete cascade
        on update cascade
)engine = Innodb;

-- Tabla AsignaturasMaricula
create table asignaturasMatricula(
    idMatricula  int unsigned,
    codigo char(4) not null,

	primary key(idMatricula,codigo),
    foreign key (idMatricula) REFERENCES matricula (idMatricula)
        on delete cascade
        on update cascade,
    foreign key (codigo) REFERENCES asignatura (codigo)
                on delete cascade
                on update cascade
)engine = Innodb;

commit;



