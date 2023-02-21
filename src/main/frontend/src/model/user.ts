import { Board } from "./board";
import { Reply } from "./reply";

export enum Track {
  mobile,
  web,
  bigData,
  digitalContent,
  none //1학년은 트랙 선택 아직 안되었기에 none
}
export interface User {
  studentId: String;
  password: String;
  name: String;
  nickname: String;
  profileImg: String;
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