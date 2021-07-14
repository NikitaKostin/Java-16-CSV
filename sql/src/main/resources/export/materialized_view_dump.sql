--
-- PostgreSQL database dump
--

-- Dumped from database version 13.3 (Debian 13.3-1.pgdg100+1)
-- Dumped by pg_dump version 13.3

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: agg_repository_ui; Type: MATERIALIZED VIEW; Schema: public; Owner: postgres
--

CREATE MATERIALIZED VIEW public.agg_repository_ui AS
 SELECT
        CASE
            WHEN (count(*) FILTER (WHERE (ri.label_id = 5)) > 0) THEN true
            ELSE false
        END AS has_etp_process,
    array_agg(ri.value) FILTER (WHERE (ri.label_id = 15)) AS gu_service_codes,
    jfc.count AS java_files_count,
    count(*) FILTER (WHERE (ri.label_id = 6)) AS count_efp,
    count(*) FILTER (WHERE (ri.label_id = 10)) AS count_pom,
    r.repository_name
   FROM ((public.repository_info ri
     LEFT JOIN public.repository r ON ((ri.repository_id = r.id)))
     LEFT JOIN ( SELECT repository_info.repository_id,
            repository_info.value AS count
           FROM public.repository_info
          WHERE (repository_info.label_id = 3)
          GROUP BY repository_info.repository_id, repository_info.value) jfc ON ((ri.repository_id = jfc.repository_id)))
  WHERE (ri.label_id = ANY (ARRAY[(5)::bigint, (3)::bigint, (15)::bigint, (6)::bigint, (10)::bigint]))
  GROUP BY r.repository_name, jfc.count
  WITH NO DATA;


ALTER TABLE public.agg_repository_ui OWNER TO postgres;

--
-- Name: agg_repository_ui; Type: MATERIALIZED VIEW DATA; Schema: public; Owner: postgres
--

REFRESH MATERIALIZED VIEW public.agg_repository_ui;


--
-- PostgreSQL database dump complete
--

