import { BoardType } from "./board";
import { User } from "./user";
import { Reply } from "./reply";

export interface Posting {
  type: BoardType;
  uuid: String;
  title: String;
  article: String;
  imgUrl?: Array<String>;
  writer: User;
  createdDate: String;
  modifiedDate?: String;
  bookmarks: number;
  replies: Array<Reply>;
  reports: number;
  condition?: String; //구인게시판 조건
  points?: number; //Q&A 포인트
  //todo(은서): 게시판 성격따라서 다른 옵션 추가되어 질 수 있음
}
