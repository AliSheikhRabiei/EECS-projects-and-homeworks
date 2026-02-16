--adding date_of_birth to actors for later uses
ALTER TABLE public."Actor"
ADD COLUMN date_of_birth date;


--Fight club info for bradpit and David Fincher

INSERT INTO public."Movie"(title, release_time, date, rating, budget, gross)
SELECT 'Fight Club', TIME '00:00:00', DATE '1999-10-15', 88, 63000000, 101200000
WHERE NOT EXISTS (
  SELECT 1 FROM public."Movie" WHERE title='Fight Club'
);

-- Brad Pitt
INSERT INTO public."BirthLocation"(country, city, state)
SELECT 'USA','Shawnee','OK'
WHERE NOT EXISTS (
  SELECT 1 FROM public."BirthLocation"
  WHERE country='USA' AND city='Shawnee' AND state='OK'
);

INSERT INTO public."Actor"(first_name, surname, year_of_birth, "id_BirthLocation", eye_color, date_of_birth)
SELECT 'Brad','Pitt', 1963, bl.id, 'blue', DATE '1963-12-18'
FROM public."BirthLocation" bl
WHERE bl.country='USA' AND bl.city='Shawnee' AND bl.state='OK'
  AND NOT EXISTS (
    SELECT 1 FROM public."Actor"
    WHERE first_name='Brad' AND surname='Pitt'
  );

-- David Fincher
INSERT INTO public."BirthLocation"(country, city, state)
SELECT 'USA','Denver','CO'
WHERE NOT EXISTS (
  SELECT 1 FROM public."BirthLocation"
  WHERE country='USA' AND city='Denver' AND state='CO'
);

INSERT INTO public."Director"(first_name, surname, year_of_birth, "id_BirthLocation", "id_Movie")
SELECT 'David','Fincher', 1962, bl.id, mv.id
FROM (SELECT id FROM public."Movie" WHERE title='Fight Club' LIMIT 1) mv
JOIN public."BirthLocation" bl
  ON bl.country='USA' AND bl.city='Denver' AND bl.state='CO'
WHERE NOT EXISTS (
  SELECT 1 FROM public."Director" d
  WHERE d.first_name='David' AND d.surname='Fincher' AND d."id_Movie"=mv.id
);

-- Cast link
INSERT INTO public."MovieActor"("id_Movie","id_Actor")
SELECT mv.id, a.id
FROM (SELECT id FROM public."Movie"  WHERE title='Fight Club' LIMIT 1) mv,
     (SELECT id FROM public."Actor"  WHERE first_name='Brad' AND surname='Pitt' LIMIT 1) a
WHERE NOT EXISTS (
  SELECT 1 FROM public."MovieActor"
  WHERE "id_Movie"=mv.id AND "id_Actor"=a.id
);

-- Genre link (Drama could be action or Comedy too)
INSERT INTO public."MovieGenre"("id_Movie","id_Genre")
SELECT mv.id, g.id
FROM (SELECT id FROM public."Movie" WHERE title='Fight Club' LIMIT 1) mv
JOIN public."Genre" g ON g.type='Drama'
WHERE NOT EXISTS (
  SELECT 1 FROM public."MovieGenre"
  WHERE "id_Movie"=mv.id AND "id_Genre"=g.id
);


--info for Beautiful mind and its actor and release date and its budget, i have not watched it
INSERT INTO public."Movie"(title, release_time, date, rating, budget, gross)
SELECT 'A Beautiful Mind', TIME '00:00:00', DATE '2001-12-21', 86, 58000000, 313000000
WHERE NOT EXISTS (
  SELECT 1 FROM public."Movie" WHERE title = 'A Beautiful Mind'
);
INSERT INTO public."BirthLocation"(country, city, state)
SELECT v.country, v.city, v.state
FROM (VALUES
  ('USA','Duncan','OK'),              -- Ron Howard
  ('New Zealand','Wellington','WGN')  -- Russell Crowe
) AS v(country,city,state)
WHERE NOT EXISTS (
  SELECT 1 FROM public."BirthLocation" b
  WHERE b.country=v.country AND b.city=v.city AND b.state=v.state
);

-- Insert Russell Crowe
INSERT INTO public."Actor"(first_name, surname, year_of_birth, date_of_birth, "id_BirthLocation")
SELECT 'Russell','Crowe', 1964, DATE '1964-04-07', bl.id
FROM public."BirthLocation" bl
WHERE bl.country = 'New Zealand'
  AND bl.city    = 'Wellington'
  AND bl.state   = 'WGN'
  AND NOT EXISTS (
    SELECT 1 FROM public."Actor" a
    WHERE a.first_name = 'Russell'
      AND a.surname    = 'Crowe'
  );
-- Insert Ron Howard
INSERT INTO public."Director"(first_name, surname, year_of_birth, "id_BirthLocation")
SELECT 'Ron','Howard', 1954, bl.id
FROM public."BirthLocation" bl
WHERE bl.country='USA' AND bl.city='Duncan' AND bl.state='OK'
AND NOT EXISTS (
  SELECT 1 FROM public."Director" d
  WHERE d.first_name='Ron' AND d.surname='Howard'
);
UPDATE public."Director" d
SET "id_Movie" = m.id
FROM public."Movie" m
WHERE d.first_name='Ron' AND d.surname='Howard'
  AND m.title='A Beautiful Mind'
  AND (d."id_Movie" IS DISTINCT FROM m.id);
  INSERT INTO public."MovieActor"("id_Movie","id_Actor")
  SELECT m.id, a.id
  FROM public."Movie" m
  JOIN public."Actor" a
    ON a.first_name='Russell' AND a.surname='Crowe'
  WHERE m.title='A Beautiful Mind'
  AND NOT EXISTS (
    SELECT 1 FROM public."MovieActor" ma
    WHERE ma."id_Movie"=m.id AND ma."id_Actor"=a.id
  );


-- Comedy movie with lead actor under 40
--Insrt the movie
INSERT INTO public."Movie"(title, release_time, date, rating, budget, gross)
SELECT 'Swiss Army Man', TIME '00:00:00', DATE '2016-06-24', 82, 3000000, 5000000
WHERE NOT EXISTS (
    SELECT 1 FROM public."Movie" WHERE title = 'Swiss Army Man'
);

INSERT INTO public."BirthLocation"(country, city, state)
SELECT 'USA', 'New York', 'NY'
WHERE NOT EXISTS (
    SELECT 1 FROM public."BirthLocation"
    WHERE country='USA' AND city='New York' AND state='NY'
);

--Insert lead actor (Paul Dano)
INSERT INTO public."Actor"(first_name, surname, year_of_birth, "id_BirthLocation", eye_color, date_of_birth)
SELECT
    'Paul',
    'Dano',
    1984,
    b.id,
    'blue',
    DATE '1984-06-19'
FROM public."BirthLocation" b
WHERE b.country='USA' AND b.city='New York' AND b.state='NY'
  AND NOT EXISTS (
        SELECT 1 FROM public."Actor"
        WHERE first_name='Paul' AND surname='Dano'
  );

--Link Paul Dano to the movie
INSERT INTO public."MovieActor"("id_Movie", "id_Actor")
SELECT
    m.id,
    a.id
FROM public."Movie" m,
     public."Actor" a
WHERE m.title='Swiss Army Man'
  AND a.first_name='Paul'
  AND a.surname='Dano'
  AND NOT EXISTS (
        SELECT 1 FROM public."MovieActor"
        WHERE "id_Movie" = m.id AND "id_Actor" = a.id
  );

INSERT INTO public."BirthLocation"(country, city, state)
SELECT 'USA', 'Westborough', 'MA'
WHERE NOT EXISTS (
    SELECT 1 FROM public."BirthLocation"
    WHERE country='USA' AND city='Westborough' AND state='MA'
);

--Insert director Dan Kwan
INSERT INTO public."Director"(first_name, surname, year_of_birth, "id_BirthLocation", "id_Movie", "id_University")
SELECT
    'Dan','Kwan',1988,  b.id,m.id, NULL
FROM public."BirthLocation" b,
     public."Movie" m
WHERE b.country='USA' AND b.city='Westborough' AND b.state='MA'
  AND m.title='Swiss Army Man'
  AND NOT EXISTS (
        SELECT 1 FROM public."Director"
        WHERE first_name='Dan' AND surname='Kwan' AND "id_Movie" = m.id
  );


  --info for David Lynch - semi accurate

  --Lynch movies
WITH new_movies AS (
    SELECT *
    FROM (VALUES
        ('Eraserhead', TIME '00:00:00', DATE '1977-03-19', 80,  100000::DOUBLE PRECISION,  710000::DOUBLE PRECISION),
        ('The Elephant Man', TIME '00:00:00', DATE '1980-10-03', 91, 5000000::DOUBLE PRECISION, 26000000::DOUBLE PRECISION),
        ('Blue Velvet', TIME '00:00:00', DATE '1986-09-19', 88, 6000000::DOUBLE PRECISION, 8680000::DOUBLE PRECISION),
        ('Lost Highway', TIME '00:00:00', DATE '1997-02-21', 77,15000000::DOUBLE PRECISION, 3850000::DOUBLE PRECISION),
        ('Mulholland Drive', TIME '00:00:00', DATE '2001-10-19', 90,15000000::DOUBLE PRECISION,20300000::DOUBLE PRECISION)
    ) AS v(title, release_time, date, rating, budget, gross)
)
INSERT INTO public."Movie"(title, release_time, date, rating, budget, gross)
SELECT nm.title, nm.release_time, nm.date, nm.rating, nm.budget, nm.gross
FROM new_movies nm
WHERE NOT EXISTS (
    SELECT 1 FROM public."Movie" m
    WHERE m.title = nm.title
);

  --Insert birth location
  WITH v(country, city, state) AS (
    VALUES ('USA','Missoula','MT')
  )
  INSERT INTO public."BirthLocation"(country, city, state)
  SELECT v.country, v.city, v.state
  FROM v
  LEFT JOIN public."BirthLocation" b
    ON b.country=v.country AND b.city=v.city AND b.state=v.state
  WHERE b.id IS NULL;

  --Insert director (David Lynch)
  WITH bl AS (
    SELECT id FROM public."BirthLocation"
    WHERE country='USA' AND city='Missoula' AND state='MT'
  ),
  mv AS (
    SELECT id, title FROM public."Movie"
    WHERE title IN ('Eraserhead','The Elephant Man','Blue Velvet','Lost Highway','Mulholland Drive')
  )
  INSERT INTO public."Director"(first_name, surname, year_of_birth, "id_BirthLocation", "id_Movie")
  SELECT 'David','Lynch',1946, bl.id, mv.id
  FROM bl, mv
  WHERE NOT EXISTS (
    SELECT 1 FROM public."Director" d
    WHERE d.first_name='David' AND d.surname='Lynch' AND d."id_Movie"=mv.id
  );

  --Insert actors' birth locations
  WITH v(country, city, state) AS (
    VALUES
      ('USA','Boston','MA'),
      ('UK','Chesterfield','Derbyshire'),
      ('USA','Yakima','WA'),
      ('USA','Buffalo','NY'),
      ('UK','Shoreham','Kent')
  )
  INSERT INTO public."BirthLocation"(country, city, state)
  SELECT v.country, v.city, v.state
  FROM v
  LEFT JOIN public."BirthLocation" b
    ON b.country=v.country AND b.city=v.city AND b.state=v.state
  WHERE b.id IS NULL;

  --Insert actors
  WITH bl AS (
    SELECT country, city, state, id FROM public."BirthLocation"
  )
  INSERT INTO public."Actor"(first_name, surname, year_of_birth, "id_BirthLocation", date_of_birth)
  SELECT * FROM (
    SELECT 'Jack','Nance',1943,(SELECT id FROM bl WHERE country='USA' AND city='Boston' AND state='MA'),DATE '1943-12-21' UNION ALL
    SELECT 'John','Hurt',1940,(SELECT id FROM bl WHERE country='UK' AND city='Chesterfield' AND state='Derbyshire'),DATE '1940-01-22' UNION ALL
    SELECT 'Kyle','MacLachlan',1959,(SELECT id FROM bl WHERE country='USA' AND city='Yakima' AND state='WA'),DATE '1959-02-22' UNION ALL
    SELECT 'Bill','Pullman',1953,(SELECT id FROM bl WHERE country='USA' AND city='Buffalo' AND state='NY'),DATE '1953-12-17' UNION ALL
    SELECT 'Naomi','Watts',1968,(SELECT id FROM bl WHERE country='UK' AND city='Shoreham' AND state='Kent'),DATE '1968-09-28'
  ) AS x(fn,sn,yob,blid,dob)
  WHERE NOT EXISTS (
    SELECT 1 FROM public."Actor" a
    WHERE a.first_name=x.fn AND a.surname=x.sn AND a.year_of_birth=x.yob
  );

  --Link actors to their movies
  WITH m AS (
    SELECT id, title FROM public."Movie"
    WHERE title IN ('Eraserhead','The Elephant Man','Blue Velvet','Lost Highway','Mulholland Drive')
  ),
  a AS (
    SELECT id, first_name, surname FROM public."Actor"
  )
  INSERT INTO public."MovieActor"("id_Movie","id_Actor")
  SELECT m.id, a.id
  FROM m
  JOIN a ON (
    (m.title='Eraserhead'       AND a.first_name='Jack'  AND a.surname='Nance') OR
    (m.title='The Elephant Man' AND a.first_name='John'  AND a.surname='Hurt')  OR
    (m.title='Blue Velvet'      AND a.first_name='Kyle'  AND a.surname='MacLachlan') OR
    (m.title='Lost Highway'     AND a.first_name='Bill'  AND a.surname='Pullman') OR
    (m.title='Mulholland Drive' AND a.first_name='Naomi' AND a.surname='Watts')
  )
  WHERE NOT EXISTS (
    SELECT 1 FROM public."MovieActor" ma
    WHERE ma."id_Movie"=m.id AND ma."id_Actor"=a.id
  );


  --Insert director canadian
  INSERT INTO public."BirthLocation"(country, city, state)
  SELECT 'Canada', 'Toronto', 'ON'
  WHERE NOT EXISTS (
      SELECT 1
      FROM public."BirthLocation"
      WHERE country = 'Canada'
        AND city    = 'Toronto'
        AND state   = 'ON'
  );

  -- Insert David Cronenberg as a director
INSERT INTO public."Director"(first_name, surname, year_of_birth, "id_BirthLocation")
SELECT
    'David','Cronenberg',1943,b.id
FROM public."BirthLocation" b
WHERE b.country = 'Canada'
  AND b.city    = 'Toronto'
  AND b.state   = 'ON'
  AND NOT EXISTS (
        SELECT 1
        FROM public."Director" d
        WHERE d.first_name = 'David'
          AND d.surname    = 'Cronenberg'
    );


    -- Extra birth locations for A/D-surname directors
    INSERT INTO public."BirthLocation"(country, city, state)
    SELECT v.country, v.city, v.state
    FROM (
        VALUES
            ('USA','Houston','TX'),     -- Wes Anderson
            ('Canada','Montreal','QC') -- Xavier Dolan
    ) AS v(country, city, state)
    LEFT JOIN public."BirthLocation" b
      ON b.country = v.country
     AND b.city    = v.city
     AND b.state   = v.state
    WHERE b.id IS NULL;


    -- Director with surname starting with 'A'
    INSERT INTO public."Director"(first_name, surname, year_of_birth,
                                  "id_BirthLocation", "id_Movie", "id_University")
    SELECT
        'Wes', 'Anderson', 1969,
        b.id,
        NULL,
        NULL
    FROM public."BirthLocation" b
    WHERE b.country = 'USA'
      AND b.city    = 'Houston'
      AND b.state   = 'TX'
      AND NOT EXISTS (
            SELECT 1
            FROM public."Director" d
            WHERE d.first_name = 'Wes'
              AND d.surname    = 'Anderson'
      );


    -- Director with surname starting with 'D'
    INSERT INTO public."Director"(first_name, surname, year_of_birth,
                                  "id_BirthLocation", "id_Movie", "id_University")
    SELECT
        'Xavier', 'Dolan', 1989,
        b.id,
        NULL,
        NULL
    FROM public."BirthLocation" b
    WHERE b.country = 'Canada'
      AND b.city    = 'Montreal'
      AND b.state   = 'QC'
      AND NOT EXISTS (
            SELECT 1
            FROM public."Director" d
            WHERE d.first_name = 'Xavier'
              AND d.surname    = 'Dolan'
      );


      -- Green-eyed actor
      INSERT INTO public."Actor"(first_name, surname, year_of_birth,
                                 "id_BirthLocation", eye_color, date_of_birth)
      SELECT
          'Alex', 'Green', 1990,
          b.id,
          'green',
          DATE '1990-05-05'
      FROM public."BirthLocation" b
      WHERE b.country = 'USA'
        AND b.city    = 'Boston'
        AND b.state   = 'MA'
        AND NOT EXISTS (
              SELECT 1
              FROM public."Actor" a
              WHERE a.first_name = 'Alex'
                AND a.surname    = 'Green'
        );

  -- Universities
  INSERT INTO public."University"(name, is_private, color)
  SELECT u.name, u.is_private, u.color
  FROM (
    VALUES
      ('York University',FALSE, 'red'),
      ('University of Toronto',  FALSE, 'blue'),
      ('Harvard University', TRUE,  'crimson'),
      ('Massachusetts Institute of Technology', TRUE, 'grey'),
      ('University of California, Los Angeles', FALSE, 'gold')
  ) AS u(name, is_private, color)
  WHERE NOT EXISTS (
    SELECT 1 FROM public."University" x
    WHERE x.name = u.name
  );

  -- Departments
  INSERT INTO public."Department"("id_University", name)
  SELECT uni.id, d.dept_name
  FROM (
    VALUES
      ('York University', 'Computer Science'),
      ('University of Toronto', 'Mathematics'),
      ('Harvard University','Film Studies'),
      ('Massachusetts Institute of Technology', 'Media Lab'),
      ('University of California, Los Angeles', 'Theatre & Film')
  ) AS d(university_name, dept_name)
  JOIN public."University" uni
    ON uni.name = d.university_name
  WHERE NOT EXISTS (
    SELECT 1 FROM public."Department" dept
    WHERE dept.name = d.dept_name
      AND dept."id_University" = uni.id
  );

-- Cinemas
INSERT INTO public."Cinema"(cinema_name, location, type)
SELECT c.cinema_name, c.location, c.type
FROM (
  VALUES
    ('Cineplex Yonge-Dundas', 'Toronto', 'regular'),
    ('Scotiabank Theatre', 'Toronto', 'imax'),
    ('Bloor Cinema','Toronto', 'arthouse'),
    ('TIFF Bell Lightbox', 'Toronto', 'regular'),
    ('York Cinema','Toronto', 'regular')
) AS c(cinema_name, location, type)
WHERE NOT EXISTS (
  SELECT 1 FROM public."Cinema" x
  WHERE x.cinema_name = c.cinema_name
);

-- Tickets
INSERT INTO public."Ticket"(price, "id_Cinema")
SELECT t.price, c.id
FROM (
  VALUES
    (14.99, 'Cineplex Yonge-Dundas'),
    (16.50, 'Scotiabank Theatre'),
    (12.00, 'Bloor Cinema'),
    (15.00, 'TIFF Bell Lightbox'),
    (11.00, 'York Cinema')
) AS t(price, cinema_name)
JOIN public."Cinema" c
  ON c.cinema_name = t.cinema_name
WHERE NOT EXISTS (
  SELECT 1 FROM public."Ticket" tk
  WHERE tk.price = t.price AND tk."id_Cinema" = c.id
);

-- ShowTimes
WITH tk AS (
  SELECT tk.id, c.cinema_name
  FROM public."Ticket" tk
  JOIN public."Cinema" c ON c.id = tk."id_Cinema"
),
mv AS (
  SELECT id, title
  FROM public."Movie"
  WHERE title IN ('Fight Club',
                  'Swiss Army Man',
                  'A Beautiful Mind',
                  'Blue Velvet',
                  'Eraserhead')
)
INSERT INTO public."ShowTime"(show_name, show_time, show_duration, "id_Cinema_Ticket", "id_Movie")
SELECT s.show_name,
       s.show_time,
       s.duration,
       tk.id,
       mv.id
FROM (
  VALUES
    ('Morning Show', TIME '10:00', 120, 'Fight Club','Cineplex Yonge-Dundas'),
    ('Matinee', TIME '13:30', 100, 'Swiss Army Man','TIFF Bell Lightbox'),
    ('Evening', TIME '18:00', 135, 'A Beautiful Mind','Scotiabank Theatre'),
    ('Late Night', TIME '21:00', 115, 'Blue Velvet', 'Bloor Cinema'),
    ('Retro',TIME '23:00',  90, 'Eraserhead', 'York Cinema')
) AS s(show_name, show_time, duration, movie_title, cinema_name)
JOIN mv  ON mv.title = s.movie_title
JOIN tk  ON tk.cinema_name = s.cinema_name
WHERE NOT EXISTS (
  SELECT 1 FROM public."ShowTime" st
  WHERE st.show_name = s.show_name
    AND st."id_Movie" = mv.id
    AND st."id_Cinema_Ticket" = tk.id
);

-- Genres
INSERT INTO public."Genre"(type)
SELECT g.type
FROM (
  VALUES
    ('Comedy'),
    ('Drama'),
    ('Thriller'),
    ('Sci-Fi'),
    ('Horror')
) AS g(type)
WHERE NOT EXISTS (
  SELECT 1 FROM public."Genre" x
  WHERE x.type = g.type
);

-- MovieGenre links
WITH mv AS (
  SELECT id, title
  FROM public."Movie"
  WHERE title IN ('Swiss Army Man',
                  'Fight Club',
                  'A Beautiful Mind',
                  'Eraserhead',
                  'Blue Velvet')
),
gn AS (
  SELECT id, type FROM public."Genre"
)
INSERT INTO public."MovieGenre"("id_Movie", "id_Genre")
SELECT mv.id,
       CASE mv.title
         WHEN 'Swiss Army Man' THEN (SELECT id FROM gn WHERE type = 'Comedy')
         WHEN 'Fight Club' THEN (SELECT id FROM gn WHERE type = 'Thriller')
         WHEN 'A Beautiful Mind' THEN (SELECT id FROM gn WHERE type = 'Drama')
         WHEN 'Eraserhead' THEN (SELECT id FROM gn WHERE type = 'Horror')
         WHEN 'Blue Velvet' THEN (SELECT id FROM gn WHERE type = 'Drama')
       END AS id_Genre
FROM mv
WHERE NOT EXISTS (
  SELECT 1 FROM public."MovieGenre" mg
  WHERE mg."id_Movie" = mv.id
);

-- Awards
INSERT INTO public."Award"(award_name, "id_Movie")
SELECT x.award_name, x.id_Movie
FROM (
  SELECT 'Best Picture', (SELECT id FROM public."Movie" WHERE title='A Beautiful Mind') UNION ALL
  SELECT 'Best Actor',  (SELECT id FROM public."Movie" WHERE title='A Beautiful Mind') UNION ALL
  SELECT 'Best Director', (SELECT id FROM public."Movie" WHERE title='Fight Club') UNION ALL
  SELECT 'Critics Choice',(SELECT id FROM public."Movie" WHERE title='Swiss Army Man') UNION ALL
  SELECT 'Festival Special Award', (SELECT id FROM public."Movie" WHERE title='Blue Velvet')
) AS x(award_name, id_Movie)
WHERE NOT EXISTS (
  SELECT 1 FROM public."Award" a
  WHERE a.award_name = x.award_name
    AND a."id_Movie" = x.id_Movie
);
