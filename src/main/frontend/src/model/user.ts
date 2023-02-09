import {Board, Reply} from "./board";
//-------------INTERFACE-------------

export enum Track {
    mobile,
    web,
    bigData,
    digitalContent

}zz
export interface User {
    studentId: String;
    password: String;
    name: String;
    nickname: String;
    profileImg?: String;
    point: number;
    track: Array<Track>; //todo(은서):여기 track1, track2로 나눌지 그냥 배열로 할지 고민
    gitHubAccount: String;

    comment: Array<Reply>;
    board: Array<Board>;
    bookmark: Array<Board>;

    dailySummary: Array<Board>;
    selfIntroduction: String;
}