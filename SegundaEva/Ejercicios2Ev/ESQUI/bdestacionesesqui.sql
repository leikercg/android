CREATE TABLE esqui (_id integer primary key autoincrement, nombre text not null, cordillera text not null, n_remontes int not null, km_pistas float, fecha_ult_visita long, valoracion float, notas text);
INSERT INTO esqui VALUES(1,'Alto Campoo','Cantábrica',13,27,27/07/2011,6,'Para un fin de semana');
INSERT INTO esqui VALUES(2,'Formigal','Pirineo Aragonés',21,130,15/09/2012,10,'Para una buena semana');
INSERT INTO esqui VALUES(3,'Baqueira','Pirineo Catalán',37,140,06/01/2013,9,'Muy cara');
INSERT INTO esqui VALUES(4,'Navacerrada','Sistema Central',10,70,25/12/2008,5,'Mucha gente');
CREATE UNIQUE INDEX nombre ON esqui(nombre ASC);