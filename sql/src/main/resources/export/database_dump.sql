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

--
-- Name: insert_data(); Type: PROCEDURE; Schema: public; Owner: postgres
--

CREATE PROCEDURE public.insert_data()
    LANGUAGE plpgsql
    AS $$

DECLARE

i INTEGER := 0;

BEGIN
CREATE TABLE person(
        id     BIGSERIAL NOT NULL PRIMARY KEY,
        name   TEXT,
        add    TEXT,
        phones TEXT[]
);
WHILE i<50
    LOOP
        i:=i+1;
insert into person(name, add, phones) values (concat('name',i::text), 'init',
                                              CASE WHEN i % 3 = 0
                                                  THEN ARRAY [repeat('1', 3) || '-' || repeat('2', 3) || '-' || repeat('3', 3)]
                                                  WHEN i % 3 = 1 THEN ARRAY [repeat('1', 3) || '-' || repeat('2', 3) || '-' || repeat('3', 3), repeat('4', 3) || '-' || repeat('5', 3) || '-' || repeat('6', 3)]
                                                  ELSE ARRAY [repeat('1', 3) || '-' || repeat('2', 3) || '-' || repeat('3', 3), repeat('4', 3) || '-' || repeat('5', 3) || '-' || repeat('6', 3), repeat('7', 3) || '-' || repeat('8', 3) || '-' || repeat('9', 3)] END
                                              );
END LOOP;
CREATE TABLE history
(
        id     BIGSERIAL PRIMARY KEY,
        name   TEXT,
        add    TEXT,
        phones TEXT[]
);

END;

$$;


ALTER PROCEDURE public.insert_data() OWNER TO postgres;

--
-- Name: trigger_func(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.trigger_func() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
    IF TG_OP = 'INSERT' THEN
        INSERT INTO history(name, add, phones, type, person_id)
        VALUES (NEW.name, NEW.name, NEW.phones, 'insert', NEW.id);
    END IF;

    IF TG_OP = 'DELETE' THEN
        INSERT INTO history(name, add, phones, type, person_id)
        VALUES (OLD.name, OLD.name, OLD.phones, 'deleted', OLD.id);
    END IF;

RETURN NEW;
END;
$$;


ALTER FUNCTION public.trigger_func() OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: repository; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.repository (
    id bigint NOT NULL,
    repository_name text,
    created timestamp without time zone DEFAULT now()
);


ALTER TABLE public.repository OWNER TO postgres;

--
-- Name: repository_info; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.repository_info (
    id bigint NOT NULL,
    repository_id bigint,
    label_id bigint,
    value text,
    created timestamp without time zone DEFAULT now()
);


ALTER TABLE public.repository_info OWNER TO postgres;

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
-- Name: history; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.history (
    id bigint NOT NULL,
    name text,
    add text,
    phones text[],
    type text,
    person_id bigint
);


ALTER TABLE public.history OWNER TO postgres;

--
-- Name: history_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.history_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.history_id_seq OWNER TO postgres;

--
-- Name: history_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.history_id_seq OWNED BY public.history.id;


--
-- Name: person; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.person (
    id bigint NOT NULL,
    name text,
    add text,
    phones text[]
);


ALTER TABLE public.person OWNER TO postgres;

--
-- Name: person_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.person_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.person_id_seq OWNER TO postgres;

--
-- Name: person_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.person_id_seq OWNED BY public.person.id;


--
-- Name: person_ui; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.person_ui AS
 SELECT person.id,
    person.name,
    person.add,
    person.phones
   FROM public.person;


ALTER TABLE public.person_ui OWNER TO postgres;

--
-- Name: repository_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.repository_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.repository_id_seq OWNER TO postgres;

--
-- Name: repository_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.repository_id_seq OWNED BY public.repository.id;


--
-- Name: repository_info_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.repository_info_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.repository_info_id_seq OWNER TO postgres;

--
-- Name: repository_info_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.repository_info_id_seq OWNED BY public.repository_info.id;


--
-- Name: repository_info_type; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.repository_info_type (
    id bigint NOT NULL,
    label text,
    description text,
    type text,
    pattern_file text,
    pattern_content text,
    created timestamp without time zone DEFAULT now()
);


ALTER TABLE public.repository_info_type OWNER TO postgres;

--
-- Name: repository_info_type_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.repository_info_type_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.repository_info_type_id_seq OWNER TO postgres;

--
-- Name: repository_info_type_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.repository_info_type_id_seq OWNED BY public.repository_info_type.id;


--
-- Name: history id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.history ALTER COLUMN id SET DEFAULT nextval('public.history_id_seq'::regclass);


--
-- Name: person id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.person ALTER COLUMN id SET DEFAULT nextval('public.person_id_seq'::regclass);


--
-- Name: repository id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.repository ALTER COLUMN id SET DEFAULT nextval('public.repository_id_seq'::regclass);


--
-- Name: repository_info id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.repository_info ALTER COLUMN id SET DEFAULT nextval('public.repository_info_id_seq'::regclass);


--
-- Name: repository_info_type id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.repository_info_type ALTER COLUMN id SET DEFAULT nextval('public.repository_info_type_id_seq'::regclass);


--
-- Data for Name: history; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.history (id, name, add, phones, type, person_id) FROM stdin;
1	name57	name57	{111-222-333,444-555-666}	insert	57
3	name52	name52	{129-123-133}	deleted	52
\.


--
-- Data for Name: person; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.person (id, name, add, phones) FROM stdin;
1	name1	init	{111-222-333,444-555-666}
2	name2	init	{111-222-333,444-555-666,777-888-999}
4	name4	init	{111-222-333,444-555-666}
5	name5	init	{111-222-333,444-555-666,777-888-999}
7	name7	init	{111-222-333,444-555-666}
8	name8	init	{111-222-333,444-555-666,777-888-999}
10	name10	init	{111-222-333,444-555-666}
11	name11	init	{111-222-333,444-555-666,777-888-999}
13	name13	init	{111-222-333,444-555-666}
14	name14	init	{111-222-333,444-555-666,777-888-999}
16	name16	init	{111-222-333,444-555-666}
17	name17	init	{111-222-333,444-555-666,777-888-999}
19	name19	init	{111-222-333,444-555-666}
20	name20	init	{111-222-333,444-555-666,777-888-999}
22	name22	init	{111-222-333,444-555-666}
23	name23	init	{111-222-333,444-555-666,777-888-999}
25	name25	init	{111-222-333,444-555-666}
26	name26	init	{111-222-333,444-555-666,777-888-999}
28	name28	init	{111-222-333,444-555-666}
29	name29	init	{111-222-333,444-555-666,777-888-999}
31	name31	init	{111-222-333,444-555-666}
32	name32	init	{111-222-333,444-555-666,777-888-999}
34	name34	init	{111-222-333,444-555-666}
35	name35	init	{111-222-333,444-555-666,777-888-999}
37	name37	init	{111-222-333,444-555-666}
38	name38	init	{111-222-333,444-555-666,777-888-999}
40	name40	init	{111-222-333,444-555-666}
41	name41	init	{111-222-333,444-555-666,777-888-999}
43	name43	init	{111-222-333,444-555-666}
44	name44	init	{111-222-333,444-555-666,777-888-999}
46	name46	init	{111-222-333,444-555-666}
47	name47	init	{111-222-333,444-555-666,777-888-999}
49	name49	init	{111-222-333,444-555-666}
50	name50	init	{111-222-333,444-555-666,777-888-999}
53	name53	init2	{129-122-122}
56	name56	init23	{129-122-122}
3	name3	init del fired	{111-222-333}
6	name6	init del fired	{111-222-333}
9	name9	init del fired	{111-222-333}
12	name12	init del fired	{111-222-333}
15	name15	init del fired	{111-222-333}
18	name18	init del fired	{111-222-333}
21	name21	init del fired	{111-222-333}
24	name24	init del fired	{111-222-333}
27	name27	init del fired	{111-222-333}
30	name30	init del fired	{111-222-333}
33	name33	init del fired	{111-222-333}
36	name36	init del fired	{111-222-333}
39	name39	init del fired	{111-222-333}
42	name42	init del fired	{111-222-333}
45	name45	init del fired	{111-222-333}
48	name48	init del fired	{111-222-333}
51	name51	init del fired	{123-123-133}
57	name57	init del fired	{111-222-333,444-555-666}
\.


--
-- Data for Name: repository; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.repository (id, repository_name, created) FROM stdin;
2	ETP.PH	2021-07-01 17:43:57.654
3	ETP.PPENS_INF	2021-07-01 17:43:57.908
4	ETP.RCOI	2021-07-01 17:43:58.629
5	ETP.RestData	2021-07-01 17:43:59.388
6	ETP.RPR	2021-07-01 17:44:01.785
7	ETP.SK2_DWH	2021-07-01 17:44:02.141
8	ETP.SNILS_ONLINE	2021-07-01 17:44:02.425
9	ETP.TAXI	2021-07-01 17:44:03.096
10	ETP.UMS	2021-07-01 17:44:03.586
11	MMTS	2021-07-01 17:44:05.177
\.


--
-- Data for Name: repository_info; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.repository_info (id, repository_id, label_id, value, created) FROM stdin;
1	2	1	10	2021-07-01 17:43:57.659
2	2	2	11451	2021-07-01 17:43:57.659
3	2	3	18	2021-07-01 17:43:57.659
4	2	4	14	2021-07-01 17:43:57.659
5	2	5	/ETP.PH.Bar/ph_dev.bar	2021-07-01 17:43:57.659
6	2	5	/ETP.PH.Bar/ph_prod.bar	2021-07-01 17:43:57.659
7	2	9	/ETP.PH.AgAdapter/store_ag.mqsc	2021-07-01 17:43:57.659
8	2	9	/ETP.PH.Service/ph.mqsc	2021-07-01 17:43:57.659
9	2	9	/ETP.PH.Service/ph_asguf.mqsc	2021-07-01 17:43:57.659
10	2	9	/ETP.PH.Service/ph_mpgu.mqsc	2021-07-01 17:43:57.659
11	2	9	/HBaseAgStore/hbase_ag.mqsc	2021-07-01 17:43:57.668
12	2	10	/ETP.PH.CacheRest/pom.xml	2021-07-01 17:43:57.668
13	2	10	/HBaseAgStore/pom.xml	2021-07-01 17:43:57.668
14	2	15	051202	2021-07-01 17:43:57.668
15	2	15	051201	2021-07-01 17:43:57.668
16	2	16	/ETP.PH.LIB/fromAG_OUT/pers_inf.xsd	2021-07-01 17:43:57.668
17	2	16	/ETP.PH.LIB/fromAG_OUT/register_meeting_req.xsd	2021-07-01 17:43:57.668
18	2	16	/ETP.PH.LIB/fromAG_OUT/voting_req.xsd	2021-07-01 17:43:57.668
19	2	16	/ETP.PH.LIB/public_hearings_req.xsd	2021-07-01 17:43:57.668
20	2	16	/ETP.PH.LIB/public_hearing_req.xsd	2021-07-01 17:43:57.668
21	2	16	/ETP.PH.LIB/register_meeting_res.xsd	2021-07-01 17:43:57.668
22	2	16	/ETP.PH.LIB/toAG_INC/attend_meeting_req.xsd	2021-07-01 17:43:57.668
23	2	16	/ETP.PH.LIB/toAG_INC/register_meeting_req.xsd	2021-07-01 17:43:57.668
24	2	16	/ETP.PH.LIB/toAG_INC/voting_req.xsd	2021-07-01 17:43:57.668
25	2	16	/ETP.PH.LIB/v5_2_051201.xsd	2021-07-01 17:43:57.668
26	2	16	/ETP.PH.LIB/v5_2_051202.xsd	2021-07-01 17:43:57.668
27	2	16	/ETP.PH.LIB/voting_res.xsd	2021-07-01 17:43:57.668
28	2	16	/ETP.PH.XMLConverter/schema/051201.xsd	2021-07-01 17:43:57.668
29	3	1	5	2021-07-01 17:43:57.908
30	3	2	1085	2021-07-01 17:43:57.908
31	3	3	0	2021-07-01 17:43:57.908
32	3	4	4	2021-07-01 17:43:57.908
33	3	5	/ETP.PPENS_INF.Bar/etp.ppens_inf_cnv.bar	2021-07-01 17:43:57.908
34	3	5	/ETP.PPENS_INF.Bar/etp.ppens_inf_src.bar	2021-07-01 17:43:57.908
35	3	9	/mqsc/ETPQM01/ppens_inf.mqsc	2021-07-01 17:43:57.908
36	3	9	/mqsc/GU01QM/ppens_inf_srvc.mqsc	2021-07-01 17:43:57.908
37	3	15	068101	2021-07-01 17:43:57.908
38	3	16	/ETP.PPENS_INF.Converter/068101.xsd	2021-07-01 17:43:57.908
39	3	16	/ETP.PPENS_INF.Converter/pre-retirement-age-1.0.1.xsd	2021-07-01 17:43:57.908
40	3	16	/ETP.PPENS_INF.Converter/smev-supplementary-commons-1.0.1.xsd	2021-07-01 17:43:57.908
41	3	16	/ETP.PPENS_INF.Service/pre-retirement-age-1.0.1.xsd	2021-07-01 17:43:57.908
42	3	16	/ETP.PPENS_INF.Service/smev-supplementary-commons-1.0.1.xsd	2021-07-01 17:43:57.908
43	4	1	9	2021-07-01 17:43:58.629
44	4	2	3976	2021-07-01 17:43:58.634
45	4	3	14	2021-07-01 17:43:58.634
46	4	4	3	2021-07-01 17:43:58.634
47	4	5	/ETP.RCOI.Bar/rcoi.bar	2021-07-01 17:43:58.634
48	4	5	/ETP.RCOI.Bar/rcoi_prod.bar	2021-07-01 17:43:58.634
49	4	8	/EFP.RCOI.EfspAdapter/Adapter/docker/rcoi/buildfiles/Dockerfile	2021-07-01 17:43:58.634
50	4	9	/mqsc/GU01QM/rcoi.mqsc	2021-07-01 17:43:58.634
51	4	10	/EFP.DROOLS.RCOI/pom.xml	2021-07-01 17:43:58.634
52	4	10	/EFP.RCOI.EfspAdapter/Adapter/pom.xml	2021-07-01 17:43:58.634
127	9	3	41	2021-07-01 17:44:03.098
128	9	4	2	2021-07-01 17:44:03.098
129	9	5	/ETP.TAXI.Bar/taxi.bar	2021-07-01 17:44:03.098
130	9	5	/ETP.TAXI.Bar/taxi_dev.bar	2021-07-01 17:44:03.099
131	9	7	/mqsc/GU01QM/taxi_br.mqsc	2021-07-01 17:44:03.099
132	9	8	/ETP.Taxi.InfinispanRest/Dockerfile	2021-07-01 17:44:03.1
133	9	8	/ETP.Taxi.PgToInSpan/Dockerfile	2021-07-01 17:44:03.1
134	9	9	/ETP.TAXI.Service/scripts/taxi.mqsc	2021-07-01 17:44:03.101
135	9	9	/mqsc/GU01QM/taxi_br.mqsc	2021-07-01 17:44:03.101
136	9	10	/ETP.TAXI.CacheRest/pom.xml	2021-07-01 17:44:03.101
137	9	10	/ETP.Taxi.InfinispanRest/pom.xml	2021-07-01 17:44:03.102
138	9	10	/ETP.Taxi.PgToInSpan/pom.xml	2021-07-01 17:44:03.102
139	9	11	/ETP.TAXI.Service/scripts/taxi.mqsc	2021-07-01 17:44:03.103
140	9	15	77200101	2021-07-01 17:44:03.103
141	9	15	020204	2021-07-01 17:44:03.103
142	9	15	020203	2021-07-01 17:44:03.103
143	9	15	020202	2021-07-01 17:44:03.104
144	9	14	CLIENT.TAXI.SVRCONN	2021-07-01 17:44:03.104
145	9	13	9000062	2021-07-01 17:44:03.104
146	10	1	8	2021-07-01 17:44:03.586
147	10	2	3296	2021-07-01 17:44:03.587
148	10	3	13	2021-07-01 17:44:03.588
53	4	10	/EFP.RCOI.EfspAdapter/pom.xml	2021-07-01 17:43:58.639
54	4	11	/mqsc/GU01QM/rcoi.mqsc	2021-07-01 17:43:58.639
55	4	15	040205	2021-07-01 17:43:58.639
56	4	15	040204	2021-07-01 17:43:58.639
57	4	15	040206	2021-07-01 17:43:58.639
58	4	15	066201	2021-07-01 17:43:58.639
59	4	16	/ETP.RCOI.Service/rcoi_v10.xsd	2021-07-01 17:43:58.639
60	4	16	/ETP.RCOI.Service/subscription.xsd	2021-07-01 17:43:58.639
61	4	14	CLIENT.RCOI.SVRCONN	2021-07-01 17:43:58.639
62	5	1	11	2021-07-01 17:43:59.388
63	5	2	33195	2021-07-01 17:43:59.388
64	5	3	64	2021-07-01 17:43:59.388
65	5	4	0	2021-07-01 17:43:59.388
66	5	8	/ETP.RestData.CacheRest/Dockerfile	2021-07-01 17:43:59.388
67	5	8	/ETP.RestData.SearchContact/Dockerfile	2021-07-01 17:43:59.388
68	5	10	/ETP.RestData.CacheRest/pom.xml	2021-07-01 17:43:59.388
69	5	10	/ETP.RestData.JspConf/pom.xml	2021-07-01 17:43:59.388
70	5	10	/ETP.RestData.ProftechCD/pom.xml	2021-07-01 17:43:59.388
71	5	10	/ETP.RestData.SearchContact/pom.xml	2021-07-01 17:43:59.388
72	5	10	/MessageSender/pom.xml	2021-07-01 17:43:59.388
73	6	1	9	2021-07-01 17:44:01.785
74	6	2	6864	2021-07-01 17:44:01.786
75	6	3	113	2021-07-01 17:44:01.786
76	6	4	21	2021-07-01 17:44:01.787
77	6	5	/ETP.RPR.Bar/rpr.bar	2021-07-01 17:44:01.787
78	6	5	/ETP.RPR.Bar/rpr_cnv.bar	2021-07-01 17:44:01.788
79	6	9	/ETP.RPR.Converter/rpr_cnv.mqsc	2021-07-01 17:44:01.788
80	6	9	/ETP.RPR.Service/add_etpclusterqm.mqsc	2021-07-01 17:44:01.789
81	6	9	/ETP.RPR.Service/rpr.mqsc	2021-07-01 17:44:01.789
82	6	9	/ETP.RPR.Service/rpr_aismfc.mqsc	2021-07-01 17:44:01.79
83	6	10	/RprETL/RPRETL/poms/code/routines/pom.xml	2021-07-01 17:44:01.79
84	6	10	/RprETL/RPRETL/poms/jobs/process/copytable_0.1/pom.xml	2021-07-01 17:44:01.791
85	6	10	/RprETL/RPRETL/poms/jobs/process/copytable_0.2/pom.xml	2021-07-01 17:44:01.791
86	6	10	/RprETL/RPRETL/poms/pom.xml	2021-07-01 17:44:01.792
87	6	10	/RprFront/pom.xml	2021-07-01 17:44:01.792
88	6	11	/ETP.RPR.Service/add_etpclusterqm.mqsc	2021-07-01 17:44:01.792
89	6	11	/ETP.RPR.Service/rpr.mqsc	2021-07-01 17:44:01.793
90	6	15	060101	2021-07-01 17:44:01.793
91	6	15	060102	2021-07-01 17:44:01.793
92	6	15	043301	2021-07-01 17:44:01.794
93	6	16	/ETP.RPR.Service/10466.xsd	2021-07-01 17:44:01.794
94	6	16	/ETP.RPR.XMLConverter/schema/043301_rpr.xsd	2021-07-01 17:44:01.794
95	6	16	/ETP.RPR.XMLConverter/schema/060101_rpr.xsd	2021-07-01 17:44:01.795
96	6	16	/ETP.RPR.XMLConverter/schema/060102_rpr.xsd	2021-07-01 17:44:01.795
97	6	14	QM1CH	2021-07-01 17:44:01.796
98	6	14	QM4CH	2021-07-01 17:44:01.796
99	6	14	CLIENT.RPR.SVRCONN	2021-07-01 17:44:01.796
100	7	1	7	2021-07-01 17:44:02.142
101	7	2	122440	2021-07-01 17:44:02.142
102	7	3	9	2021-07-01 17:44:02.143
103	7	4	1	2021-07-01 17:44:02.143
104	7	5	/ETP.SK2_DWH.Bar/sk2_dwh.bar	2021-07-01 17:44:02.144
105	7	8	/Sk2Proxy/Dockerfile	2021-07-01 17:44:02.144
106	7	9	/ETP.SK2_DWH.DwhSender/scripts/sk2_dwh.mqsc	2021-07-01 17:44:02.145
107	7	10	/Sk2Proxy/pom.xml	2021-07-01 17:44:02.145
108	8	1	8	2021-07-01 17:44:02.426
109	8	2	5342	2021-07-01 17:44:02.426
110	8	3	2	2021-07-01 17:44:02.427
111	8	4	6	2021-07-01 17:44:02.427
112	8	5	/ETP.SNILS_ONLINE.Bar/snils_online_service.bar	2021-07-01 17:44:02.427
113	8	5	/ETP.SNILS_ONLINE_CNV.ETPCLUSTER.Bar/snils_online_cnv.bar	2021-07-01 17:44:02.428
114	8	9	/ETP.SNILS_ONLINE.Converter/snils_online_cnv.mqsc	2021-07-01 17:44:02.428
115	8	9	/ETP.SNILS_ONLINE.Service/snils_online.mqsc	2021-07-01 17:44:02.429
116	8	15	051105	2021-07-01 17:44:02.429
117	8	15	051106	2021-07-01 17:44:02.429
118	8	15	051107	2021-07-01 17:44:02.43
119	8	16	/ETP.SNILS_ONLINE.LIB/attachments-description-mfc-status-1.0.0.xsd	2021-07-01 17:44:02.43
120	8	16	/ETP.SNILS_ONLINE.LIB/mfc-1.0.1.xsd	2021-07-01 17:44:02.43
121	8	16	/ETP.SNILS_ONLINE.LIB/mfc-attachments-1.0.0.xsd	2021-07-01 17:44:02.431
122	8	16	/ETP.SNILS_ONLINE.LIB/mfc-status-1.0.2.xsd	2021-07-01 17:44:02.431
123	8	16	/ETP.SNILS_ONLINE.LIB/smev-supplementary-commons-1.0.1.xsd	2021-07-01 17:44:02.432
124	8	16	/ETP.SNILS_ONLINE.LIB/v5_3_051105_ServiceProperties.xsd	2021-07-01 17:44:02.432
125	9	1	9	2021-07-01 17:44:03.097
126	9	2	5466	2021-07-01 17:44:03.097
149	10	4	5	2021-07-01 17:44:03.588
150	10	5	/ETP.UMS.Bar/ums.bar	2021-07-01 17:44:03.588
151	10	8	/EFP.UMS.EfspAdapter/docker/buildfiles/Dockerfile	2021-07-01 17:44:03.589
152	10	9	/ETP.UMS.Service/scripts/ums.mqsc	2021-07-01 17:44:03.589
153	10	9	/ETP.UMS.Service/scripts/ums_aismfc_erl.mqsc	2021-07-01 17:44:03.59
154	10	9	/mqsc/GU01QM/ums.mqsc	2021-07-01 17:44:03.59
155	10	10	/EFP.UMS.EfspAdapter/pom.xml	2021-07-01 17:44:03.59
156	10	15	006703	2021-07-01 17:44:03.591
157	10	15	006701	2021-07-01 17:44:03.591
158	10	16	/ETP.UMS.Service/aiserl.xsd	2021-07-01 17:44:03.592
159	10	16	/ETP.UMS.Service/xsd/20_divorce.xsd	2021-07-01 17:44:03.592
160	10	16	/ETP.UMS.Service/xsd/22_20018_birth.xsd	2021-07-01 17:44:03.593
161	10	16	/ETP.UMS.Service/xsd/7758_name_change.xsd	2021-07-01 17:44:03.593
162	10	16	/ETP.UMS.Service/xsd/7796_deth.xsd	2021-07-01 17:44:03.594
163	10	16	/ETP.UMS.Service/xsd/80_death.xsd	2021-07-01 17:44:03.594
164	10	16	/ETP.UMS.Service/xsd/aismfc.xsd	2021-07-01 17:44:03.594
165	10	16	/ETP.UMS.Service/xsd/dszn17.xsd	2021-07-01 17:44:03.595
166	10	16	/ETP.UMS.Service/xsd/Dul.xsd	2021-07-01 17:44:03.595
167	10	16	/ETP.UMS.Service/xsd/ums.xsd	2021-07-01 17:44:03.595
168	11	1	17	2021-07-01 17:44:05.178
169	11	2	26465	2021-07-01 17:44:05.178
170	11	3	179	2021-07-01 17:44:05.178
171	11	4	0	2021-07-01 17:44:05.179
172	11	9	/mkr.mqsc	2021-07-01 17:44:05.179
173	11	9	/mqsc/PIVQM/KSP.OPER_REPORT.mqsc	2021-07-01 17:44:05.18
174	11	9	/mqsc/PIVQM/KSP.REPORT.mqsc	2021-07-01 17:44:05.18
175	11	9	/mqsc/PIVQM/MLC.REPORT.mqsc	2021-07-01 17:44:05.181
176	11	10	/data-display/pom.xml	2021-07-01 17:44:05.181
177	11	10	/entity/pom.xml	2021-07-01 17:44:05.181
178	11	10	/execution-service/pom.xml	2021-07-01 17:44:05.182
179	11	10	/mail/pom.xml	2021-07-01 17:44:05.182
180	11	10	/parser-core/pom.xml	2021-07-01 17:44:05.182
181	11	10	/pom.xml	2021-07-01 17:44:05.183
182	11	10	/queue-parser/pom.xml	2021-07-01 17:44:05.183
183	11	10	/request-notification/pom.xml	2021-07-01 17:44:05.184
184	11	10	/request-parser/pom.xml	2021-07-01 17:44:05.184
185	11	10	/rest-proxy/pom.xml	2021-07-01 17:44:05.185
186	11	10	/rpc/pom.xml	2021-07-01 17:44:05.185
187	11	11	/mkr.mqsc	2021-07-01 17:44:05.186
188	11	14	PIV.DEF.SVRCONN	2021-07-01 17:44:05.186
189	11	14	SVDSMRT.SVRCONN	2021-07-01 17:44:05.187
190	11	14	MMTS.DEF.SVRCONN	2021-07-01 17:44:05.187
\.


--
-- Data for Name: repository_info_type; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.repository_info_type (id, label, description, type, pattern_file, pattern_content, created) FROM stdin;
12	POM_ROOT	Путь внутри директории для каждого файла с названием pom.xml и имеющего внутри структуру\r\n<modules>\r\n<module>my-module</module>\r\n</modules>	IS_FILE_EXISTS	CUSTOM	.*<modules>.*	2021-07-01 14:43:39.550978
1	SUBDIRECTORY_COUNT	Вернуть количество директорий в корне репозитория, без учета генерируемых, как .idea, target, .gradle, node_modules и тп	CUSTOM	\N	\N	2021-07-01 14:43:39.550978
2	DIRECTORY_SIZE	Вернуть размер файлов в директории в Кб, без учета генерируемых, как .idea, target, .gradle, node_modules и тп	CUSTOM	\N	\N	2021-07-01 14:43:39.550978
3	STAT_JAVA	Количество файлов оканчивающихся на .java	COUNT	.+\\.java$	\N	2021-07-01 14:43:39.550978
4	STAT_MSGFLOW	Количество файлов оканчивающихся на .msgflow	COUNT	.+\\.msgflow	\N	2021-07-01 14:43:39.550978
5	ETP_PROCESS	Путь внутри директории для каждого файла с расширением .bar	IS_FILE_EXISTS	.+\\.bar$	\N	2021-07-01 14:43:39.550978
6	EFP_PROCESS_BY_NAME	Путь внутри директории дo директории оканчивающейся на EfspAdapter	IS_FILE_EXISTS	.+\\.EfspAdapter$	\N	2021-07-01 14:43:39.550978
7	BR_CLUSTER	Путь внутри директории для каждого файла с расширением .mqsc, содержащий внутри строку вида DEFINE QLOCAL ('Asur.Isur.XXXX.CoorV6.1.Output')	IS_FILE_EXISTS	.+\\.mqsc$	^DEFINE QLOCAL [(]'Asur[.]Isur[.][0-9]{7}[.]CoorV6[.]1[.]Output'[)].*	2021-07-01 14:43:39.550978
8	DOCKERFILE	Путь внутри директории дo файла называющегося Dockerfile	IS_FILE_EXISTS	^Dockerfile$	\N	2021-07-01 14:43:39.550978
9	MQSC	Путь внутри директории для каждого файла с расширением .mqsc	IS_FILE_EXISTS	.+\\.mqsc$	\N	2021-07-01 14:43:39.550978
10	POM	Путь внутри директории дo файла называющегося pom.xml	IS_FILE_EXISTS	^pom.xml$	\N	2021-07-01 14:43:39.550978
11	ETP_CHANNEL_PATH	Путь внутри директории для каждого файла с расширением .mqsc, содержащий внутри строку вида DEFINE CHANNEL	IS_FILE_EXISTS	.+\\.mqsc$	^DEFINE CHANNEL.*	2021-07-01 14:43:39.550978
15	GU_SERVICE_CODE_ETP	Необходимо получить коды ГУ из файлов с расширением .sql, которые содержат команды insert в любые таблицы, среди наименований столбцов должен быть servicetypecode или formid.	CUSTOM	.+\\.sql$	\N	2021-07-01 14:43:39.550978
16	XSD	Путь внутри директории для каждого файла с расширением .xsd	IS_FILE_EXISTS	.+\\.xsd$	\N	2021-07-01 14:43:39.550978
14	ETP_CHANNEL_NAME	Необходимо получить каналы подключения из файлов .mqsc, которые содержат строку вида "DEFINE CHANNEL(CLIENT.RCOI.SVRCONN) CHLTYPE(SVRCONN) MCAUSER('rcoi') TRPTYPE(TCP) REPLACE;", Необходимо сохранить в БД значение "CLIENT.RCOI.SVRCONN"	CUSTOM	.+\\.mqsc$	(?<=DEFINE CHANNEL)(\\()(.*?)(\\)).*	2021-07-01 14:43:39.550978
13	BR_SYSTEM_CODE	Необходимо получить коды системы из файлов .mqsc, которые содержат строку вида DEFINE QLOCAL ('Asur.Isur.XXXX.CoorV6.1.Output') - необходимо сохранить в value код XXXX	CUSTOM	.+\\.mqsc$	(?<=DEFINE QLOCAL [(]'Asur[.]Isur[.])\\s*([0-9]+)\\s*(?=[.]CoorV6[.]1[.]Output'[)].*)	2021-07-01 14:43:39.550978
\.


--
-- Name: history_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.history_id_seq', 3, true);


--
-- Name: person_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.person_id_seq', 51, true);


--
-- Name: repository_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.repository_id_seq', 11, true);


--
-- Name: repository_info_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.repository_info_id_seq', 190, true);


--
-- Name: repository_info_type_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.repository_info_type_id_seq', 16, true);


--
-- Name: history history_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.history
    ADD CONSTRAINT history_pkey PRIMARY KEY (id);


--
-- Name: person person_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.person
    ADD CONSTRAINT person_pkey PRIMARY KEY (id);


--
-- Name: repository_info repository_info_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.repository_info
    ADD CONSTRAINT repository_info_pkey PRIMARY KEY (id);


--
-- Name: repository_info_type repository_info_type_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.repository_info_type
    ADD CONSTRAINT repository_info_type_pkey PRIMARY KEY (id);


--
-- Name: repository repository_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.repository
    ADD CONSTRAINT repository_pkey PRIMARY KEY (id);


--
-- Name: person view_trigger; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER view_trigger AFTER INSERT OR DELETE ON public.person FOR EACH ROW EXECUTE FUNCTION public.trigger_func();


--
-- Name: repository_info repository_info_label_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.repository_info
    ADD CONSTRAINT repository_info_label_id_fkey FOREIGN KEY (label_id) REFERENCES public.repository_info_type(id);


--
-- Name: repository_info repository_info_repository_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.repository_info
    ADD CONSTRAINT repository_info_repository_id_fkey FOREIGN KEY (repository_id) REFERENCES public.repository(id);


--
-- Name: agg_repository_ui; Type: MATERIALIZED VIEW DATA; Schema: public; Owner: postgres
--

REFRESH MATERIALIZED VIEW public.agg_repository_ui;


--
-- PostgreSQL database dump complete
--

