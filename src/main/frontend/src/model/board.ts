//-------------INTERFACE-------------

/*
 * 게시판 유형 (추후 변경 될 수 있음)
 */
export enum BoardType {
  free,
  question,
  recruit,
  notice,
  summary //todo(은서): 마이페이지 내 공부요약글 추후 따로 빠질 수 있음
}

export interface Board {
  type: BoardType;
  id: string;
  title: String;
  article: String;
  code?: String; //Q&A 게시판에서 사용
  imgUrl?: Array<String>;
  /*
  nickname: String; //게시글 작성자의 nickname
  profileImg?: String; //게시글 작성자의 프로필사진
  studentId: String; //게시글 작성자의 학번
  */
  writer: User; //게시글에 작성자 정보만 들어가면 되는거라 User로 바꿈.
  createdDate: String;
  modifiedDate: String;
  bookmarks: number;
  replies: Array<Reply>;
  reports: number;
  condition?: String; //구인게시판 조건
  points? : number; //Q&A 포인트
  //todo(은서): 게시판 성격따라서 다른 옵션 추가되어 질 수 있음
}

export interface Reply {
  id: String;
  nickname: String;
  article: String;
  createdDate: String;
  modifiedDate: String;
  reports: number;
  isChosen?: boolean;
  isParent?: boolean;
}
//-------------INTERFACE-------------

export enum Track {
  mobile,
  web,
  bigData,
  digitalContent,
  none, //1학년은 트랙 선택 아직 안되었기에 none
}
export interface User {
  studentId: String;
  password: String;
  name: String;
  nickname: String;
  profileImg?: String;
  point: number;
  track1: Track;
  track2: Track;
  showOff?: Array<String>;

  reply: Array<Reply>;
  board: Array<Board>;
  bookmark: Array<Board>;

  dailySummary: Array<Board>;
  selfIntroduction: String;
}
