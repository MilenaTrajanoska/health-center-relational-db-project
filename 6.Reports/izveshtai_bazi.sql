-- BROJ NA PACIENTI GRUPIRANI PO GODINI KOI BILE DIJAGNOSTICIRANI SO ODREDENA DIJAGNOZA
with dijagnozi_vozrast as(select dijagnoza.dijagnoza, ((now()::date - p.datum_ragjanje)/365)::int as vozrast, count(distinct c.embg) as broj_pacienti
from izveshtaj
inner join dijagnoza on izveshtaj.broj_na_izveshtaj = dijagnoza.broj_na_izveshtaj
inner join pregled on izveshtaj.broj_na_pregled = pregled.broj_na_pregled
inner join pacient p on p.embg = pregled.embg_pacient
inner join chovek c on c.embg = p.embg
group by dijagnoza.dijagnoza, vozrast
order by dijagnoza)
select d.dijagnoza, d.vozrast, d.broj_pacienti,
        d.broj_pacienti::double precision / (select sum(v.broj_pacienti)
       from dijagnozi_vozrast
           as v where v.vozrast = d.vozrast)*100 as procent_po_vozrast,
      d.broj_pacienti::double precision/count(distinct p.embg) *100 as vkupen_procent_pacienti
from dijagnozi_vozrast as d, dijagnozi_vozrast as s, pacient as p
group by d.dijagnoza, d.vozrast, d.broj_pacienti
order by vkupen_procent_pacienti, procent_po_vozrast desc;

--PAROVI NA DIJAGNOZI KOI SE POJAVUVALE ZAEDNO I BROJ NA PACIENTI KOI GI IMALE
with dijagnozi_grupirani as (select d1.broj_na_pregled as br_pregled_1, d1.dijagnoza as dijagnoza_1,
                                    d2.broj_na_pregled as br_pregled_2, d2.dijagnoza as dijagnoza_2
from dijagnoza as d1, dijagnoza as d2)
select distinct  ( case
    when dijagnozi_grupirani.dijagnoza_1 > dijagnozi_grupirani.dijagnoza_2
    then dijagnozi_grupirani.dijagnoza_1
    else dijagnozi_grupirani.dijagnoza_2
    end) as dijagnoza_1, ( case
    when dijagnozi_grupirani.dijagnoza_1 > dijagnozi_grupirani.dijagnoza_2
    then dijagnozi_grupirani.dijagnoza_2
    else dijagnozi_grupirani.dijagnoza_1
    end) as dijagnoza_2, count(pac1.embg)
from dijagnozi_grupirani
join pregled p1 on dijagnozi_grupirani.br_pregled_1 = p1.broj_na_pregled
join pacient pac1 on p1.embg_pacient = pac1.embg
join pregled p2 on dijagnozi_grupirani.br_pregled_2 = p2.broj_na_pregled
join pacient pac2 on pac2.embg = p2.embg_pacient
where dijagnoza_1!=dijagnoza_2 and pac1.embg=pac2.embg
group by dijagnozi_grupirani.dijagnoza_1, dijagnozi_grupirani.dijagnoza_2;

--NAJCHESTA TERAPIJA ZA DIJAGNOZA
with terapii_za_dijagnoza as (select distinct dijagnoza.dijagnoza, terapija, count(p.broj_na_pregled) as broj_na_pregledi
from dijagnoza
join pregled p on dijagnoza.broj_na_pregled = p.broj_na_pregled
join terapija t on t.broj_na_pregled = p.broj_na_pregled
group by dijagnoza.dijagnoza, terapija)
select distinct on (terapii_za_dijagnoza.dijagnoza) dijagnoza, terapija, broj_na_pregledi
from terapii_za_dijagnoza
where broj_na_pregledi >= all (select t2.broj_na_pregledi
                                from terapii_za_dijagnoza as t2
                                where terapii_za_dijagnoza.dijagnoza = t2.dijagnoza)
order by dijagnoza;