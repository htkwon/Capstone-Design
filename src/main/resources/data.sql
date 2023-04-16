INSERT INTO user (stu_id, name, nickname, point, student_id, career, introduce)
VALUES (100, 'USER1', 'NICK1', 0, '181234', '', ''),
       (101, 'USER2', 'NICK2', 0, '185678', '', '');

INSERT INTO free_board (free_board_id, title, content, stu_id, created_at)
VALUES (100, 'Test title 1', 'Test content 1', 100, '2023-02-07 18:41:40.000000'),
       (101, 'Test title 2', 'Test content 2', 100, '2023-02-07 19:41:40.000000'),
       (102, 'Test title 3', 'Test content 3', 101, '2023-02-08 15:41:40.000000'),
       (103, 'Test title 4', 'Test content 4', 100, '2023-02-08 16:41:40.000000'),
       (104, 'Test title 5', 'Test content 5', 101, '2023-02-08 19:41:40.000000'),
       (105, 'Test title 6', 'Test content 6', 101, '2023-02-09 15:41:40.000000'),
       (106, 'Test title 7', 'Test content 7', 101, '2023-02-09 16:41:40.000000'),
       (107, 'Test title 8', 'Test content 8', 100, '2023-02-09 17:42:40.000000'),
       (108, 'Test title 9', 'Test content 9', 101, '2023-02-10 18:46:40.000000'),
       (109, 'Test title 10', 'Test content 10', 100, '2023-02-10 19:47:40.000000');

INSERT INTO qna_board (stu_id, title, content, tag, point, created_at, language)
VALUES (100, 'hi1', 'content', '#spring', 10, '2023-02-07 18:47:40.000000', 'java'),
       (100, 'hi2', 'content', '#spring', 15, '2023-02-08 12:48:40.000000', 'c'),
       (100, 'hi3', 'content', '#spring', 15, '2023-02-08 18:49:40.000000', 'c'),
       (100, 'hi4', 'content', '#spring', 15, '2023-02-08 22:59:40.000000', 'java');

INSERT INTO recruit_board (recruit_board_id, title, content, created_at, modified_at, bookmarks, stu_id, required,
                           optional, party, gathered)
VALUES (1, '게임 개발 공모전 같이 나가실 분을 구합니다.', '쉽지 않은 인생입니다.', '2023-03-18', NULL, 1, 100, '게임개발에 대한 열정이 넘치시는 분!',
        '유니티 사용 경험이 있으시다면 더욱 좋겠습니다!', 6, 3),
       (2, '캡스톤 팀원 구합니다아아앙 [한명만 더!!]', '하나하나 차근차근', '2023-03-14', '2023-03-20', 0, 100, '프랩 A이신분', NULL, 3, 1),
       (3, '10학번이랑 팀플 같이 할 사람', '해내다 보면 언젠가는', '2023-03-14', NULL, 1, 100, '데베설 B분반', '깃허브 사용해본 경험 있으신 분', 4, 2),
       (4, '재앙 가논 잡으러 갈 하이랄의 용사를 구합니다', '성취할 날이 올겁니다.', '2023-03-22', NULL, 0, 101, '마스터소드를 뽑아보신 분',
        '신체 건강하고 해외여행결격사유가 없으신 분', 2, 1),
       (5, '네프네프네프네프', '그러니 우리 마지막까지 힘내서', '2022-02-22', NULL, 7, 100, '네프 C반! 학점 4.0 이상!!', '2학년 자바프로젝트 보여주셔야합니다.', 2,
        1),
       (6, '디자인띵킹 팀원 구해봅니다 [한명만 더!!]', '한 학기 잘 마무리해봐요 !!!', '2023-03-10', '2023-03-11', 0, 101, '디띵 C분반!!!', NULL, 3,
        1);