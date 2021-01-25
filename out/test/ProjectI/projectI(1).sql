--
-- PostgreSQL database dump
--

-- Dumped from database version 13.1
-- Dumped by pg_dump version 13.1

-- Started on 2021-01-24 19:34:52

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
-- TOC entry 2 (class 3079 OID 16384)
-- Name: adminpack; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS adminpack WITH SCHEMA pg_catalog;


--
-- TOC entry 3053 (class 0 OID 0)
-- Dependencies: 2
-- Name: EXTENSION adminpack; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION adminpack IS 'administrative functions for PostgreSQL';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 201 (class 1259 OID 16394)
-- Name: BangCap; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."BangCap" (
    "ID" character varying NOT NULL,
    ten character varying NOT NULL,
    totnghiep boolean NOT NULL,
    cosocap character varying NOT NULL,
    chuyennganh character varying NOT NULL,
    chuyenmon character varying NOT NULL,
    minhchung character varying NOT NULL,
    nam integer NOT NULL,
    hsd integer NOT NULL,
    level character varying NOT NULL
);


ALTER TABLE public."BangCap" OWNER TO postgres;

--
-- TOC entry 202 (class 1259 OID 16400)
-- Name: ChuyenMon; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."ChuyenMon" (
    nhom character varying NOT NULL,
    chuyennganh character varying NOT NULL,
    chuyenmon character varying NOT NULL
);


ALTER TABLE public."ChuyenMon" OWNER TO postgres;

--
-- TOC entry 203 (class 1259 OID 16406)
-- Name: CongTy; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."CongTy" (
    mact character varying NOT NULL,
    ten character varying NOT NULL,
    diachi character varying NOT NULL,
    quocgia character varying NOT NULL
);


ALTER TABLE public."CongTy" OWNER TO postgres;

--
-- TOC entry 204 (class 1259 OID 16412)
-- Name: EmployeeInformation; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."EmployeeInformation" (
    "ID" character varying NOT NULL,
    trinhdo character varying NOT NULL,
    chucvu character varying NOT NULL,
    mahdld character varying NOT NULL,
    hoten character varying NOT NULL
);


ALTER TABLE public."EmployeeInformation" OWNER TO postgres;

--
-- TOC entry 205 (class 1259 OID 16418)
-- Name: HDLD; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."HDLD" (
    mahdld character varying NOT NULL,
    "from" date NOT NULL,
    "to" date NOT NULL
);


ALTER TABLE public."HDLD" OWNER TO postgres;

--
-- TOC entry 206 (class 1259 OID 16424)
-- Name: LichSuCongTac; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."LichSuCongTac" (
    "ID" character varying NOT NULL,
    tenduan character varying NOT NULL,
    chuyennganh character varying NOT NULL,
    "from" integer NOT NULL,
    mact character varying NOT NULL,
    chuyenmon character varying NOT NULL,
    vitri character varying NOT NULL,
    "to" integer NOT NULL,
    minhchung character varying NOT NULL
);


ALTER TABLE public."LichSuCongTac" OWNER TO postgres;

--
-- TOC entry 207 (class 1259 OID 16430)
-- Name: PrivateEmployeeInformation; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."PrivateEmployeeInformation" (
    "ID" character varying NOT NULL,
    ngaysinh date NOT NULL,
    quequan character varying NOT NULL,
    gioitinh character varying NOT NULL,
    diachi character varying NOT NULL
);


ALTER TABLE public."PrivateEmployeeInformation" OWNER TO postgres;

--
-- TOC entry 208 (class 1259 OID 16496)
-- Name: Tinnhan; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Tinnhan" (
    matn character varying NOT NULL,
    tinnhan character varying NOT NULL
);


ALTER TABLE public."Tinnhan" OWNER TO postgres;

--
-- TOC entry 209 (class 1259 OID 16850)
-- Name: temp1; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.temp1 AS
 SELECT main."ID" AS id,
    main.hoten,
    main.trinhdo,
    main.chucvu,
    main.mahdld,
    bangcap."ID",
    bangcap.ten,
    bangcap.totnghiep,
    bangcap.cosocap,
    bangcap.chuyennganh,
    bangcap.chuyenmon,
    bangcap.minhchung,
    bangcap.nam,
    bangcap.hsd,
    bangcap.level
   FROM (public."EmployeeInformation" main
     LEFT JOIN public."BangCap" bangcap ON (((main."ID")::text = (bangcap."ID")::text)));


ALTER TABLE public.temp1 OWNER TO postgres;

--
-- TOC entry 3040 (class 0 OID 16394)
-- Dependencies: 201
-- Data for Name: BangCap; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."BangCap" ("ID", ten, totnghiep, cosocap, chuyennganh, chuyenmon, minhchung, nam, hsd, level) FROM stdin;
65662182	Information Engineering Certification	t	Hanoi University of Science and Technology	Information Technology			2015	0	Good
45804397	Information Engineering Certification	t	Hanoi University of Science and Technology	Information Technology			2015	0	Good
48582995	Information Engineering Certification	t	Hanoi University of Science and Technology	Information Technology			2015	0	Good
53933874	Information Engineering Certification	t	Hanoi University of Science and Technology	Information Technology			2015	0	Good
34214633	Information Engineering Certification	t	Hanoi University of Science and Technology	Information Technology			2015	0	Good
87038927	Information Engineering Certification	t	Hanoi University of Science and Technology	Information Technology			2015	0	Good
78703964	Information Engineering Certification	t	Hanoi University of Science and Technology	Information Technology			2015	0	Good
87380316	Information Engineering Certification	t	Hanoi University of Science and Technology	Information Technology			2015	0	Good
17063906	Information Engineering Certification	t	Hanoi University of Science and Technology	Information Technology			2015	0	Good
84705815	Information Engineering Certification	t	Hanoi University of Science and Technology	Information Technology			2015	0	Good
02528076	Information Engineering Certification	t	Hanoi University of Science and Technology	Information Technology			2015	0	Good
01036022	Information Engineering Certification	t	Hanoi University of Science and Technology	Information Technology			2015	0	Good
42472735	Information Engineering Certification	t	Hanoi University of Science and Technology	Information Technology			2015	0	Good
05270576	Information Engineering Certification	t	Hanoi University of Science and Technology	Information Technology			2015	0	Good
84413208	Information Engineering Certification	t	Hanoi University of Science and Technology	Information Technology			2015	0	Good
11041894	Information Engineering Certification	t	Hanoi University of Science and Technology	Information Technology			2015	0	Good
38441042	Information Engineering Certification	t	Hanoi University of Science and Technology	Information Technology			2015	0	Good
73534266	Information Engineering Certification	t	Hanoi University of Science and Technology	Information Technology			2015	0	Good
15646545	Information Engineering Certification	t	Hanoi University of Science and Technology	Information Technology			2015	0	Good
08303011	Information Engineering Certification	t	Hanoi University of Science and Technology	Information Technology			2015	0	Good
16858190	Information Engineering Certification	t	Hanoi University of Science and Technology	Information Technology			2015	0	Good
65105979	Information Engineering Certification	t	Hanoi University of Science and Technology	Information Technology			2015	0	Good
02982236	Information Engineering Certification	t	Hanoi University of Science and Technology	Information Technology			2015	0	Good
11496441	Information Engineering Certification	t	Hanoi University of Science and Technology	Information Technology			2015	0	Good
88533735	Information Engineering Certification	t	Hanoi University of Science and Technology	Information Technology			2015	0	Good
29566679	Information Engineering Certification	t	Hanoi University of Science and Technology	Information Technology			2015	0	Good
91093844	Information Engineering Certification	t	Hanoi University of Science and Technology	Information Technology			2015	0	Good
53498236	Information Engineering Certification	t	Hanoi University of Science and Technology	Information Technology			2015	0	Good
24395328	Information Engineering Certification	t	Hanoi University of Science and Technology	Information Technology			2015	0	Good
45655081	Information Engineering Certification	t	Hanoi University of Science and Technology	Information Technology			2015	0	Good
49820829	Information Engineering Certification	t	Hanoi University of Science and Technology	Information Technology			2015	0	Good
49218800	Information Engineering Certification	t	Hanoi University of Science and Technology	Information Technology			2015	0	Good
14993602	Information Engineering Certification	t	Hanoi University of Science and Technology	Information Technology			2015	0	Good
23681828	Information Engineering Certification	t	Hanoi University of Science and Technology	Information Technology			2015	0	Good
34398814	Information Engineering Certification	t	Hanoi University of Science and Technology	Information Technology			2015	0	Good
79510196	Information Engineering Certification	t	Hanoi University of Science and Technology	Information Technology			2015	0	Good
63897661	Information Engineering Certification	t	Hanoi University of Science and Technology	Information Technology			2015	0	Good
97442721	Information Engineering Certification	t	Hanoi University of Science and Technology	Information Technology			2015	0	Good
05453191	Information Engineering Certification	t	Hanoi University of Science and Technology	Information Technology			2015	0	Good
71446714	Information Engineering Certification	t	Hanoi University of Science and Technology	Information Technology			2015	0	Good
08166203	Information Engineering Certification	t	Hanoi University of Science and Technology	Information Technology			2015	0	Good
38282225	Information Engineering Certification	t	Hanoi University of Science and Technology	Information Technology			2015	0	Good
71604428	Information Engineering Certification	t	Hanoi University of Science and Technology	Information Technology			2015	0	Good
06444273	Information Engineering Certification	t	Hanoi University of Science and Technology	Information Technology			2015	0	Good
51709167	Information Engineering Certification	t	Hanoi University of Science and Technology	Information Technology			2015	0	Good
88353555	Information Engineering Certification	t	Hanoi University of Science and Technology	Information Technology			2015	0	Good
06317591	Information Engineering Certification	t	Hanoi University of Science and Technology	Information Technology			2015	0	Good
65662182	Telecommunication Engineering Certification	t	Hanoi University of Science and Technology	Electrical Telecommunication			2014	0	Good
01036022	Telecommunication Engineering Certification	t	Hanoi University of Science and Technology	Electrical Telecommunication			2014	0	Good
84413208	Telecommunication Engineering Certification	t	Hanoi University of Science and Technology	Electrical Telecommunication			2014	0	Good
38441042	Telecommunication Engineering Certification	t	Hanoi University of Science and Technology	Electrical Telecommunication			2014	0	Good
14993602	Telecommunication Engineering Certification	t	Hanoi University of Science and Technology	Electrical Telecommunication			2014	0	Good
23681828	Telecommunication Engineering Certification	t	Hanoi University of Science and Technology	Electrical Telecommunication			2014	0	Good
08166203	Telecommunication Engineering Certification	t	Hanoi University of Science and Technology	Electrical Telecommunication			2014	0	Good
71604428	Telecommunication Engineering Certification	t	Hanoi University of Science and Technology	Electrical Telecommunication			2014	0	Good
34214633	SQL Master Certification	f	Oracle	Information Technology	Database		2014	0	Professional
17063906	SQL Master Certification	f	Oracle	Information Technology	Database		2014	0	Professional
01036022	SQL Master Certification	f	Oracle	Information Technology	Database		2014	0	Professional
84413208	SQL Master Certification	f	Oracle	Information Technology	Database		2014	0	Professional
91093844	SQL Master Certification	f	Oracle	Information Technology	Database		2014	0	Professional
14993602	SQL Master Certification	f	Oracle	Information Technology	Database		2014	0	Professional
08166203	SQL Master Certification	f	Oracle	Information Technology	Database		2014	0	Professional
87038927	Microsoft Certified: Azure Fundamentals	f	Microsoft	Information Technology	Cloud Services		2014	0	Professional
78703964	Microsoft Certified: Azure Fundamentals	f	Microsoft	Information Technology	Cloud Services		2014	0	Professional
87380316	Microsoft Certified: Azure Fundamentals	f	Microsoft	Information Technology	Cloud Services		2014	0	Professional
17063906	Microsoft Certified: Azure Fundamentals	f	Microsoft	Information Technology	Cloud Services		2014	0	Professional
42472735	Microsoft Certified: Azure Fundamentals	f	Microsoft	Information Technology	Cloud Services		2014	0	Professional
73534266	Microsoft Certified: Azure Fundamentals	f	Microsoft	Information Technology	Cloud Services		2014	0	Professional
88533735	Microsoft Certified: Azure Fundamentals	f	Microsoft	Information Technology	Cloud Services		2014	0	Professional
06444273	Microsoft Certified: Azure Fundamentals	f	Microsoft	Information Technology	Cloud Services		2014	0	Professional
87038927	Microsoft Expert Certified: Database Solution Master	f	Microsoft	Information Technology	Database		2020	2024	Professional
42472735	Microsoft Expert Certified: Database Solution Master	f	Microsoft	Information Technology	Database		2020	2024	Professional
73534266	Microsoft Expert Certified: Database Solution Master	f	Microsoft	Information Technology	Database		2020	2024	Professional
97442721	Microsoft Expert Certified: Database Solution Master	f	Microsoft	Information Technology	Database		2020	2024	Professional
71604428	Microsoft Expert Certified: Database Solution Master	f	Microsoft	Information Technology	Database		2020	2024	Professional
87038927	Microsoft Management Expert Certified	f	Microsoft				2020	2024	Professional
78703964	Microsoft Management Expert Certified	f	Microsoft				2020	2024	Professional
87380316	Microsoft Management Expert Certified	f	Microsoft				2020	2024	Professional
84705815	Microsoft Management Expert Certified	f	Microsoft				2020	2024	Professional
71604428	Microsoft Management Expert Certified	f	Microsoft				2020	2024	Professional
87038927	Telecommunication Expert Certified	f	Microsoft	Electrical Telecommunication	Network		2020	2024	Professional
78703964	Telecommunication Expert Certified	f	Microsoft	Electrical Telecommunication	Network		2020	2024	Professional
17063906	Telecommunication Expert Certified	f	Microsoft	Electrical Telecommunication	Network		2020	2024	Professional
65105979	Telecommunication Expert Certified	f	Microsoft	Electrical Telecommunication	Network		2020	2024	Professional
29566679	Telecommunication Expert Certified	f	Microsoft	Electrical Telecommunication	Network		2020	2024	Professional
79510196	Telecommunication Expert Certified	f	Microsoft	Electrical Telecommunication	Network		2020	2024	Professional
51709167	Telecommunication Expert Certified	f	Microsoft	Electrical Telecommunication	Network		2020	2024	Professional
06317591	Telecommunication Expert Certified	f	Microsoft	Electrical Telecommunication	Network		2020	2024	Professional
17063906	MS Web Expert Certified	f	Microsoft	Information Technology	Website		2020	2026	Professional
16858190	MS Web Expert Certified	f	Microsoft	Information Technology	Website		2020	2026	Professional
65105979	MS Web Expert Certified	f	Microsoft	Information Technology	Website		2020	2026	Professional
29566679	MS Web Expert Certified	f	Microsoft	Information Technology	Website		2020	2026	Professional
63897661	MS Web Expert Certified	f	Microsoft	Information Technology	Website		2020	2026	Professional
06317591	MS Web Expert Certified	f	Microsoft	Information Technology	Website		2020	2026	Professional
\.


--
-- TOC entry 3041 (class 0 OID 16400)
-- Dependencies: 202
-- Data for Name: ChuyenMon; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."ChuyenMon" (nhom, chuyennganh, chuyenmon) FROM stdin;
Technology	Information Technology	Database
Technology	Electrical Telecommunication	Network
Technology	Information Technology	Cloud Services
Technology	Information Technology	Website
\.


--
-- TOC entry 3042 (class 0 OID 16406)
-- Dependencies: 203
-- Data for Name: CongTy; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."CongTy" (mact, ten, diachi, quocgia) FROM stdin;
\.


--
-- TOC entry 3043 (class 0 OID 16412)
-- Dependencies: 204
-- Data for Name: EmployeeInformation; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."EmployeeInformation" ("ID", trinhdo, chucvu, mahdld, hoten) FROM stdin;
65662182	Engineer	Coordinator	087409660X	May Luettgen Jr.
45804397	Engineer	Technician	0797010521	In Mueller
48582995	Engineer	Architect	0833941224	Kurt Schulist
53933874	Engineer	Director	1002167183	Erich Sauer
34214633	Engineer	Agent	1954614446	Arron Yost
87038927	Engineer	Orchestrator	1469294419	Francina Wuckert
78703964	Engineer	Producer	0927643510	Mr. Henry Wiza
87380316	Engineer	Consultant	0268904820	Guy Moore DVM
17063906	Engineer	Manager	0858134160	Earl Daugherty
84705815	Engineer	Liaison	1765864267	Mrs. Bret Rodriguez
02528076	Engineer	Associate	1406627704	Lorretta Kihn
01036022	Engineer	Engineer	1970494220	Jamaal Harvey
42472735	Engineer	Administrator	1941123821	Odell Daniel
05270576	Engineer	Specialist	1022296078	Debroah White
84413208	Engineer	Coordinator	0340011750	Inge Douglas
11041894	Engineer	Consultant	0096652799	Clifton Schoen
38441042	Engineer	Technician	0021257051	Latasha Heidenreich
73534266	Engineer	Administrator	0431548013	Dewayne Bauch IV
15646545	Engineer	Administrator	1820679756	Florencio Moen
08303011	Engineer	Orchestrator	108521124X	Terrance Champlin
16858190	Engineer	Consultant	0045126216	Han Bode III
65105979	Engineer	Executive	170001370X	Ms. Vida McClure
02982236	Engineer	Facilitator	1888298138	Tomasa Ernser
11496441	Engineer	Manager	0735801665	Frank Rippin
88533735	Engineer	Designer	0948468645	Arlen Quitzon
29566679	Engineer	Developer	1875427244	Dr. Jaime Simonis
91093844	Engineer	Executive	182625448X	Rosario Pacocha II
53498236	Engineer	Architect	1963691008	Doretha Smith
24395328	Engineer	Designer	1082680761	Val Pagac
45655081	Engineer	Supervisor	1421166666	Millie Auer DDS
49820829	Engineer	Producer	0230378846	Arlie Ledner I
49218800	Engineer	Engineer	0383530202	Toi Dare
14993602	Engineer	Producer	1499484011	Ela Balistreri
23681828	Engineer	Strategist	1941194516	Calandra Kutch DVM
34398814	Engineer	Developer	0863195717	Hobert Pagac
79510196	Engineer	Representative	1896868959	Jetta DuBuque
63897661	Engineer	Associate	1994005475	Hannelore Ebert II
97442721	Engineer	Planner	0727458035	Katy Halvorson PhD
05453191	Engineer	Administrator	0057025142	Reed Wiegand
71446714	Engineer	Facilitator	0549882073	Mr. Elden Wolf
08166203	Engineer	Administrator	0891818391	Mr. Mary Rolfson
38282225	Engineer	Assistant	1603005668	Mr. Hedy Orn
71604428	Engineer	Supervisor	1880080923	Mrs. Jonathon Quigley
06444273	Engineer	Executive	0705426254	Delphia Effertz
51709167	Engineer	Designer	1925262294	Ms. Shon Turner
88353555	Engineer	Developer	086679445X	Ms. Suk Stehr
06317591	Engineer	Developer	0926006339	Edmond Sauer
\.


--
-- TOC entry 3044 (class 0 OID 16418)
-- Dependencies: 205
-- Data for Name: HDLD; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."HDLD" (mahdld, "from", "to") FROM stdin;
0946584427	2015-01-01	2025-01-01
087409660X	2015-01-01	2025-01-01
0797010521	2015-01-01	2025-01-01
0833941224	2015-01-01	2025-01-01
1002167183	2015-01-01	2025-01-01
1954614446	2015-01-01	2025-01-01
1469294419	2015-01-01	2025-01-01
0927643510	2015-01-01	2025-01-01
0268904820	2015-01-01	2025-01-01
0858134160	2015-01-01	2025-01-01
1765864267	2015-01-01	2025-01-01
1406627704	2015-01-01	2025-01-01
1970494220	2015-01-01	2025-01-01
1941123821	2015-01-01	2025-01-01
1022296078	2015-01-01	2025-01-01
0340011750	2015-01-01	2025-01-01
0096652799	2015-01-01	2025-01-01
0021257051	2015-01-01	2025-01-01
0431548013	2015-01-01	2025-01-01
1820679756	2015-01-01	2025-01-01
108521124X	2015-01-01	2025-01-01
0045126216	2015-01-01	2025-01-01
170001370X	2015-01-01	2025-01-01
1888298138	2015-01-01	2025-01-01
0735801665	2015-01-01	2025-01-01
0948468645	2015-01-01	2025-01-01
1875427244	2015-01-01	2025-01-01
182625448X	2015-01-01	2025-01-01
1963691008	2015-01-01	2025-01-01
1082680761	2015-01-01	2025-01-01
1421166666	2015-01-01	2025-01-01
0230378846	2015-01-01	2025-01-01
0383530202	2015-01-01	2025-01-01
1499484011	2015-01-01	2025-01-01
1941194516	2015-01-01	2025-01-01
0863195717	2015-01-01	2025-01-01
1896868959	2015-01-01	2025-01-01
1994005475	2015-01-01	2025-01-01
0727458035	2015-01-01	2025-01-01
0057025142	2015-01-01	2025-01-01
0549882073	2015-01-01	2025-01-01
0891818391	2015-01-01	2025-01-01
1603005668	2015-01-01	2025-01-01
1880080923	2015-01-01	2025-01-01
0705426254	2015-01-01	2025-01-01
1925262294	2015-01-01	2025-01-01
086679445X	2015-01-01	2025-01-01
0926006339	2015-01-01	2025-01-01
\.


--
-- TOC entry 3045 (class 0 OID 16424)
-- Dependencies: 206
-- Data for Name: LichSuCongTac; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."LichSuCongTac" ("ID", tenduan, chuyennganh, "from", mact, chuyenmon, vitri, "to", minhchung) FROM stdin;
87038927	Lắp đặt thiết bị giám sát an toàn thông tin	Information Technology	2015	This	Information Security	Main Assistant	2017	
78703964	Lắp đặt thiết bị giám sát an toàn thông tin	Information Technology	2015	This	Information Security	Main Assistant	2017	
17063906	Lắp đặt thiết bị giám sát an toàn thông tin	Information Technology	2015	This	Information Security	Main Assistant	2017	
65105979	Lắp đặt thiết bị giám sát an toàn thông tin	Information Technology	2015	This	Information Security	Main Assistant	2017	
29566679	Lắp đặt thiết bị giám sát an toàn thông tin	Information Technology	2015	This	Information Security	Main Assistant	2017	
79510196	Lắp đặt thiết bị giám sát an toàn thông tin	Information Technology	2015	This	Information Security	Main Assistant	2017	
51709167	Lắp đặt thiết bị giám sát an toàn thông tin	Information Technology	2015	This	Information Security	Main Assistant	2017	
06317591	Lắp đặt thiết bị giám sát an toàn thông tin	Information Technology	2015	This	Information Security	Main Assistant	2017	
17063906	Lắp đặt hạ tầng mạng	Information Technology	2015	This	Website	Side Assistant	2017	
16858190	Lắp đặt hạ tầng mạng	Information Technology	2015	This	Website	Side Assistant	2017	
65105979	Lắp đặt hạ tầng mạng	Information Technology	2015	This	Website	Side Assistant	2017	
29566679	Lắp đặt hạ tầng mạng	Information Technology	2015	This	Website	Side Assistant	2017	
63897661	Lắp đặt hạ tầng mạng	Information Technology	2015	This	Website	Side Assistant	2017	
06317591	Lắp đặt hạ tầng mạng	Information Technology	2015	This	Website	Side Assistant	2017	
\.


--
-- TOC entry 3046 (class 0 OID 16430)
-- Dependencies: 207
-- Data for Name: PrivateEmployeeInformation; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."PrivateEmployeeInformation" ("ID", ngaysinh, quequan, gioitinh, diachi) FROM stdin;
\.


--
-- TOC entry 3047 (class 0 OID 16496)
-- Dependencies: 208
-- Data for Name: Tinnhan; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."Tinnhan" (matn, tinnhan) FROM stdin;
202102416248	dcdcdc
202102416253	cdc
\.


--
-- TOC entry 2894 (class 2606 OID 16437)
-- Name: CongTy CongTy_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."CongTy"
    ADD CONSTRAINT "CongTy_pkey" PRIMARY KEY (mact);


--
-- TOC entry 2896 (class 2606 OID 16439)
-- Name: EmployeeInformation EmployeeInformation_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."EmployeeInformation"
    ADD CONSTRAINT "EmployeeInformation_pkey" PRIMARY KEY ("ID");


--
-- TOC entry 2898 (class 2606 OID 16441)
-- Name: HDLD HDLD_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."HDLD"
    ADD CONSTRAINT "HDLD_pkey" PRIMARY KEY (mahdld);


--
-- TOC entry 2900 (class 2606 OID 16443)
-- Name: LichSuCongTac LichSuCongTac_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."LichSuCongTac"
    ADD CONSTRAINT "LichSuCongTac_pkey" PRIMARY KEY ("ID", tenduan, vitri);


--
-- TOC entry 2902 (class 2606 OID 16445)
-- Name: PrivateEmployeeInformation PrivateEmployeeInformation_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."PrivateEmployeeInformation"
    ADD CONSTRAINT "PrivateEmployeeInformation_pkey" PRIMARY KEY ("ID");


--
-- TOC entry 2904 (class 2606 OID 16503)
-- Name: Tinnhan Tinnhan_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Tinnhan"
    ADD CONSTRAINT "Tinnhan_pkey" PRIMARY KEY (matn);


--
-- TOC entry 2890 (class 2606 OID 16476)
-- Name: BangCap bangcap_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."BangCap"
    ADD CONSTRAINT bangcap_pkey PRIMARY KEY ("ID", ten, chuyenmon);


--
-- TOC entry 2892 (class 2606 OID 16449)
-- Name: ChuyenMon chuyenmon_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."ChuyenMon"
    ADD CONSTRAINT chuyenmon_pkey PRIMARY KEY (nhom, chuyennganh, chuyenmon);


--
-- TOC entry 2905 (class 2606 OID 16482)
-- Name: BangCap bangcap_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."BangCap"
    ADD CONSTRAINT bangcap_fkey FOREIGN KEY ("ID") REFERENCES public."EmployeeInformation"("ID") ON DELETE CASCADE NOT VALID;


--
-- TOC entry 2906 (class 2606 OID 16477)
-- Name: EmployeeInformation ei_fkey1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."EmployeeInformation"
    ADD CONSTRAINT ei_fkey1 FOREIGN KEY (mahdld) REFERENCES public."HDLD"(mahdld) ON DELETE CASCADE NOT VALID;


--
-- TOC entry 2907 (class 2606 OID 16487)
-- Name: LichSuCongTac lichsu_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."LichSuCongTac"
    ADD CONSTRAINT lichsu_fkey FOREIGN KEY ("ID") REFERENCES public."EmployeeInformation"("ID") ON DELETE CASCADE NOT VALID;


--
-- TOC entry 2908 (class 2606 OID 16470)
-- Name: PrivateEmployeeInformation pei_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."PrivateEmployeeInformation"
    ADD CONSTRAINT pei_fkey FOREIGN KEY ("ID") REFERENCES public."EmployeeInformation"("ID") NOT VALID;


-- Completed on 2021-01-24 19:34:52

--
-- PostgreSQL database dump complete
--

