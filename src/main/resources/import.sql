INSERT INTO company (company_name, questions) VALUES ('금융결제원', '금융결제원 지원 동기(금융결제원이 본인을 채용해야 하는 이유) ※ 작성 내용에 본인의 성명, 출생지, 성장지, 출신 학교(동아리명, 대회명, 교수명 등), 가족관계 등 인적 사항을 확인할 수 있는 사항이나 허위사실이 포함될 경우 불이익이 있습니다. (예시 : 일체의 인명 작성 금지(위인, 유명인, 작가 등 포함) / 구체적 동아리명 → OO 관련 동아리 / 구체적 회사, 부서명 -> ○○회사에서 ○○업무를 할 당시 / 지원자가 특정되는 논문 제목, 대회 및 링크 언급 금지 / 미국 학창 시절, 프랑스 교환학생 -> 해외 학창 시절, 외국 교환학생)||금융결제원이 제공하는 서비스를 이용해 본 경험과 느낀 점을 기술하시오.||채용공고 상의 수행 직무 및 본인이 희망하는 관심 직무와 관련하여 전문가가 되기 위한 노력(프로젝트 경험 등)을 구체적으로 기술하시오.(프로그램 개발 언어 사용 능력 등 기술적 설명 포함)||조직(예 : 학교, 동아리, 가족 등)의 구성원으로서 갈등이 있었던 상황을 설명하고, 원인, 대처방안, 현재 시점에서의 판단을 구체적으로 기술하시오.||자신의 가치관 또는 인생관에 영향을 미친 경험을 소개하고, 이를 통해 배우거나 느낀 점을 구체적으로 기술하시오.||(선택) 빅데이터 관련 역량을 개발하기 위해 수행한 노력을 본인의 경험 및 경력을 기반으로 기술하시오. (빅데이터 관련 직무 또는 학습경험이 있는 자에 한하며, 면접 시 참고 예정)');
INSERT INTO company (company_name, questions) VALUES ('(주)가비아', '우리 회사에 지원하는 이유와 해당 직무에 지원하는 이유에 대해 각각 서술하여 주십시오.||백엔드 개발자로서 역량 향상을 위해 어떤 노력을 했고 가장 중요시하는 부분이 무엇인지와 그 이유를 서술하여 주십시오.||자신이 진행했던 프로젝트를 기술하고 본인이 기여한 부분을 구체적으로 서술하여 주십시오.||활발하게 활동하는 외부 개발 커뮤니티가 있다면 서술하여 주십시오.||개인 저장소 및 블로그 등이 있다면 운영 목적과 함께 서술하여 주십시오. (URL 포함)');
INSERT INTO company (company_name, questions) VALUES ('나이스피앤아이(주)', '역량 또는 경력 추가 기술 (양식, 분량 자유)||자기소개 (양식, 분량 자유)');
INSERT INTO company (company_name, questions) VALUES ('(주)아이티센', '당사에 입사지원하신 이유와 지원한 분야를 희망하는 이유, 그리고 면접관이 꼭 알아주셨으면 하는 점을 기재해 주세요.||본인이 지원한 직무와 관련한 경험 중 성공했거나 실패했던 사례가 있다면 기재해 주세요.||인생을 살아가면서 가장 중요하다고 생각하는 가치가 있다면 본인의 경험을 토대로 그 이유에 대해서 기재해 주세요.');
INSERT INTO company (company_name, questions) VALUES ('(주)티웨이항공', '동료 또는 주변 사람이 평가하는 본인은 어떤 유형의 사람인지 서술하여 주시기 바랍니다.||티웨이항공을 선택한 이유는 무엇이며, 티웨이항공이 더 성장하기 위해 갖추어야 할 요소가 무엇인지 서술하여 주시기 바랍니다.||본인이 지원한 분야에서 그 일을 남들보다 잘할 수 있는 차별화된 능력과 경험이 무엇인지 서술하여 주시기 바랍니다.');

INSERT INTO occupation (occupation_name) VALUES ('백엔드개발자');
INSERT INTO occupation (occupation_name) VALUES ('자재관리자');
INSERT INTO occupation (occupation_name) VALUES ('금융영업');
INSERT INTO occupation (occupation_name) VALUES ('영업지원');
INSERT INTO occupation (occupation_name) VALUES ('R&D·연구원');

INSERT INTO USER (AGE, GENDER, MODE, STATUS, COMPANY_NO, CREATED_DATE, DELETED_DATE, OCCUPATION_NO, BIRTH_DATE, EMAIL, PASSWORD, ROLE, USERNAME, WISH_COMPANY_NO) VALUES (30, 'M', 2, 2, 1, '2024-07-14 12:17:39', null, 1, '1994-02-15', 'test1@test.com', '$2a$10$KkxtdyPCf996aEQ617CB0OVfkKmMNzta.syOxlXI8cFmE41m3RwQe', 'ROLE_ADMIN', 'test1', 2);
INSERT INTO USER (AGE, GENDER, MODE, STATUS, COMPANY_NO, CREATED_DATE, DELETED_DATE, OCCUPATION_NO, BIRTH_DATE, EMAIL, PASSWORD, ROLE, USERNAME, WISH_COMPANY_NO) VALUES (20, 'F', 2, 2, 1, '2024-07-14 12:17:39', null, 1, '1994-02-15', 'test2@test.com', '$2a$10$KkxtdyPCf996aEQ617CB0OVfkKmMNzta.syOxlXI8cFmE41m3RwQe', 'ROLE_ADMIN', 'test2', 3);
INSERT INTO USER (AGE, GENDER, MODE, STATUS, COMPANY_NO, CREATED_DATE, DELETED_DATE, OCCUPATION_NO, BIRTH_DATE, EMAIL, PASSWORD, ROLE, USERNAME, WISH_COMPANY_NO) VALUES (10, 'M', 2, 2, 1, '2024-07-14 12:17:39', null, 1, '1994-02-15', 'test3@test.com', '$2a$10$KkxtdyPCf996aEQ617CB0OVfkKmMNzta.syOxlXI8cFmE41m3RwQe', 'ROLE_ADMIN', 'test3', 4);
INSERT INTO USER (AGE, GENDER, MODE, STATUS, COMPANY_NO, CREATED_DATE, DELETED_DATE, OCCUPATION_NO, BIRTH_DATE, EMAIL, PASSWORD, ROLE, USERNAME, WISH_COMPANY_NO) VALUES (25, 'F', 1, 2, 1, '2024-07-14 12:17:39', null, 1, '1994-02-15', 'test4@test.com', '$2a$10$KkxtdyPCf996aEQ617CB0OVfkKmMNzta.syOxlXI8cFmE41m3RwQe', 'ROLE_ADMIN', 'test4', 5);
INSERT INTO USER (AGE, GENDER, MODE, STATUS, COMPANY_NO, CREATED_DATE, DELETED_DATE, OCCUPATION_NO, BIRTH_DATE, EMAIL, PASSWORD, ROLE, USERNAME, WISH_COMPANY_NO) VALUES (35, 'M', 1, 2, 1, '2024-07-14 12:17:39', '2024-07-14 12:17:39', 1, '1994-02-15', 'test5@test.com', '$2a$10$KkxtdyPCf996aEQ617CB0OVfkKmMNzta.syOxlXI8cFmE41m3RwQe', 'ROLE_ADMIN', 'test5', 2);

INSERT INTO RESUME_GUIDE (USER_NO, COMPANY_NO, OCCUPATION_NO, CONTENT) VALUES (1, 1, 1, '가이드 받은 내용');
INSERT INTO RESUME_GUIDE (USER_NO, COMPANY_NO, OCCUPATION_NO, CONTENT) VALUES (2, 2, 2, '가이드 받은 내용2');
INSERT INTO RESUME_GUIDE (USER_NO, COMPANY_NO, OCCUPATION_NO, CONTENT) VALUES (3, 3, 3, '가이드 받은 내용3');
INSERT INTO RESUME_GUIDE (USER_NO, COMPANY_NO, OCCUPATION_NO, CONTENT) VALUES (3, 4, 4, '가이드 받은 내용4');
INSERT INTO RESUME_GUIDE (USER_NO, COMPANY_NO, OCCUPATION_NO, CONTENT) VALUES (5, 5, 5, '가이드 받은 내용5');

INSERT INTO review (USER_NO, content, rating, mode, display, CREATED_DATE) VALUES (1, '자소서 첨삭 서비스 덕분에 취업에 성공했습니다!', 5, 1, 'true', '2023-06-01 12:00:00');
INSERT INTO review (USER_NO, content, rating, mode, display, CREATED_DATE) VALUES (1, 'reditor의 꼼꼼한 첨삭으로 자소서가 완벽해졌어요.', 5, 1, 'true', '2023-06-02 13:30:00');
INSERT INTO review (USER_NO, content, rating, mode, display, CREATED_DATE) VALUES (2, '유용했지만 조금 더 상세한 피드백이 필요해요.', 3, 1, 'false', '2023-06-03 14:45:00');
INSERT INTO review (USER_NO, content, rating, mode, display, CREATED_DATE) VALUES (4, '첨삭 서비스가 도움이 되었지만, 시간이 좀 오래 걸렸어요.', 4, 1, 'true', '2023-06-04 15:00:00');
INSERT INTO review (USER_NO, content, rating, mode, display, CREATED_DATE) VALUES (5, '기대에 미치지 못한 서비스였습니다.', 2, 1, 'false', '2023-06-05 16:20:00');

INSERT INTO RESUME (USER_NO, CONTENT, CREATED_DATE) VALUES (1, '5/7 첨삭 후 자소서입니다', NOW());
INSERT INTO RESUME (USER_NO, CONTENT, CREATED_DATE) VALUES (2, '자기소개 (성장배경, 직장경력, 사회경험 등) 저는 올해 초등학교 6학년인 남자아이와 4학년인 여자아이를 자녀로 두고 있는 42살의 14년차 전업주부 OOO입니다.',  NOW());
INSERT INTO RESUME (USER_NO, CONTENT, CREATED_DATE) VALUES (3, '게임은 사람들에게 친근하게 다가갈 수 있는 소프트웨어인 만큼 사용자들의 피드백이 빠르게 들어옵니다',  NOW());
INSERT INTO RESUME (USER_NO, CONTENT, CREATED_DATE) VALUES (4, '**지원동기를 기술해주세요.** 저는 게임이 사용자들에게 가장 친근하게 다가갈 수 있는 소프트웨어이기 때문에, 사용자들의 피드백이 빠르게 전달된다고 생각합니다.',  NOW());
INSERT INTO RESUME (USER_NO, CONTENT, CREATED_DATE) VALUES (4, '현대자동차는 전기차 시장에서 가격적 측면을 넘어, 고성능 브랜드 가치를 추가하기 위해 박차를 가하고 있습니다.',  NOW());

INSERT INTO RESUME_EDIT (MODE, COMPANY_NO, OCCUPATION_NO, RESUME_NO, USER_NO, CONTENT, OPTIONS) VALUES (1, 1, 1, 1, 1, 'pro mode 5/7 첨삭받을 자소서입니다', null);
INSERT INTO RESUME_EDIT (MODE, COMPANY_NO, OCCUPATION_NO, RESUME_NO, USER_NO, CONTENT, OPTIONS) VALUES (2, 2, 1, 2, 1, '게임은 사람들에게 가장 친근하게 다가갈 수 있는 소프트웨어인 만큼 사용자들의 피드백이 빠르다고 생각합니다. 긍정적인 반응은 제가 개발을 하는 원동력이며 부정적인 반응은 제가 나아갈 방향을 알려주는 이정표가 된다고 생각합니다. 그렇기에 빠르게 사용자 반응을 살필 수 있는 직무에서 근무한다면 그만큼 빠르게 성장할 수 있을 거 같아 지원하였습니다. 두 번째 이유는 제가 쌓아온 경험을 기반으로 기여할수 있는 부분이 있다고 믿기 때문입니다. 저는 3개월간의 인턴십과 5개월간의 부트캠프 경험을 통해 UI구현 뿐만 아니라 API 와 DB 구축에도 참여하며 개발 경험을 쌓았습니다. 또한 다른 사람들과 같이 개발하며 다양한 협업 도구를 활용한 협업 능력 역시 키울 수 있었습니다. 마지막으로 펄어비스는 해외 매출 비중이 77%를 차지하는 글로벌 회사입니다. 저의 다년간의 해외경험은 해외 오피스와 협력하며 다양한 프로젝트를 효과적으로 수행하는 데 큰 자산이 될 것입니다.만약 제가 입사하게 된다면 게임 내 미니게임 컨텐츠를 제작해 보고 싶습니다. 게임을 즐기다 보면 간혹 어려운 메인 컨텐츠에 가로막혀 좌절했던것 같습니다. 이때 게임 내의 이벤트를 통한 보상을 통해 다음 컨텐츠로 나아갈수 있게 되었을때 큰 희열을 느꼇던것 같습니다.펄어비스의 프론트엔드 개발자로서 인게임 컨텐츠 제작을 통해 사용자들에게 최고의 사용자 경험을 제공하고 싶습니다.', null);
INSERT INTO RESUME_EDIT (MODE, COMPANY_NO, OCCUPATION_NO, RESUME_NO, USER_NO, CONTENT, OPTIONS) VALUES (1, 3, 3, 3, 2, 'Industrial AI 개발에 있어서 가장 중요한 역량은 현업 이해도와 AI 개발 능력의 조화이며, 저는 개발 능력의 향상을 위해 장기 AI 교육과 프로젝트 수행 기간을 가졌습니다. ', null);
INSERT INTO RESUME_EDIT (MODE, COMPANY_NO, OCCUPATION_NO, RESUME_NO, USER_NO, CONTENT, OPTIONS) VALUES (2, 3, 2, 4, 3, '"일정과 비용을 모두 관리하는 생산관리 실무자가 되겠습니다"', null);
INSERT INTO RESUME_EDIT (MODE, COMPANY_NO, OCCUPATION_NO, RESUME_NO, USER_NO, CONTENT, OPTIONS) VALUES (1, 5, 5, 5, 4, 'CSE는 이슈 발생 시 동료 엔지니어와 협업 하는 과정에서 유연하고 분석적인 사고가 필요합니다.', null);

INSERT INTO RESUME_RATING (RATING, RESUME_NO, USER_NO) VALUES (4.5, 1, 1);
INSERT INTO RESUME_RATING (RATING, RESUME_NO, USER_NO) VALUES (4.5, 2, 2);
INSERT INTO RESUME_RATING (RATING, RESUME_NO, USER_NO) VALUES (5, 3, 4);
INSERT INTO RESUME_RATING (RATING, RESUME_NO, USER_NO) VALUES (5, 4, 5);
INSERT INTO RESUME_RATING (RATING, RESUME_NO, USER_NO) VALUES (1, 5, 1);

INSERT INTO COMMENT (CREATED_DATE, DELETED_DATE, RESUME_NO, UPDATED_DATE, USER_NO, CONTENT) VALUES ('2024-07-14 13:27:51.000000', null, 1, null, 1, '내용1');
INSERT INTO COMMENT (CREATED_DATE, DELETED_DATE, RESUME_NO, UPDATED_DATE, USER_NO, CONTENT) VALUES ('2024-07-14 13:27:54.000000', null, 2, '2024-07-14 13:28:13.000000', 1, '내용3');
INSERT INTO COMMENT (CREATED_DATE, DELETED_DATE, RESUME_NO, UPDATED_DATE, USER_NO, CONTENT) VALUES ('2024-07-14 13:27:55.000000', null, 2, '2024-07-14 13:28:14.000000', 3, '테스트2');
INSERT INTO COMMENT (CREATED_DATE, DELETED_DATE, RESUME_NO, UPDATED_DATE, USER_NO, CONTENT) VALUES ('2024-07-14 13:27:56.000000', null, 4, null, 4, '테스트');
INSERT INTO COMMENT (CREATED_DATE, DELETED_DATE, RESUME_NO, UPDATED_DATE, USER_NO, CONTENT) VALUES ('2024-07-14 13:27:56.000000', '2024-07-14 13:28:04.000000', 5, null, 5, '내용5');

INSERT INTO BOOKMARK (CREATED_DATE, RESUME_NO, USER_NO) VALUES ('2024-07-14 13:29:40.000000', 1, 1);
INSERT INTO BOOKMARK (CREATED_DATE, RESUME_NO, USER_NO) VALUES ('2024-07-14 13:29:41.000000', 2, 1);
INSERT INTO BOOKMARK (CREATED_DATE, RESUME_NO, USER_NO) VALUES ('2024-07-14 13:29:41.000000', 3, 2);
INSERT INTO BOOKMARK (CREATED_DATE, RESUME_NO, USER_NO) VALUES ('2024-07-14 13:29:42.000000', 4, 3);
INSERT INTO BOOKMARK (CREATED_DATE, RESUME_NO, USER_NO) VALUES ('2024-07-14 13:29:43.000000', 4, 4);

INSERT INTO verification ( email, code, created_date, expires_date) VALUES ('example1234@gmail.com', '87906786-2d', '2024-05-30 14:17:26', '2024-05-30 14:22:26');
INSERT INTO verification ( email, code, created_date, expires_date) VALUES ('iamyeongyeong@gmail.com', 'a6a9aa69-79', '2024-06-07 06:35:45', '2024-06-07 06:40:45');
INSERT INTO verification ( email, code, created_date, expires_date) VALUES ('yejeejudy@naver.com', 'd852eb16-9e', '2024-05-30 15:22:19', '2024-05-30 15:27:19');
INSERT INTO verification ( email, code, created_date, expires_date) VALUES ('seventhseven@naver.com', 'be41f1ba-39', '2024-05-30 15:23:43', '2024-05-30 15:28:43');
INSERT INTO verification ( email, code, created_date, expires_date) VALUES ('je_hn@naver.com', '5ce6ce61-89', '2024-05-30 15:23:37', '2024-05-30 15:28:37');

INSERT INTO PUBLIC.RESUME_STATISTICS (RATING, RATING_COUNT, READ_COUNT, RESUME_NO, TITLE) VALUES (4, 1, 1, 1, 'test');
