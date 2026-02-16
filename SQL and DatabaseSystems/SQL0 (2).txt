--(a): Directors born in Canada
SELECT d.first_name, d.surname, bl.city, bl.state, bl.country
FROM public."Director" d
JOIN public."BirthLocation" bl ON d."id_BirthLocation" = bl.id
WHERE bl.country = 'Canada'
ORDER BY d.surname;

--(b): Movies directed by David Lynch
SELECT m.id, m.title, m.date, d.first_name, d.surname
FROM public."Movie" m
JOIN public."Director" d ON d."id_Movie" = m.id
WHERE d.first_name = 'David' AND d.surname = 'Lynch'
ORDER BY m.date;

--(c): Actors in movies with budget > $1M with converted currencies
WITH rates AS (
  SELECT
      1::numeric AS usd,
      1.34::numeric AS cad,
      146::numeric AS jpy,
      93::numeric AS rub,
      0.93::numeric AS eur,
      0.90::numeric AS chf
)
SELECT DISTINCT
  a.first_name || ' ' || a.surname AS actor_name,
  m.title,
  m.budget,
  ROUND((m.budget * r.cad)::numeric, 2) AS budget_cad,
  ROUND((m.budget * r.jpy)::numeric, 2) AS budget_jpy,
  ROUND((m.budget * r.rub)::numeric, 2) AS budget_rub,
  ROUND((m.budget * r.eur)::numeric, 2) AS budget_eur,
  ROUND((m.budget * r.chf)::numeric, 2) AS budget_chf
FROM public."Actor" a
JOIN public."MovieActor" ma ON a.id = ma."id_Actor"
JOIN public."Movie" m       ON ma."id_Movie" = m.id,
     rates r
WHERE m.budget > 1000000
ORDER BY m.budget DESC;


--(d): Directors whose surnames start with A or D
SELECT first_name, surname
FROM public."Director"
WHERE surname ILIKE 'A%' OR surname ILIKE 'D%'
ORDER BY surname;

--(e): Comedy movies with at least one actor younger than 40
WITH ages AS (
  SELECT
    a.id,
    a.first_name,
    a.surname,
    CASE
      WHEN a.date_of_birth IS NOT NULL
        THEN EXTRACT(YEAR FROM AGE(CURRENT_DATE, a.date_of_birth))::INT
      WHEN a.year_of_birth IS NOT NULL
        THEN (EXTRACT(YEAR FROM CURRENT_DATE))::INT - a.year_of_birth
      ELSE NULL
    END AS age_years
  FROM public."Actor" a
)
SELECT DISTINCT
  m.title,
  ag.first_name || ' ' || ag.surname AS actor_name,
  g.type AS genre,
  ag.age_years
FROM public."Movie" m
JOIN public."MovieActor" ma ON ma."id_Movie" = m.id
JOIN ages ag ON ag.id = ma."id_Actor"
JOIN public."MovieGenre" mg ON mg."id_Movie" = m.id
JOIN public."Genre" g ON g.id = mg."id_Genre"
WHERE g.type ILIKE 'Comedy'
  AND ag.age_years < 40
ORDER BY m.title;

--(f): All pairs of blue-eyed actors (alphabetical, no duplicates)
SELECT
  LEAST(a1.first_name || ' ' || a1.surname, a2.first_name || ' ' || a2.surname) AS actor1,
  GREATEST(a1.first_name || ' ' || a1.surname, a2.first_name || ' ' || a2.surname) AS actor2
FROM public."Actor" a1
JOIN public."Actor" a2 ON a1.id < a2.id
WHERE a1.eye_color ILIKE 'blue'
  AND a2.eye_color ILIKE 'blue'
ORDER BY actor1, actor2;
