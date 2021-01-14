-- FORMI

-- kreiranje na upat spored specijalizacija na doktor
begin;
insert into upat (broj_na_upat, datum, prichina, opis, broj_na_pregled, embg_pacient, embg_doktor_kreira, embg_doktor_upaten_kon)
values (24, (now()::date+5), 'visok krven pritisok', 'pokachen krven pritisok vo poslednata nedela', null,'0502011450312',
        (select embg_matichen_lekar from pacient where embg='0502011450312'),
        (select max(embg_doktor) from specijalizacija where specijalizacija.specijalizacija='kardiologija'));
insert into pregled(broj_na_pregled, tip, datum, vreme_pochetok, vreme_kraj, embg_pacient, embg_doktor)
values (12, 'kontrolen', now()::date + 5, '13:30', null, '0502011450312',
        (select embg_doktor_upaten_kon from upat where broj_na_upat=24));
commit;

--kreiranje izveshtaj za pregled so vnesuvanje dijagnoza
begin;
insert into izveshtaj (broj_na_izveshtaj, broj_na_pregled, tip, datum, anamneza, naod)
values (13, 13,'opsht', now()::date, 'anamneza' , 'naod');
insert into dijagnoza(broj_na_izveshtaj, broj_na_pregled, dijagnoza) values (13, 13, 'sinusitis');
--insert into dijagnoza(broj_na_izveshtaj, broj_na_pregled, dijagnoza) values (13, 13, 'artritis');
insert into terapija(broj_na_izveshtaj, broj_na_pregled, terapija) values (13, 13, 'Nazalni kortikosteroidi');
commit;

--odrzhuvanje na pregled spored upat
begin;
update pregled set vreme_kraj=now()::time where broj_na_pregled=11;
update upat set broj_na_pregled=11 where broj_na_upat = 20;
commit;
         
--zakazuvanje pregled
begin;
insert into pregled(broj_na_pregled, tip, datum, vreme_pochetok, vreme_kraj, embg_pacient, embg_doktor)
values (13, 'kontrolen', now()::date + 5, '15:30', null, '0502011450312',
        (select embg_doktor_upaten_kon from upat where broj_na_upat=24));
commit;

-- POGLEDI
create view dijagnozi_za_pacienti as
select row_number() over (order by p.datum desc) as id, c.ime || ' ' || c.prezime as ime_prezime, zdravstvena_legitimacija, p.broj_na_pregled, i.datum,
       i.tip as tip_izveshtaj, dijagnoza.dijagnoza, terapija
from dijagnoza
join izveshtaj i on i.broj_na_izveshtaj = dijagnoza.broj_na_izveshtaj
join pregled p on dijagnoza.broj_na_pregled = p.broj_na_pregled
join pacient pac on pac.embg = p.embg_pacient
join chovek c on c.embg = pac.embg
join terapija t on t.broj_na_izveshtaj = i.broj_na_izveshtaj and t.broj_na_pregled=p.broj_na_pregled
order by i.datum desc;

select row_number() over (order by p.datum desc) as id, c.ime || ' ' || c.prezime as ime_prezime, zdravstvena_legitimacija, p.broj_na_pregled, p.datum,
       i.tip as tip_izveshtaj, d.dijagnoza, t.terapija
from pregled as p
join izveshtaj as i on p.broj_na_pregled = i.broj_na_pregled
join dijagnoza as d on d.broj_na_pregled=p.broj_na_pregled and i.broj_na_izveshtaj=d.broj_na_izveshtaj
join pacient pac on pac.embg = p.embg_pacient
join chovek c on c.embg = pac.embg
join terapija as t on t.broj_na_izveshtaj=i.broj_na_izveshtaj and t.broj_na_pregled=p.broj_na_pregled;

create view pregledi_za_tekovniot_den as
select row_number() over (order by vreme_pochetok, vreme_kraj) as id, pregled.broj_na_pregled, pregled.tip as tip_na_pregled, pregled.datum, pregled.vreme_pochetok, pregled.vreme_kraj,
        zdravstvena_legitimacija, c2.ime || ' ' || c2.prezime as ime_pacient,
        c.ime || ' '|| c.prezime as ime_doktor
from pregled
join doktor d on d.embg = pregled.embg_doktor
join chovek c on c.embg = d.embg
join pacient p on p.embg = pregled.embg_pacient
join chovek c2 on c2.embg = p.embg
where datum=now()::date
order by vreme_pochetok, vreme_kraj;
