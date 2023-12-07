CREATE SEQUENCE q_songs_id INCREMENT BY 1 MINVALUE 1 NO MAXVALUE START WITH 1 CACHE 10 NO CYCLE;
CREATE TABLE IF NOT EXISTS t_songs (
   id bigint DEFAULT nextval('q_songs_id'),
   name VARCHAR,
   artist VARCHAR,
   album VARCHAR,
   length VARCHAR,
   resource_id VARCHAR,
   year VARCHAR,

   CONSTRAINT pk_songs PRIMARY KEY (id)
);