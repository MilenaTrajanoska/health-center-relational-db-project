create table chovek(
  EMBG char(13),
  ime character varying not null,
  prezime character varying not null,
  constraint pk_chovek primary key (EMBG)
);

create table doktor(
   EMBG char(13),
   constraint pk_doktor primary key (EMBG),
   constraint fk_doktor_chovek foreign key (EMBG) references chovek(EMBG)
);

create table pacient(
    EMBG char(13),
    zdravstvena_legitimacija char(11) not null,
    broj_na_osigurenik char(9),   -- [0-9]{3}-[0-9]{4}-[0-9]{2}
    datum_ragjanje date not null,
    adresa character varying,
    EMBG_matichen_lekar char(13) not null ,
    constraint pk_pacient primary key (EMBG),
    constraint fk_pacient_chovek foreign key (EMBG) references chovek(EMBG),
    constraint fk_pacient_matichen_lekar foreign key (EMBG_matichen_lekar) references doktor(EMBG)
);

create type tip_pregled as enum ('redoven', 'kontrolen');
create type tip_izveshtaj as enum ('opsht', 'specijalisticki');

create table pregled(
    broj_na_pregled bigserial,
    tip tip_pregled not null,
    datum date not null ,
    vreme_pochetok time not null,
    vreme_kraj time,
    EMBG_pacient char(13) not null ,
    EMBG_doktor char(13) not null ,
    constraint pk_pregled primary key (broj_na_pregled),
    constraint fk_pregled_pacient foreign key (EMBG_pacient) references pacient(EMBG),
    constraint fk_pregled_doktor foreign key (EMBG_doktor) references doktor(EMBG)
);

create table upat(
    broj_na_upat bigserial,
    datum date not null,
    prichina character varying,
    opis character varying,
    broj_na_pregled bigint unique,
    EMBG_pacient char(13) not null ,
    EMBG_doktor_kreira char(13) not null ,
    EMBG_doktor_upaten_kon char(13) not null,
    constraint pk_upat primary key (broj_na_upat),
    constraint fk_upat_pregled foreign key (broj_na_pregled) references pregled(broj_na_pregled),
    constraint fk_upat_pacient foreign key (EMBG_pacient) references pacient(EMBG),
    constraint fk_upat_doktor_kreira foreign key (EMBG_doktor_kreira) references doktor(EMBG),
    constraint fk_upat_doktor_upaten_kon foreign key (EMBG_doktor_upaten_kon) references doktor(EMBG)
);

create table izveshtaj(
    broj_na_izveshtaj bigserial,
    broj_na_pregled bigint,
    tip tip_izveshtaj not null,
    datum date not null,
    anamneza character varying,
    naod character varying,
    constraint pk_izveshtaj primary key (broj_na_izveshtaj, broj_na_pregled),
    constraint fk_izveshtaj_pregled foreign key (broj_na_pregled) references pregled(broj_na_pregled)
);

create table kontakt(
    EMBG_pacient char(13),
    kontakt varchar(50),
    constraint pk_kontakt primary key (EMBG_pacient, kontakt),
    constraint fk_kontakt_pacient foreign key (EMBG_pacient) references pacient(EMBG)
);

create table specijalizacija(
    EMBG_doktor char(13),
    specijalizacija char(50),
    constraint pk_specijalizacija primary key (EMBG_doktor, specijalizacija),
    constraint fk_specijalizacija_doktor foreign key (EMBG_doktor) references doktor(EMBG)
);

create table dijagnoza(
    broj_na_izveshtaj bigint,
    broj_na_pregled bigint,
    dijagnoza char(100),
    constraint pk_dijagnoza primary key (broj_na_izveshtaj, broj_na_pregled, dijagnoza),
    constraint fk_dijagnoza_izveshtaj foreign key (broj_na_izveshtaj, broj_na_pregled) references izveshtaj(broj_na_izveshtaj, broj_na_pregled)
);

create table terapija(
    broj_na_izveshtaj bigint,
    broj_na_pregled bigint,
    terapija char(100),
    constraint pk_terapija primary key (broj_na_izveshtaj, broj_na_pregled, terapija),
    constraint fk_terapija_izveshtaj foreign key (broj_na_izveshtaj, broj_na_pregled) references izveshtaj(broj_na_izveshtaj, broj_na_pregled)
);

insert into chovek(embg, ime, prezime) VALUES ('1203996455123', 'Marija', 'Todoroska'),
                                              ('0211956450023', 'Trajko', 'Petkovski'),
                                              ('2607003450321', 'Marko', 'Stankovski'),
                                              ('1210994423123', 'Stefanija', 'Angelovska'),
                                              ('1111001431123', 'Andrej', 'Markovikj'),
                                              ('2112004455022', 'Eva', 'Popovska'),
                                              ('0502011450312', 'Petar', 'Tasev'),
                                              ('1203976455123', 'Angela', 'Gligorovska'),
                                              ('0707963450012', 'Dimitar', 'Dodevski'),
                                              ('1705973455333', 'Anja', 'Andovska'),
                                              ('2404968450003', 'Stefan', 'Kostadinov'),
                                              ('0301975455013', 'Ivana', 'Cvetkovska');

insert into doktor(embg) select EMBG from chovek where EMBG not like '____99%' and EMBG not like '____0%';

insert into pacient(embg, zdravstvena_legitimacija, broj_na_osigurenik, datum_ragjanje, adresa, embg_matichen_lekar) VALUES
('1203996455123', '133-1234-02', '032356789', '1996-03-12', null, '1203976455123'),
('2607003450321', '231-6889-23', '12377785', '2003-07-26', null, '0211956450023'),
('1210994423123', '432-5432-07', null, '1994-10-12', 'bul. Jane Sandanski 11-2/20', '0707963450012'),
('1111001431123', '231-3212-32', '000235123', '2001-11-11', 'ul. Sasa 45', '0301975455013'),
('2112004455022', '111-3264-01', '003213234', '2004-12-21', 'bul. Partizanski odredi 21/23', '2404968450003'),
('0502011450312', '326-7657-32', '006357834', '2011-02-05', null, '1705973455333');

insert into pregled(tip, datum, vreme_pochetok, vreme_kraj, EMBG_pacient, EMBG_doktor) VALUES
('redoven', '2019-03-21', '12:30', '13:15', '1203996455123', '1203976455123'),
('kontrolen', '2020-04-11', '15:00', null, '0502011450312', '0211956450023'),
('kontrolen', '2019-12-03', '09:35', '09:50', '1111001431123', '0301975455013'),
('redoven', '2020-07-11', '16:10', null, '2112004455022', '1705973455333'),
('redoven', '2020-10-12', '11:20', '11:50', '0502011450312', '1705973455333');

insert into upat(datum, prichina, opis, broj_na_pregled, EMBG_pacient, EMBG_doktor_kreira, EMBG_doktor_upaten_kon) values
('2020-01-21', null, null, 2, '0502011450312', '1705973455333', '0211956450023'),
('2020-03-04', 'pokachen pritisok', null, 3, '1111001431123', '0707963450012', '0301975455013'),
('2020-03-04', 'abdomenalni bolki', null, null, '2607003450321', '2404968450003', '1203976455123'),
('2019-10-23', null, null, null, '0502011450312', '0301975455013', '1705973455333'),
('2019-11-23', null, null, null, '1210994423123', '0211956450023', '1705973455333');

insert into izveshtaj(broj_na_pregled, tip, datum, anamneza, naod) values (1, 'opsht', '2019-03-21', null, null),
                                                                          (2, 'specijalisticki', '2020-04-11', null, null),
                                                                          (3, 'specijalisticki', '2019-12-03', null, null),
                                                                          (4, 'opsht', '2020-07-11', null, null),
                                                                          (5, 'opsht', '2020-10-12', null, null);

insert into kontakt(embg_pacient, kontakt) VALUES ('1203996455123', '078657231'),
                                                  ('2607003450321', 'marko_stankovski@domain.com'),
                                                  ('1210994423123', '070123321'),
                                                  ('1111001431123', '223305'),
                                                  ('2112004455022', '076123798'),
                                                  ('0502011450312', '071466789');

insert into specijalizacija(embg_doktor, specijalizacija) VALUES ('0211956450023', 'semejna medicina'),
('1203976455123', 'kardiologija'),
('0707963450012', 'nevrologija'),
('1705973455333', 'gastroenterologija'),
('2404968450003', 'generalna interna medicina'),
('0301975455013', 'hematologija');

insert into dijagnoza(broj_na_izveshtaj, broj_na_pregled, dijagnoza) values (1, 1, 'bronho-pneumonija'),
                                                                            (2, 2, 'lipo-proteinsko narushuvanje'),
                                                                            (3, 3, 'akuten apendicitis'),
                                                                            (4, 4, 'otvorena rana na glavata'),
                                                                            (5, 5, 'nastinka');

insert into terapija(broj_na_izveshtaj, broj_na_pregled, terapija) values (1, 1, 'amoxicillin'),
                                                                          (2, 2, 'preporaka za dieta'),
                                                                          (3, 3, 'cefotaxime'),
                                                                          (4, 4, '/'),
                                                                          (5, 5, '/');
