INSERT INTO user (stu_id, name, nickname, student_id, career, introduce)
VALUES (100, 'USER1', 'NICK1', '181234', '', ''),
       (101, 'USER2', 'NICK2', '185678', '', '');

INSERT INTO board (id, created_at, views, dtype, stu_id)
VALUES (1, '2023-04-28 18:41:40.000000', 0, 'FreeBoard', 100),
       (2, '2023-04-28 18:41:40.000000', 1, 'FreeBoard', 100),
       (3, '2023-04-28 18:41:40.000000', 2, 'FreeBoard', 101),
       (4, '2023-04-28 18:41:40.000000', 3, 'FreeBoard', 100),
       (5, '2023-04-28 18:41:40.000000', 4, 'FreeBoard', 101),
       (6, '2023-04-28 18:41:40.000000', 5, 'FreeBoard', 100),
       (7, '2023-04-28 18:41:40.000000', 6, 'FreeBoard', 100),
       (8, '2023-04-28 18:41:40.000000', 0, 'FreeBoard', 101),
       (9, '2023-04-28 18:41:40.000000', 4, 'FreeBoard', 101),
       (10, '2023-04-28 18:41:40.000000', 2, 'FreeBoard', 101),
       (11, '2023-04-28 18:41:40.000000', 0, 'QnaBoard', 100),
       (12, '2023-04-28 18:41:40.000000', 0, 'QnaBoard', 101),
       (13, '2023-04-28 18:41:40.000000', 7, 'QnaBoard', 100),
       (14, '2023-04-28 18:41:40.000000', 0, 'QnaBoard', 100),
       (15, '2023-04-28 18:41:40.000000', 8, 'RecruitBoard', 101),
       (16, '2023-04-28 18:41:40.000000', 9, 'RecruitBoard', 101),
       (17, '2023-04-28 18:41:40.000000', 10, 'RecruitBoard', 100),
       (18, '2023-04-28 18:41:40.000000', 0, 'RecruitBoard', 100),
       (19, '2023-04-28 18:41:40.000000', 2, 'RecruitBoard', 100),
       (20, '2023-04-28 18:41:40.000000', 0, 'RecruitBoard', 100);

INSERT INTO free_board (id, title, content, stu_id)
VALUES (1, 'Test title 1', 'Test content 1', 100),
       (2, 'Test title 2', 'Test content 2', 100),
       (3, 'Test title 3', 'Test content 3', 101),
       (4, 'Test title 4', 'Test content 4', 100),
       (5, 'Test title 5', 'Test content 5', 101),
       (6, 'Test title 6', 'Test content 6', 101),
       (7, 'Test title 7', 'Test content 7', 101),
       (8, 'Test title 8', 'Test content 8', 100),
       (9, 'Test title 9', 'Test content 9', 101),
       (10, 'Test title 10', 'Test content 10', 100);

INSERT INTO qna_board (id, title, content, tag, language, stu_id)
VALUES (11, 'hi1', 'content', '#spring', 'java', 101),
       (12, 'hi2', 'content', '#spring', 'c', 101),
       (13, 'hi3', 'content', '#spring', 'c', 101),
       (14, 'hi4', 'content', '#spring', 'java', 101);

INSERT INTO recruit_board (id, title, content, stu_id, required,
                           optional, party, gathered, is_completed)
VALUES (15, '게임 개발 공모전 같이 나가실 분을 구합니다.', '쉽지 않은 인생입니다.', 100, '게임개발에 대한 열정이 넘치시는 분!',
        '유니티 사용 경험이 있으시다면 더욱 좋겠습니다!', 6, 3, false),
       (16, '캡스톤 팀원 구합니다아아앙 [한명만 더!!]', '하나하나 차근차근', 100, '프랩 A이신분', NULL, 3, 1, false),
       (17, '10학번이랑 팀플 같이 할 사람', '해내다 보면 언젠가는', 100, '데베설 B분반', '깃허브 사용해본 경험 있으신 분', 4, 2, false),
       (18, '재앙 가논 잡으러 갈 하이랄의 용사를 구합니다', '성취할 날이 올겁니다.', 101, '마스터소드를 뽑아보신 분',
        '신체 건강하고 해외여행결격사유가 없으신 분', 2, 1, false),
       (19, '네프네프네프네프', '그러니 우리 마지막까지 힘내서', 100, '네프 C반! 학점 4.0 이상!!', '2학년 자바프로젝트 보여주셔야합니다.', 2,
        1, false),
       (20, '디자인띵킹 팀원 구해봅니다 [한명만 더!!]', '한 학기 잘 마무리해봐요 !!!', 101, '디띵 C분반!!!', NULL, 3,
        1, false);

-- FE에 정의된 "skillData"의 이름을 미리 DB에 저장
INSERT INTO skill (skill_id, name)
VALUES (1, 'JavaScript'),
       (2, 'C'),
       (3, 'JAVA'),
       (4, 'C++'),
       (5, 'Flutter'),
       (6, 'React');