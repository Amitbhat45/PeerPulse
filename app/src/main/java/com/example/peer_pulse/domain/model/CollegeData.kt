package com.example.peer_pulse.domain.model

import com.example.peer_pulse.R

data class College(
    val name :String,
    val code : String,
    val shortName : String? = null,
    val logo : Int? = null,
    val background : Int?= null,
    val description : String? = null
)


val colleges = listOf(
    College("ACHARAYA INSTITUTE OF TECHNOLOGY", "1AY"),
    College("A.P.S COLLEGE OF ENGINEERING.", "1AP"),
    College("ALPHA COLLEGE OF ENGINEERING", "1AC"),
    College("AMC ENGINEERING COLLEGE", "1AM"),
    College("AMRUTHA INSTITUTE OF ENGINEERING AND MGMT. SCIENCES", "1AR"),
    College("ATRIA INSTITUTE OF TECHNOLOGY", "1AT"),
    College("B.N.M.INSTITUTE OF TECHNOLOGY", "1BG"),
    College("B.T.L.INSTITUTE OF TECHNOLOGY", "1BT"),
    College("BANGALORE COLLEGE OF ENGINEERING AND TECHNOLOGY", "1BC"),
    College("BANGALORE INSTITUTE OF TECHNOLOGY", "1BI"),
    College("BMS COLLEGE OF ENGINEERING", "1BM"),
    College("BMS EVENING COLLEGE OF ENGINEERING", "1BE"),
    College("BMS INSTITUTE OF TECHNOLOGY", "1BY"),
    College("BRINDAVAN COLLEGE OF ENGG", "1BO"),
    College("C.M.R INSTITUTE OF TECHNOLOGY", "1CR"),
    College("CAMBRIDGE INSTITUTE OF TECHNOLOGY", "1CD"),
    College("CHANNA BASAVESHWARA INSTITUTE OF TECHNOLOGY", "1CG"),
    College("CITY ENGINEERING COLLEGE", "1CE"),
    College("DAYANANDA SAGAR COLLEGE OF ENGINEERING", "1DS"),
    College("DON BOSCO INSTITUTE OF TECHNOLOGY", "1DB"),
    College("DR. T THIMAIAH INSTITUTE OF TECHNOLOGY", "1GV"),
    College(
        "DR. AMBEDKAR INSTITUTE OF TECHNOLOGY",
        "1DA",
        shortName = "Dr.AIT",
        logo = R.drawable.drait_logo,
        background = R.drawable.drait_background,
        description = "Dr. Ambedkar Institute of Technology (Dr. AIT) was founded by M.H. Jayaprakash Narayan in 1980. Named after Dr. B.R. Ambedkar, the institute is affiliated to Visvesvaraya Technological University (VTU), Belagavi and is recognized and accredited by AICTE and NBA respectively. The institution is accredited by NAAC with ‘A+’ Grade. The Institution has achieved 163 Rank in National Institutional Ranking Framework (NIRF). "),
    College("EAST POINT COLLEGE OF ENGINEERING AND TECHNOLOGY", "1EP"),
    College("EAST WEST INSTITUTE OF TECHNOLOGY", "1EW"),
    College("GHOUSIA COLLEGE OF ENGINEERING", "1GC"),
    College("GLOBAL ACADEMY OF TECHNOLOGY", "1GA"),
    College("GOVERNMENT S.K.S.J.T. INSTITUTE OF TECHNOLOGY", "1SK"),
    College("GOVERNMENT SKSJT EVENING COLLEGE", "1SE"),
    College("GOVERNMENT TOOL ROOM AND TRAINING CENTRE", "1GT"),
    College("GOVT. ENGINEERING COLLEGE RAMNAGAR", "1GG"),
    College("HKBK COLLEGE OF ENGINEERING", "1HK"),
    College("HMS INSTITUTE OF TECHNOLOGY", "1HM"),
    College("IMPACT COLLEGE OF ENGINEERING", "1IC"),
    College("ISLAMIAH INSTITUTE OF TECHNOLOGY", "1II"),
    College("JNANA VIKAS INSTITUTE OF TENCNOLOGY", "1JV"),
    College("JSS ACADEMY OF TECHNICIAL EDUCATION", "1JS"),
    College("K.S.INSTITUTE OF TECHNOLOGY", "1KS"),
    College("KALPATARU INSTITUTE OF TECHNOLOGY", "1KI"),
    College("KNS INSTITUTE OF TECHNOLOGY", "1KN"),
    College("M.S.ENGINEERING COLLEGE", "1ME"),
    College("M.S.RAMAIAH INSTITUTE OF TECHNIOLOGY", "1MS"),
    College("MVJ COLLEGE OF ENGINEERING", "1MJ"),
    College("NAGARJUNA COLLEGE OF ENGINEERING AND TECHNOLOGY", "1NC"),
    College("NEW HORIZON COLLEGE OF ENGINEERING", "1NH"),
    College("NITTE MEENAKSHI INSTITUTE OF TECHNOLOGY", "1NT"),
    College("OXFORD COLLEGE OF ENGINEERING", "1OX"),
    College("PESIT BANGALORE SOUTH CAMPUS", "1PE"),
    College("R R INSTITUTE OF TECHNOLOGY", "1RI"),
    College("R.L.JALAPPA INSTITUTE OF TECHNOLOGY", "1RL"),
    College("R.V.COLLEGE OF ENGINEERING", "1RV"),
    College("RAJARAJESWARI COLLEGE OF ENGINEERING", "1RR"),
    College("RAJIV GANDHI INSTITUTE OF TECHNOLOGY", "1RG"),
    College("RNS INSTITUTE OF TECHNOLOGY", "1RN"),
    College("S.J.C INSTITUTE OF TECHNOLOGY", "1SJ"),
    College("SAI VIDYA INSTITUTE OF TECHNOLOGY", "1VA"),
    College("SAMBHRAM INSTITUTE OF TECHNOLOGY", "1ST"),
    College("SAPTHAGIRI COLLEGE OF ENGINEERING", "1SG"),
    College("SCT INSTITUTE OF TECHNOLOGY", "1SC"),
    College("SEA COLLEGE OF ENGINEERING AND TECHNOLOGY", "1SP"),
    College("SRI SAIRAM COLLEGE OF ENGINEERING", "1SB"),
    College("SHRIDEVI INSTITUTE OF ENGINEERING AND TECHNOLOGY", "1SV"),
    College("SIDDAGANGA INSTITUTE OF TECHNOLOGY", "1SI"),
    College("SIR M. VISVESVARAYA INSTITUTE OF TECHNOLOGY", "1MV"),
    College("SJB INSTITUTE OF TECHNOLOGY", "1JB"),
    College("Dr. Sri Sri Sri SHIVKUMAR MAHASWAMY, COLLEGE OF ENGG.", "1CC"),
    College("SRI KRISHNA INSTITUTE OF TECHNOLOGY", "1KT"),
    College("SRI REVANASIDDESHWARA INSTITUTE OF TECHNOLOGY", "1RC"),
    College("SRI VENKATESHWARA COLLEGE OF ENGINEERING", "1VE"),
    College("T. JOHN INSTITUTE OF TECHNOLOGY", "1TJ"),
    College("VEMANA INSTITUTE OF TECHNOLOGY", "1VI"),
    College("VIVEKANANDA INSTITUTE OF TECHNOLOGY", "1VK"),
    College("YELLAMMA DASAPPA INSTITUTE OF TECHNOLOGY", "1YD"),
    College("ACHARYS NRV SCHOOL OF ARCHITECTURE", "1AA"),
    College("ACS COLLEGE OF ENGINEERING", "1AH"),
    College("AKSHAYA INSTITUTE OF TECHNOLOGY", "1AK"),
    College("C BYEREGOWDA INSTITUTE OF TECHNOLOGY", "1CK"),
    College("P N S INSTITUTE OF TECHNOLOGY", "1PN"),
    College("VIJAYA VITTALA INSTITUTE OF TECHNOLOGY", "1VJ"),
    College("SHASHIB COLLEGE OF ENGINEERING", "1HS"),
    College("ACHUTHA INSTITUTE OF TECHNOLOGY", "1AO"),
    College("SAMPOORNA INSTITUTE OF TECHNOLOGY RESEARCH", "1SZ"),
    College("K.S SCHOOL OF ENGG & MGMT", "1KG"),
    College("GOPALAN COLLEGE OF ENGINEERING MANAGEMENT", "1GD"),
    College("BANGALORE TECHNOLOGICAL INSTITUTE", "1BH"),
    College("JYOTHY INSTITUTE OF TECHNOLOGY", "1JT"),
    College("DAYANANDA SAGAR ACADEMY OF TECHNOLOGY AND MGMT.", "1DT"),
    College("SHRI PILLAPPA COLLEGE OF ENGINEERING", "1PL"),
    College("ADARSHA INSTITUTE OF TECHNOLOGY", "1AJ"),
    College("SRI VIDYA VINYAKA INSTITUTE OF TECHNOLOGY", "1VG"),
    College("IMPACT SCHOOL OF ARCHITECTURE", "1IS"),
    College("R V COLLEGE OF ARCHITECTURE", "1RW"),
    College("BMS SCHOOL OF ARCHITECTURE", "1BQ"),
    College("S J B School of Arch. & Planning", "1JA"),
    College("GOPALAN SCHOOL OF ARCHITECTURE & PLANNING", "1GO"),
    College("R.R. SCHOOL OF ARCHITECTURE", "1RR"),
    College("ADITHYA ACADEMY OF ARCHITECTURE & DESGIN", "1AN"),
    College("BGS SCHOOL OF ARCHITECTURE & PLANNING", "1PC"),
    College("K S SCHOOL OF ARCHITECTURE", "1KF"),
    College("EAST WEST COLLEGE OF ENGG", "1EE"),
    College("SRI VINAYAKA INSTITUTE OF TECHNOLOGY", "1VB"),
    College("Sir. M. V. School of Architecture", "1IV"),
    College("Nitte School of Architecture", "1NS"),
    College("HMS School of Architecture", "1IT"),
    College("Brindavan College of Architecture", "1IE"),
    College("Nadgir Inst. of Tech. & Tech", "1NJ"),
    College("BMS College of Architecture", "1CF"),
    College("OXFORD SCHOOL OF ARCHITECTURE", "1OQ"),
    College("RNS SCHOOL OF ARCHITECTURE", "1RD"),
    College("KLE DR M S SHESHGIRI COLLEGE OF ENGINEERING AND TECHNOLOGY", "2HG"),
    College("KLS'S VISHWANATHRAO DESHPANDE RURAL INSTITUTE OF TECHNOLOGY", "2IT"),
    College("BALLARI INSTITUTE OF TECHNOLOGY AND MANAGEMENT", "2BI"),
    College("GOVERNMENT ENGINEERING COLLEGE HAVERI", "2HH"),
    College("K S SCHOOL OF ENGINEERING AND TECHNOLOGY", "2HN"),
    College("KLE COLLEGE OF ENG. AND TECHNOLOGY CHIKODI", "2KD"),
    College("KLE INSTITUTE OF TECH HUBLI", "2KE"),
    College("KLE Dr M. S. SHESHGIRI COLLEGE OF ENGINEERING AND TECHNOLOGY", "2KL"),
    College("KLS GOGTE INSTITUTE OF TECHNOLOGY", "2GI"),
    College("MALIK SANDAL INSTITUTE OF ART AND ARCHITECTURE", "2MB"),
    College("MARATHA MANDALS ENGINEERING COLLEGE", "2MM"),
    College("RURAL ENGINEERING COLLEGE, HULKOTI", "2RH"),
    College("S G BALEKUNDRI INST. OF TECH", "2BU"),
    College("S.T.J. INSTITUTE OF TECHNOLOGY", "2SR"),
    College("SDM COLLEGE OF ENGINEERING AND TECHNOLOGY", "2SD"),
    College("SECAB INSTITUTE OF ENGINEERING AND TECHNOLOGY", "2SA"),
    College("SMT. KAMALA AND SRI VENKAPPA M. AGADI COLLEGE OF ENGINEERING AND TECHNOLOGY", "2KA"),
    College("SRI TONTADARAYA COLLEGE OF ENGINEERING", "2TG"),
    College("VISHWANATHA RAO DESHPANDE RURAL INSTITUTE OF TECHNOLOGY", "2VD"),
    College("GOVT. ENGINEERING COLLEGE HUVINHADAGALI", "2GB"),
    College("GOVERNMENT ENGINEERING COLLEGE KARWAR", "2GP"),
    College("SHAIKH COLLEGE OF ENGINEERING AND TECHNOLOGY", "2HA"),
    College("JSS ACADEMY OF TECHNICAL EDUCATION", "2JM"),
    College("ANGADI INSTITUTE OF TECHNOLOGY AND MGMT.", "2AG"),
    College("JAIN COLLEGE OF ENGINEERING", "2JI"),
    College("V S M’S INSTITUTE OF TECHNOLOGY", "2VS"),
    College("AGM RURAL COLLEGE OF ENGINEERING & TECHNOLOGY", "2AV"),
    College("GRIJABAI SAIL INSTITUTE OF TECHNOLOGY (GSIT), KARWAR", "2GJ"),
    College("BILURU GURUBASAVA MAHASWAMIJI INSTITUTE OF TECHNOLOGY", "2LB"),
    College("BASAVA ENGG SCHOOL OF TECHNOLOGY ZALAKI", "2VL"),
    College("JAIN COLLEGE OF ENGG HUBBALLI", "2IH"),
    College("BILURU GURUBASAVA MAHASWAMIJI INSTITUTE OF TECHNOLOGY", "2IG"),
    College("APPA INSTITUTE OF ENGINEERING AND TECHNOLOGY", "3AE"),
    College("BASAVAKALYAN ENGINEERING COLLEGE", "3BK"),
    College("BELLARY ENGINEERING COLLEGE", "3BR"),
    College("GOVT. ENGINEERING COLLEGE RAICHUR", "3GU"),
    College("GURU NANAK DEV ENGINEERING COLLEGE", "3GN"),
    College("K.C.T. ENGINEERING COLLEGE", "3KC"),
    College("KHAJA BANDA NAWAZ COLLEGE OF ENGINEERING", "3KB"),
    College("NAVODAYA INSTITUTE OF TECHNOLOGY", "3NA"),
    College("POOJYA DODAPPAPPA COLLEGE OF ENGINEERING", "3PD"),
    College("PROUDADEVARAYA INSTITUTE OF TECHNOLOGY", "3PG"),
    College("RAO BAHADDUR Y MAHABALESHWARAPPA ENGG COLLEGE", "3VC"),
    College("BHEEMANNA KHANDRE INSTITUTE OF TECHNOLOGY, BHALKI", "3RB"),
    College("SLN COLLEGE OF ENGINEERING", "3SL"),
    College("VEERAPPA NISTY ENGINEERING COLLEGE", "3VN"),
    College("LINGARAJ APPA ENGINEERING COLLEGE", "3LA"),
    College("GODUTAI ENGINEERING COLLEGE FOR WOMEN", "3GF"),
    College("SHETTY INSTITUTE OF TECHNOLOGY", "3TS"),
    College("ADICHUNCHANAGIRI INSTITUTE OF TECHNOLOGY", "4AI"),
    College("ALVAS INST. OF ENGG. AND TECHNOLOGY", "4AL"),
    College("B.G.S.INSTITUTE OF TECHONOLOGY", "4BW"),
    College("BAHUBALI COLLEGE OF ENGINEERING", "4BB"),
    College("BAPUJI INSTITUTE OF ENGINEERING AND TECHNOLOGY", "4BD"),
    College("BEARYS INSTITUTE OF TECHNOLOGY", "4BP"),
    College("CANARA ENGINEERING COLLEGE", "4CB"),
    College("COORG INSTITUTE OF TECHNOLOGY", "4CI"),
    College("YENEPOYA INSTITUTE OF TECHNOLOGY", "4DM"),
    College("GM.INSTITUTE OF TECHONOLOGY", "4GM"),
    College("GOVT. ENGINEERING COLLEGE CHAMARAJANAGARA", "4GE"),
    College("GOVT. ENGINEERING COLLEGE HASSAN", "4GH"),
    College("GOVT. ENGINEERING COLLEGE KUSHAL NAGAR", "4GL"),
    College("GOVT. ENGINEERING COLLEGE MANDYA", "4GK"),
    College("GOVT. TOOL ROOM AND TRAINING CENTRE", "4GR"),
    College("GSSS INSTITUTE OF ENGINEERING AND TECHNOLOGY FOR WOMEN", "4GW"),
    College("JAWAHARLAL NEHRU NATIONAL COLLEGE OF ENGINERING", "4JN"),
    College("K.V.G. COLLEGE OF ENGINEERING", "4KV"),
    College("KARAVALI INSTITUTE OF TECHNOLOGY", "4KM"),
    College("MAHARAJA INSTITUTE OF TECH", "4MH"),
    College("MALNAD COLLEGE OF ENGINEERING", "4MC"),
    College("MANGALORE INSTITUTE OF TECHNOLOGY AND ENGINEERING", "4MT"),
    College("MOODLAKATTE INSTITUTE OF TECHONOLOGY", "4MK"),
    College("NATIONAL INSTITUTE OF ENGG. EVENING", "4NE"),
    College("NATIONAL INSTITUTE OF ENGINEERING", "4NI"),
    College("NIE INST. OF TECHNOLOGY", "4NN"),
    College("NMAM INSTITUTE OF TECHONOLOGY", "4NM"),
    College("P.A.COLLEGE OF ENGINEERING", "4PA"),
    College("P.E.S COLLEGE OF ENGINEERING", "4PS"),
    College("PES INSITUTE OF TECHNOLOGY AND MGMT.", "4PM"),
    College("RAJEEV INST. OF TECHNOLOGY", "4RA"),
    College("SHREE DEVI INSTITUTE OF TECHNOLOGY", "4SH"),
    College("SJM INSTITUTE OF TECHNOLOGY", "4SM"),
    College("SRI DHARMASTHAL MANJUNATHESHWAR INSTITUTE OF TECHNOLOGY", "4SU"),
    College("SRI JAYACHAMARAJENDRA COLLEGE OF ENGINEERING", "4JC"),
    College("SRI JAYACHAMRAJENDRA COLLEGE OFF ENGG. EVENING", "4JE"),
    College("SRINIVAS INSTITUTE OF TECHNOLOGY", "4SN"),
    College("ST.JOSPEH ENGINEERING COLLEGE", "4SO"),
    College("VIDYA VARDHAKA COLLEGE OF ENGINERING", "4VV"),
    College("VIDYA VIKAS INSTITUTE OF ENGINEERING AND TECHNOLOGY", "4VM"),
    College("VIVEKANANDA COLLEGE OF ENGINEERING AND TECHNOLOGY", "4VP"),
    College("EKALAVYA INSTITUTE OF TECHNOLOGY", "4EK"),
    College("SRINIVAS SCHOOL OF ENGINEERING", "4ES"),
    College("YAGACHI INSTITUTE OF TECHNOLOGY", "4YG"),
    College("SHRI MADHWA VADIRAJA INSTITUTE OF TECHNOLOGY & MANAGEMENT", "4MW"),
    College("ACADEMY FOR TECHNICAL AND MANAGEMENT EXCELLENCE", "4AD"),
    College("UBDT ENGINEERING COLLEGE DAVANAGERE ( Constituent College of VTU )", "4UB"),
    College("G MADEGOWDA INSTITUTE OF TECHNOLOGY", "4MG"),
    College("SAHYADRI COLLEGE OF ENGINEERING & MANAGEMENT, MANGALORE", "4SF"),
    College("JAIN INSTITUTE OF TECHNOLOGY", "4JD"),
    College("MANGALORE MARINE COLLEGE & TECHNOLOGY", "4MR"),
    College("CAUVERY INSTITUTE OF TECHNOLOGY", "4CA"),
    College("MYSORE SCHOOL OF ARCHITECTURE", "4MA"),
    College("BEARYS ENVIRONMENT ARCHITECTURE DESIGN SCHOOL MANGALORE", "4ED"),
    College("MYSORE COLLEGE OF ENGINEERING AND MANAGEMENT", "4MO"),
    College("MYSURU ROYAL INSTITUTE OF TECHNOLOGY", "4RI"),
    College("SRINIVAS COLLEGE OF ARCHITECTURE", "4SI"),
    College("VISHVESHWARAIAH COLLEGE OF APPLIED SCIENCES", "4VA"),
    College("ST. ALOYSIUS INSTITUTE OF TECHNOLOGY", "4ST"),
    College("MANGALORE COLLEGE OF TECHNOLOGY AND ENGINEERING", "4MT"),
    College("SCHOOL OF INFORMATION SCIENCES AND TECHNOLOGY", "4IS"),
    College("WADIYAR CENTRE FOR ARCHITECTURE","4CM"),
    College("Maharaja Institute of Technology", "4MN"),
    College("A. J. Institute of Engineering", "4JK"),
)

