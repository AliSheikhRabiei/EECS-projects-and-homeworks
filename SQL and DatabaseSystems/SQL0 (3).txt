-- a) Average age of all actors
WITH ages AS (
  SELECT
    a.id,
    CASE
      WHEN a."date_of_birth" IS NOT NULL
        THEN EXTRACT(YEAR FROM AGE(CURRENT_DATE, a."date_of_birth"))::INT
      WHEN a."year_of_birth" IS NOT NULL
        THEN (EXTRACT(YEAR FROM CURRENT_DATE)::INT - a."year_of_birth")
      ELSE NULL
    END AS age_years
  FROM public."Actor" a
)
SELECT AVG(age_years)::NUMERIC(10,2) AS avg_actor_age_years
FROM ages
WHERE age_years IS NOT NULL;


-- b)Beautiful mind countries
SELECT COUNT(DISTINCT bl.country) AS countries
FROM public."Movie" m
JOIN public."MovieActor" ma ON ma."id_Movie" = m.id
JOIN public."Actor" a ON a.id = ma."id_Actor"
JOIN public."BirthLocation" bl ON bl.id = a."id_BirthLocation"
WHERE m.title = 'A Beautiful Mind';

-- (c) Number of actors with green eye color
SELECT COUNT(*) AS green_eyed_actors
FROM public."Actor"
WHERE eye_color ILIKE 'green';

-- (d) Number of movies Brad Pitt acted in
SELECT COUNT(DISTINCT ma."id_Movie") AS movies_with_brad_pitt
FROM public."MovieActor" ma
JOIN public."Actor" a ON a.id = ma."id_Actor"
WHERE a.first_name ILIKE 'Brad'
AND a.surname ILIKE 'Pitt';

--(e) Min,Avg,Max budget per Genre
SELECT
  g.type AS genre,
  COUNT(m.id) AS movie_count,
  MIN(m.budget) AS min_budget_usd,
  AVG(m.budget) AS avg_budget_usd,
  MAX(m.budget) AS max_budget_usd
FROM public."Genre" g
JOIN public."MovieGenre" mg ON mg."id_Genre" = g.id
JOIN public."Movie" m ON m.id= mg."id_Movie"
WHERE m.budget IS NOT NULL
GROUP BY g.type
ORDER BY g.type;

-- (f) Average rating for movies with a Toronto-born director OR a blue-eyed actor
SELECT
  m.title,
  AVG(m.rating)::NUMERIC(10,2) AS avg_rating
FROM public."Movie" m
LEFT JOIN public."Director" d ON d."id_Movie"= m.id
LEFT JOIN public."BirthLocation" bl ON bl.id= d."id_BirthLocation"
LEFT JOIN public."MovieActor" ma ON ma."id_Movie" = m.id
LEFT JOIN public."Actor" a ON a.id = ma."id_Actor"
WHERE (bl.city ILIKE 'Toronto')
   OR (a.eye_color ILIKE 'blue')
GROUP BY m.id, m.title
ORDER BY m.title;

-- (g) Movies with actors born in ≥ 2 countries
SELECT
  m.title,
  COUNT(DISTINCT bl.country) AS distinct_actor_countries
FROM public."Movie" m
JOIN public."MovieActor" ma ON ma."id_Movie" = m.id
JOIN public."Actor" a  ON a.id = ma."id_Actor"
JOIN public."BirthLocation" bl ON bl.id = a."id_BirthLocation"
GROUP BY m.id, m.title
HAVING COUNT(DISTINCT bl.country) >= 2
ORDER BY m.title;

-- (h) Award count and rank per movie
SELECT
  m.title,
  COUNT(aw.award_id) AS total_awards,
  RANK() OVER (ORDER BY COUNT(aw.award_id) DESC) AS rank_by_awards
FROM public."Movie" m
LEFT JOIN public."Award" aw ON aw."id_Movie" = m.id
GROUP BY m.id, m.title
ORDER BY total_awards DESC, m.title;
