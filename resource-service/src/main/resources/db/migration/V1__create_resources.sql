CREATE SEQUENCE q_resources_id INCREMENT BY 1 MINVALUE 1 NO MAXVALUE START WITH 1 CACHE 10 NO CYCLE;
CREATE TABLE IF NOT EXISTS t_resources (
   id bigint DEFAULT nextval('q_resources_id'),
   location VARCHAR,

   CONSTRAINT pk_resources PRIMARY KEY (id)
);