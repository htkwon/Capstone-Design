INSERT INTO user (stu_id, name, nickname, student_id, career, introduce)
VALUES (100, 'USER1', 'NICK1', '181234', '', ''),
       (101, 'USER2', 'NICK2', '185678', '', ''),
       (102, 'ADMIN', 'ADMIN', '1800000', '', '');

INSERT INTO board (id, title, content, created_at, views, dtype, stu_id)
VALUES (1, 'Test title 1', 'Test content 1', '2023-04-28 18:41:40.000000', 0, 'FreeBoard', 100),
       (2, 'Test title 1', 'Test content 1', '2023-04-28 18:41:40.000000', 1, 'FreeBoard', 100),
       (3, 'Test title 1', 'Test content 1', '2023-04-28 18:41:40.000000', 2, 'FreeBoard', 101),
       (4, 'Test title 1', 'Test content 1', '2023-04-28 18:41:40.000000', 3, 'FreeBoard', 100),
       (5, 'Test title 1', 'Test content 1', '2023-04-28 18:41:40.000000', 4, 'FreeBoard', 101),
       (6, 'Test title 1', 'Test content 1', '2023-04-28 18:41:40.000000', 5, 'FreeBoard', 100),
       (7, 'Test title 1', 'Test content 1', '2023-04-28 18:41:40.000000', 6, 'FreeBoard', 100),
       (8, 'Test title 1', 'Test content 1', '2023-04-28 18:41:40.000000', 0, 'FreeBoard', 101),
       (9, 'Test title 1', 'Test content 1', '2023-04-28 18:41:40.000000', 4, 'FreeBoard', 101),
       (10, 'Test title 1', 'Test content 1', '2023-04-28 18:41:40.000000', 2, 'FreeBoard', 101),
       (11, 'hi1', 'content', '2023-04-28 18:41:40.000000', 0, 'QnaBoard', 100),
       (12, 'hi2', 'content', '2023-04-28 18:41:40.000000', 0, 'QnaBoard', 101),
       (13, 'hi3', 'content', '2023-04-28 18:41:40.000000', 7, 'QnaBoard', 100),
       (14, 'hi4', 'content', '2023-04-28 18:41:40.000000', 0, 'QnaBoard', 100),
       (15, '게임 개발 공모전 같이 나가실 분을 구합니다.', '쉽지 않은 인생입니다.', '2023-04-28 18:41:40.000000', 8, 'RecruitBoard', 101),
       (16, '캡스톤 팀원 구합니다아아앙 [한명만 더!!]', '하나하나 차근차근', '2023-04-28 18:41:40.000000', 9, 'RecruitBoard', 101),
       (17, '10학번이랑 팀플 같이 할 사람', '해내다 보면 언젠가는', '2023-04-28 18:41:40.000000', 10, 'RecruitBoard', 100),
       (18, '재앙 가논 잡으러 갈 하이랄의 용사를 구합니다', '성취할 날이 올겁니다.', '2023-04-28 18:41:40.000000', 0, 'RecruitBoard', 100),
       (19, '네프네프네프네프', '그러니 우리 마지막까지 힘내서', '2023-04-28 18:41:40.000000', 2, 'RecruitBoard', 100),
       (20, '디자인띵킹 팀원 구해봅니다 [한명만 더!!]', '한 학기 잘 마무리해봐요 !!!', '2023-04-28 18:41:40.000000', 0, 'RecruitBoard', 100),
       (21, 'Ping Pong에 오신것을 환영합니다!',
        '<h2>안녕하세요! <strong>Ping Pong</strong>에 오신 것을 환영합니다!</h2><p><br></p><p><strong>Ping Pong</strong>은 한성대학교 컴퓨터 공학부 학생들을 위한 커뮤니티입니다. 컴퓨터 공학부는 대개 개인이 아닌 팀으로 결과물을 내야 하기에 소통과 협력이 매우 중요합니다. 저희 <strong>Cohesion </strong>팀은 컴퓨터 공학부 일원으로 있으면서 다수의 학생 분들이 적절한 소통 공간이 없어 학교 생활에 어려움을 겪고 있다는 것을 느꼈고 그 어려움을 해결하기 위해 <strong>Ping Pong</strong>을 개발하게 되었습니다.</p><p><br></p><h2><strong>Ping Pong</strong>은 크게 세 가지의 게시판 기능을 이용자 분들께 제공합니다.</h2><p><br></p><ul><li>자유 게시판: 자유롭게 자신의 생각, 지식 등을 학우들과 나눌 수 있는 공간입니다.</li><li>Q&amp;A 게시판: CS 지식, 코드 질문 등의 전공 관련 질문들을 게시하고 답변할 수 있는 공간입니다.</li><li>구인 게시판: 텀 프로젝트, 캡스톤 등 팀원을 모집해야 하는 경우 팀원들을 구인할 수 있는 공간입니다.</li></ul><p><br></p><p>이러한 기능들과 함께 저희 <strong>Cohesion </strong>팀은 다음과 같은 효과를 기대합니다.</p><p><br></p><ul><li>소통 부재 해소</li><li>질문하는 역량 증대</li><li>팀원 모집 용이</li></ul><p><br></p><h2><strong>Ping Pong</strong>에서 새로운 도전과 협력의 기회를 만들어 볼까요?</h2>',
        '2023-05-26 18:41:40.000000', 7, 'NoticeBoard', 102),
       (22, '공지사항 제목2', '공지사항 내용', '2023-05-26 18:41:40.000000', 7, 'NoticeBoard', 102),
       (23, '공지사항 제목3', '공지사항 내용', '2023-05-26 18:41:40.000000', 7, 'NoticeBoard', 102),
       (24, '공지사항 제목4', '공지사항 내용', '2023-05-26 18:41:40.000000', 7, 'NoticeBoard', 102),
       (25, '공지사항 제목5', '공지사항 내용', '2023-05-26 18:41:40.000000', 7, 'NoticeBoard', 102);

INSERT INTO free_board (id)
VALUES (1),
       (2),
       (3),
       (4),
       (5),
       (6),
       (7),
       (8),
       (9),
       (10);

INSERT INTO qna_board (id, language)
VALUES (11, 'Java'),
       (12, 'C'),
       (13, 'C'),
       (14, 'Java');


INSERT INTO recruit_board (id, required, optional, party, gathered, is_completed)
VALUES (15, '게임개발에 대한 열정이 넘치시는 분!', '유니티 사용 경험이 있으시다면 더욱 좋겠습니다!', 6, 3, false),
       (16, '프랩 A이신분', NULL, 3, 1, false),
       (17, '데베설 B분반', '깃허브 사용해본 경험 있으신 분', 4, 2, false),
       (18, '마스터소드를 뽑아보신 분', '신체 건강하고 해외여행결격사유가 없으신 분', 2, 1, false),
       (19, '네프 C반! 학점 4.0 이상!!', '2학년 자바프로젝트 보여주셔야합니다.', 2, 1, false),
       (20, '디띵 C분반!!!', NULL, 3, 1, false);

INSERT INTO notice_board (id)
VALUES (21),
       (22),
       (23),
       (24),
       (25);

-- FE에 정의된 "skillData"의 이름을 미리 DB에 저장
INSERT INTO skill (skill_id, name)
VALUES (1, 'JavaScript'),
       (2, 'C'),
       (3, 'Java'),
       (4, 'C++'),
       (5, 'Flutter'),
       (6, 'React');