INSERT INTO user (stu_id, name, nickname, point, career, introduce)
VALUES (100, 'USER1', 'NICK1', 0, '', ''),
       (101, 'USER2', 'NICK2', 0, '', '');

INSERT INTO free_article (free_article_id, title, content, bookmark_hits, hits, report, stu_id, created_at)
VALUES (100, 'Test title 1', 'Test content 1', 0, 0, 0, 100, '2023-02-08 18:41:40.000000'),
       (101, 'Test title 2', 'Test content 2', 0, 0, 0, 100, '2023-02-08 19:41:40.000000'),
       (102, 'Test title 3', 'Test content 3', 0, 0, 0, 101, '2023-02-08 15:41:40.000000'),
       (103, 'Test title 4', 'Test content 4', 0, 0, 0, 100, '2023-02-08 12:41:40.000000'),
       (104, 'Test title 5', 'Test content 5', 0, 0, 0, 101, '2023-02-08 14:41:40.000000'),
       (105, 'Test title 6', 'Test content 6', 0, 0, 0, 101, '2023-02-08 15:41:40.000000'),
       (106, 'Test title 7', 'Test content 7', 0, 0, 0, 101, '2023-02-08 13:41:40.000000'),
       (107, 'Test title 8', 'Test content 8', 0, 0, 0, 100, '2023-02-08 18:42:40.000000'),
       (108, 'Test title 9', 'Test content 9', 0, 0, 0, 101, '2023-02-08 18:46:40.000000'),
       (109, 'Test title 10', 'Test content 10', 0, 0, 0, 100, '2023-02-08 18:47:40.000000');

INSERT INTO qna_board (stu_id, title, content, tag, point, created_at) values
    (100, 'hi1', 'content', '#spring', 10, '2023-02-07 18:47:40.000000'),
    (100, 'hi2','content','#spring',15,'2023-02-08 12:48:40.000000'),
    (100, 'hi3','content','#spring',15,'2023-02-08 18:49:40.000000'),
    (100, 'hi4','content','#spring',15,'2023-02-08 22:59:40.000000');
