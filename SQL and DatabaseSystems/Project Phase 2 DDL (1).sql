
-- BirthLocation table
DROP TABLE IF EXISTS public."BirthLocation" CASCADE;
CREATE TABLE public."BirthLocation" (
    id smallint NOT NULL GENERATED ALWAYS AS IDENTITY,
    country VARCHAR NOT NULL,
    city VARCHAR NOT NULL,
    state VARCHAR NOT NULL,
    CONSTRAINT "BirthLocation_pk" PRIMARY KEY (id)
);
ALTER TABLE public."BirthLocation" OWNER TO postgres;

-- Movie table
DROP TABLE IF EXISTS public."Movie" CASCADE;
CREATE TABLE public."Movie" (
    id smallint NOT NULL GENERATED ALWAYS AS IDENTITY,
    title VARCHAR NOT NULL,
    release_time TIME NOT NULL,
    date DATE NOT NULL,
    rating smallint NOT NULL,
    budget float NOT NULL,
    gross float NOT NULL,
    CONSTRAINT "Movie_pk" PRIMARY KEY (id)
);
ALTER TABLE public."Movie" OWNER TO postgres;

-- University table
DROP TABLE IF EXISTS public."University" CASCADE;
CREATE TABLE public."University" (
    id smallint NOT NULL GENERATED ALWAYS AS IDENTITY,
    name VARCHAR NOT NULL,
    is_private boolean NOT NULL,
    color VARCHAR NOT NULL,
    CONSTRAINT "University_pk" PRIMARY KEY (id)
);
ALTER TABLE public."University" OWNER TO postgres;

-- Director table
DROP TABLE IF EXISTS public."Director" CASCADE;
CREATE TABLE public."Director" (
    id smallint NOT NULL GENERATED ALWAYS AS IDENTITY,
    first_name VARCHAR NOT NULL,
    surname VARCHAR NOT NULL,
    year_of_birth smallint NOT NULL,
    "id_BirthLocation" smallint NOT NULL,
    "id_Movie" smallint,
    "id_University" smallint,
    CONSTRAINT "Director_pk" PRIMARY KEY (id),
    CONSTRAINT "Director_BirthLocation_fk" FOREIGN KEY ("id_BirthLocation") 
        REFERENCES public."BirthLocation"(id) MATCH FULL ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT "Director_Movie_fk" FOREIGN KEY ("id_Movie") 
        REFERENCES public."Movie"(id) MATCH SIMPLE ON DELETE SET NULL ON UPDATE CASCADE,
    CONSTRAINT "Director_University_fk" FOREIGN KEY ("id_University") 
        REFERENCES public."University"(id) MATCH SIMPLE ON DELETE SET NULL ON UPDATE CASCADE
);
ALTER TABLE public."Director" OWNER TO postgres;

-- Actor table
DROP TABLE IF EXISTS public."Actor" CASCADE;
CREATE TABLE public."Actor" (
    id smallint NOT NULL GENERATED ALWAYS AS IDENTITY,
    first_name VARCHAR NOT NULL,
    surname VARCHAR NOT NULL,
    year_of_birth smallint NOT NULL,
    "id_BirthLocation" smallint NOT NULL,
	eye_color VARCHAR(20),
    CONSTRAINT "Actor_pk" PRIMARY KEY (id),
    CONSTRAINT "Actor_BirthLocation_fk" FOREIGN KEY ("id_BirthLocation") 
        REFERENCES public."BirthLocation"(id) MATCH FULL ON DELETE CASCADE ON UPDATE CASCADE
);
ALTER TABLE public."Actor" OWNER TO postgres;

-- Department table
DROP TABLE IF EXISTS public."Department" CASCADE;
CREATE TABLE public."Department" (
    id smallint NOT NULL GENERATED ALWAYS AS IDENTITY,
    "id_University" smallint NOT NULL UNIQUE, -- Unique constraint ensures one-to-one relationship
    name VARCHAR NOT NULL,
    CONSTRAINT "Department_pk" PRIMARY KEY (id),
    CONSTRAINT "Department_University_fk" FOREIGN KEY ("id_University") 
        REFERENCES public."University"(id) MATCH SIMPLE ON DELETE CASCADE ON UPDATE CASCADE
);
ALTER TABLE public."Department" OWNER TO postgres;

-- Remaining tables and constraints can be added similarly...

-- MovieActor table
DROP TABLE IF EXISTS public."MovieActor" CASCADE;
CREATE TABLE public."MovieActor" (
    id smallint NOT NULL GENERATED ALWAYS AS IDENTITY,
    "id_Movie" smallint NOT NULL,
    "id_Actor" smallint NOT NULL,
    CONSTRAINT "MovieActor_pk" PRIMARY KEY (id),
    CONSTRAINT "MovieActor_Movie_fk" FOREIGN KEY ("id_Movie") 
        REFERENCES public."Movie"(id) MATCH FULL ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT "MovieActor_Actor_fk" FOREIGN KEY ("id_Actor") 
        REFERENCES public."Actor"(id) MATCH FULL ON DELETE RESTRICT ON UPDATE CASCADE
);
ALTER TABLE public."MovieActor" OWNER TO postgres;

-- Genre table
DROP TABLE IF EXISTS public."Genre" CASCADE;
CREATE TABLE public."Genre" (
    id smallint NOT NULL GENERATED ALWAYS AS IDENTITY,
    type VARCHAR NOT NULL,
    CONSTRAINT "Genre_pk" PRIMARY KEY (id)
);
ALTER TABLE public."Genre" OWNER TO postgres;

-- MovieGenre table
DROP TABLE IF EXISTS public."MovieGenre" CASCADE;
CREATE TABLE public."MovieGenre" (
    id smallint NOT NULL GENERATED ALWAYS AS IDENTITY,
    "id_Movie" smallint NOT NULL,
    "id_Genre" smallint NOT NULL,
    CONSTRAINT "MovieGenre_pk" PRIMARY KEY (id),
    CONSTRAINT "MovieGenre_Movie_fk" FOREIGN KEY ("id_Movie") 
        REFERENCES public."Movie"(id) MATCH FULL ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT "MovieGenre_Genre_fk" FOREIGN KEY ("id_Genre") 
        REFERENCES public."Genre"(id) MATCH FULL ON DELETE RESTRICT ON UPDATE CASCADE
);
ALTER TABLE public."MovieGenre" OWNER TO postgres;

-- Cinema table
DROP TABLE IF EXISTS public."Cinema" CASCADE;
CREATE TABLE public."Cinema" (
    id smallint NOT NULL GENERATED ALWAYS AS IDENTITY,
    cinema_name VARCHAR NOT NULL,
    location VARCHAR NOT NULL,
    type VARCHAR NOT NULL,
    CONSTRAINT "Cinema_pk" PRIMARY KEY (id)
);
ALTER TABLE public."Cinema" OWNER TO postgres;

-- Ticket table
DROP TABLE IF EXISTS public."Ticket" CASCADE;
CREATE TABLE public."Ticket" (
    id smallint NOT NULL GENERATED ALWAYS AS IDENTITY,
    price float8 NOT NULL,
    "id_Cinema" smallint NOT NULL,
    CONSTRAINT "Ticket_pk" PRIMARY KEY (id),
    CONSTRAINT "Ticket_Cinema_fk" FOREIGN KEY ("id_Cinema") 
        REFERENCES public."Cinema"(id) MATCH FULL ON DELETE CASCADE ON UPDATE CASCADE
);
ALTER TABLE public."Ticket" OWNER TO postgres;

-- ShowTime table
DROP TABLE IF EXISTS public."ShowTime" CASCADE;
CREATE TABLE public."ShowTime" (
    show_id smallint NOT NULL GENERATED ALWAYS AS IDENTITY,
    show_name VARCHAR NOT NULL,
    show_time TIME NOT NULL,
    show_duration smallint,
    "id_Cinema_Ticket" smallint NOT NULL,
    "id_Movie" smallint NOT NULL,
    CONSTRAINT "ShowTime_pk" PRIMARY KEY (show_id),
    CONSTRAINT "ShowTime_Ticket_fk" FOREIGN KEY ("id_Cinema_Ticket") 
        REFERENCES public."Ticket"(id) MATCH FULL ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT "ShowTime_Movie_fk" FOREIGN KEY ("id_Movie") 
        REFERENCES public."Movie"(id) MATCH FULL ON DELETE CASCADE ON UPDATE CASCADE
);
ALTER TABLE public."ShowTime" OWNER TO postgres;

-- Award table
DROP TABLE IF EXISTS public."Award" CASCADE;
CREATE TABLE public."Award" (
    award_id smallint NOT NULL GENERATED ALWAYS AS IDENTITY,
    award_name VARCHAR NOT NULL,
    "id_Movie" smallint NOT NULL,
    CONSTRAINT "Award_pk" PRIMARY KEY (award_id),
    CONSTRAINT "Award_Movie_fk" FOREIGN KEY ("id_Movie") 
        REFERENCES public."Movie"(id) MATCH FULL ON DELETE CASCADE ON UPDATE CASCADE
);
ALTER TABLE public."Award" OWNER TO postgres;

-- Category table
DROP TABLE IF EXISTS public."Category" CASCADE;
CREATE TABLE public."Category" (
    category_id smallint NOT NULL GENERATED ALWAYS AS IDENTITY,
    category_name VARCHAR NOT NULL,
    "award_id_Award" smallint NOT NULL,
    "id_Movie_Award" smallint NOT NULL,
    CONSTRAINT "Category_pk" PRIMARY KEY (category_id),
    CONSTRAINT "Category_Award_fk" FOREIGN KEY ("award_id_Award") 
        REFERENCES public."Award"(award_id) MATCH FULL ON DELETE CASCADE ON UPDATE CASCADE
);
ALTER TABLE public."Category" OWNER TO postgres;
